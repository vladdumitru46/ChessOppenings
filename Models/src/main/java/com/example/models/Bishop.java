package com.example.models;

public class Bishop extends Pieces {
    public Bishop(boolean white) {
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

        return true;
    }
}
