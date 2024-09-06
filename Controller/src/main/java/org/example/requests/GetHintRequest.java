package org.example.requests;

public record GetHintRequest(String token, String courseName, String subCourseName, Integer boardId) {
}
