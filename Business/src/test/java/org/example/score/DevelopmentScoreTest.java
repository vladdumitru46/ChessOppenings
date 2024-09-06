package org.example.score;

import com.example.models.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class DevelopmentScoreTest {
    DevelopmentScore developmentScore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        developmentScore = new DevelopmentScore();
    }

    @Test
    void developmentBonus() {
        Board board = new Board();
        boolean isWhite = true;
        int result = developmentScore.developmentBonus(board, isWhite);
        assertEquals(0, result);
    }
}