package org.example.player;

import com.example.models.player.Player;
import com.example.models.player.registration.ConfirmationToken;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.PlayerValidator;
import org.example.exceptions.PlayerValidationException;
import org.example.player.email.EmailService;
import org.example.repositoryes.player.PlayerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service("playerService")
@Slf4j
@AllArgsConstructor
public class PlayerService implements UserDetailsService {

    private final PlayerRepository playerRepository;

    private final PlayerValidator playerValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailSender;

    public String savePlayer(Player player, String from) throws PlayerValidationException, MessagingException {
        log.info("Save player");
        Optional<Player> playerByEmail = playerRepository.findByEmail(player.getEmail());
        if (playerByEmail.isPresent()) {
            log.warn("The email exists already! Choose other email! " + player.getEmail());
            throw new PlayerValidationException("The email exists already! Choose other email!\n");
        }
        Optional<Player> playerByUsername = playerRepository.findByUserName(player.getUserName());
        if (playerByUsername.isPresent()) {
            log.warn("The userName is taken! Please choose other userName! " + player.getUserName());
            throw new PlayerValidationException("The userName is taken! Please choose other userName!\n");
        }

        try {
            playerValidator.validatePlayer(player);
        } catch (PlayerValidationException e) {
            throw new PlayerValidationException(e.getMessage());
        }

        BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
        String cryptPassword = cryptPasswordEncoder.encode(player.getPassword());
        player.setPassword(cryptPassword);
        player.setLocked(true);
        playerRepository.save(player);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), player);

        confirmationTokenService.saveToken(confirmationToken);
        log.info("player saved");
        String link = "http://localhost:8080/chess/register/confirmToken?token=" + token;
        emailSender.send(player.getEmail(), buildEmail(player.getName(), link), from);
        return token;
    }

    public Player searchPlayerByEmailAndPassword(String email, String password) throws Exception {
        log.info("search player by email and password");
        Optional<Player> player = playerRepository.findByEmailAndPassword(email, password);
        if (player.isEmpty()) {
            log.warn("player with email {} and password {} does not exist", email, password);
            throw new Exception("Email or password is invalid!\n");
        }
        log.info("player with username {} returned", player.get().getUserName());
        return player.get();
    }

    public Player searchPlayerById(Integer id) throws Exception {
        log.info("search player by id {}", id);
        Optional<Player> player = playerRepository.findById(id);
        if (player.isEmpty()) {
            throw new Exception("A player with that id does not exist!");
        }
        return player.get();
    }

    public Player searchPlayerByUsernameOrEmail(String emailOrUserName) throws Exception {
        log.info("search player by username {}", emailOrUserName);
        Optional<Player> player = playerRepository.findByUserName(emailOrUserName);
        if (player.isEmpty()) {
            player = playerRepository.findByEmail(emailOrUserName);
            if (player.isEmpty()) {
                throw new Exception("There is no player with that userName!");
            }
        }
        return player.get();
    }

    public void logIn(String emailOrUsername, String password) throws Exception {
        Optional<Player> playerOptional = playerRepository.findByEmail(emailOrUsername);
        BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
        if (playerOptional.isEmpty()) {
            playerOptional = playerRepository.findByUserName(emailOrUsername);
            if (playerOptional.isEmpty()) {
                throw new PlayerValidationException("email does not exist");
            }
        }
        if (!cryptPasswordEncoder.matches(password, playerOptional.get().getPassword())) {
            throw new IllegalAccessException("password is invalid!");
        }
        if (playerOptional.get().isLocked()) {
            throw new PlayerValidationException("Account is not yet verified!");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Transactional
    public String confirmToken(String token) throws Exception {
        ConfirmationToken confirmationToken;
        try {
            confirmationToken = confirmationTokenService.confirmToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (confirmationToken.getConfirmedAt() != null) {
            throw new Exception("token is already confirmed!");
        }
        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new Exception("token is expired!");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
//        confirmationTokenService.updateToken(confirmationToken);

        Optional<Player> player = playerRepository.findByEmail(confirmationToken.getPlayer().getEmail());

        //            playerRepository.update(player.get().getId(), player.get().isLocked());
        player.ifPresent(value -> value.setLocked(false));
        return "The email has been confirmed!\nPlease go back and try to logIn in the application!";
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }


    public void checkPlayer(Player player, boolean changedEmail) throws PlayerValidationException {
        if(changedEmail) {
            Optional<Player> playerByEmail = playerRepository.findByEmail(player.getEmail());
            if (playerByEmail.isPresent()) {
                log.warn("The email exists already! Choose other email! " + player.getEmail());
                throw new PlayerValidationException("The email exists already! Choose other email!\n");
            }
        }
        try {
            playerValidator.validatePlayer(player);
        } catch (PlayerValidationException e) {
            throw new PlayerValidationException(e.getMessage());
        }

        BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
        String cryptPassword = cryptPasswordEncoder.encode(player.getPassword());
        player.setPassword(cryptPassword);
    }

    public void sendEmail(Player player, String from) throws MessagingException {
        player.setLocked(true);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), player);

        confirmationTokenService.saveToken(confirmationToken);
        log.info("player saved");
        String link = "http://localhost:8080/chess/register/confirmToken?token=" + token;
        emailSender.send(player.getEmail(), buildEmail(player.getName(), link), from);
    }

    @Transactional
    public void updatePlayer(Player player) {
        player.setName(player.getName());
        player.setEmail(player.getEmail());
        player.setPassword(player.getPassword());
        player.setLocked(player.isLocked());
    }
}
