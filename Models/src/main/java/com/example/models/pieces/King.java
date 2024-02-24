package com.example.models.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class King extends Pieces {

    private boolean castlingDone = false;
    private boolean inCheck = false;
    private boolean hasBeenMoved = false;
    private final Integer points = 0;

    public King(boolean white) {
        super(white);
    }

    @Override
    public boolean canAttackTheKing(Board board, CellOnTheBoard start, CellOnTheBoard end, Pieces piece) {
        if (end != null) {
            int lineCoordinate = Math.abs(end.getLineCoordinate() - start.getLineCoordinate());
            int columnCoordinate = Math.abs(end.getColumnCoordinate() - start.getColumnCoordinate());
            if (start.getLineCoordinate() == end.getLineCoordinate() || start.getColumnCoordinate() == end.getColumnCoordinate()) {
                return !(lineCoordinate + columnCoordinate > 1);
            } else {
                return lineCoordinate + columnCoordinate == 2;
            }
        }
        return false;
    }

}
