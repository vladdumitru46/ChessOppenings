package com.example.models.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;

public class Queen extends Pieces {
    public Queen(boolean white) {
        super(white);
    }

    @Override
    public boolean canAttackTheKing(Board board, CellOnTheBord start, CellOnTheBord end, CellOnTheBord piece) {
        Integer line = Math.abs(start.getLineCoordinate() - piece.getLineCoordinate());
        Integer column = Math.abs(start.getColumnCoordinate() - piece.getColumnCoordinate());
        if (Math.abs(line - column) != 0) {
            return false;
        }

        int startLine = start.getLineCoordinate();
        int startColumn = start.getColumnCoordinate();
        int endLine = piece.getLineCoordinate();
        int endColumn = piece.getColumnCoordinate();

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
        if (start.getLineCoordinate() != piece.getLineCoordinate() && start.getColumnCoordinate() != piece.getColumnCoordinate()) {
            return false;
        }
        startLine = start.getLineCoordinate();
        startColumn = start.getColumnCoordinate();
        endLine = piece.getLineCoordinate();
        endColumn = piece.getColumnCoordinate();

        rowIncrement = (endLine > startLine) ? 1 : (endLine < startLine) ? -1 : 0;
        colIncrement = (endColumn > startColumn) ? 1 : (endColumn < startColumn) ? -1 : 0;

        currentLine = startLine + rowIncrement;
        currentColumn = startColumn + colIncrement;

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
}
