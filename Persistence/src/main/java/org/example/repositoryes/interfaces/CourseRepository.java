package org.example.repositoryes.interfaces;

import com.example.models.courses.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("SELECT c FROM Course c WHERE c.name = ?1")
    Optional<Course> findCourseByName(String name);
}
