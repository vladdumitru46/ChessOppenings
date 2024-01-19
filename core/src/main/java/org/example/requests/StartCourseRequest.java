package org.example.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(force = true)
public class StartCourseRequest {
    private final Integer courseId;
    private final Integer playerId;
}
