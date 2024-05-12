package org.example.repositoryes.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.King;
import com.example.models.pieces.Pieces;
import com.example.models.pieces.Rook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class KingRepository implements IKingRepository {
    Logger logger = LoggerFactory.getLogger(KingRepository.class);

    @Override
    public boolean canMove(Board board, CellOnTheBoard start, CellOnTheBoard end, King king) {
        if (end.getPieces() != null) {
            if (end.getPieces().isWhite() == king.isWhite()) {
                logger.info("the king cannot move in this coordinates {}{}, because there is a piece with the same colour as the king", end.getLineCoordinate(), end.getColumnCoordinate());
                return false;
            }
        }
        int lineCoordinate = Math.abs(start.getLineCoordinate() - end.getLineCoordinate());
        int columnCoordinate = Math.abs(start.getColumnCoordinate() - end.getColumnCoordinate());

        if (start.getLineCoordinate() == end.getLineCoordinate()) {
            if (columnCoordinate == 1) {
                logger.info("check if the king is not in check");
                if (!checkIfTheKingIsInCheckAfterMove(board, start, end, king.isWhite())) {
                    logger.info("king cannot move to this coordinates {}{} because there will be in check", end.getLineCoordinate(), end.getColumnCoordinate());
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else if (start.getColumnCoordinate() == end.getColumnCoordinate()) {
            if (lineCoordinate == 1) {
                logger.info("check if the king is not in check");
                if (!checkIfTheKingIsInCheckAfterMove(board, start, end, king.isWhite())) {
                    logger.info("king cannot move to this coordinates {}{} because there will be in check", end.getLineCoordinate(), end.getColumnCoordinate());
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            if (lineCoordinate == 1 && columnCoordinate == 1) {
                logger.info("check if the king is not in check");
                if (!checkIfTheKingIsInCheckAfterMove(board, start, end, king.isWhite())) {
                    logger.info("king cannot move to this coordinates {}{} because there will be in check", end.getLineCoordinate(), end.getColumnCoordinate());
                    return false;
                } else {
                    return true;
                }

            } else {
                logger.info("king cannot move to this coordinates {}{}", end.getLineCoordinate(), end.getColumnCoordinate());
            }
        }
        return false;
    }

    @Override
    public boolean checkIfTheKingIsInCheck(Board board, CellOnTheBoard start, CellOnTheBoard end, Pieces isWhite) {
        CellOnTheBoard king = board.getKing(isWhite.isWhite());

        return Arrays.stream(board.getCellOnTheBoardMap())
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getPieces() != null && cell.getPieces().isWhite() != isWhite.isWhite() && king != null)
                .noneMatch(cell -> cell.getPieces().canAttackTheKing(board, cell, king, cell.getPieces()));
    }

    public boolean checkIfTheKingIsInCheckAfterMove(Board board, CellOnTheBoard start, CellOnTheBoard end, boolean isWhite) {
        CellOnTheBoard kingsCell = board.getKing(isWhite);
        King king = (King) kingsCell.getPieces();
        Pieces pieceOnStart = board.getCellOnTheBoardMap()[start.getLineCoordinate()][start.getColumnCoordinate()].getPieces();
        Pieces pieceOnEnd = board.getCellOnTheBoardMap()[end.getLineCoordinate()][end.getColumnCoordinate()].getPieces();
        board.getCellOnTheBoardMap()[end.getLineCoordinate()][end.getColumnCoordinate()].setPieces(pieceOnStart);
        board.getCellOnTheBoardMap()[start.getLineCoordinate()][start.getColumnCoordinate()].setPieces(null);

        if (!checkIfTheKingIsInCheck(board,
                board.getCellOnTheBoardMap()[end.getLineCoordinate()][end.getColumnCoordinate()], kingsCell, king)) {
            board.getCellOnTheBoardMap()[end.getLineCoordinate()][end.getColumnCoordinate()].setPieces(pieceOnEnd);
            board.getCellOnTheBoardMap()[start.getLineCoordinate()][start.getColumnCoordinate()].setPieces(pieceOnStart);
            return false;
        }

        board.getCellOnTheBoardMap()[end.getLineCoordinate()][end.getColumnCoordinate()].setPieces(pieceOnEnd);
        board.getCellOnTheBoardMap()[start.getLineCoordinate()][start.getColumnCoordinate()].setPieces(pieceOnStart);
        return true;
    }


    public boolean canCastle(Board board, CellOnTheBoard start, CellOnTheBoard end, King king) {
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
                    Pieces pieces = board.getCellOnTheBoardMap()[0][0].getPieces();
                    if (!(pieces instanceof Rook rook)) {
                        return false;
                    }
                    if (rook.isHasBeenMoved()) {
                        return false;
                    }
                    return board.getCellOnTheBoardMap()[0][1].getPieces() == null && board.getCellOnTheBoardMap()[0][2].getPieces() == null && board.getCellOnTheBoardMap()[0][3].getPieces() == null;
                } else if (end.getColumnCoordinate() == 6) {
                    Pieces pieces = board.getCellOnTheBoardMap()[0][7].getPieces();
                    if (!(pieces instanceof Rook rook)) {
                        return false;
                    }
                    if (rook.isHasBeenMoved()) {
                        return false;
                    }
                    return board.getCellOnTheBoardMap()[0][5].getPieces() == null && board.getCellOnTheBoardMap()[0][6].getPieces() == null;
                } else {
                    return false;
                }
            } else {
                if (end.getColumnCoordinate() == 2) {
                    Pieces pieces = board.getCellOnTheBoardMap()[7][0].getPieces();
                    if (!(pieces instanceof Rook rook)) {
                        return false;
                    }
                    if (rook.isHasBeenMoved()) {
                        return false;
                    }
                    return board.getCellOnTheBoardMap()[7][1].getPieces() == null && board.getCellOnTheBoardMap()[7][2].getPieces() == null && board.getCellOnTheBoardMap()[7][3].getPieces() == null;
                } else if (end.getColumnCoordinate() == 6) {
                    Pieces pieces = board.getCellOnTheBoardMap()[7][7].getPieces();
                    if (!(pieces instanceof Rook rook)) {
                        return false;
                    }
                    if (rook.isHasBeenMoved()) {
                        return false;
                    }
                    return board.getCellOnTheBoardMap()[7][5].getPieces() == null && board.getCellOnTheBoardMap()[7][6].getPieces() == null;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

}
