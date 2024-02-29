package org.example.requests;

public record MovePiecesRequest(String gameId, String start, String end, String pieceColour) {

}
