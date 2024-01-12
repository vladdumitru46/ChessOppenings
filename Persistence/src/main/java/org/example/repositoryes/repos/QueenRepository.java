package org.example.repositoryes.repos;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.King;
import com.example.models.pieces.Queen;
import org.example.repositoryes.interfaces.pieces.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class QueenRepository implements IRepository<Queen> {
    Logger logger = LoggerFactory.getLogger(QueenRepository.class);

    @Override
    public boolean canMove(Board board, CellOnTheBord start, CellOnTheBord end, Queen pieces) {
        if (end.getPieces() != null) {
            if (end.getPieces().isWhite() == pieces.isWhite()) {
                return false;
            }
        }
        Integer line = Math.abs(start.getLineCoordinate() - end.getLineCoordinate());
        Integer column = Math.abs(start.getColumnCoordinate() - end.getColumnCoordinate());
        if (Math.abs(line - column) != 0) {
            logger.info("queen cannot move to this coordinates {}{}, because it's not diagonal", end.getLineCoordinate(), end.getColumnCoordinate());
            if (start.getLineCoordinate() != end.getLineCoordinate() && start.getColumnCoordinate() != end.getColumnCoordinate()) {
                logger.info("queen cannot move to this coordinates {}{}, because it's not a straight line", end.getLineCoordinate(), end.getColumnCoordinate());
                return false;
            }
            int startLine = start.getLineCoordinate();
            int startColumn = start.getColumnCoordinate();
            int endLine = end.getLineCoordinate();
            int endColumn = end.getColumnCoordinate();

            int rowIncrement = (endLine > startLine) ? 1 : (endLine < startLine) ? -1 : 0;
            int colIncrement = (endColumn > startColumn) ? 1 : (endColumn < startColumn) ? -1 : 0;

            int currentLine = startLine + rowIncrement;
            int currentColumn = startColumn + colIncrement;

            while (currentLine != endLine || currentColumn != endColumn) {
                CellOnTheBord currentCell = board.getCellOnTheBordMap()[currentLine][currentColumn];
                if (currentCell.getPieces() != null) {
                    logger.info("queen cannot move to this coordinates {}{}, because there is a piece blocking the way", end.getLineCoordinate(), end.getColumnCoordinate());
                    return false;
                }

                currentLine += rowIncrement;
                currentColumn += colIncrement;
            }

            return true;
        }

        int startLine = start.getLineCoordinate();
        int startColumn = start.getColumnCoordinate();
        int endLine = end.getLineCoordinate();
        int endColumn = end.getColumnCoordinate();

        int rowIncrement = (endLine > startLine) ? 1 : -1;
        int colIncrement = (endColumn > startColumn) ? 1 : -1;

        int currentLine = startLine + rowIncrement;
        int currentColumn = startColumn + colIncrement;
        int ok = 1;
        while (currentLine != endLine && currentColumn != endColumn) {
            CellOnTheBord currentCell = board.getCellOnTheBordMap()[currentLine][currentColumn];
            if (currentCell.getPieces() != null) {
                logger.info("queen cannot move to this coordinates {}{} from {}{}, because there is a piece that blocks the way", end.getLineCoordinate(), end.getColumnCoordinate(), start.getLineCoordinate(), start.getColumnCoordinate());
                return false;
            }

            currentLine += rowIncrement;
            currentColumn += colIncrement;
        }

        KingRepository kingRepository = new KingRepository();
        CellOnTheBord kingsCell = board.getKing(pieces.isWhite());
        King king = (King) kingsCell.getPieces();
        if (king.isInCheck()) {
            if (!kingRepository.checkIfTheKingIsInCheck(board,
                    board.getCellOnTheBordMap()[end.getLineCoordinate()][end.getColumnCoordinate()], kingsCell, king)) {
                return false;
            } else {
                king.setInCheck(false);
            }
        }

        return true;
    }

    @Override
    public int getNrOfMoves(Board board, CellOnTheBord cell, int nr) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (canMove(board, cell, board.getCellOnTheBordMap()[i][j], (Queen) cell.getPieces())) {
                    nr++;
                }
            }
        }
        return nr;
    }
}

