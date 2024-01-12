package org.example.repositoryes.repos;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.King;
import com.example.models.pieces.Rook;
import org.example.repositoryes.interfaces.pieces.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RookRepository implements IRepository<Rook> {
    Logger logger = LoggerFactory.getLogger(QueenRepository.class);

    @Override
    public boolean canMove(Board board, CellOnTheBord start, CellOnTheBord end, Rook pieces) {
        if (end.getPieces() != null) {
            if (end.getPieces().isWhite() == pieces.isWhite()) {
                return false;
            }
        }
        if (start.getLineCoordinate() != end.getLineCoordinate() && start.getColumnCoordinate() != end.getColumnCoordinate()) {
            logger.info("rook cannot move to this coordinates {}{}, because it's not a straight line", end.getLineCoordinate(), end.getColumnCoordinate());
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
                logger.info("rook cannot move to this coordinates {}{}, because there is a piece blocking the way", end.getLineCoordinate(), end.getColumnCoordinate());
                return false;
            }

            currentLine += rowIncrement;
            currentColumn += colIncrement;
        }
        pieces.setHasBeenMoved(true);
        start.setPieces(pieces);

        KingRepository kingRepository = new KingRepository();
        CellOnTheBord kingsCell = board.getKing(pieces.isWhite());
        King king = (King) kingsCell.getPieces();
        if (king.isInCheck()) {
            if (!kingRepository.checkIfTheKingIsInCheck(board, board.getCellOnTheBordMap()[endLine][endColumn], kingsCell, king)) {
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
                if (canMove(board, cell, board.getCellOnTheBordMap()[i][j], (Rook) cell.getPieces())) {
                    nr++;
                }
            }
        }
        return nr;
    }
}
