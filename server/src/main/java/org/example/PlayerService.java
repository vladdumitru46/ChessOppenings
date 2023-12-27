package org.example;

import com.example.models.courses.Player;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.repositoryes.interfaces.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("playerService")
@AllArgsConstructor
@Slf4j
public class PlayerService implements IService {

    private final PlayerRepository playerRepository;


    public void savePlayer(Player player) throws Exception {
        log.info("Save player");
        Optional<Player> playerByEmail = playerRepository.findByEmail(player.getEmail());
        if (playerByEmail.isPresent()) {
            log.warn("The email exists already! Choose other email! " + player.getEmail());
            throw new Exception("The email exists already! Choose other email!\n");
        }
        Optional<Player> playerByUsername = playerRepository.findByUserName(player.getUserName());
        if (playerByUsername.isPresent()) {
            log.warn("The userName is taken! Please choose other userName! " + player.getUserName());
            throw new Exception("The userName is taken! Please choose other userName!\n");
        }
        playerRepository.save(player);
        log.info("player saved");
    }

    public Optional<Player> searchPlayerByEmailAndPassword(String email, String password) throws Exception {
        log.info("search player by email and password");
        Optional<Player> player = playerRepository.findByEmailAndPassword(email, password);
        if (player.isEmpty()) {
            log.warn("player with email {} and password {} does not exist", email, password);
            throw new Exception("Email or password is invalid!\n");
        }
        log.info("player with username {} returned", player.get().getUserName());
        return player;
    }

    public Optional<Player> searchPlayerById(Integer id) {
        log.info("search player by id {}", id);
        return playerRepository.findById(id);
    }

    public Optional<Player> searchPlayerByUsername(String userName) {
        log.info("search player by username {}", userName);
        return playerRepository.findByUserName(userName);
    }

    @Override
    public void logIn(Player player, IServiceObserver client) throws Exception {
        
    }

    @Override
    public void logOut(Player player, IServiceObserver client) throws Exception {

    }
}
