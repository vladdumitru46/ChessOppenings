package org.example.repositoryes;

import com.example.models.Bishop;
import com.example.models.Board;
import com.example.models.CellOnTheBord;

public class BishopRepository implements IRepository<Bishop> {
    @Override
    public boolean canMove(Board board, CellOnTheBord start, CellOnTheBord end, Bishop pieces) {
        if (end.getPieces().isWhite() != pieces.isWhite()) {
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
                CellOnTheBord currentCell = board.getCellOnTheBordMap()[currentLine][currentColumn];
                if (currentCell.getPieces() != null) {
                    return false;
                }

                currentLine += rowIncrement;
                currentColumn += colIncrement;
            }

        }
        return false;
    }
}
