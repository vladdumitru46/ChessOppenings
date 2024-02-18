package org.example;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.*;
import org.example.miniMax.MiniMax;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest(classes = {MainApplication.class})
public class MovePieceTest {
    @Autowired
    PieceService pieceService;

    Board board = new Board();

    @Autowired
     BoardService boardService;

//    @Test
//    public void testAllPossibleMoves() {
//        CellOnTheBoard[][] cellOnTheBoardMap = new CellOnTheBoard[8][8];
//        cellOnTheBoardMap[0][0] = new CellOnTheBoard(new Rook(true), 0, 0);
//        cellOnTheBoardMap[0][1] = new CellOnTheBoard(new Knight(true), 0, 1);
//        cellOnTheBoardMap[0][2] = new CellOnTheBoard(new Bishop(true), 0, 2);
//        cellOnTheBoardMap[0][3] = new CellOnTheBoard(new Queen(true), 0, 3);
//        cellOnTheBoardMap[0][4] = new CellOnTheBoard(new King(true), 0, 4);
//        cellOnTheBoardMap[0][5] = new CellOnTheBoard(new Bishop(true), 0, 5);
//        cellOnTheBoardMap[0][6] = new CellOnTheBoard(new Knight(true), 0, 6);
//        cellOnTheBoardMap[0][7] = new CellOnTheBoard(new Rook(true), 0, 7);
//
//        cellOnTheBoardMap[1][0] = new CellOnTheBoard(new Pawn(true), 1, 0);
//        cellOnTheBoardMap[1][1] = new CellOnTheBoard(new Pawn(true), 1, 1);
//        cellOnTheBoardMap[1][2] = new CellOnTheBoard(new Pawn(true), 1, 2);
//        cellOnTheBoardMap[1][3] = new CellOnTheBoard(new Pawn(true), 1, 3);
//        cellOnTheBoardMap[1][4] = new CellOnTheBoard(new Pawn(true), 1, 4);
//        cellOnTheBoardMap[1][5] = new CellOnTheBoard(new Pawn(true), 1, 5);
//        cellOnTheBoardMap[1][6] = new CellOnTheBoard(new Pawn(true), 1, 6);
//        cellOnTheBoardMap[1][7] = new CellOnTheBoard(new Pawn(true), 1, 7);
//
//        cellOnTheBoardMap[7][0] = new CellOnTheBoard(new Rook(false), 7, 0);
//        cellOnTheBoardMap[7][1] = new CellOnTheBoard(new Knight(false), 7, 1);
//        cellOnTheBoardMap[7][2] = new CellOnTheBoard(new Bishop(false), 7, 2);
//        cellOnTheBoardMap[7][3] = new CellOnTheBoard(new Queen(false), 7, 3);
//        cellOnTheBoardMap[7][4] = new CellOnTheBoard(new King(false), 7, 4);
//        cellOnTheBoardMap[7][5] = new CellOnTheBoard(new Bishop(false), 7, 5);
//        cellOnTheBoardMap[7][6] = new CellOnTheBoard(new Knight(false), 7, 6);
//        cellOnTheBoardMap[7][7] = new CellOnTheBoard(new Rook(false), 7, 7);
//
//        cellOnTheBoardMap[6][0] = new CellOnTheBoard(new Pawn(false), 6, 0);
//        cellOnTheBoardMap[6][1] = new CellOnTheBoard(new Pawn(false), 6, 1);
//        cellOnTheBoardMap[6][2] = new CellOnTheBoard(new Pawn(false), 6, 2);
//        cellOnTheBoardMap[6][3] = new CellOnTheBoard(new Pawn(false), 6, 3);
//        cellOnTheBoardMap[6][4] = new CellOnTheBoard(new Pawn(false), 6, 4);
//        cellOnTheBoardMap[6][5] = new CellOnTheBoard(new Pawn(false), 6, 5);
//        cellOnTheBoardMap[6][6] = new CellOnTheBoard(new Pawn(false), 6, 6);
//        cellOnTheBoardMap[6][7] = new CellOnTheBoard(new Pawn(false), 6, 7);
//
//        for (int i = 2; i < 6; i++) {
//            for (int j = 0; j < 8; j++) {
//                cellOnTheBoardMap[i][j] = new CellOnTheBoard(null, i, j);
//            }
//        }
//
//        board = new Board(cellOnTheBoardMap);
//
//        System.out.println(pieceService.getAllPossibleMoves(board));
//    }
//
//    @Test
//    public void numberOfPiecesThatCanBeCapturedTest() {
//        CellOnTheBoard[][] cellOnTheBoardMap = new CellOnTheBoard[8][8];
//        cellOnTheBoardMap[0][0] = new CellOnTheBoard(new Rook(true), 0, 0);
//        cellOnTheBoardMap[0][1] = new CellOnTheBoard(new Knight(true), 0, 1);
//        cellOnTheBoardMap[0][2] = new CellOnTheBoard(null, 0, 2);
//        cellOnTheBoardMap[0][3] = new CellOnTheBoard(new Queen(true), 0, 3);
//        cellOnTheBoardMap[0][4] = new CellOnTheBoard(new King(true), 0, 4);
//        cellOnTheBoardMap[0][5] = new CellOnTheBoard(new Bishop(true), 0, 5);
//        cellOnTheBoardMap[0][6] = new CellOnTheBoard(new Knight(true), 0, 6);
//        cellOnTheBoardMap[0][7] = new CellOnTheBoard(new Rook(true), 0, 7);
//
//        cellOnTheBoardMap[1][0] = new CellOnTheBoard(new Pawn(true), 1, 0);
//        cellOnTheBoardMap[1][1] = new CellOnTheBoard(new Pawn(true), 1, 1);
//        cellOnTheBoardMap[1][2] = new CellOnTheBoard(new Pawn(true), 1, 2);
//        cellOnTheBoardMap[1][3] = new CellOnTheBoard(new Pawn(true), 1, 3);
//        cellOnTheBoardMap[1][4] = new CellOnTheBoard(new Pawn(true), 1, 4);
//        cellOnTheBoardMap[1][5] = new CellOnTheBoard(new Pawn(true), 1, 5);
//        cellOnTheBoardMap[1][6] = new CellOnTheBoard(new Pawn(true), 1, 6);
//        cellOnTheBoardMap[1][7] = new CellOnTheBoard(new Pawn(true), 1, 7);
//
//        cellOnTheBoardMap[7][0] = new CellOnTheBoard(new Rook(false), 7, 0);
//        cellOnTheBoardMap[7][1] = new CellOnTheBoard(new Knight(false), 7, 1);
//        cellOnTheBoardMap[7][2] = new CellOnTheBoard(new Bishop(false), 7, 2);
//        cellOnTheBoardMap[7][3] = new CellOnTheBoard(new Queen(false), 7, 3);
//        cellOnTheBoardMap[7][4] = new CellOnTheBoard(new King(false), 7, 4);
//        cellOnTheBoardMap[7][5] = new CellOnTheBoard(new Bishop(false), 7, 5);
//        cellOnTheBoardMap[7][6] = new CellOnTheBoard(new Knight(false), 7, 6);
//        cellOnTheBoardMap[7][7] = new CellOnTheBoard(new Rook(false), 7, 7);
//
//        cellOnTheBoardMap[6][0] = new CellOnTheBoard(new Pawn(false), 6, 0);
//        cellOnTheBoardMap[6][1] = new CellOnTheBoard(new Pawn(false), 6, 1);
//        cellOnTheBoardMap[6][2] = new CellOnTheBoard(new Pawn(false), 6, 2);
//        cellOnTheBoardMap[6][3] = new CellOnTheBoard(new Pawn(false), 6, 3);
//        cellOnTheBoardMap[6][4] = new CellOnTheBoard(new Pawn(false), 6, 4);
//        cellOnTheBoardMap[6][5] = new CellOnTheBoard(new Pawn(false), 6, 5);
//        cellOnTheBoardMap[6][6] = new CellOnTheBoard(new Pawn(false), 6, 6);
//        cellOnTheBoardMap[6][7] = new CellOnTheBoard(new Pawn(false), 6, 7);
//
//        for (int i = 2; i < 6; i++) {
//            for (int j = 0; j < 8; j++) {
//                cellOnTheBoardMap[i][j] = new CellOnTheBoard(null, i, j);
//            }
//        }
//
//        cellOnTheBoardMap[6][1] = new CellOnTheBoard(new Bishop(true), 6, 1);
//
//        board = new Board(cellOnTheBoardMap);
//
//        Assertions.assertEquals(pieceService.canAWhitePieceBeCaptured(board), 2);
//    }
//
//    @Test
//    public void isCheckMateIn1Test() {
//        CellOnTheBoard[][] cellOnTheBoardMap = new CellOnTheBoard[8][8];
//        cellOnTheBoardMap[0][0] = new CellOnTheBoard(new Rook(true), 0, 0);
//        cellOnTheBoardMap[0][1] = new CellOnTheBoard(new Knight(true), 0, 1);
//        cellOnTheBoardMap[0][2] = new CellOnTheBoard(new Bishop(true), 0, 2);
//        cellOnTheBoardMap[0][3] = new CellOnTheBoard(new Queen(true), 0, 3);
//        cellOnTheBoardMap[0][4] = new CellOnTheBoard(new King(true), 0, 4);
//        cellOnTheBoardMap[0][5] = new CellOnTheBoard(new Bishop(true), 0, 5);
//        cellOnTheBoardMap[0][6] = new CellOnTheBoard(new Knight(true), 0, 6);
//        cellOnTheBoardMap[0][7] = new CellOnTheBoard(new Rook(true), 0, 7);
//
//        cellOnTheBoardMap[1][0] = new CellOnTheBoard(new Pawn(true), 1, 0);
//        cellOnTheBoardMap[1][1] = new CellOnTheBoard(new Pawn(true), 1, 1);
//        cellOnTheBoardMap[1][2] = new CellOnTheBoard(new Pawn(true), 1, 2);
//        cellOnTheBoardMap[1][3] = new CellOnTheBoard(new Pawn(true), 1, 3);
//        cellOnTheBoardMap[1][4] = new CellOnTheBoard(new Pawn(true), 1, 4);
//        cellOnTheBoardMap[1][5] = new CellOnTheBoard(new Pawn(true), 1, 5);
//        cellOnTheBoardMap[1][6] = new CellOnTheBoard(new Pawn(true), 1, 6);
//        cellOnTheBoardMap[1][7] = new CellOnTheBoard(new Pawn(true), 1, 7);
//
//        cellOnTheBoardMap[7][0] = new CellOnTheBoard(new Rook(false), 7, 0);
//        cellOnTheBoardMap[7][1] = new CellOnTheBoard(new Knight(false), 7, 1);
//        cellOnTheBoardMap[7][2] = new CellOnTheBoard(new Bishop(false), 7, 2);
//        cellOnTheBoardMap[7][3] = new CellOnTheBoard(new Queen(false), 7, 3);
//        cellOnTheBoardMap[7][4] = new CellOnTheBoard(new King(false), 7, 4);
//        cellOnTheBoardMap[7][5] = new CellOnTheBoard(new Bishop(false), 7, 5);
//        cellOnTheBoardMap[7][6] = new CellOnTheBoard(new Knight(false), 7, 6);
//        cellOnTheBoardMap[7][7] = new CellOnTheBoard(new Rook(false), 7, 7);
//
//        cellOnTheBoardMap[6][0] = new CellOnTheBoard(new Pawn(false), 6, 0);
//        cellOnTheBoardMap[6][1] = new CellOnTheBoard(new Pawn(false), 6, 1);
//        cellOnTheBoardMap[6][2] = new CellOnTheBoard(new Pawn(false), 6, 2);
//        cellOnTheBoardMap[6][3] = new CellOnTheBoard(new Pawn(false), 6, 3);
//        cellOnTheBoardMap[6][4] = new CellOnTheBoard(new Pawn(false), 6, 4);
//        cellOnTheBoardMap[6][5] = new CellOnTheBoard(new Pawn(false), 6, 5);
//        cellOnTheBoardMap[6][6] = new CellOnTheBoard(new Pawn(false), 6, 6);
//        cellOnTheBoardMap[6][7] = new CellOnTheBoard(new Pawn(false), 6, 7);
//
//        for (int i = 2; i < 6; i++) {
//            for (int j = 0; j < 8; j++) {
//                cellOnTheBoardMap[i][j] = new CellOnTheBoard(null, i, j);
//            }
//        }
//        //bc4
//        //qf3
//
//        cellOnTheBoardMap[3][2] = new CellOnTheBoard(new Bishop(true), 3, 2);
//        cellOnTheBoardMap[2][5] = new CellOnTheBoard(new Queen(true), 2, 5);
//
//        board = new Board(cellOnTheBoardMap);
//
//        MiniMax miniMax = new MiniMax();
//
////        Assertions.assertTrue(miniMax.isCheckMateIn1(board, true, pieceService));
//        System.out.println(miniMax.getBestMove(board, 3, true, pieceService));
//    }

