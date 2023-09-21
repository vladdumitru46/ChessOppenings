package org.example.repositoryes.repos;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.Rook;
import org.example.repositoryes.interfaces.IRepository;
import org.springframework.stereotype.Repository;

@Repository
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
