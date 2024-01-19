package com.example.models.board;

import com.example.models.pieces.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;

@ToString
@Getter
@Setter
public class Board {

    private Integer id;
    private CellOnTheBord[][] cellOnTheBordMap;
    private boolean whitesTurn = true;

    public Board(CellOnTheBord[][] cellOnTheBordMap) {
        this.cellOnTheBordMap = cellOnTheBordMap;
    }

    public Board() {
        cellOnTheBordMap = setBoard();
    }


    public CellOnTheBord getKing(final boolean isWhite) {
        return Arrays.stream(cellOnTheBordMap)
                .flatMap(Arrays::stream)
                .filter(i -> i.getPieces() instanceof King && i.getPieces().isWhite() == isWhite)
                .findFirst()
                .orElse(null);

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


    public Integer getTotalPointsForWhite() {
        return Arrays.stream(cellOnTheBordMap)
                .flatMap(Arrays::stream)
                .filter(i -> i.getPieces() != null && i.getPieces().isWhite())
                .mapToInt(i -> i.getPieces().getPoints())
                .sum();
    }

    public Integer getTotalPointsForBlack() {
        return Arrays.stream(cellOnTheBordMap)
                .flatMap(Arrays::stream)
                .filter(i -> i.getPieces() != null && !i.getPieces().isWhite())
                .mapToInt(i -> i.getPieces().getPoints())
                .sum();
    }


    public String transformLinesToLetters(CellOnTheBord cell) {
        switch (cell.getLineCoordinate()) {
            case 0:
                return "a";
            case 1:
                return "b";
            case 2:
                return "c";
            case 3:
                return "d";
            case 4:
                return "e";
            case 5:
                return "f";
            case 6:
                return "g";
            case 7:
                return "h";
        }
        return "";
    }

    public String transformMoveToCorrectNotation( CellOnTheBord start, CellOnTheBord end) {
        String notation = "";
        if (start.getPieces() instanceof King) {
            notation += "K";
        } else if (start.getPieces() instanceof Queen) {
            notation += "Q";
        } else if (start.getPieces() instanceof Rook) {
            notation += "R";
        } else if (start.getPieces() instanceof Bishop) {
            notation += "B";
        } else if (start.getPieces() instanceof Knight) {
            notation += "N";
        }

        if (!(start.getPieces() instanceof Pawn)) {

            for (int j = 0; j < 8; j++) {
                CellOnTheBord endCell = getCellOnTheBordMap()[start.getLineCoordinate()][j];
                if (start.getPieces() == endCell.getPieces()) {
                    notation += transformLinesToLetters(start);
                    break;
                }
            }
        }
        if (end.getPieces() != null) {
            if (start.getPieces() instanceof Pawn) {
                notation += transformLinesToLetters(start);
            }
            notation += "x";
        }
        notation += transformLinesToLetters(end) + end.getColumnCoordinate();
        return notation;
    }
}
