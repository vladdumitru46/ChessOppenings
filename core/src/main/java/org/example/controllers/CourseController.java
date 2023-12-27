package org.example.controllers;

import com.example.models.courses.Course;
import lombok.AllArgsConstructor;
import org.example.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/course")
@CrossOrigin(origins = "*")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/getCourse")
    private ResponseEntity<?> getCourse(@RequestParam String name) {
        Optional<Course> course = courseService.findCourseByName(name);
        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            return ResponseEntity.badRequest().body("Course does not exist!");
        }
    }

}
