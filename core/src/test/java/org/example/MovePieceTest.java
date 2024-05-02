package org.example;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.*;
import org.example.board.BoardService;
import org.example.board.PieceService;
import org.example.game.GameService;
import org.example.score.CapturePiecesScore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MainApplication.class})
public class MovePieceTest {
    @Autowired
    PieceService pieceService;
    @Autowired
    CapturePiecesScore capturePiecesScore;

    Board board = new Board();

    @Autowired
    BoardService boardService;

    @Autowired
    GameService gameService;

    @Test
    public void isCheckMateIn2Test() {
        board.getCellOnTheBoardMap()[2][2].setPieces(new Knight(true));
        board.getCellOnTheBoardMap()[3][4].setPieces(new Pawn(true));
        board.getCellOnTheBoardMap()[5][5].setPieces(new Pawn(false));
        board.getCellOnTheBoardMap()[4][1].setPieces(new Pawn(false));

        board.getCellOnTheBoardMap()[0][1].setPieces(null);
        board.getCellOnTheBoardMap()[1][4].setPieces(null);
        board.getCellOnTheBoardMap()[6][5].setPieces(null);
        board.getCellOnTheBoardMap()[6][1].setPieces(null);

        System.out.println(board);

        System.out.println(capturePiecesScore.canAPieceBeCaptured(board, true));
    }


}
