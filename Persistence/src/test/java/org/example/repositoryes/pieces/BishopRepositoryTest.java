package org.example.repositoryes.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.Bishop;
import com.example.models.pieces.Pawn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BishopRepositoryTest {

    BishopRepository bishopRepository = new BishopRepository();
    @Test
    void canMove_falseBlockedPiece() {

        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][2];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[3][2];
        assertFalse(bishopRepository.canMove(board, start, end, (Bishop) start.getPieces()));
    }

    @Test
    void canMove_falseBlockedNoDiagonal() {

        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][2];
        board.getCellOnTheBoardMap()[1][1].setPieces(null);
        CellOnTheBoard end = board.getCellOnTheBoardMap()[2][2];
        assertFalse(bishopRepository.canMove(board, start, end, (Bishop) start.getPieces()));
    }
    @Test
    void canMove_falsePieceInTheWay() {

        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][2];
        board.getCellOnTheBoardMap()[1][3].setPieces(null);
        board.getCellOnTheBoardMap()[2][4].setPieces(new Pawn(start.getPieces().isWhite()));
        CellOnTheBoard end = board.getCellOnTheBoardMap()[3][5];
        assertFalse(bishopRepository.canMove(board, start, end, (Bishop) start.getPieces()));
    }

    @Test
    void canMove_true() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][2];
        board.getCellOnTheBoardMap()[1][1].setPieces(null);
        CellOnTheBoard end = board.getCellOnTheBoardMap()[2][0];
        assertTrue(bishopRepository.canMove(board, start, end, (Bishop) start.getPieces()));
    }
}