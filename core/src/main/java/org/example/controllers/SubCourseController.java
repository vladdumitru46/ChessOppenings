package org.example.controllers;

import com.example.models.courses.Course;
import com.example.models.courses.SubCourse;
import lombok.AllArgsConstructor;
import org.example.course.CourseService;
import org.example.course.SubCourseService;
import org.example.requests.AddAllSubCoursesRequest;
import org.example.requests.AddSubCourseRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chess/subCourses")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class SubCourseController {

    private final SubCourseService subCourseService;
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<?> getAllSubCourses(@RequestParam String courseName) {
        try {
            List<SubCourse> subCourseServiceList = subCourseService.getAllSubCourses(courseName);
            String names = subCourseServiceList.stream()
                    .map(SubCourse::getName)
                    .collect(Collectors.joining(","));
            return new ResponseEntity<>(names, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/subCourse")
    public ResponseEntity<?> getSubCourse(@RequestParam String name, @RequestParam String courseName) {
        try {
            SubCourse subCourse = subCourseService.getByName(name, courseName);
            return new ResponseEntity<>(subCourse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllSubCourse() {
        try {
            return new ResponseEntity<>(subCourseService.getAllSubCourses(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public void addSubCourse(@RequestBody AddSubCourseRequest addSubCourseRequest) {
        subCourseService.addSubCourse(new SubCourse(addSubCourseRequest.name(), addSubCourseRequest.movesThatTheComputerWillPlay(), addSubCourseRequest.movesThatThePlayerShouldPlay(), addSubCourseRequest.courseName()));
    }
    @PostMapping("/addAll")
    public void addAllSubCourse(@RequestBody AddAllSubCoursesRequest addAllSubCoursesRequest) {
        subCourseService.addAll(addAllSubCoursesRequest.subCourseList());
    }

}
