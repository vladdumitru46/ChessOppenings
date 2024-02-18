package org.example;

import com.example.models.board.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MainApplication.class})
public class MovePiecesTest {

    @Autowired
    PieceService pieceService;
    Board board = new Board();

    @Test
    public void testAllPossibleMoves() {
//        pieceService.getAllPossibleMoves(board)
//                .forEach(System.out::println);
//
//        System.out.println("White");
//        pieceService.getAllPossibleMovesForWhite(board)
//                .forEach(System.out::println);
//
//        System.out.println("Black");
//        pieceService.getAllPossibleMovesForBlack(board)
//                .forEach(System.out::println);

        System.out.println(pieceService.numberOfPossibleMoves(board));
        System.out.println(pieceService.numberOfPossibleMovesForWhite(board));
        System.out.println(pieceService.numberOfPossibleMovesForBlack(board));

    }
}
