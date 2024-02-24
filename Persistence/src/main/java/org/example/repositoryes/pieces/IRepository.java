package org.example.repositoryes.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.Pieces;

sealed interface IRepository<E extends Pieces> permits IKingRepository, BishopRepository, KnightRepository, PawnRepository, QueenRepository, RookRepository {
    boolean canMove(Board board,
                    CellOnTheBoard start, CellOnTheBoard end, E pieces);

    int getNrOfMoves(Board board, CellOnTheBoard cell, int nr);
}
