package com.example.models;

public class King implements Pieces {

    private boolean castlingDone = false;
    private boolean killed = false;
    private boolean white;

    public King(boolean white) {
        this.white = white;
    }

    @Override
    public boolean isKilled() {
        return killed;
    }

    @Override
    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    @Override
    public boolean isWhite() {
        return white;
    }

    @Override
    public void setWhite(boolean white) {
        this.white = white;
    }

    @Override
    public boolean canMove(Board board, CellOnTheBord start, CellOnTheBord end) {
        if (end.getPieces().isWhite() == this.isWhite()) {
            return false;
        }
        int lineCoordinate = Math.abs(start.getLineCoordinate() - end.getLineCoordinate());
        int columnCoordinate = Math.abs(start.getColumnCoordinate() - end.getColumnCoordinate());
        if (lineCoordinate + columnCoordinate == 1) {
            return checkIfPieceIsNotAttacked(board, start, end);
        }
        return false;
    }

    @Override
    public boolean checkIfPieceIsNotAttacked(Board board, CellOnTheBord start, CellOnTheBord end) {
        for (int i = 0; i < board.getCellOnTheBordMap().length; i++) {
            for (int j = 0; j < board.getCellOnTheBordMap().length; j++) {
                CellOnTheBord cell = board.getCellOnTheBordMap()[i][j];
                Pieces piece = cell.getPieces();
                if (piece != null && piece.isWhite() != this.isWhite() && piece.canAttackTheKing(board, cell, end, cell)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean canAttackTheKing(Board board, CellOnTheBord start, CellOnTheBord end, CellOnTheBord cell) {
        if (cell.getPieces() instanceof King) {
            int lineCoordinate = Math.abs(end.getLineCoordinate() - cell.getLineCoordinate());
            int columnCoordinate = Math.abs(end.getColumnCoordinate() - cell.getColumnCoordinate());
            return lineCoordinate + columnCoordinate >= 1;
        }
        return true;
    }

    public boolean isCastlingDone() {
        return castlingDone;
    }

    public void setCastlingDone(boolean castlingDone) {
        this.castlingDone = castlingDone;
    }
}
