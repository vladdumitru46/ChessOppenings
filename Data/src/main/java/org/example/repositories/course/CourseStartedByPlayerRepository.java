package org.example.repositories.course;

import com.example.models.courses.CourseStartedByPlayer;
import com.example.models.courses.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseStartedByPlayerRepository extends JpaRepository<CourseStartedByPlayer, Integer> {
    //    @Transactional
//    @Modifying
//    @Query("UPDATE CourseStartedByPlayer set move_number = :moveNumber where id = :id")
//    void updateCourseStartedByPlayer(Integer id, Integer moveNumber);
    @Query("SELECT c FROM CourseStartedByPlayer c WHERE c.playerId.id = :playerId AND c.courseName.name = :courseName AND c.boardId.id = :boardId")
    Optional<CourseStartedByPlayer> findByPlayerIdAndCourseNameAndBoardId(Integer playerId, String courseName, Integer boardId);


    @Query("SELECT c FROM CourseStartedByPlayer c WHERE c.playerId.id = :playerId AND c.courseName.name = :courseName")
    Optional<CourseStartedByPlayer> findByPlayerIdAndCourseName(Integer playerId, String courseName);

    @Query("SELECT c FROM CourseStartedByPlayer c WHERE c.courseStatus = :courseStatus AND c.playerId.id = :playerId")
    Optional<List<CourseStartedByPlayer>> findByCourseStatus(CourseStatus courseStatus, int playerId);

}
