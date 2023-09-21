package org.example.repositoryes.repos;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.Bishop;
import org.example.repositoryes.interfaces.IRepository;
import org.springframework.stereotype.Repository;

//TODO loggers
@Repository
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
