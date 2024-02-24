package org.example.repositoryes.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.King;
import com.example.models.pieces.Pieces;

public non-sealed interface IKingRepository extends IRepository<King> {
    boolean checkIfTheKingIsInCheck(Board board, CellOnTheBoard start, CellOnTheBoard end, Pieces pieces);

}
