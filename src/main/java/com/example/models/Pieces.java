package com.example.models;

import java.io.Serializable;

public interface Pieces extends Serializable {

    boolean isKilled();

    void setKilled(boolean killed);

    boolean isWhite();

    void setWhite(boolean white);

    boolean canMove(Board board,
                    CellOnTheBord start, CellOnTheBord end);

    boolean checkIfPieceIsNotAttacked(Board board, CellOnTheBord start, CellOnTheBord end);

    boolean canAttackTheKing(Board board,
                             CellOnTheBord start, CellOnTheBord end, CellOnTheBord cell);
}
