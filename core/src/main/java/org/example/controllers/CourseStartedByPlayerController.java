package org.example.controllers;

import com.example.models.courses.CourseStartedByPlayer;
import lombok.AllArgsConstructor;
import org.example.CourseStartedByPlayerService;
import org.example.requests.StartCourseRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/startCourse")
@CrossOrigin(origins = "*")
public class CourseStartedByPlayerController {

    private final CourseStartedByPlayerService courseStartedByPlayerService;

    @PostMapping("/start")
    private void startCourse(@RequestBody StartCourseRequest startCourseRequest) {
        CourseStartedByPlayer courseStartedByPlayer = new CourseStartedByPlayer(startCourseRequest.getPlayerId(), startCourseRequest.getCourseId());
        courseStartedByPlayerService.addPlayerThatStartedTheCourse(courseStartedByPlayer);
    }

    @PutMapping("/finish")
    private void finishCourse(@RequestBody StartCourseRequest startCourseRequest) {
        CourseStartedByPlayer courseStartedByPlayer = new CourseStartedByPlayer(startCourseRequest.getPlayerId(), startCourseRequest.getCourseId());
        courseStartedByPlayerService.finishCourse(courseStartedByPlayer);
    }
}
