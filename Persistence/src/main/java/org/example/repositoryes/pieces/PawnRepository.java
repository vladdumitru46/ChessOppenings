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
                    } else if (end.getPieces() != null) {
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
                    } else if (end.getPieces() != null) {
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

    public boolean canEnPassant(Board board, CellOnTheBoard start, CellOnTheBoard end, Pawn pawn) {
        if (pawn == null) {
            return false;
        }

        if (end.getPieces() instanceof King) {
            return false;
        }
        if (pawn.isWhite() && end.getLineCoordinate() != 5 || !pawn.isWhite() && end.getLineCoordinate() != 2) {
            return false;
        }
        CellOnTheBoard pieceOnEnd;
        if (pawn.isWhite()) {
            pieceOnEnd = board.getCellOnTheBoardMap()[end.getLineCoordinate() - 1][end.getColumnCoordinate()];
        } else {
            pieceOnEnd = board.getCellOnTheBoardMap()[end.getLineCoordinate() + 1][end.getColumnCoordinate()];
        }
        if (pieceOnEnd == null) {
            return false;
        }
        if (!(pieceOnEnd.getPieces() instanceof Pawn)) {
            return false;
        }
        if (pawn.isWhite() && start.getLineCoordinate() != end.getLineCoordinate() - 1
                || !pawn.isWhite() && start.getLineCoordinate() != end.getLineCoordinate() + 1) {
            return false;
        }

        return true;
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
