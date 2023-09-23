package org.example;

import com.example.models.courses.Course;
import com.example.models.courses.CourseStartedByPlayer;
import com.example.models.courses.Player;
import org.example.repositoryes.interfaces.CourseRepository;
import org.example.repositoryes.interfaces.CourseStartedByPlayerRepository;
import org.example.repositoryes.interfaces.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@org.springframework.stereotype.Service("courseService")
public class CourseService {

    Logger logger = LoggerFactory.getLogger(CourseService.class);
    private final PlayerRepository playerRepository;
    private final CourseRepository courseRepository;
    private final CourseStartedByPlayerRepository courseStartedByPlayerRepository;

    public CourseService(PlayerRepository playerRepository, CourseRepository courseRepository, CourseStartedByPlayerRepository courseStartedByPlayerRepository) {
        this.playerRepository = playerRepository;
        this.courseRepository = courseRepository;
        this.courseStartedByPlayerRepository = courseStartedByPlayerRepository;
    }

    public void savePlayer(Player player) throws Exception {
        logger.info("Save player");
        Optional<Player> playerByEmail = playerRepository.findByEmail(player.getEmail());
        if (playerByEmail.isPresent()) {
            logger.warn("The email exists already! Choose other email! " + player.getEmail());
            throw new Exception("The email exists already! Choose other email!\n");
        }
        Optional<Player> playerByUsername = playerRepository.findByUserName(player.getUserName());
        if (playerByUsername.isPresent()) {
            logger.warn("The userName is taken! Please choose other userName! " + player.getUserName());
            throw new Exception("The userName is taken! Please choose other userName!\n");
        }
        playerRepository.save(player);
        logger.info("player saved");
    }

    public Optional<Player> searchPlayerByEmailAndPassword(String email, String password) throws Exception {
        logger.info("search player by email and password");
        Optional<Player> player = playerRepository.findByEmailAndPassword(email, password);
        if (player.isEmpty()) {
            logger.warn("player with email {} and password {} does not exist", email, password);
            throw new Exception("Email or password is invalid!\n");
        }
        logger.info("player with username {} returned", player.get().getUserName());
        return player;
    }

    public Optional<Course> findCourseById(Integer id) {
        logger.info("return course with id {}", id);
        return courseRepository.findById(id);
    }

    public void savePlayerThatStartedACourse(CourseStartedByPlayer courseStartedByPlayer) {
        logger.info("save player that started a course");
        courseStartedByPlayerRepository.save(courseStartedByPlayer);
    }

    public Optional<Player> searchPlayerById(Integer id) {
        logger.info("search player by id {}", id);
        return playerRepository.findById(id);
    }

    public Optional<Player> searchPlayerByUsername(String userName) {
        logger.info("search player by username {}", userName);
        return playerRepository.findByUserName(userName);
    }


}
