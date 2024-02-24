package org.example.requests;

public record MovePiecesRequest(String boardId, String start, String end, String pieceColour) {

}
