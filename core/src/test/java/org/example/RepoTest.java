package org.example;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.*;
import org.example.repositoryes.pieces.BishopRepository;
import org.example.repositoryes.pieces.KnightRepository;
import org.example.repositoryes.pieces.QueenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MainApplication.class})
class RepoTest {
    @Autowired
    BishopRepository bishopRepository;
    @Autowired
    KnightRepository knightRepository;
    @Autowired
    QueenRepository queenRepository;

    @Test
    void canMoveBishop() {
        Board board = new Board();
        CellOnTheBoard cell = board.getCellOnTheBoardMap()[0][2];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[1][3];
        Assertions.assertFalse(bishopRepository.canMove(board, cell, end, new Bishop(true)));


        CellOnTheBoard[][] cellOnTheBoardMap = new CellOnTheBoard[8][8];
        cellOnTheBoardMap[0][0] = new CellOnTheBoard(new Rook(true), 0, 0);
        cellOnTheBoardMap[0][1] = new CellOnTheBoard(new Knight(true), 0, 1);
        cellOnTheBoardMap[0][2] = new CellOnTheBoard(new Bishop(true), 0, 2);
        cellOnTheBoardMap[0][3] = new CellOnTheBoard(new Queen(true), 0, 3);
        cellOnTheBoardMap[0][4] = new CellOnTheBoard(new King(true), 0, 4);
        cellOnTheBoardMap[0][5] = new CellOnTheBoard(new Bishop(true), 0, 5);
        cellOnTheBoardMap[0][6] = new CellOnTheBoard(new Knight(true), 0, 6);
        cellOnTheBoardMap[0][7] = new CellOnTheBoard(new Rook(true), 0, 7);

        cellOnTheBoardMap[1][0] = new CellOnTheBoard(new Pawn(true), 1, 0);
        cellOnTheBoardMap[1][1] = new CellOnTheBoard(new Pawn(true), 1, 1);
        cellOnTheBoardMap[1][2] = new CellOnTheBoard(new Pawn(true), 1, 2);
        cellOnTheBoardMap[1][3] = new CellOnTheBoard(new Pawn(false), 1, 3);
        cellOnTheBoardMap[1][4] = new CellOnTheBoard(null, 1, 4);
        cellOnTheBoardMap[1][5] = new CellOnTheBoard(new Pawn(true), 1, 5);
        cellOnTheBoardMap[1][6] = new CellOnTheBoard(new Pawn(true), 1, 6);
        cellOnTheBoardMap[1][7] = new CellOnTheBoard(new Pawn(true), 1, 7);

        cellOnTheBoardMap[7][0] = new CellOnTheBoard(new Rook(false), 7, 0);
        cellOnTheBoardMap[7][1] = new CellOnTheBoard(new Knight(false), 7, 1);
        cellOnTheBoardMap[7][2] = new CellOnTheBoard(new Bishop(false), 7, 2);
        cellOnTheBoardMap[7][3] = new CellOnTheBoard(new Queen(false), 7, 3);
        cellOnTheBoardMap[7][4] = new CellOnTheBoard(new King(false), 7, 4);
        cellOnTheBoardMap[7][5] = new CellOnTheBoard(new Bishop(false), 7, 5);
        cellOnTheBoardMap[7][6] = new CellOnTheBoard(new Knight(false), 7, 6);
        cellOnTheBoardMap[7][7] = new CellOnTheBoard(new Rook(false), 7, 7);

        cellOnTheBoardMap[6][0] = new CellOnTheBoard(new Pawn(false), 6, 0);
        cellOnTheBoardMap[6][1] = new CellOnTheBoard(new Pawn(false), 6, 1);
        cellOnTheBoardMap[6][2] = new CellOnTheBoard(new Pawn(false), 6, 2);
        cellOnTheBoardMap[6][3] = new CellOnTheBoard(new Pawn(false), 6, 3);
        cellOnTheBoardMap[6][4] = new CellOnTheBoard(new Pawn(false), 6, 4);
        cellOnTheBoardMap[6][5] = new CellOnTheBoard(new Pawn(false), 6, 5);
        cellOnTheBoardMap[6][6] = new CellOnTheBoard(new Pawn(false), 6, 6);
        cellOnTheBoardMap[6][7] = new CellOnTheBoard(new Pawn(false), 6, 7);

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                cellOnTheBoardMap[i][j] = new CellOnTheBoard(null, i, j);
            }
        }
        cellOnTheBoardMap[2][4] = new CellOnTheBoard(new Pawn(false), 2, 4);

        board = new Board(cellOnTheBoardMap);
        cell = board.getCellOnTheBoardMap()[0][2];
        end = board.getCellOnTheBoardMap()[1][3];
        Assertions.assertTrue(bishopRepository.canMove(board, cell, end, new Bishop(true)));
        end = board.getCellOnTheBoardMap()[3][5];
        Assertions.assertFalse(bishopRepository.canMove(board, cell, end, new Bishop(true)));
        end = board.getCellOnTheBoardMap()[5][5];
        Assertions.assertFalse(bishopRepository.canMove(board, cell, end, new Bishop(true)));
    }

    @Test
    void canMoveKnight() {
        Board board = new Board();
        CellOnTheBoard cell = board.getCellOnTheBoardMap()[0][1];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[2][3];
        Assertions.assertFalse(knightRepository.canMove(board, cell, end, new Knight(true)));
        cell = board.getCellOnTheBoardMap()[0][1];
        end = board.getCellOnTheBoardMap()[2][2];
        Assertions.assertTrue(knightRepository.canMove(board, cell, end, new Knight(true)));

        board.getCellOnTheBoardMap()[4][3] = new CellOnTheBoard(new Knight(true), 4, 3);

        cell = board.getCellOnTheBoardMap()[4][3];
        end = board.getCellOnTheBoardMap()[6][2];
        Assertions.assertTrue(knightRepository.canMove(board, cell, end, new Knight(true)));
    }


    @Test
    void canMoveQueen() {
        Board board = new Board();
        CellOnTheBoard cell = board.getCellOnTheBoardMap()[0][2];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[1][3];
//        Assertions.assertFalse(queenRepository.canMove(board, cell, end, new Queen(true)));


        CellOnTheBoard[][] cellOnTheBoardMap = new CellOnTheBoard[8][8];
        cellOnTheBoardMap[0][0] = new CellOnTheBoard(new Rook(true), 0, 0);
        cellOnTheBoardMap[0][1] = new CellOnTheBoard(new Knight(true), 0, 1);
        cellOnTheBoardMap[0][2] = new CellOnTheBoard(new Bishop(true), 0, 2);
        cellOnTheBoardMap[0][3] = new CellOnTheBoard(new Queen(true), 0, 3);
        cellOnTheBoardMap[0][4] = new CellOnTheBoard(new King(true), 0, 4);
        cellOnTheBoardMap[0][5] = new CellOnTheBoard(new Bishop(true), 0, 5);
        cellOnTheBoardMap[0][6] = new CellOnTheBoard(new Knight(true), 0, 6);
        cellOnTheBoardMap[0][7] = new CellOnTheBoard(new Rook(true), 0, 7);


        cellOnTheBoardMap[7][0] = new CellOnTheBoard(new Rook(false), 7, 0);
        cellOnTheBoardMap[7][1] = new CellOnTheBoard(new Knight(false), 7, 1);
        cellOnTheBoardMap[7][2] = new CellOnTheBoard(new Bishop(false), 7, 2);
        cellOnTheBoardMap[7][3] = new CellOnTheBoard(new Queen(false), 7, 3);
        cellOnTheBoardMap[7][4] = new CellOnTheBoard(new King(false), 7, 4);
        cellOnTheBoardMap[7][5] = new CellOnTheBoard(new Bishop(false), 7, 5);
        cellOnTheBoardMap[7][6] = new CellOnTheBoard(new Knight(false), 7, 6);
        cellOnTheBoardMap[7][7] = new CellOnTheBoard(new Rook(false), 7, 7);

        cellOnTheBoardMap[6][0] = new CellOnTheBoard(new Pawn(false), 6, 0);
        cellOnTheBoardMap[6][1] = new CellOnTheBoard(new Pawn(false), 6, 1);
        cellOnTheBoardMap[6][2] = new CellOnTheBoard(new Pawn(false), 6, 2);
        cellOnTheBoardMap[6][3] = new CellOnTheBoard(new Pawn(false), 6, 3);
        cellOnTheBoardMap[6][4] = new CellOnTheBoard(new Pawn(false), 6, 4);
        cellOnTheBoardMap[6][5] = new CellOnTheBoard(new Pawn(false), 6, 5);
        cellOnTheBoardMap[6][6] = new CellOnTheBoard(new Pawn(false), 6, 6);
        cellOnTheBoardMap[6][7] = new CellOnTheBoard(new Pawn(false), 6, 7);

        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                cellOnTheBoardMap[i][j] = new CellOnTheBoard(null, i, j);
            }
        }
        cellOnTheBoardMap[2][4] = new CellOnTheBoard(new Pawn(false), 2, 4);

        board = new Board(cellOnTheBoardMap);
        cell = board.getCellOnTheBoardMap()[0][3];
//        end = board.getCellOnTheBordMap()[1][4];
//        Assertions.assertTrue(queenRepository.canMove(board, cell, end, new Queen(true)));
//        end = board.getCellOnTheBordMap()[3][5];
//        Assertions.assertFalse(queenRepository.canMove(board, cell, end, new Queen(true)));
//        end = board.getCellOnTheBordMap()[5][5];
//        Assertions.assertFalse(queenRepository.canMove(board, cell, end, new Queen(true)));
//        end = board.getCellOnTheBordMap()[2][3];
//        Assertions.assertTrue(queenRepository.canMove(board, cell, end, new Queen(true)));
        end = board.getCellOnTheBoardMap()[5][3];
        Assertions.assertTrue(queenRepository.canMove(board, cell, end, new Queen(true)));
    }
}