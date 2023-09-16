package com.example.models;

public class Pawn implements Pieces {
    private boolean canEnPassant = false;
    private boolean killed = false;
    private boolean white;

    public Pawn(boolean white) {
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

    //TODO enPassant
    public void setCanEnPassant(boolean canEnPassant) {
        this.canEnPassant = canEnPassant;
    }

    public boolean isCanEnPassant() {
        return canEnPassant;
    }

    @Override
    public boolean canMove(Board board, CellOnTheBord start, CellOnTheBord end) {
        Pieces pieces = board.getCellOnTheBordMap()[end.getLineCoordinate()][end.getColumnCoordinate()].getPieces();

        if (white) {
            if (start.getLineCoordinate() + 1 == end.getLineCoordinate() && pieces == null) {
                return true;
            }
            if (start.getLineCoordinate() == 1) {
                if (end.getLineCoordinate() == 3 && end.getColumnCoordinate() == start.getColumnCoordinate()) {
                    return true;
                }
            }
        } else {
            if (start.getLineCoordinate() - 1 == end.getLineCoordinate() && pieces == null) {
                return true;
            }
            if (start.getLineCoordinate() == 6) {
                if (end.getLineCoordinate() == 4 && end.getColumnCoordinate() == start.getColumnCoordinate()) {
                    return true;
                }
            }
        }
        if (canAttackOtherPiece(board, start, end)) {
            return true;
        }
        return false;
    }

    private boolean canAttackOtherPiece(Board board, CellOnTheBord start, CellOnTheBord end) {
        for (int i = 0; i < board.getCellOnTheBordMap().length; i++) {
            for (int j = 0; j < board.getCellOnTheBordMap().length; j++) {
                CellOnTheBord cell = board.getCellOnTheBordMap()[i][j];
                Pieces piece = cell.getPieces();
                if (whereCanThePawnAttack(start, end, cell, piece)) return true;
            }
        }
        return false;
    }

    private boolean whereCanThePawnAttack(CellOnTheBord start, CellOnTheBord end, CellOnTheBord cell, Pieces piece) {
        if (piece != null && piece.isWhite() != this.isWhite()) {
            if (end.getLineCoordinate() == cell.getLineCoordinate() && end.getColumnCoordinate() == cell.getColumnCoordinate()) {
                if (start.getColumnCoordinate() - 1 == cell.getColumnCoordinate() || start.getColumnCoordinate() + 1 == cell.getColumnCoordinate()) {
                    if (white) {
                        return start.getLineCoordinate() + 1 == cell.getLineCoordinate();
                    } else {
                        return start.getLineCoordinate() - 1 == cell.getLineCoordinate();
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkIfPieceIsNotAttacked(Board board, CellOnTheBord start, CellOnTheBord end) {
        return false;
    }

    @Override
    public boolean canAttackTheKing(Board board, CellOnTheBord start, CellOnTheBord end, CellOnTheBord cell) {
        Pieces piece = cell.getPieces();
        if (piece instanceof King) {
            return whereCanThePawnAttack(start, end, cell, piece);
        }
        return false;
    }
}
