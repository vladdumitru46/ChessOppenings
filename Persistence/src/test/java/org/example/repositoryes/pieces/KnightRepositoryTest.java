package org.example.repositoryes.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.Bishop;
import com.example.models.pieces.Knight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightRepositoryTest {

    KnightRepository knightRepository = new KnightRepository();

    @Test
    void canMove_falseNotLShaped() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][1];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[3][2];
        assertFalse(knightRepository.canMove(board, start, end, (Knight) start.getPieces()));
    }
    @Test
    void canMove_true() {
        Board board = new Board();
        CellOnTheBoard start = board.getCellOnTheBoardMap()[0][1];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[2][0];
        assertTrue(knightRepository.canMove(board, start, end, (Knight) start.getPieces()));
    }
}