package org.example.course;

import com.example.models.courses.Course;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.CourseNotFoundException;
import org.example.repositories.course.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("courseService")
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Optional<Course> findCourseById(Integer id) {
        return courseRepository.findById(id);
    }

    public Course findCourseByName(String name) throws CourseNotFoundException {
        return courseRepository.findCourseByName(name)
                .orElseThrow(()->new CourseNotFoundException("Course does not exist"));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }


    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void addAll(List<Course> courses) {
        courseRepository.saveAll(courses);
    }

    @Transactional
    public void updateCourse(Course course) {
        course.setForWhite(course.isForWhite());
    }
}
