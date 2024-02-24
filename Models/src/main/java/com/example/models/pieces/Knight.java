package com.example.models.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public final class Knight extends Pieces {

    private final Integer points = 3;

    public Knight(boolean white) {
        super(white);
    }

    @Override
    public boolean canAttackTheKing(Board board, CellOnTheBoard start, CellOnTheBoard end, Pieces piece) {
        int line = Math.abs(start.getLineCoordinate() - end.getLineCoordinate());
        int column = Math.abs(start.getColumnCoordinate() - end.getColumnCoordinate());
        return line * column == 2;
    }
}
