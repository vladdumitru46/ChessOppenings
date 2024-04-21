package org.example.repositoryes.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.Pawn;
import com.example.models.pieces.Rook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RookRepositoryTest {

    RookRepository rookRepository = new RookRepository();

    @Test
    void canMove_falseBlockedPiece() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][0];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[0][2];
        assertFalse(rookRepository.canMove(board, start, end, (Rook) start.getPieces()));
    }
    @Test
    void canMove_falsePieceInTheWay() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][0];
        board.getCellOnTheBoardMap()[1][0].setPieces(null);
        board.getCellOnTheBoardMap()[2][0].setPieces(new Pawn(start.getPieces().isWhite()));
        CellOnTheBoard end = board.getCellOnTheBoardMap()[3][0];
        assertFalse(rookRepository.canMove(board, start, end, (Rook) start.getPieces()));
    }

    @Test
    void canMove_falseBlockedNoStraightLine() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][0];
        board.getCellOnTheBoardMap()[0][1].setPieces(null);
        CellOnTheBoard end = board.getCellOnTheBoardMap()[2][4];
        assertFalse(rookRepository.canMove(board, start, end, (Rook) start.getPieces()));
    }
    @Test
    void canMove_trueStraightLine() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][0];
        board.getCellOnTheBoardMap()[1][0].setPieces(null);
        CellOnTheBoard end = board.getCellOnTheBoardMap()[3][0];
        assertTrue(rookRepository.canMove(board, start, end, (Rook) start.getPieces()));
    }
}