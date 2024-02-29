package org.example;

import com.example.models.board.Board;
import com.example.models.pieces.*;
import org.example.board.BoardService;
import org.example.board.PieceService;
import org.example.game.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MainApplication.class})
public class MovePieceTest {
    @Autowired
    PieceService pieceService;

    Board board = new Board();

    @Autowired
    BoardService boardService;

    @Autowired
    GameService gameService;
    @Test
    public void isCheckMateIn2Test() {
//        board.getCellOnTheBoardMap()[2][1].setPieces(new Queen(false));
//        board.getCellOnTheBoardMap()[2][4].setPieces(new Queen(false));
        System.out.println(board);
//        try {
//            System.out.println(gameService.getAfterPlayerId(25));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

////        board.getCellOnTheBoardMap()[2][4].setPieces(new Queen(false));
//        System.out.println(pieceService.canAPieceBeCaptured(board, false));
    }


}
