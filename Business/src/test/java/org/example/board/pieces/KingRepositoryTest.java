package org.example.board.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.King;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KingRepositoryTest {


    KingService kingRepository = new KingService();

    @Test
    void canMove_falseBlockedPiece() {

        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][4];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[1][4];
        assertFalse(kingRepository.canMove(board, start, end, (King) start.getPieces()));
    }

    @Test
    void canMove_trueDiagonal() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][4];
        board.getCellOnTheBoardMap()[1][3].setPieces(null);
        CellOnTheBoard end = board.getCellOnTheBoardMap()[1][3];
        assertTrue(kingRepository.canMove(board, start, end, (King) start.getPieces()));
    }
    @Test
    void canMove_trueStraightLine() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][4];
        board.getCellOnTheBoardMap()[1][4].setPieces(null);
        CellOnTheBoard end = board.getCellOnTheBoardMap()[1][4];
        assertTrue(kingRepository.canMove(board, start, end, (King) start.getPieces()));
    }

    @Test
    void canCastle_falseBlockedPath() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][4];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[0][2];
        assertFalse(kingRepository.canCastle(board, start, end, (King) start.getPieces()));
    }

    @Test
    void canCastle_falsePieceMoved() {
        Board board = new Board();
        King king = (King) board.getCellOnTheBoardMap()[0][4].getPieces();
        king.setHasBeenMoved(true);
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][4];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[0][2];
        assertFalse(kingRepository.canCastle(board, start, end, (King) start.getPieces()));
    }
    @Test
    void canCastle_true() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][4];
        board.getCellOnTheBoardMap()[0][3].setPieces(null);
        board.getCellOnTheBoardMap()[0][2].setPieces(null);
        board.getCellOnTheBoardMap()[0][1].setPieces(null);
        CellOnTheBoard end = board.getCellOnTheBoardMap()[0][2];
        assertTrue(kingRepository.canCastle(board, start, end, (King) start.getPieces()));
    }


}