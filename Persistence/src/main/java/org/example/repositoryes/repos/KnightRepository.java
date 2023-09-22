package org.example.repositoryes.repos;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.Knight;
import org.example.repositoryes.interfaces.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class KnightRepository implements IRepository<Knight> {
    Logger logger = LoggerFactory.getLogger(KnightRepository.class);

    @Override
    public boolean canMove(Board board, CellOnTheBord start, CellOnTheBord end, Knight pieces) {
        if (end.getPieces().isWhite() == pieces.isWhite()) {
            logger.info("the knight cannot move in this coordinates {}{}, because there is a piece with the same colour as the king", end.getLineCoordinate(), end.getColumnCoordinate());

            return false;
        }
        int line = Math.abs(start.getLineCoordinate() - end.getColumnCoordinate());
        int column = Math.abs(start.getLineCoordinate() - end.getColumnCoordinate());
        return line * column == 2;
    }

}
