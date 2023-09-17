package org.example.repositoryes;

import com.example.models.Board;
import com.example.models.CellOnTheBord;
import com.example.models.Rook;

public class RookRepository implements IRepository<Rook> {
    @Override
    public boolean canMove(Board board, CellOnTheBord start, CellOnTheBord end, Rook pieces) {
        if (end.getPieces().isWhite() != pieces.isWhite()) {
            if (start.getLineCoordinate() != end.getLineCoordinate() && start.getColumnCoordinate() != end.getColumnCoordinate()) {
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
                    return false;
                }

                currentLine += rowIncrement;
                currentColumn += colIncrement;
            }

            return true;
        }
        return false;
    }
}
