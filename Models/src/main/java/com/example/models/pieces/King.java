package com.example.models.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class King extends Pieces {

    private boolean castlingDone = false;
    private boolean inCheck = false;
    private boolean hasBeenMoved = false;

    public King(boolean white) {
        super(white);
    }

    @Override
    public boolean canAttackTheKing(Board board, CellOnTheBord start, CellOnTheBord end, Pieces piece) {
        int lineCoordinate = Math.abs(end.getLineCoordinate() - start.getLineCoordinate());
        int columnCoordinate = Math.abs(end.getColumnCoordinate() - start.getColumnCoordinate());
        if (start.getLineCoordinate() == end.getLineCoordinate() || start.getColumnCoordinate() == end.getColumnCoordinate()) {
            return !(lineCoordinate + columnCoordinate > 1);
        } else {
            return lineCoordinate + columnCoordinate == 2;
        }
    }

}
