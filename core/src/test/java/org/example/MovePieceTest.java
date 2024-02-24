package org.example;

import com.example.models.board.Board;
import com.example.models.pieces.*;
import org.example.board.BoardService;
import org.example.board.PieceService;
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

    @Test
    public void isCheckMateIn2Test() {
        board.getCellOnTheBoardMap()[3][1].setPieces(new Queen(true));
//        board.getCellOnTheBoardMap()[2][4].setPieces(new Queen(false));
        System.out.println(pieceService.numberOfCenterSquaresAttacked(board, true));
    }


}
