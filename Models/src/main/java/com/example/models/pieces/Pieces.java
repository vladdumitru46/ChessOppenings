package com.example.models.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public abstract sealed class Pieces implements Serializable permits King, Queen, Rook, Bishop, Knight, Pawn {

    @Serial
    private static final long serialVersionUID = 7331115341259248461L;

    private boolean white;

    private Integer points;


    public Pieces(boolean white) {
        this.white = white;
    }


    public abstract boolean canAttackTheKing(Board board, CellOnTheBoard start, CellOnTheBoard end, Pieces piece);
}
