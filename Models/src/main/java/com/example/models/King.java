package com.example.models;

public class King extends Pieces {

    private boolean castlingDone = false;

    public King(boolean white) {
        super(white);
    }
    
    @Override
    public boolean canAttackTheKing(Board board, CellOnTheBord start, CellOnTheBord end, CellOnTheBord piece) {
        int lineCoordinate = Math.abs(end.getLineCoordinate() - piece.getLineCoordinate());
        int columnCoordinate = Math.abs(end.getColumnCoordinate() - piece.getColumnCoordinate());
        return lineCoordinate + columnCoordinate >= 1;
    }

    public boolean isCastlingDone() {
        return castlingDone;
    }

    public void setCastlingDone(boolean castlingDone) {
        this.castlingDone = castlingDone;
    }
}
