package org.example.repositoryes;

import com.example.models.Board;
import com.example.models.CellOnTheBord;
import com.example.models.Pieces;

public interface IRepository<E extends Pieces> {
    boolean canMove(Board board,
                    CellOnTheBord start, CellOnTheBord end, E pieces);

}
