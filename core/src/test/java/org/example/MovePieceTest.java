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
        try {
            board = boardService.findById(27);
            System.out.println(board);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
