package org.example.repositoryes.repos;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.Pawn;
import com.example.models.pieces.Pieces;
import org.example.repositoryes.interfaces.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PawnRepository implements IRepository<Pawn> {
    Logger logger = LoggerFactory.getLogger(PawnRepository.class);

    @Override
    public boolean canMove(Board board, CellOnTheBord start, CellOnTheBord end, Pawn pawn) {
        Pieces pieces = board.getCellOnTheBordMap()[end.getLineCoordinate()][end.getColumnCoordinate()].getPieces();

        if (pawn.isWhite()) {
            if (start.getLineCoordinate() + 1 == end.getLineCoordinate() && pieces == null) {
                logger.info("the pawn can move");
                return true;
            }
            if (start.getLineCoordinate() == 1) {
                if (end.getLineCoordinate() == 3 && end.getColumnCoordinate() == start.getColumnCoordinate()) {
                    logger.info("the pawn can move");
                    return true;
                }
            }
        } else {
            if (start.getLineCoordinate() - 1 == end.getLineCoordinate() && pieces == null) {
                logger.info("the pawn can move");
                return true;
            }
            if (start.getLineCoordinate() == 6) {
                if (end.getLineCoordinate() == 4 && end.getColumnCoordinate() == start.getColumnCoordinate()) {
                    logger.info("the pawn can move");
                    return true;
                }
            }
        }
        if (canAttackOtherPiece(board, start, end, pawn)) {
            logger.info("the pawn can move");
            return true;
        }
        logger.info("the pawn cannot move to this square {}{}", end.getLineCoordinate(), end.getColumnCoordinate());
        return false;
    }

    private boolean canAttackOtherPiece(Board board, CellOnTheBord start, CellOnTheBord end, Pawn pawn) {
        for (int i = 0; i < board.getCellOnTheBordMap().length; i++) {
            for (int j = 0; j < board.getCellOnTheBordMap().length; j++) {
                CellOnTheBord cell = board.getCellOnTheBordMap()[i][j];
                Pieces piece = cell.getPieces();
                if (whereCanThePawnAttack(start, end, cell, piece, pawn)) {
                    logger.info("the pawn can attack other pieces");
                    return true;
                }
            }
        }
        logger.info("the pawn cannot attack other pieces");
        return false;
    }

    private boolean whereCanThePawnAttack(CellOnTheBord start, CellOnTheBord end, CellOnTheBord cell, Pieces piece, Pawn pawn) {
        if (piece != null && piece.isWhite() != pawn.isWhite()) {
            if (end.getLineCoordinate() == cell.getLineCoordinate() && end.getColumnCoordinate() == cell.getColumnCoordinate()) {
                if (start.getColumnCoordinate() - 1 == cell.getColumnCoordinate() || start.getColumnCoordinate() + 1 == cell.getColumnCoordinate()) {
                    if (pawn.isWhite()) {
                        piece.setKilled(true);
                        return start.getLineCoordinate() + 1 == cell.getLineCoordinate();
                    } else {
                        piece.setKilled(true);
                        return start.getLineCoordinate() - 1 == cell.getLineCoordinate();
                    }
                }
            }
        }
        return false;
    }


}
