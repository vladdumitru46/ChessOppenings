package org.example.repositoryes.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.King;
import com.example.models.pieces.Knight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.stream.IntStream;

@Repository
public non-sealed class KnightRepository implements IRepository<Knight> {
    Logger logger = LoggerFactory.getLogger(KnightRepository.class);

    @Override
    public boolean canMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Knight pieces) {
        logger.info("knight tries to move from {}{} to {}{}", start.getLineCoordinate(), start.getColumnCoordinate(), end.getLineCoordinate(), end.getColumnCoordinate());

        if (end.getPieces() instanceof King) {
            return false;
        }
        if (end.getPieces() != null) {
            if (end.getPieces().isWhite() == pieces.isWhite()) {
                logger.info("the knight cannot move in this coordinates {}{}, because there is a piece with the same colour as the king", end.getLineCoordinate(), end.getColumnCoordinate());
                return false;
            }
        }

        int line = Math.abs(start.getLineCoordinate() - end.getLineCoordinate());
        int column = Math.abs(start.getColumnCoordinate() - end.getColumnCoordinate());
        if (line * column != 2) {
            return false;
        }
        KingRepository kingRepository = new KingRepository();
        return kingRepository.checkIfTheKingIsInCheckAfterMove(board, start, end, pieces.isWhite());
    }

    @Override
    public int getNrOfMoves(Board board, CellOnTheBoard cell, int nr) {
        return Math.toIntExact(IntStream.range(0, 8)
                .flatMap(i -> IntStream.range(0, 8)
                        .filter(j -> canMove(board, cell, board.getCellOnTheBoardMap()[i][j], (Knight) cell.getPieces())))
                .count());
    }

}
