package com.example.models.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class Pieces implements Serializable {

    private static final long serialVersionUID = 7331115341259248461L;

    private boolean killed = false;
    private boolean white;
    private Integer points;


    public Pieces(boolean white) {
        this.white = white;
    }


    public abstract boolean canAttackTheKing(Board board, CellOnTheBord start, CellOnTheBord end, Pieces piece);
}
