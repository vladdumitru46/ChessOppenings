package org.example.requests;

public record GetHintRequest(String playerUsernameOrEmail, String courseName, String subCourseName, Integer boardId) {
}
