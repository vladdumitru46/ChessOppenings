package org.example.requests;

import com.example.models.courses.SubCourse;

import java.util.List;

public record AddAllSubCoursesRequest(List<SubCourse> subCourseList) {
}
