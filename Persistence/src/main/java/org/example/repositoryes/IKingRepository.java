package org.example.repositoryes;

import com.example.models.Board;
import com.example.models.CellOnTheBord;
import com.example.models.King;
import com.example.models.Pieces;

public interface IKingRepository extends IRepository<King> {
    boolean checkIfPieceIsNotAttacked(Board board, CellOnTheBord start, CellOnTheBord end, Pieces pieces);

}
