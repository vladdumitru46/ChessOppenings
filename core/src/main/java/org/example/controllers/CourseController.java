package org.example.controllers;

import com.example.models.courses.Course;
import lombok.AllArgsConstructor;
import org.example.course.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
