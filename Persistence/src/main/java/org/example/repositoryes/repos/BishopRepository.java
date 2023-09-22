package org.example.repositoryes.repos;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.Bishop;
import org.example.repositoryes.interfaces.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

//TODO loggers
@Repository
public class BishopRepository implements IRepository<Bishop> {
    Logger logger = LoggerFactory.getLogger(BishopRepository.class);

    @Override
    public boolean canMove(Board board, CellOnTheBord start, CellOnTheBord end, Bishop pieces) {
        if (end.getPieces().isWhite() != pieces.isWhite()) {
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
                CellOnTheBord currentCell = board.getCellOnTheBordMap()[currentLine][currentColumn];
                if (currentCell.getPieces() != null) {
                    logger.info("bishop cannot move to this coordinates {}{}, because there is a piece that blocks the way", end.getLineCoordinate(), end.getColumnCoordinate());
                    return false;
                }

                currentLine += rowIncrement;
                currentColumn += colIncrement;
            }
            logger.info("the bishop can move!");
            return true;
        }
        logger.info("bishop cannot move to this coordinates {}{}, because on te end square is a piece with the same colour as the bishop", end.getLineCoordinate(), end.getColumnCoordinate());
        return false;
    }
}
