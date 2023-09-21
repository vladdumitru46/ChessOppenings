package org.example.repositoryes.repos;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.Knight;
import org.example.repositoryes.interfaces.IRepository;
import org.springframework.stereotype.Repository;

@Repository
public class KnightRepository implements IRepository<Knight> {
    @Override
    public boolean canMove(Board board, CellOnTheBord start, CellOnTheBord end, Knight pieces) {
        if (end.getPieces().isWhite() == pieces.isWhite()) {
            return false;
        }
        int line = Math.abs(start.getLineCoordinate() - end.getColumnCoordinate());
        int column = Math.abs(start.getLineCoordinate() - end.getColumnCoordinate());
        return line * column == 2;
    }

}
