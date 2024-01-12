package org.example.repositoryes.interfaces.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.Pieces;

public interface IRepository<E extends Pieces> {
    boolean canMove(Board board,
                    CellOnTheBord start, CellOnTheBord end, E pieces);

    int getNrOfMoves(Board board, CellOnTheBord cell, int nr);
}
