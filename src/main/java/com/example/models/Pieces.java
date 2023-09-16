package com.example.models;

import java.io.Serializable;

public abstract class Pieces implements Serializable {
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
    public abstract boolean canMove(Board board,
                                    CellOnTheBord start, CellOnTheBord end);

    public abstract boolean checkIfPieceIsNotAttacked(Board board, CellOnTheBord start, CellOnTheBord end);
}
