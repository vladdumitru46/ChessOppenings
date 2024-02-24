package org.example.controllers;

import com.example.models.courses.SubCourse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.course.SubCourseService;
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


}
