package org.example.repositoryes.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.Bishop;
import com.example.models.pieces.Pawn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnRepositoryTest {

    PawnRepository pawnRepository = new PawnRepository();

    @Test
    void canMove_falseToManySquaresForward() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[1][2];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[4][2];
        assertFalse(pawnRepository.canMove(board, start, end, (Pawn) start.getPieces()));
    }
    @Test
    void canMove_falseTriesToGoDiagonal() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[1][2];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[2][1];
        assertFalse(pawnRepository.canMove(board, start, end, (Pawn) start.getPieces()));
    }
    @Test
    void canMove_falseTriesToGo2ForwardWhenNotOnStartLine() {
        Board board = new Board();
        board.getCellOnTheBoardMap()[2][2].setPieces(new Pawn(true));
        CellOnTheBoard start = board.getCellOnTheBoardMap()[2][2];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[4][2];
        assertFalse(pawnRepository.canMove(board, start, end, (Pawn) start.getPieces()));
    }
    @Test
    void canMove_trueOneSquare() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[1][2];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[2][2];
        assertTrue(pawnRepository.canMove(board, start, end, (Pawn) start.getPieces()));
    }
    @Test
    void canMove_trueTwoSquare() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[1][2];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[3][2];
        assertTrue(pawnRepository.canMove(board, start, end, (Pawn) start.getPieces()));
    }
    @Test
    void canMove_trueCapturePiece() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[1][2];
        board.getCellOnTheBoardMap()[2][1].setPieces(new Pawn(false));
        CellOnTheBoard end = board.getCellOnTheBoardMap()[2][1];
        assertTrue(pawnRepository.canMove(board, start, end, (Pawn) start.getPieces()));
    }
}