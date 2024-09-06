package org.example.requests;

import com.example.models.courses.Course;

public record AddSubCourseRequest(String name, String movesThatTheComputerWillPlay, String movesThatThePlayerShouldPlay, Course courseName) {
}
