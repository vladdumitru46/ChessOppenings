package org.example.controllers;

import com.example.models.courses.Course;
import lombok.AllArgsConstructor;
import org.example.course.CourseService;
import org.example.requests.AddAllCoursesRequest;
import org.example.requests.AddAllSubCoursesRequest;
import org.example.requests.AddCourseRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/chess/course")
@CrossOrigin(origins = "*")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/getCourse")
    private ResponseEntity<?> getCourse(@RequestParam String name) {
        try {
            Course course = courseService.findCourseByName(name);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    private ResponseEntity<?> getAllCourses() {

        List<Course> course = courseService.getAllCourses();
        return ResponseEntity.ok(course);
    }

    @PostMapping("/add")
    public void addCourse(@RequestBody AddCourseRequest addCourseRequest) {
        Course course = new Course(addCourseRequest.name(), addCourseRequest.description(), addCourseRequest.video(), addCourseRequest.forWhite());
        courseService.addCourse(course);
    }
    @PostMapping("/addAll")
    public void addAllSubCourse(@RequestBody AddAllCoursesRequest addAllCoursesRequest) {
        courseService.addAll(addAllCoursesRequest.courseList());
    }
}
