package org.example.repositoryes.interfaces.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.King;
import com.example.models.pieces.Pieces;

public interface IKingRepository extends IRepository<King> {
    boolean checkIfTheKingIsInCheck(Board board, CellOnTheBord start, CellOnTheBord end, Pieces pieces);

}
