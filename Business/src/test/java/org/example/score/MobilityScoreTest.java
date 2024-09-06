package org.example.score;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import org.example.board.pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MobilityScoreTest {

    @Mock
    PawnService pawnRepository;

    @Mock
    RookService rookRepository;

    @Mock
    KnightService knightRepository;

    @Mock
    BishopService bishopService;

    @Mock
    QueenService queenRepository;

    @Mock
    KingService kingRepository;

    MobilityScore mobilityScore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mobilityScore = new MobilityScore(pawnRepository, rookRepository, knightRepository, bishopService, queenRepository, kingRepository);
    }

    @Test
    void getAllPossibleMoves() {
        Board board = new Board();
        boolean isWhite = true;
        int result = mobilityScore.getAllPossibleMoves(board, isWhite).size();
        assertEquals(0, result);
    }


    @Test
    void getAllPossibleMovesForASpecificPiece() {
        Board board = new Board();
        CellOnTheBoard startCell = board.getCellOnTheBoardMap()[1][1];
        int result = mobilityScore.getAllPossibleMovesForASpecificPiece(board, startCell).size();
        assertEquals(0, result);
    }
}
