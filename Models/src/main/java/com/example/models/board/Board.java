package com.example.models.board;

import com.example.models.pieces.*;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@Setter
@Entity(name = "Board")
@Table(name = "boards")
public class Board implements Serializable {

    @Id
    @SequenceGenerator(
            name = "board_sequence",
            sequenceName = "board_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "board_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @Column(
            name = "cell_on_the_board_map",
            nullable = false,
            columnDefinition = "bytea[]"
    )
    private CellOnTheBoard[][] cellOnTheBoardMap;


    public Board(CellOnTheBoard[][] cellOnTheBoardMap) {
        this.cellOnTheBoardMap = cellOnTheBoardMap;
    }

    public Board() {
        cellOnTheBoardMap = setBoard();
    }


    public CellOnTheBoard getKing(boolean isWhite) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cellOnTheBoardMap[i][j].getPieces() instanceof King) {
                    if (cellOnTheBoardMap[i][j].getPieces().isWhite() == isWhite) {
                        return cellOnTheBoardMap[i][j];
                    }
                }
            }
        }
        return null;
    }

    public CellOnTheBoard[][] setBoard() {
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
        cellOnTheBoardMap[1][3] = new CellOnTheBoard(new Pawn(true), 1, 3);
        cellOnTheBoardMap[1][4] = new CellOnTheBoard(new Pawn(true), 1, 4);
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
        return cellOnTheBoardMap;
    }


    public Integer getTotalPoints(boolean isWhite) {
        return Arrays.stream(cellOnTheBoardMap)
                .flatMap(Arrays::stream)
                .filter(i -> i.getPieces() != null && i.getPieces().isWhite() == isWhite)
                .mapToInt(i -> i.getPieces().getPoints())
                .sum();
    }


    @Override
    public String toString() {
        StringBuilder boardToString = new StringBuilder("\n");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                CellOnTheBoard cell = this.cellOnTheBoardMap[i][j];
                String piece;
                if (cell.getPieces() != null) {
                    piece = cell.getPieces().isWhite() ? "white " : "black ";
                    switch (cell.getPieces()) {
                        case King ignored -> piece += "king";
                        case Queen ignored -> piece += "queen";
                        case Rook ignored -> piece += "rook";
                        case Bishop ignored -> piece += "bishop";
                        case Knight ignored -> piece += "knight";
                        case Pawn ignored -> piece += "pawn";
                    }
                } else {
                    piece = "none";
                }
                boardToString.append(i).append(j).append(" ").append(piece).append(",");
            }
            boardToString.append("\n");
        }
        return boardToString.toString();
    }
}
