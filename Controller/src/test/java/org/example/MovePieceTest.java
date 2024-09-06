package org.example;

import com.example.models.board.Board;
import org.example.board.BoardService;
import org.example.board.PieceService;
import org.example.game.GameService;
import org.example.board.pieces.PawnService;
import org.example.score.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MainApplication.class})
public class MovePieceTest {
    @Autowired
    PieceService pieceService;
    @Autowired
    PawnService pawnRepository;
    @Autowired
    CapturePiecesScore capturePiecesScore;
    @Autowired
    KingSafetyScore kingSafetyScore;
    @Autowired
    CenterControlScore centerControlScore;
    @Autowired
    DevelopmentScore developmentScore;
    @Autowired
    PawnStructureScore pawnStructureScore;

    @Autowired
    MobilityScore mobilityScore;

    Board board = new Board();

    @Autowired
    BoardService boardService;

    @Autowired
    GameService gameService;

    @Test
    public void isCheckMateIn2Test() {
//        Arrays.stream(board.getCellOnTheBoardMap())
//                .flatMap(Arrays::stream)
//                .forEach(c -> c.setPieces(null));
//        King king = new King(true);
//        board.getCellOnTheBoardMap()[0][4].setPieces(king);
//        board.getCellOnTheBoardMap()[5][4].setPieces(new King(false));
//        board.getCellOnTheBoardMap()[4][0].setPieces(new Pawn(false));
//        board.getCellOnTheBoardMap()[4][1].setPieces(new Pawn(true));
//        int id = boardService.save(board);
//        Game game = new Game(25, id, GameStatus.STARTED);
//        game.setWhitesTurn(true);
//        game.setPlayerColour("white");
//        game.setMoves("31 41;60 40, ");
//        game.setWhiteMove(", b5");
//        game.setBlackMove(", a5");
//        game.setMoveNumber(2);
//        gameService.addANewGame(game);
//        try{
//            Game game = gameService.getGameById(421);
//            Board board1 = boardService.findById(game.getBoardId());
////            System.out.println(capturePiecesScore.canTheQueenBeCaptured(board1, false));
//            King king = (King) board1.getKing(true).getPieces();
//            System.out.println(king.isHasBeenMoved());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

    }

}

