package org.example.score;

import com.example.models.board.Board;
import com.example.models.pieces.Bishop;
import com.example.models.pieces.Knight;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CapturePiecesScoreTest {
    @Mock
    CapturePiecesScore capturePiecesScore;

    @Test
    void canAPieceBeCaptured() {
    }

    @Test
    void isTheAttackedPieceProtected() {
        Board board = new Board();

        board.getCellOnTheBoardMap()[2][2].setPieces(new Knight(false));
        board.getCellOnTheBoardMap()[3][3].setPieces(new Bishop(true));

//        System.out.println(capturePiecesScore.isTheAttackedPieceProtected(board, true));

    }
}