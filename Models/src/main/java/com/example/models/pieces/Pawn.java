package com.example.models.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pawn extends Pieces {
    private boolean canEnPassant = false;
    private final Integer points = 1;

    public Pawn(boolean white) {
        super(white);
    }


    @Override
    public boolean canAttackTheKing(Board board, CellOnTheBord start, CellOnTheBord end, Pieces cell) {
        if (start.getPieces().isWhite()) {
            if (start.getLineCoordinate() == end.getLineCoordinate() - 1) {
                if (end.getColumnCoordinate() == start.getColumnCoordinate() + 1 || end.getColumnCoordinate() == start.getColumnCoordinate() - 1) {
                    return true;
                }
            }
        } else {
            if (start.getLineCoordinate() == end.getLineCoordinate() + 1) {
                if (end.getColumnCoordinate() == start.getColumnCoordinate() + 1 || end.getColumnCoordinate() == start.getColumnCoordinate() - 1) {
                    return true;
                }
            }
        }
        return false;
    }
}
