package org.example;

import com.example.models.courses.CourseStartedByPlayer;
import com.example.models.courses.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.repositoryes.interfaces.CourseStartedByPlayerRepository;
import org.springframework.stereotype.Service;

@Service("courseStartedByPlayerService")
@AllArgsConstructor
@Slf4j
public class CourseStartedByPlayerService {
    private final CourseStartedByPlayerRepository courseStartedByPlayerRepository;

    public void addPlayerThatStartedTheCourse(CourseStartedByPlayer courseStartedByPlayer) {
        log.info("adding a player that started a course...");
        courseStartedByPlayer.setCourseStatus(CourseStatus.InPROGRESS);
        courseStartedByPlayerRepository.save(courseStartedByPlayer);
        log.info("Player added");
    }

    public void finishCourse(CourseStartedByPlayer courseStartedByPlayer) {
        log.info("player has finished the course");
        courseStartedByPlayer.setCourseStatus(CourseStatus.COMPLETED);
        courseStartedByPlayerRepository.updateCourseStartedByPlayer(courseStartedByPlayer.getPlayerId(), courseStartedByPlayer.getCourseStatus());
        log.info("Course has been updated!");
    }

}
