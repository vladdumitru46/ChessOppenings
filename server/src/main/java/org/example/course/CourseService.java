package org.example.course;

import com.example.models.courses.Course;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.CourseNotFoundException;
import org.example.repositoryes.course.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("courseService")
@AllArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;

    public Optional<Course> findCourseById(Integer id) {
        log.info("return course with id {}", id);
        return courseRepository.findById(id);
    }

    public Course findCourseByName(String name) throws CourseNotFoundException {
        log.info("getting course by name!");
        Optional<Course> course =  courseRepository.findCourseByName(name);
        if(course.isEmpty()){
            throw new CourseNotFoundException("Course does not exist");
        }
        return course.get();
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
