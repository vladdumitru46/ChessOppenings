package org.example;

import com.example.models.courses.Course;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.repositoryes.interfaces.CourseRepository;

import java.util.Optional;

@org.springframework.stereotype.Service("courseService")
@AllArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;

    public Optional<Course> findCourseById(Integer id) {
        log.info("return course with id {}", id);
        return courseRepository.findById(id);
    }

    public Optional<Course> findCourseByName(String name) {
        log.info("getting course by name!");
        return courseRepository.findCourseByName(name);
    }

}
