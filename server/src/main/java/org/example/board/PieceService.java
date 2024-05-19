package org.example.board;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.board.Move;
import com.example.models.pieces.*;
import lombok.extern.slf4j.Slf4j;
import org.example.repositoryes.pieces.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.LongStream;

import static java.lang.Math.abs;


@Service("pieceService")
@Slf4j
public class PieceService {
    private final PawnRepository pawnRepository;
    private final RookRepository rookRepository;
    private final KnightRepository knightRepository;
    private final BishopRepository bishopRepository;
    private final QueenRepository queenRepository;
    private final KingRepository kingRepository;


    public PieceService(PawnRepository pawnRepository, RookRepository rookRepository, KnightRepository knightRepository, BishopRepository bishopRepository, QueenRepository queenRepository, KingRepository kingRepository) {
        this.pawnRepository = pawnRepository;
        this.rookRepository = rookRepository;
        this.knightRepository = knightRepository;
        this.bishopRepository = bishopRepository;
        this.queenRepository = queenRepository;
        this.kingRepository = kingRepository;
    }


    public boolean canThePawnMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Pawn pawn) {
        log.info("verify if the pawn can move");
        return pawnRepository.canMove(board, start, end, pawn);
    }

    public boolean canTheRookMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Rook rook) {
        log.info("verify if the rook can move");

        if (rookRepository.canMove(board, start, end, rook)) {
            rook.setHasBeenMoved(true);
            start.setPieces(rook);
            return true;
        } else {
            return false;
        }
    }

    public boolean canTheKnightMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Knight knight) {
        log.info("verify if the knight can move");
        return knightRepository.canMove(board, start, end, knight);
    }

    public boolean canTheBishopMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Bishop bishop) {
        log.info("verify if the bishop can move");
        return bishopRepository.canMove(board, start, end, bishop);
    }

    public boolean canTheQueenMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Queen queen) {
        log.info("verify if the queen can move");
        return queenRepository.canMove(board, start, end, queen);
    }

    public boolean canTheKingMove(Board board, CellOnTheBoard start, CellOnTheBoard end, King king) {
        log.info("verify if the king can move");
        if (kingRepository.canMove(board, start, end, king)) {
            king.setHasBeenMoved(true);
            start.setPieces(king);
            return true;
        } else {
            return false;
        }
    }

    public boolean canThePawnPromote(Board board, CellOnTheBoard cell, CellOnTheBoard end) {
        return pawnRepository.canPromote(board, cell, end);
    }


    public boolean canCastle(Board board, CellOnTheBoard start, CellOnTheBoard end, King king) {
        if (kingRepository.canCastle(board, start, end, king)) {
            king.setHasBeenMoved(true);
            start.setPieces(king);
            doCastle(board, end, king);
            return true;
        } else {
            return false;
        }
    }

    private void doCastle(Board board, CellOnTheBoard end, King king) {
        if (king.isWhite()) {
            if (end.getColumnCoordinate() == 2) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[0][0].getPieces();
                rook.setHasBeenMoved(true);
                board.getCellOnTheBoardMap()[0][3].setPieces(rook);
                board.getCellOnTheBoardMap()[0][0].setPieces(null);
            }
            if (end.getColumnCoordinate() == 6) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[0][7].getPieces();
                rook.setHasBeenMoved(true);
                board.getCellOnTheBoardMap()[0][5].setPieces(rook);
                board.getCellOnTheBoardMap()[0][7].setPieces(null);
            }
        } else {
            if (end.getColumnCoordinate() == 2) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[7][0].getPieces();
                rook.setHasBeenMoved(true);
                board.getCellOnTheBoardMap()[7][3].setPieces(rook);
                board.getCellOnTheBoardMap()[7][0].setPieces(null);
            } else if (end.getColumnCoordinate() == 6) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[7][7].getPieces();
                rook.setHasBeenMoved(true);
                board.getCellOnTheBoardMap()[7][5].setPieces(rook);
                board.getCellOnTheBoardMap()[7][7].setPieces(null);
            }
        }
        king.setHasBeenMoved(true);
    }

    private void undoCastle(Board board, CellOnTheBoard end, King king) {
        if (king.isWhite()) {
            if (end.getColumnCoordinate() == 2) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[0][3].getPieces();
                rook.setHasBeenMoved(false);
                board.getCellOnTheBoardMap()[0][0].setPieces(rook);
                board.getCellOnTheBoardMap()[0][3].setPieces(null);
            }
            if (end.getColumnCoordinate() == 6) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[0][5].getPieces();
                rook.setHasBeenMoved(false);
                board.getCellOnTheBoardMap()[0][7].setPieces(rook);
                board.getCellOnTheBoardMap()[0][5].setPieces(null);
            }
        } else {
            if (end.getColumnCoordinate() == 2) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[7][3].getPieces();
                rook.setHasBeenMoved(false);
                board.getCellOnTheBoardMap()[7][0].setPieces(rook);
                board.getCellOnTheBoardMap()[7][3].setPieces(null);
            } else if (end.getColumnCoordinate() == 6) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[7][5].getPieces();
                rook.setHasBeenMoved(false);
                board.getCellOnTheBoardMap()[7][7].setPieces(rook);
                board.getCellOnTheBoardMap()[7][5].setPieces(null);
            }
        }
        king.setHasBeenMoved(false);
    }


    public synchronized void makeMove(Board board, Move move) {
        Pieces pieceOnStart = move.getStart().getPieces();
        Pieces pieceOnEnd = move.getEnd().getPieces();
        Pieces realPieceOnEnd = board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()].getPieces();
        CellOnTheBoard kingsCell = board.getKing(!move.getStart().getPieces().isWhite());
        King enemyKing = (King) kingsCell.getPieces();
        if (pieceOnStart instanceof Pawn && pieceOnEnd != null && pieceOnEnd.isWhite() == pieceOnStart.isWhite()
                && (realPieceOnEnd == null || realPieceOnEnd.isWhite() != pieceOnStart.isWhite())) {
            board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()]
                    .setPieces(pieceOnEnd);
            board.getCellOnTheBoardMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()]
                    .setPieces(null);
        }
        if (pieceOnStart instanceof King king && !((King) pieceOnStart).isHasBeenMoved() &&
                abs(move.getStart().getColumnCoordinate() - move.getEnd().getColumnCoordinate()) == 2) {
            board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()]
                    .setPieces(move.getStart().getPieces());
            board.getCellOnTheBoardMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()].setPieces(null);
            doCastle(board, move.getEnd(), king);
        } else {
            board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()]
                    .setPieces(move.getStart().getPieces());
            board.getCellOnTheBoardMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()].setPieces(null);
        }
        updateKingStatus(board, move, enemyKing);
    }

    private synchronized void updateKingStatus(Board board, Move move, King king) {
        king.setInCheck(!kingRepository.checkIfTheKingIsInCheck(board,
                board.getCellOnTheBoardMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()],
                board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()],
                king));
    }

    public synchronized void undoMove(Board board, Move move, Pieces pieceOnStart, Pieces pieceOnEnd) {
        King enemyKing = (King) board.getKing(!board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()]
                .getPieces().isWhite()).getPieces();
        if (pieceOnStart instanceof Pawn && pieceOnEnd != null && pieceOnEnd.isWhite() == pieceOnStart.isWhite()) {
            board.getCellOnTheBoardMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()]
                    .setPieces(pieceOnStart);
            board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()]
                    .setPieces(null);
        }
        if (pieceOnStart instanceof King king && ((King) pieceOnStart).isHasBeenMoved() &&
                abs(move.getStart().getColumnCoordinate() - move.getEnd().getColumnCoordinate()) == 2) {
            board.getCellOnTheBoardMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()]
                    .setPieces(pieceOnStart);
            board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()].setPieces(pieceOnEnd);
            undoCastle(board, move.getEnd(), king);
        } else {
            board.getCellOnTheBoardMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()]
                    .setPieces(pieceOnStart);
            board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()].setPieces(pieceOnEnd);
        }
        updateKingStatus(board, move, enemyKing);
    }


    public String transformMoveToCorrectNotation(CellOnTheBoard start, CellOnTheBoard end, Board board) {
        Pieces pieceOnStart = start.getPieces();
        Pieces pieceOnEnd = end.getPieces();
        String notation = "";

        if (start.getPieces() == null) {
            System.out.println(start);
            System.out.println(board);
        }
        switch (start.getPieces()) {
            case King ignored -> notation += "K";
            case Queen ignored -> notation += "Q";
            case Rook ignored -> notation += "R";
            case Bishop ignored -> notation += "B";
            case Knight ignored -> notation += "N";
            case Pawn ignored -> notation += "";
        }

        if (!(start.getPieces() instanceof Pawn)) {

            for (int j = 0; j < 8; j++) {
                CellOnTheBoard endCell = board.getCellOnTheBoardMap()[start.getLineCoordinate()][j];
                if (start.getPieces() == endCell.getPieces() && start.getColumnCoordinate() != j) {
                    notation += transformColumnToLetters(start);
                    break;
                }
            }
        }
        if (end.getPieces() != null) {
            if (start.getPieces() instanceof Pawn) {
                notation += transformColumnToLetters(start);
            }
            notation += "x";
        }
        notation += transformColumnToLetters(end) + (end.getLineCoordinate() + 1);

        makeMove(board, new Move(board.getCellOnTheBoardMap()[start.getLineCoordinate()][start.getColumnCoordinate()], board.getCellOnTheBoardMap()[end.getLineCoordinate()][end.getColumnCoordinate()]));
        King king = (King) board.getKing(!pieceOnStart.isWhite()).getPieces();
        if (king.isInCheck()) {
            notation += "+";
        }
        undoMove(board, new Move(board.getCellOnTheBoardMap()[start.getLineCoordinate()][start.getColumnCoordinate()], board.getCellOnTheBoardMap()[end.getLineCoordinate()][end.getColumnCoordinate()]), pieceOnStart, pieceOnEnd);
        return notation;
    }

    public String transformColumnToLetters(CellOnTheBoard cell) {
        return switch (cell.getColumnCoordinate()) {
            case 0 -> "a";
            case 1 -> "b";
            case 2 -> "c";
            case 3 -> "d";
            case 4 -> "e";
            case 5 -> "f";
            case 6 -> "g";
            case 7 -> "h";
            default -> "";
        };
    }

}