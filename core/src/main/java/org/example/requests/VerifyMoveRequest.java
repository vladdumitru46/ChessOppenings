package org.example.requests;

public record VerifyMoveRequest(String playerUsernameOrEmail, String courseName, String subCourseName, Integer boardId,
                                String start, String end, String pieceColour) {
}
