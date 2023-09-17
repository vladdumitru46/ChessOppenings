package org.example.repositoryes.repos;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.King;
import com.example.models.pieces.Pieces;
import org.example.repositoryes.interfaces.IKingRepository;

public class KingRepository implements IKingRepository {
    @Override
    public boolean canMove(Board board, CellOnTheBord start, CellOnTheBord end, King king) {
        if (end.getPieces().isWhite() == king.isWhite()) {
            return false;
        }
        int lineCoordinate = Math.abs(start.getLineCoordinate() - end.getLineCoordinate());
        int columnCoordinate = Math.abs(start.getColumnCoordinate() - end.getColumnCoordinate());
        if (lineCoordinate + columnCoordinate == 1) {
            return checkIfPieceIsNotAttacked(board, start, end, king);
        }
        return false;
    }

    @Override
    public boolean checkIfPieceIsNotAttacked(Board board, CellOnTheBord start, CellOnTheBord end, Pieces king) {
        for (int i = 0; i < board.getCellOnTheBordMap().length; i++) {
            for (int j = 0; j < board.getCellOnTheBordMap().length; j++) {
                CellOnTheBord cell = board.getCellOnTheBordMap()[i][j];
                Pieces piece = cell.getPieces();
                if (piece != null && piece.isWhite() != king.isWhite() && cell.getPieces() instanceof King && !king.canAttackTheKing(board, cell, end, cell)) {
                    return true;
                }
            }
        }

        return false;
    }


}