    @Test
    public void isCheckMateIn2Test() {
//        CellOnTheBoard[][] cellOnTheBoardMap = new CellOnTheBoard[8][8];
//        cellOnTheBoardMap[0][0] = new CellOnTheBoard(new Rook(true), 0, 0);
//        cellOnTheBoardMap[0][1] = new CellOnTheBoard(new Knight(true), 0, 1);
//        cellOnTheBoardMap[0][2] = new CellOnTheBoard(new Bishop(true), 0, 2);
//        cellOnTheBoardMap[0][3] = new CellOnTheBoard(new Queen(true), 0, 3);
//        cellOnTheBoardMap[0][4] = new CellOnTheBoard(new King(true), 0, 4);
//        cellOnTheBoardMap[0][5] = new CellOnTheBoard(new Bishop(true), 0, 5);
//        cellOnTheBoardMap[0][6] = new CellOnTheBoard(new Knight(true), 0, 6);
//        cellOnTheBoardMap[0][7] = new CellOnTheBoard(new Rook(true), 0, 7);
//
//        cellOnTheBoardMap[1][0] = new CellOnTheBoard(new Pawn(true), 1, 0);
//        cellOnTheBoardMap[1][1] = new CellOnTheBoard(new Pawn(true), 1, 1);
//        cellOnTheBoardMap[1][2] = new CellOnTheBoard(new Pawn(true), 1, 2);
//        cellOnTheBoardMap[1][3] = new CellOnTheBoard(new Pawn(true), 1, 3);
//        cellOnTheBoardMap[1][4] = new CellOnTheBoard(null, 1, 4);
//        cellOnTheBoardMap[1][5] = new CellOnTheBoard(new Pawn(true), 1, 5);
//        cellOnTheBoardMap[1][6] = new CellOnTheBoard(new Pawn(true), 1, 6);
//        cellOnTheBoardMap[1][7] = new CellOnTheBoard(new Pawn(true), 1, 7);
//
//        cellOnTheBoardMap[7][0] = new CellOnTheBoard(new Rook(false), 7, 0);
//        cellOnTheBoardMap[7][1] = new CellOnTheBoard(new Knight(false), 7, 1);
//        cellOnTheBoardMap[7][2] = new CellOnTheBoard(new Bishop(false), 7, 2);
//        cellOnTheBoardMap[7][3] = new CellOnTheBoard(new Queen(false), 7, 3);
//        cellOnTheBoardMap[7][4] = new CellOnTheBoard(new King(false), 7, 4);
//        cellOnTheBoardMap[7][5] = new CellOnTheBoard(new Bishop(false), 7, 5);
//        cellOnTheBoardMap[7][6] = new CellOnTheBoard(new Knight(false), 7, 6);
//        cellOnTheBoardMap[7][7] = new CellOnTheBoard(new Rook(false), 7, 7);
//
//        cellOnTheBoardMap[6][0] = new CellOnTheBoard(new Pawn(false), 6, 0);
//        cellOnTheBoardMap[6][1] = new CellOnTheBoard(new Pawn(false), 6, 1);
//        cellOnTheBoardMap[6][2] = new CellOnTheBoard(new Pawn(false), 6, 2);
//        cellOnTheBoardMap[6][3] = new CellOnTheBoard(new Pawn(false), 6, 3);
//        cellOnTheBoardMap[6][4] = new CellOnTheBoard(new Pawn(false), 6, 4);
//        cellOnTheBoardMap[6][5] = new CellOnTheBoard(new Pawn(false), 6, 5);
//        cellOnTheBoardMap[6][6] = new CellOnTheBoard(new Pawn(false), 6, 6);
//        cellOnTheBoardMap[6][7] = new CellOnTheBoard(new Pawn(false), 6, 7);
//
//        for (int i = 2; i < 6; i++) {
//            for (int j = 0; j < 8; j++) {
//                cellOnTheBoardMap[i][j] = new CellOnTheBoard(null, i, j);
//            }
//        }
//        //bc4
//        //qf3
//
//        cellOnTheBoardMap[3][2] = new CellOnTheBoard(new Bishop(true), 3, 2);
////        cellOnTheBordMap[2][5] = new CellOnTheBord(new Queen(true), 2, 5);
//
//        board = new Board(cellOnTheBoardMap);
//
//        MiniMax miniMax = new MiniMax();
//
////        Assertions.assertTrue(miniMax.isCheckmateIn2(board, true, pieceService));
//        System.out.println(miniMax.getBestMove(board, 3, true, pieceService));


//        boardService.save(new Board());

        board.getCellOnTheBoardMap()[2][1].setPieces(new Pawn(true));
        board.getCellOnTheBoardMap()[3][1].setPieces(new Pawn(true));
        System.out.println(pieceService.verifyIfThereAreDoublePawns(board, true));

    }


}
