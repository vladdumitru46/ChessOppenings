package org.example.score;

import com.example.models.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PawnStructureScoreTest {

    PawnStructureScore pawnStructureScore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        pawnStructureScore = new PawnStructureScore();
    }

    @Test
    void verifyPawnStructure() {
        Board board = new Board();
        boolean isWhite = true;

        int result = pawnStructureScore.verifyPawnStructure(board, isWhite);
        assertEquals(8, result);
    }

    @Test
    void isolatedPawns() {
        Board board = new Board();
        boolean isWhite = true;
        int result = pawnStructureScore.isolatedPawns(board, isWhite);
        assertEquals(0, result);
    }

    @Test
    void verifyIfThereAreDoublePawns() {
        Board board = new Board();
        boolean isWhite = true;
        int result = pawnStructureScore.verifyIfThereAreDoublePawns(board, isWhite);
        assertEquals(0, result);
    }
}
