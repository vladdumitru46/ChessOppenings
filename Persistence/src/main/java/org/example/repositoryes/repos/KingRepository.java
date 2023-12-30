package org.example.repositoryes.repos;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.King;
import com.example.models.pieces.Pieces;
import com.example.models.pieces.Rook;
import org.example.repositoryes.interfaces.pieces.IKingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class KingRepository implements IKingRepository {
    Logger logger = LoggerFactory.getLogger(KingRepository.class);

    @Override
    public boolean canMove(Board board, CellOnTheBord start, CellOnTheBord end, King king) {
        if (end.getPieces() != null) {
            if (end.getPieces().isWhite() == king.isWhite()) {
                logger.info("the king cannot move in this coordinates {}{}, because there is a piece with the same colour as the king", end.getLineCoordinate(), end.getColumnCoordinate());
                return false;
            }
        }
        int lineCoordinate = Math.abs(start.getLineCoordinate() - end.getLineCoordinate());
        int columnCoordinate = Math.abs(start.getColumnCoordinate() - end.getColumnCoordinate());
        if (lineCoordinate + columnCoordinate == 1) {
            logger.info("check if the king is not in check");
            if (!checkIfTheKingIsInCheck(board, start, end, king)) {
                king.setInCheck(true);
                start.setPieces(king);
                logger.info("king cannot move to this coordinates {}{} because there will be in check", end.getLineCoordinate(), end.getColumnCoordinate());
                return false;
            } else {
                king.setHasBeenMoved(true);
                start.setPieces(king);
                return true;
            }
        } else {
            if (start.getLineCoordinate() != end.getLineCoordinate() && start.getColumnCoordinate() != end.getColumnCoordinate()) {
                if (lineCoordinate + columnCoordinate == 2) {
                    logger.info("check if the king is not in check");
                    if (!checkIfTheKingIsInCheck(board, start, end, king)) {
                        king.setInCheck(true);
                        logger.info("king cannot move to this coordinates {}{} because there will be in check", end.getLineCoordinate(), end.getColumnCoordinate());
                        return false;
                    } else {
                        king.setHasBeenMoved(true);
                        start.setPieces(king);
                        return true;
                    }
                }
            } else {
                logger.info("king cannot move to this coordinates {}{}", end.getLineCoordinate(), end.getColumnCoordinate());
            }
        }
        return false;
    }

    @Override
    public boolean checkIfTheKingIsInCheck(Board board, CellOnTheBord start, CellOnTheBord end, Pieces king) {
        for (int i = 0; i < board.getCellOnTheBordMap().length; i++) {
            for (int j = 0; j < board.getCellOnTheBordMap().length; j++) {
                CellOnTheBord cell = board.getCellOnTheBordMap()[i][j];
                Pieces piece = cell.getPieces();
                if (piece != null && piece.isWhite() != king.isWhite() &&
                        piece.canAttackTheKing(board, cell, end, piece)) {
                    logger.info("the king is in check on this square {}{}", end.getLineCoordinate(),
                            end.getColumnCoordinate());
                    return false;
                }
            }
        }
        return true;
    }


    public boolean canCastle(Board board, CellOnTheBord start, CellOnTheBord end, King king) {
        if (!king.isCastlingDone()) {
            if (start.getLineCoordinate() != end.getLineCoordinate() &&
                    start.getLineCoordinate() != 0 && start.getLineCoordinate() != 7) {
                return false;
            }
            if (king.isHasBeenMoved()) {
                return false;
            }
            if (king.isWhite()) {
                if (end.getColumnCoordinate() == 2) {
                    Rook rook = (Rook) board.getCellOnTheBordMap()[0][0].getPieces();
                    if (rook == null) {
                        return false;
                    }
                    if (rook.isHasBeenMoved()) {
                        return false;
                    }
                    if (board.getCellOnTheBordMap()[0][1].getPieces() != null || board.getCellOnTheBordMap()[0][2].getPieces() != null || board.getCellOnTheBordMap()[0][3].getPieces() != null) {
                        return false;
                    }
                    rook.setHasBeenMoved(true);
                    king.setHasBeenMoved(true);
                    start.setPieces(king);
                    board.getCellOnTheBordMap()[0][3].setPieces(rook);
                    board.getCellOnTheBordMap()[0][0] = new CellOnTheBord(null, 0, 0);
                } else if (end.getColumnCoordinate() == 6) {
                    Rook rook = (Rook) board.getCellOnTheBordMap()[0][7].getPieces();
                    if (rook == null) {
                        return false;
                    }
                    if (rook.isHasBeenMoved()) {
                        return false;
                    }
                    if (board.getCellOnTheBordMap()[0][5].getPieces() != null || board.getCellOnTheBordMap()[0][6].getPieces() != null) {
                        return false;
                    }
                    rook.setHasBeenMoved(true);
                    king.setHasBeenMoved(true);
                    start.setPieces(king);
                    board.getCellOnTheBordMap()[0][5].setPieces(rook);
                    board.getCellOnTheBordMap()[0][7] = new CellOnTheBord(null, 0, 0);
                } else {
                    return false;
                }
            } else {
                if (end.getColumnCoordinate() == 2) {
                    Rook rook = (Rook) board.getCellOnTheBordMap()[7][0].getPieces();
                    if (rook == null) {
                        return false;
                    }
                    if (rook.isHasBeenMoved()) {
                        return false;
                    }
                    if (board.getCellOnTheBordMap()[7][1].getPieces() != null || board.getCellOnTheBordMap()[7][2].getPieces() != null || board.getCellOnTheBordMap()[7][3].getPieces() != null) {
                        return false;
                    }
                    rook.setHasBeenMoved(true);
                    king.setHasBeenMoved(true);
                    start.setPieces(king);
                    board.getCellOnTheBordMap()[7][3].setPieces(rook);
                    board.getCellOnTheBordMap()[7][0] = new CellOnTheBord(null, 7, 0);
                } else if (end.getColumnCoordinate() == 6) {
                    Rook rook = (Rook) board.getCellOnTheBordMap()[7][7].getPieces();
                    if (rook == null) {
                        return false;
                    }
                    if (rook.isHasBeenMoved()) {
                        return false;
                    }
                    if (board.getCellOnTheBordMap()[7][5].getPieces() != null || board.getCellOnTheBordMap()[7][6].getPieces() != null) {
                        return false;
                    }
                    rook.setHasBeenMoved(true);
                    king.setHasBeenMoved(true);
                    start.setPieces(king);
                    board.getCellOnTheBordMap()[7][5].setPieces(rook);
                    board.getCellOnTheBordMap()[7][7] = new CellOnTheBord(null, 7, 0);
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
