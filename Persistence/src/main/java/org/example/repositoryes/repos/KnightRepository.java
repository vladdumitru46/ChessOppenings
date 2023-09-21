package org.example.repositoryes.repos;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.King;
import org.example.repositoryes.interfaces.IRepository;

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
