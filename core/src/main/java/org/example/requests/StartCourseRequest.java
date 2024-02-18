package org.example.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StartCourseRequest {
    private final String courseName;
    private final String playerUsername;
}
