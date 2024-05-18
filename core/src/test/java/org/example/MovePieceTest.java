package org.example;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.game.Game;
import com.example.models.game.GameStatus;
import com.example.models.pieces.*;
import org.example.board.BoardService;
import org.example.board.PieceService;
import org.example.game.GameService;
import org.example.miniMax.MiniMax;
import org.example.miniMax.score.Evaluation;
import org.example.score.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.lang.model.element.ElementVisitor;

@SpringBootTest(classes = {MainApplication.class})
public class MovePieceTest {
    @Autowired
    PieceService pieceService;
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
        Evaluation evaluation = new Evaluation(capturePiecesScore, centerControlScore, developmentScore, kingSafetyScore, mobilityScore, pawnStructureScore);
        MiniMax miniMax = new MiniMax(board, 2, true, mobilityScore, pieceService, kingSafetyScore, evaluation);
        System.out.println(miniMax.getBestMove());
    }


}
