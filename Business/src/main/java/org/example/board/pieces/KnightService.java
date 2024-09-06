package org.example.board.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.King;
import com.example.models.pieces.Knight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public non-sealed class KnightService implements IService<Knight> {

    @Override
    public boolean canMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Knight pieces) {

        if (end.getPieces() instanceof King) {
            return false;
        }
        if (end.getPieces() != null) {
            if (end.getPieces().isWhite() == pieces.isWhite()) {
                return false;
            }
        }

        int line = Math.abs(start.getLineCoordinate() - end.getLineCoordinate());
        int column = Math.abs(start.getColumnCoordinate() - end.getColumnCoordinate());
        if (line * column != 2) {
            return false;
        }
        KingService kingRepository = new KingService();
        return kingRepository.checkIfTheKingIsInCheckAfterMove(board, start, end, pieces.isWhite());
    }

}
