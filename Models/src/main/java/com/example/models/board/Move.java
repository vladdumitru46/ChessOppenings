package com.example.models.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
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
