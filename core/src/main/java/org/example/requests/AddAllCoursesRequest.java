package org.example.requests;

import com.example.models.courses.Course;

import java.util.List;

public record AddAllCoursesRequest(List<Course> courseList) {
}
