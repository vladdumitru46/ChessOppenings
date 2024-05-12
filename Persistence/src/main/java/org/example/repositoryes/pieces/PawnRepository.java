package org.example.repositoryes.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.King;
import com.example.models.pieces.Pawn;
import com.example.models.pieces.Pieces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public non-sealed class PawnRepository implements IRepository<Pawn> {
    Logger logger = LoggerFactory.getLogger(PawnRepository.class);

    @Override
    public boolean canMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Pawn pawn) {
        if (pawn == null) {
            return false;
        }

        if (end.getPieces() instanceof King) {
            return false;
        }
        if (!canAttackOtherPiece(board, start, end, pawn)) {
            if (start.getColumnCoordinate() != end.getColumnCoordinate()) {
                return false;
            }
            if (pawn.isWhite()) {
                if (start.getLineCoordinate() == 1) {
                    if (start.getLineCoordinate() + 1 != end.getLineCoordinate() && end.getLineCoordinate() != 3) {
                        return false;
                    } else if (board.getCellOnTheBoardMap()[3][start.getColumnCoordinate()].getPieces() != null || board.getCellOnTheBoardMap()[2][start.getColumnCoordinate()].getPieces() != null) {
                        return false;
                    }
                } else {
                    Pieces pieces = board.getCellOnTheBoardMap()[end.getLineCoordinate()][end.getColumnCoordinate()].getPieces();
                    if (start.getLineCoordinate() + 1 != end.getLineCoordinate()) {
                        return false;
                    } else if (pieces != null) {
                        return false;
                    }
                }
            } else {
                if (start.getLineCoordinate() == 6) {
                    if (start.getLineCoordinate() - 1 != end.getLineCoordinate() && end.getLineCoordinate() != 4) {
                        return false;
                    } else if (board.getCellOnTheBoardMap()[5][start.getColumnCoordinate()].getPieces() != null || board.getCellOnTheBoardMap()[4][start.getColumnCoordinate()].getPieces() != null) {
                        return false;
                    }
                } else {
                    Pieces pieces = board.getCellOnTheBoardMap()[end.getLineCoordinate()][end.getColumnCoordinate()].getPieces();
                    if (start.getLineCoordinate() - 1 != end.getLineCoordinate()) {
                        return false;
                    } else if (pieces != null) {
                        return false;
                    }
                }

            }
        }
        KingRepository kingRepository = new KingRepository();
        return kingRepository.checkIfTheKingIsInCheckAfterMove(board, start, end, pawn.isWhite());
    }

    private boolean canAttackOtherPiece(Board board, CellOnTheBoard start, CellOnTheBoard end, Pawn pawn) {
        for (int i = 0; i < board.getCellOnTheBoardMap().length; i++) {
            for (int j = 0; j < board.getCellOnTheBoardMap().length; j++) {
                CellOnTheBoard cell = board.getCellOnTheBoardMap()[i][j];
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

    private boolean whereCanThePawnAttack(CellOnTheBoard start, CellOnTheBoard end, CellOnTheBoard cell, Pieces piece, Pawn pawn) {
        if (piece != null && piece.isWhite() != pawn.isWhite()) {
            if (end.getLineCoordinate() == cell.getLineCoordinate() && end.getColumnCoordinate() == cell.getColumnCoordinate()) {
                if (start.getColumnCoordinate() - 1 == cell.getColumnCoordinate() || start.getColumnCoordinate() + 1 == cell.getColumnCoordinate()) {
                    if (pawn.isWhite()) {
                        return start.getLineCoordinate() + 1 == cell.getLineCoordinate();
                    } else {
                        return start.getLineCoordinate() - 1 == cell.getLineCoordinate();
                    }
                }
            }
        }
        return false;
    }

    public boolean canPromote(Board board, CellOnTheBoard start, CellOnTheBoard end) {
//        if (start.getPieces().isWhite()) {
//            if (start.getLineCoordinate() != 6) {
//                return false;
//            }
//            if (end.getLineCoordinate() != 7) {
//                return false;
//            }
//            if (start.getColumnCoordinate() != end.getColumnCoordinate()) {
//                if(start.getColumnCoordinate()!=end.getColumnCoordinate() - 1){
//                    if (start.getColumnCoordinate()!=end.getColumnCoordinate() + 1){
//                        return false;
//                    }
//                }
//            }
//            if (end.getPieces() != null) {
//                return false;
//            }
//            return true;
//        }else{
//            if (start.getLineCoordinate() != 1) {
//                return false;
//            }
//            if (end.getLineCoordinate() != 0) {
//                return false;
//            }
//            if (start.getColumnCoordinate() != end.getColumnCoordinate()) {
//                if(start.getColumnCoordinate()!=end.getColumnCoordinate() - 1){
//                    if (start.getColumnCoordinate()!=end.getColumnCoordinate() + 1){
//                        return false;
//                    }
//                }
//            }
//            if (end.getPieces() != null) {
//                return false;
//            }
//            return true;
//        }
        Pawn pawn = (Pawn) start.getPieces();
        if (pawn.isWhite()) {
            if (start.getLineCoordinate() == 6 && end.getLineCoordinate() == 7) {
                return canMove(board, start, end, pawn);
            }
        } else {
            if (start.getLineCoordinate() == 1 && end.getLineCoordinate() == 0) {
                return canMove(board, start, end, pawn);
            }
        }
        return false;
    }


}
