package org.example.repositoryes.interfaces;

import com.example.models.courses.CourseStartedByPlayer;
import com.example.models.courses.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseStartedByPlayerRepository extends JpaRepository<CourseStartedByPlayer, Integer> {
    @Query("UPDATE CourseStartedByPlayer set courseStatus =: courseStatus where id =: id")
    Optional<CourseStartedByPlayer> updateCourseStartedByPlayer(Integer id, CourseStatus courseStatus);
}
