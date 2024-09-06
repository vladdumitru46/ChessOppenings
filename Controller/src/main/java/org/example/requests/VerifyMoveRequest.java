package org.example.requests;

public record VerifyMoveRequest(String token, String courseName, String subCourseName, Integer boardId,
                                String start, String end, String pieceColour) {
}
