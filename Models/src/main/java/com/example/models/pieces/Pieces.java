package com.example.models.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;

import java.io.Serializable;

public abstract class Pieces implements Serializable {

    private static final long serialVersionUID = 7331115341259248461L;

    private boolean killed = false;
    private boolean white = false;


    public Pieces(boolean white) {
        this.white = white;
    }

    public boolean isKilled() {
        return killed;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public abstract boolean canAttackTheKing(Board board, CellOnTheBord start, CellOnTheBord end, CellOnTheBord piece);
}
