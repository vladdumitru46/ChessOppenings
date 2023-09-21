package org.example;

import com.example.models.courses.Course;
import com.example.models.courses.CourseStartedByPlayer;
import com.example.models.courses.Player;
import org.example.repositoryes.interfaces.CourseRepository;
import org.example.repositoryes.interfaces.CourseStartedByPlayerRepository;
import org.example.repositoryes.interfaces.PlayerRepository;

import java.util.Optional;

//TODO loggers
@org.springframework.stereotype.Service("courseService")
public class CourseService implements IService {

    private final PlayerRepository playerRepository;
    private final CourseRepository courseRepository;
    private final CourseStartedByPlayerRepository courseStartedByPlayerRepository;

    public CourseService(PlayerRepository playerRepository, CourseRepository courseRepository, CourseStartedByPlayerRepository courseStartedByPlayerRepository) {
        this.playerRepository = playerRepository;
        this.courseRepository = courseRepository;
        this.courseStartedByPlayerRepository = courseStartedByPlayerRepository;
    }

    public void savePlayer(Player player) throws Exception {
        Optional<Player> playerByEmail = playerRepository.findByEmail(player.getEmail());
        if (playerByEmail.isPresent()) {
            throw new Exception("The email exists already! Choose other email!\n");
        }
        Optional<Player> playerByUsername = playerRepository.findByUserName(player.getUserName());
        if (playerByUsername.isPresent()) {
            throw new Exception("The userName is taken! Please choose other userName!\n");
        }
        playerRepository.save(player);
    }

    public Optional<Player> searchPlayerByEmailAndPassword(String email, String password) throws Exception {
        Optional<Player> player = playerRepository.findByEmailAndPassword(email, password);
        if (player.isEmpty()) {
            throw new Exception("Email or password is invalid!\n");
        }
        return player;
    }

    public Optional<Course> findCourseById(Integer id) {
        return courseRepository.findById(id);
    }

    public void savePlayerThatStartedACourse(CourseStartedByPlayer courseStartedByPlayer) {
        courseStartedByPlayerRepository.save(courseStartedByPlayer);
    }

    public Optional<Player> searchPlayerById(Integer id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> searchPlayerByUsername(String userName) {
        return playerRepository.findByUserName(userName);
    }
}
