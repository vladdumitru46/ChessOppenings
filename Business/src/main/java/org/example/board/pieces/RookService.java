package org.example.board.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.King;
import com.example.models.pieces.Rook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public non-sealed class RookService implements IService<Rook> {

    @Override
    public boolean canMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Rook pieces) {
        if (end.getPieces() != null) {
            if (end.getPieces().isWhite() == pieces.isWhite()) {
                return false;
            }
        }

        if (end.getPieces() instanceof King) {
            return false;
        }
        if (start.getLineCoordinate() != end.getLineCoordinate() && start.getColumnCoordinate() != end.getColumnCoordinate()) {
            return false;
        }
        int startLine = start.getLineCoordinate();
        int startColumn = start.getColumnCoordinate();
        int endLine = end.getLineCoordinate();
        int endColumn = end.getColumnCoordinate();

        int rowIncrement = Integer.compare(endLine, startLine);
        int colIncrement = Integer.compare(endColumn, startColumn);

        int currentLine = startLine + rowIncrement;
        int currentColumn = startColumn + colIncrement;

        while (currentLine != endLine || currentColumn != endColumn) {
            CellOnTheBoard currentCell = board.getCellOnTheBoardMap()[currentLine][currentColumn];
            if (currentCell.getPieces() != null) {
                return false;
            }

            currentLine += rowIncrement;
            currentColumn += colIncrement;
        }

        KingService kingRepository = new KingService();
        return kingRepository.checkIfTheKingIsInCheckAfterMove(board, start, end, pieces.isWhite());
    }

}
