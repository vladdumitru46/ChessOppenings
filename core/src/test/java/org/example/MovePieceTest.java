package org.example;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.game.Game;
import com.example.models.game.GameStatus;
import com.example.models.pieces.*;
import org.example.board.BoardService;
import org.example.board.PieceService;
import org.example.game.GameService;
import org.example.score.CapturePiecesScore;
import org.example.score.MobilityScore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MainApplication.class})
public class MovePieceTest {
    @Autowired
    PieceService pieceService;
    @Autowired
    CapturePiecesScore capturePiecesScore;

    @Autowired
    MobilityScore mobilityScore;
    Board board = new Board();

    @Autowired
    BoardService boardService;

    @Autowired
    GameService gameService;

    //TODO testeaza si vezi de ce nu ia promotion
    @Test
    public void isCheckMateIn2Test() {
//        String a = "14 34;64 44, 05 14;73 46, 14 05;46 24, 15 24;75 31, 05 14;76 55, 14 05;71 52, 05 14;52 33, 14 05;67 47, 05 32;74 75, 32 05;33 12, 03 12;31 13, 12 13;75 76, 05 32;63 43, 32 43;72 54, 43 54;65 54, 13 73;70 73, 02 13;55 34, 13 22;73 63, 22 31;66 46, 31 42;";
//        System.out.println(a.length());
        try {
//            Board board = new Board();
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    board.getCellOnTheBoardMap()[i][j] = new CellOnTheBoard(null, i, j);
//                }
//            }
//            board.getCellOnTheBoardMap()[1][1].setPieces(new Pawn(false));
//            board.getCellOnTheBoardMap()[7][1].setPieces(new King(false));
//            board.getCellOnTheBoardMap()[5][0].setPieces(new King(true));
//            board.getCellOnTheBoardMap()[5][2].setPieces(new Queen(true));
//
//            int bid = boardService.save(board);
//
//            Game game = new Game(26, bid, GameStatus.STARTED);
//            gameService.addANewGame(game);

            board = boardService.findById(735);
            mobilityScore.getAllPossibleMoves(board, false).forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
