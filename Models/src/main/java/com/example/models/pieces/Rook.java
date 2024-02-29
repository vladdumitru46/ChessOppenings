package com.example.models.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public final class Rook extends Pieces {
    private boolean hasBeenMoved = false;
    private final Integer points = 3;

    public Rook(boolean white) {
        super(white);
    }

    @Override
    public boolean canAttackTheKing(Board board, CellOnTheBoard start, CellOnTheBoard end, Pieces piece) {
        if (start.getLineCoordinate() != end.getLineCoordinate() && start.getColumnCoordinate() != end.getColumnCoordinate()) {
            log.info("rook cannot move to this coordinates {}{}, because it's not a straight line", end.getLineCoordinate(), end.getColumnCoordinate());
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
            CellOnTheBoard currentCell = board.getCellOnTheBoardMap()[currentLine][currentColumn];
            if (currentCell.getPieces() != null) {
                log.info("rook cannot move to this coordinates {}{}, because there is a piece blocking the way", end.getLineCoordinate(), end.getColumnCoordinate());
                return false;
            }

            currentLine += rowIncrement;
            currentColumn += colIncrement;
        }

        return true;
    }
}
