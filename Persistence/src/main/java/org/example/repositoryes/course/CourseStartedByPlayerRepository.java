package org.example.repositoryes.course;

import com.example.models.courses.CourseStartedByPlayer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseStartedByPlayerRepository extends JpaRepository<CourseStartedByPlayer, Integer> {
    //    @Transactional
//    @Modifying
//    @Query("UPDATE CourseStartedByPlayer set move_number = :moveNumber where id = :id")
//    void updateCourseStartedByPlayer(Integer id, Integer moveNumber);
    @Query("SELECT c FROM CourseStartedByPlayer c WHERE c.playerId.id = :playerId AND c.courseName.name = :courseName AND c.boardId.id = :boardId")
    Optional<CourseStartedByPlayer> findByPlayerIdAndCourseName(Integer playerId, String courseName, Integer boardId);


}
