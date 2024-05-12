package org.example.score;

import com.example.models.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;

class KingSafetyScoreTest {

    @Mock
    MobilityScore mobilityScore;

    KingSafetyScore kingSafetyScore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        kingSafetyScore = new KingSafetyScore(mobilityScore);
    }

    @Test
    void isTheEnemyKingInCheck() {
        Board board = new Board();
        boolean isWhite = true;
        boolean result = kingSafetyScore.isTheEnemyKingInCheck(board, isWhite);
        assertFalse(result);
    }

    @Test
    void checkMateCheck() {
        Board board = new Board();
        boolean isWhite = true;
        boolean result = kingSafetyScore.checkMateCheck(board, isWhite);
        assertFalse(result);
    }
}
