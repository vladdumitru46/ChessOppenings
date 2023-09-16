package com.example.models;

public class King extends Pieces{

    private boolean castlingDone = false;
    public King(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Board board, CellOnTheBord start, CellOnTheBord end) {
        if(end.getPieces().isWhite() == this.isWhite()){
            return false;
        }
        int lineCoordinate = Math.abs(start.getLineCoordinate() - end.getLineCoordinate());
        int columnCoordinate = Math.abs(start.getColumnCoordinate() - end.getColumnCoordinate());
        if(lineCoordinate + columnCoordinate == 1){
            return !checkIfPieceIsNotAttacked(board, start, end);
        }
        return false;
    }

    @Override
    public boolean checkIfPieceIsNotAttacked(Board board, CellOnTheBord start, CellOnTheBord end) {
        return false;
    }

    public boolean isCastlingDone() {
        return castlingDone;
    }

    public void setCastlingDone(boolean castlingDone) {
        this.castlingDone = castlingDone;
    }
}
