package org.example.repositoryes;

import com.example.models.Board;
import com.example.models.CellOnTheBord;
import com.example.models.King;

public class KnightRepository implements IRepository<King> {
    @Override
    public boolean canMove(Board board, CellOnTheBord start, CellOnTheBord end, King pieces) {
        if (end.getPieces().isWhite() == pieces.isWhite()) {
            return false;
        }
        int line = Math.abs(start.getLineCoordinate() - end.getColumnCoordinate());
        int column = Math.abs(start.getLineCoordinate() - end.getColumnCoordinate());
        return line * column == 2;
    }

}
