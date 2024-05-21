package org.example;

import com.example.models.board.Board;
import com.example.models.board.Move;
import com.example.models.pieces.*;
import org.example.board.BoardService;
import org.example.board.PieceService;
import org.example.game.GameService;
import org.example.score.*;
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
        board.getCellOnTheBoardMap()[0][5].setPieces(null);
        board.getCellOnTheBoardMap()[0][6].setPieces(null);
        Pieces piecesOnStart = board.getCellOnTheBoardMap()[0][4].getPieces();
        Pieces piecesOnEnd = board.getCellOnTheBoardMap()[0][6].getPieces();
        Move move = new Move(board.getCellOnTheBoardMap()[0][4], board.getCellOnTheBoardMap()[0][6]);
        pieceService.makeMove(board, move);
        System.out.println(board);
        pieceService.undoMove(board, move, piecesOnStart, piecesOnEnd);
        System.out.println(board);
    }


}
