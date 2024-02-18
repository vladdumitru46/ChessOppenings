package org.example.repositoryes.interfaces.course;

import com.example.models.courses.CourseStartedByPlayer;
import com.example.models.courses.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CourseStartedByPlayerRepository extends JpaRepository<CourseStartedByPlayer, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE CourseStartedByPlayer set move_number = :courseStatus where id = :id")
    void updateCourseStartedByPlayer(Integer id, Integer courseStatus);

    @Query("SELECT c FROM CourseStartedByPlayer c WHERE player_id = :playerId AND course_name = :courseName AND board_id = :boardId")
    Optional<CourseStartedByPlayer> findByPlayerIdAndCourseName(Integer playerId, String courseName, Integer boardId);


}
