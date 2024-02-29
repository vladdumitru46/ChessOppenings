package com.example.models.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public final class Bishop extends Pieces {

    private final Integer points = 3;

    public Bishop(boolean white) {
        super(white);
    }

    @Override
    public boolean canAttackTheKing(Board board, CellOnTheBoard start, CellOnTheBoard end, Pieces piece) {
        Integer line = Math.abs(start.getLineCoordinate() - end.getLineCoordinate());
        Integer column = Math.abs(start.getColumnCoordinate() - end.getColumnCoordinate());
        if (line - column != 0) {
            log.info("bishop cannot move to this coordinates {}{}, because it's not diagonal", end.getLineCoordinate(), end.getColumnCoordinate());
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
        log.info("verify if it's any piece that blocks the bishop");
        while (currentLine != endLine && currentColumn != endColumn) {
            if (currentColumn == -1 || currentLine == -1) {
                return false;
            }
            CellOnTheBoard currentCell = board.getCellOnTheBoardMap()[currentLine][currentColumn];
            if (currentCell.getPieces() != null) {
                log.info("bishop cannot move to this coordinates {}{}, because there is a piece that blocks the way", end.getLineCoordinate(), end.getColumnCoordinate());
                return false;
            }

            currentLine += rowIncrement;
            currentColumn += colIncrement;
        }
        log.info("the bishop can move!");
        return true;
    }
}
