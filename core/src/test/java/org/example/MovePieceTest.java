package org.example;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.board.Move;
import com.example.models.game.Game;
import com.example.models.game.GameStatus;
import com.example.models.pieces.*;
import org.example.board.BoardService;
import org.example.board.PieceService;
import org.example.game.GameService;
import org.example.miniMax.MiniMax;
import org.example.miniMax.score.Evaluation;
import org.example.repositoryes.pieces.PawnRepository;
import org.example.score.*;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.MockAwareVerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.event.MenuDragMouseListener;
import java.lang.reflect.Array;
import java.util.Arrays;

@SpringBootTest(classes = {MainApplication.class})
public class MovePieceTest {
    @Autowired
    PieceService pieceService;
    @Autowired
    PawnRepository pawnRepository;
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
        Arrays.stream(board.getCellOnTheBoardMap())
                .flatMap(Arrays::stream)
                .forEach(c -> c.setPieces(null));
        King king = new King(true);
        board.getCellOnTheBoardMap()[0][4].setPieces(king);
        board.getCellOnTheBoardMap()[5][4].setPieces(new King(false));
        board.getCellOnTheBoardMap()[4][0].setPieces(new Pawn(true));
        board.getCellOnTheBoardMap()[4][1].setPieces(new Pawn(false));
        CellOnTheBoard start = board.getCellOnTheBoardMap()[4][0];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[5][1];

        System.out.println(pawnRepository.canMove(board, start, end, (Pawn) start.getPieces()));
//        int id = boardService.save(board);
//        Game game = new Game(25, id, GameStatus.STARTED);
//        game.setWhitesTurn(true);
//        game.setPlayerColour("white");
//        gameService.addANewGame(game);

    }

}

