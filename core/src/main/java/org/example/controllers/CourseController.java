package org.example.controllers;

import com.example.models.courses.Course;
import lombok.AllArgsConstructor;
import org.example.course.CourseService;
import org.example.exceptions.CourseNotFoundException;
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
        } catch (CourseNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unexpected error!" + e.getMessage());
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

    @PutMapping("/update")
    public void updateCourse(@RequestParam Integer id) {
        Course course = courseService.findCourseById(id).get();
        course.setForWhite(true);
        courseService.updateCourse(course);
    }
}

