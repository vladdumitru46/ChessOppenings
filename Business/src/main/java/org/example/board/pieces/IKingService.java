package org.example.board.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.King;
import com.example.models.pieces.Pieces;

public non-sealed interface IKingService extends IService<King> {
    boolean checkIfTheKingIsInCheck(Board board, CellOnTheBoard start, CellOnTheBoard end, Pieces pieces);

}
