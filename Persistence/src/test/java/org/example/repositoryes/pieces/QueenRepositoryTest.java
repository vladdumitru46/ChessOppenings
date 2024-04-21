package org.example.repositoryes.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.Queen;
import com.example.models.pieces.Pawn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueenRepositoryTest {

    QueenRepository queenRepository = new QueenRepository();

    @Test
    void canMove_falseBlockedPiece() {

        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][3];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[3][2];
        assertFalse(queenRepository.canMove(board, start, end, (Queen) start.getPieces()));
    }

    @Test
    void canMove_falseBlockedNoDiagonal() {

        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][3];
        board.getCellOnTheBoardMap()[1][1].setPieces(null);
        CellOnTheBoard end = board.getCellOnTheBoardMap()[2][2];
        assertFalse(queenRepository.canMove(board, start, end, (Queen) start.getPieces()));
    }
    @Test
    void canMove_falsePieceInTheWay() {

        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][3];
        board.getCellOnTheBoardMap()[1][2].setPieces(null);
        board.getCellOnTheBoardMap()[2][1].setPieces(new Pawn(start.getPieces().isWhite()));
        CellOnTheBoard end = board.getCellOnTheBoardMap()[1][1];
        assertFalse(queenRepository.canMove(board, start, end, (Queen) start.getPieces()));
    }

    @Test
    void canMove_falseBlockedNoStraightLine() {

        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][3];
        board.getCellOnTheBoardMap()[1][3].setPieces(null);
        CellOnTheBoard end = board.getCellOnTheBoardMap()[2][4];
        assertFalse(queenRepository.canMove(board, start, end, (Queen) start.getPieces()));
    }

    @Test
    void canMove_trueDiagonal() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][3];
        board.getCellOnTheBoardMap()[1][2].setPieces(null);
        CellOnTheBoard end = board.getCellOnTheBoardMap()[2][1];
        assertTrue(queenRepository.canMove(board, start, end, (Queen) start.getPieces()));
    }
    @Test
    void canMove_trueStraightLine() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][3];
        board.getCellOnTheBoardMap()[1][3].setPieces(null);
        CellOnTheBoard end = board.getCellOnTheBoardMap()[2][3];
        assertTrue(queenRepository.canMove(board, start, end, (Queen) start.getPieces()));
    }
}