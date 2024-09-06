package org.example.board.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.Bishop;
import com.example.models.pieces.King;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public non-sealed class BishopService implements IService<Bishop> {


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
        while (currentLine != endLine && currentColumn != endColumn) {
            CellOnTheBoard currentCell = board.getCellOnTheBoardMap()[currentLine][currentColumn];
            if (currentCell.getPieces() != null) {
                return false;
            }

            currentLine += rowIncrement;
            currentColumn += colIncrement;
        }

        KingService kingService = new KingService();
        return kingService.checkIfTheKingIsInCheckAfterMove(board, start, end, pieces.isWhite());
    }

}