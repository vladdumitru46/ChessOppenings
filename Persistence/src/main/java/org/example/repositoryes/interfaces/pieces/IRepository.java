package org.example.repositoryes.interfaces.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.Pieces;

public interface IRepository<E extends Pieces> {
    boolean canMove(Board board,
                    CellOnTheBoard start, CellOnTheBoard end, E pieces);

    int getNrOfMoves(Board board, CellOnTheBoard cell, int nr);
}
