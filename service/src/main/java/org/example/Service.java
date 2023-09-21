package org.example;

import com.example.models.courses.Player;
import org.example.repositoryes.interfaces.CourseRepository;
import org.example.repositoryes.interfaces.CourseStartedByPlayerRepository;
import org.example.repositoryes.interfaces.PlayerRepository;

import java.util.Optional;

@org.springframework.stereotype.Service
public class Service implements IService {

    private final PlayerRepository playerRepository;
    private final CourseRepository courseRepository;
    private final CourseStartedByPlayerRepository courseStartedByPlayerRepository;

    public Service(PlayerRepository playerRepository, CourseRepository courseRepository, CourseStartedByPlayerRepository courseStartedByPlayerRepository) {
        this.playerRepository = playerRepository;
        this.courseRepository = courseRepository;
        this.courseStartedByPlayerRepository = courseStartedByPlayerRepository;
    }

    public void savePlayer(Player player) throws Exception {
        Optional<Player> playerByEmail = playerRepository.findByEmail(player.getEmail());
        if (playerByEmail.isPresent()) {
            throw new Exception("The email exists already! Choose other email");
        }
        playerRepository.save(player);
    }
}
