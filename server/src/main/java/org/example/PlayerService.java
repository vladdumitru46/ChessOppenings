package org.example;

import com.example.models.player.Player;
import com.example.models.player.registration.ConfirmationToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.PlayerValidationException;
import org.example.repositoryes.interfaces.PlayerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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


    public String savePlayer(Player player) throws PlayerValidationException {
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
        playerRepository.save(player);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), player);

        confirmationTokenService.saveToken(confirmationToken);
        log.info("player saved");
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

    public Optional<Player> searchPlayerById(Integer id) {
        log.info("search player by id {}", id);
        return playerRepository.findById(id);
    }

    public Optional<Player> searchPlayerByUsername(String userName) {
        log.info("search player by username {}", userName);
        return playerRepository.findByUserName(userName);
    }

    public void logIn(String emailOrUsername, String password) throws Exception {
        Optional<Player> playerOptional = playerRepository.findByEmail(emailOrUsername);
        BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
        if (playerOptional.isEmpty()) {
            playerOptional = playerRepository.findByUserName(emailOrUsername);
            if (playerOptional.isEmpty() || !cryptPasswordEncoder.matches(password, playerOptional.get().getPassword())) {
                throw new IllegalAccessException("email or password are invalid!");
            }
            if (playerOptional.get().isLocked()) {
                throw new PlayerValidationException("Account is not yet verified!");
            }
        }
    }

    public void logOut(Player player, IServiceObserver client) throws Exception {

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
        confirmationTokenService.updateToken(confirmationToken);

        Optional<Player> player = playerRepository.findByEmail(confirmationToken.getPlayer().getEmail());

        if (player.isPresent()) {
            player.get().setLocked(false);
            playerRepository.update(player.get().getId(), player.get().isLocked());
        }
        return "ok!";
    }
}
