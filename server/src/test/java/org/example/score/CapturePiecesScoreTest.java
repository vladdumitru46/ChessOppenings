package org.example.score;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.board.Move;
import com.example.models.pieces.Bishop;
import com.example.models.pieces.Knight;
import com.example.models.pieces.Pawn;
import org.example.board.PieceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CapturePiecesScoreTest {
    @Mock
    MobilityScore mobilityScore;

    @Mock
    PieceService pieceService;

    CapturePiecesScore capturePiecesScore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        capturePiecesScore = new CapturePiecesScore(mobilityScore, pieceService);
    }

    @Test
    void canAPieceBeCaptured() {
        Board board = new Board();

        int result = capturePiecesScore.canAPieceBeCaptured(board, true);
        assertEquals(0, result);
    }

    @Test
    void isTheAttackedPieceProtected() {
        Board board = new Board();
        board.getCellOnTheBoardMap()[2][2].setPieces(new Pawn(false));
        CellOnTheBoard start = board.getCellOnTheBoardMap()[1][1];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[2][2];
        Move move = new Move(start, end);

        int result = capturePiecesScore.isTheAttackedPieceProtected(board, true, move);
        assertEquals(0, result);
    }

    @Test
    void canTheQueenBeCaptured() {
        Board board = new Board();
        boolean result = capturePiecesScore.canTheQueenBeCaptured(board, true);
        assertFalse(result);
    }
}