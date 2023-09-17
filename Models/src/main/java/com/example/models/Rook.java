package com.example.models;

public class Rook extends Pieces {
    public Rook(boolean white) {
        super(white);
    }

    @Override
    public boolean canAttackTheKing(Board board, CellOnTheBord start, CellOnTheBord end, CellOnTheBord piece) {
        if (start.getLineCoordinate() != piece.getLineCoordinate() && start.getColumnCoordinate() != piece.getColumnCoordinate()) {
            return false;
        }
        int startLine = start.getLineCoordinate();
        int startColumn = start.getColumnCoordinate();
        int endLine = piece.getLineCoordinate();
        int endColumn = piece.getColumnCoordinate();

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
}
