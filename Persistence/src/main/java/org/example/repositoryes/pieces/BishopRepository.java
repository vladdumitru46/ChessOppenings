package org.example.repositoryes.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.Bishop;
import com.example.models.pieces.King;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public non-sealed class BishopRepository implements IRepository<Bishop> {
    Logger logger = LoggerFactory.getLogger(BishopRepository.class);


    @Override
    public boolean canMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Bishop pieces) {
        if (end.getPieces() != null) {
            if (end.getPieces().isWhite() == pieces.isWhite()) {
                return false;
            }
        }
        if (end.getPieces() instanceof King) {
            return false;
        }
        Integer line = Math.abs(start.getLineCoordinate() - end.getLineCoordinate());
        Integer column = Math.abs(start.getColumnCoordinate() - end.getColumnCoordinate());
        if (line - column != 0) {
            logger.info("bishop cannot move to this coordinates {}{}, because it's not diagonal", end.getLineCoordinate(), end.getColumnCoordinate());
            return false;
        }
        int endLine = end.getLineCoordinate();
        int endColumn = end.getColumnCoordinate();
        int startLine = start.getLineCoordinate();
        int startColumn = start.getColumnCoordinate();
        int rowIncrement = (endLine > startLine) ? 1 : -1;
        int colIncrement = (endColumn > startColumn) ? 1 : -1;

        int currentLine = startLine + rowIncrement;
        int currentColumn = startColumn + colIncrement;
        logger.info("verify if it's any piece that blocks the bishop");
        while (currentLine != endLine && currentColumn != endColumn) {
            CellOnTheBoard currentCell = board.getCellOnTheBoardMap()[currentLine][currentColumn];
            if (currentCell.getPieces() != null) {
                logger.info("bishop cannot move to this coordinates {}{}, because there is a piece that blocks the way", end.getLineCoordinate(), end.getColumnCoordinate());
                return false;
            }

            currentLine += rowIncrement;
            currentColumn += colIncrement;
        }
        logger.info("the bishop can move!");
        KingRepository kingRepository = new KingRepository();
        return kingRepository.checkIfTheKingIsInCheckAfterMove(board, start, end, pieces.isWhite());
    }

}
