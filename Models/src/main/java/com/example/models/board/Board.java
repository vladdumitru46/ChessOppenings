package com.example.models.board;

import com.example.models.pieces.*;
import lombok.ToString;

@ToString
public class Board {

    private CellOnTheBord[][] cellOnTheBordMap;

    public Board(CellOnTheBord[][] cellOnTheBordMap) {
        this.cellOnTheBordMap = cellOnTheBordMap;
    }

    public Board() {
        cellOnTheBordMap = setBoard();
    }

    public CellOnTheBord[][] getCellOnTheBordMap() {
        return cellOnTheBordMap;
    }

    public void setCellOnTheBordMap(CellOnTheBord[][] cellOnTheBordMap) {
        this.cellOnTheBordMap = cellOnTheBordMap;
    }


    public CellOnTheBord[][] setBoard() {
        CellOnTheBord[][] cellOnTheBordMap = new CellOnTheBord[8][8];
        cellOnTheBordMap[0][0] = new CellOnTheBord(new Rook(true), 0, 0);
        cellOnTheBordMap[0][1] = new CellOnTheBord(new Knight(true), 0, 1);
        cellOnTheBordMap[0][2] = new CellOnTheBord(new Bishop(true), 0, 2);
        cellOnTheBordMap[0][3] = new CellOnTheBord(new Queen(true), 0, 3);
        cellOnTheBordMap[0][4] = new CellOnTheBord(new King(true), 0, 4);
        cellOnTheBordMap[0][5] = new CellOnTheBord(new Bishop(true), 0, 5);
        cellOnTheBordMap[0][6] = new CellOnTheBord(new Knight(true), 0, 6);
        cellOnTheBordMap[0][7] = new CellOnTheBord(new Rook(true), 0, 7);

        cellOnTheBordMap[1][0] = new CellOnTheBord(new Pawn(true), 1, 0);
        cellOnTheBordMap[1][1] = new CellOnTheBord(new Pawn(true), 1, 1);
        cellOnTheBordMap[1][2] = new CellOnTheBord(new Pawn(true), 1, 2);
        cellOnTheBordMap[1][3] = new CellOnTheBord(new Pawn(true), 1, 3);
        cellOnTheBordMap[1][4] = new CellOnTheBord(new Pawn(true), 1, 4);
        cellOnTheBordMap[1][5] = new CellOnTheBord(new Pawn(true), 1, 5);
        cellOnTheBordMap[1][6] = new CellOnTheBord(new Pawn(true), 1, 6);
        cellOnTheBordMap[1][7] = new CellOnTheBord(new Pawn(true), 1, 7);

        cellOnTheBordMap[7][0] = new CellOnTheBord(new Rook(false), 7, 0);
        cellOnTheBordMap[7][1] = new CellOnTheBord(new Knight(false), 7, 1);
        cellOnTheBordMap[7][2] = new CellOnTheBord(new Bishop(false), 7, 2);
        cellOnTheBordMap[7][3] = new CellOnTheBord(new Queen(false), 7, 3);
        cellOnTheBordMap[7][4] = new CellOnTheBord(new King(false), 7, 4);
        cellOnTheBordMap[7][5] = new CellOnTheBord(new Bishop(false), 7, 5);
        cellOnTheBordMap[7][6] = new CellOnTheBord(new Knight(false), 7, 6);
        cellOnTheBordMap[7][7] = new CellOnTheBord(new Rook(false), 7, 7);

        cellOnTheBordMap[6][0] = new CellOnTheBord(new Pawn(false), 6, 0);
        cellOnTheBordMap[6][1] = new CellOnTheBord(new Pawn(false), 6, 1);
        cellOnTheBordMap[6][2] = new CellOnTheBord(new Pawn(false), 6, 2);
        cellOnTheBordMap[6][3] = new CellOnTheBord(new Pawn(false), 6, 3);
        cellOnTheBordMap[6][4] = new CellOnTheBord(new Pawn(false), 6, 4);
        cellOnTheBordMap[6][5] = new CellOnTheBord(new Pawn(false), 6, 5);
        cellOnTheBordMap[6][6] = new CellOnTheBord(new Pawn(false), 6, 6);
        cellOnTheBordMap[6][7] = new CellOnTheBord(new Pawn(false), 6, 7);

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                cellOnTheBordMap[i][j] = new CellOnTheBord(null, i, j);
            }
        }
        return cellOnTheBordMap;
    }
}
