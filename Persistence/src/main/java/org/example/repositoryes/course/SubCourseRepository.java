package org.example.repositoryes.course;

import com.example.models.courses.SubCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCourseRepository extends JpaRepository<SubCourse, Integer> {

    @Query("SELECT c FROM SubCourse c WHERE c.courseName.name = :courseName")
    Optional<List<SubCourse>> findByCourseName(@Param("courseName") String courseName);

    @Query("SELECT c FROM SubCourse c WHERE c.name = :name AND c.courseName.name = :courseName")
    Optional<SubCourse> finByNameAndCourseName(String name, String courseName);
}
