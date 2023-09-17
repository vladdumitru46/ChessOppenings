package com.example.models;

public class Knight extends Pieces {
    public Knight(boolean white) {
        super(white);
    }

    @Override
    public boolean canAttackTheKing(Board board, CellOnTheBord start, CellOnTheBord end, CellOnTheBord piece) {
        if (start.getLineCoordinate() + 1 == piece.getLineCoordinate() || start.getLineCoordinate() - 1 == piece.getLineCoordinate()) {
            if (start.getColumnCoordinate() + 2 == piece.getColumnCoordinate() || start.getColumnCoordinate() - 2 == piece.getColumnCoordinate()) {
                return true;
            }
        } else if (start.getLineCoordinate() + 2 == piece.getLineCoordinate() || start.getLineCoordinate() - 2 == piece.getLineCoordinate()) {
            if (start.getColumnCoordinate() + 1 == piece.getColumnCoordinate() || start.getColumnCoordinate() - 1 == piece.getColumnCoordinate()) {
                return true;
            }
        }
        return false;
    }
}
