package org.example.score;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CenterControlScoreTest {
    @Mock
    MobilityScore mobilityScore;

    CenterControlScore centerControlScore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        centerControlScore = new CenterControlScore(mobilityScore);
    }

    @Test
    void numberOfCenterSquaresAttacked() {
        Board board = new Board();
        boolean isWhite = true;
        int result = centerControlScore.numberOfCenterSquaresAttacked(board, isWhite);
        assertEquals(14, result);
    }
}