package com.example.models.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;

public class Pawn extends Pieces {
    private boolean canEnPassant = false;

    public Pawn(boolean white) {
        super(white);
    }

    public void setCanEnPassant(boolean canEnPassant) {
        this.canEnPassant = canEnPassant;
    }

    public boolean isCanEnPassant() {
        return canEnPassant;
    }


    @Override
    public boolean canAttackTheKing(Board board, CellOnTheBord start, CellOnTheBord end, CellOnTheBord cell) {
        Pieces piece = cell.getPieces();
        if (piece instanceof King) {
            if (piece != null && piece.isWhite() != this.isWhite()) {
                if (end.getLineCoordinate() == cell.getLineCoordinate() && end.getColumnCoordinate() == cell.getColumnCoordinate()) {
                    if (start.getColumnCoordinate() - 1 == cell.getColumnCoordinate() || start.getColumnCoordinate() + 1 == cell.getColumnCoordinate()) {
                        if (this.isWhite()) {
                            return start.getLineCoordinate() + 1 == cell.getLineCoordinate();
                        } else {
                            return start.getLineCoordinate() - 1 == cell.getLineCoordinate();
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }
}
