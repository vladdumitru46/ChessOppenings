package org.example.course;

import com.example.models.courses.CourseStartedByPlayer;
import com.example.models.courses.CourseStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.CourseStartedByPlayerNotFoundException;
import org.example.repositories.course.CourseStartedByPlayerRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service("courseStartedByPlayerService")
@AllArgsConstructor
public class CourseStartedByPlayerService {
    private final CourseStartedByPlayerRepository courseStartedByPlayerRepository;

    public void addPlayerThatStartedTheCourse(CourseStartedByPlayer courseStartedByPlayer) {
        courseStartedByPlayer.setCourseStatus(CourseStatus.InPROGRESS);
        courseStartedByPlayerRepository.save(courseStartedByPlayer);
    }

    public void finishCourse(CourseStartedByPlayer courseStartedByPlayer) {
        courseStartedByPlayer.setCourseStatus(CourseStatus.COMPLETED);
    }

    public CourseStartedByPlayer getCourseStartedByPlayerAfterPlayerIdAndCourseNameAndBoardId(Integer playerId, String courseName, Integer boardId)
            throws CourseStartedByPlayerNotFoundException {
        return courseStartedByPlayerRepository.findByPlayerIdAndCourseNameAndBoardId(playerId, courseName, boardId)
                .orElseThrow(() -> new CourseStartedByPlayerNotFoundException("The course does not exist!"));
    }

    public CourseStartedByPlayer getCourseStartedByPlayerAfterPlayerIdAndCourseName(Integer playerId, String courseName)
            throws CourseStartedByPlayerNotFoundException {
        return courseStartedByPlayerRepository.findByPlayerIdAndCourseName(playerId, courseName)
                .orElseThrow(() -> new CourseStartedByPlayerNotFoundException("The course does not exist!"));
    }

    public List<CourseStartedByPlayer> getAllCoursesByCourseStatus(CourseStatus courseStatus, int playerId) throws CourseStartedByPlayerNotFoundException {
        return courseStartedByPlayerRepository.findByCourseStatus(courseStatus, playerId)
                .orElseThrow(() -> new CourseStartedByPlayerNotFoundException("There are no courses with that status for this player!"));
    }

    @Transactional
    public void update(CourseStartedByPlayer courseStartedByPlayer) {
        courseStartedByPlayer.setMoveNumber(courseStartedByPlayer.getMoveNumber());
        courseStartedByPlayer.setCourseStatus(courseStartedByPlayer.getCourseStatus());
        courseStartedByPlayer.setWhitesTurn(courseStartedByPlayer.isWhitesTurn());
    }

    public void deleteAll() {
        courseStartedByPlayerRepository.deleteAll();
    }
}
