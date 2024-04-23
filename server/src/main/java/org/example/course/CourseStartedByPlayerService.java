package org.example.course;

import com.example.models.courses.CourseStartedByPlayer;
import com.example.models.courses.CourseStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.repositoryes.course.CourseStartedByPlayerRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

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
        log.info("Course has been updated!");
    }

    public CourseStartedByPlayer getCourseStartedByPlayerAfterPlayerIdAndCourseNameAndBoardId(Integer playerId, String courseName, Integer boardId) throws Exception {
        Optional<CourseStartedByPlayer> courseStartedByPlayer = courseStartedByPlayerRepository.findByPlayerIdAndCourseNameAndBoardId(playerId, courseName, boardId);
        if (courseStartedByPlayer.isEmpty()) {
            throw new Exception("The course does bot exist!");
        }
        return courseStartedByPlayer.get();
    }
    public CourseStartedByPlayer getCourseStartedByPlayerAfterPlayerIdAndCourseName(Integer playerId, String courseName) throws Exception {
        Optional<CourseStartedByPlayer> courseStartedByPlayer = courseStartedByPlayerRepository.findByPlayerIdAndCourseName(playerId, courseName);
        if (courseStartedByPlayer.isEmpty()) {
            throw new Exception("The course does bot exist!");
        }
        return courseStartedByPlayer.get();
    }

    public List<CourseStartedByPlayer> getAllCoursesByCourseStatus(CourseStatus courseStatus, int playerId) throws Exception {
        Optional<List<CourseStartedByPlayer>> coursesByPlayer = courseStartedByPlayerRepository.findByCourseStatus(courseStatus, playerId);
        if (coursesByPlayer.isEmpty()) {
            throw new Exception("There are no courses with that status for this player!");
        }
        return coursesByPlayer.get();
    }
    @Transactional
    public void update(CourseStartedByPlayer courseStartedByPlayer) {
//        courseStartedByPlayerRepository.updateCourseStartedByPlayer(courseStartedByPlayer.getId(), courseStartedByPlayer.getMoveNumber());
        courseStartedByPlayer.setMoveNumber(courseStartedByPlayer.getMoveNumber());
        courseStartedByPlayer.setCourseStatus(courseStartedByPlayer.getCourseStatus());
        courseStartedByPlayer.setWhitesTurn(courseStartedByPlayer.isWhitesTurn());
    }

}
