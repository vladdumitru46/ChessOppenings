package com.example.models.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Move {
    private CellOnTheBoard start;
    private CellOnTheBoard end;

    @Override
    public String toString() {
        return "Move " +
                start.getLineCoordinate() + start.getColumnCoordinate() +
                "->" + end.getLineCoordinate() + end.getColumnCoordinate() +
                "piece: " + start.getPieces();
    }
}
