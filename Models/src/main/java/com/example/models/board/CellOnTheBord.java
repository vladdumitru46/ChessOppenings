package com.example.models.board;

import com.example.models.pieces.Pieces;
import lombok.*;

import java.util.Objects;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CellOnTheBord {
    private Pieces pieces;
    private int lineCoordinate;
    private int columnCoordinate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellOnTheBord cell = (CellOnTheBord) o;
        return lineCoordinate == cell.lineCoordinate && columnCoordinate == cell.columnCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieces, lineCoordinate, columnCoordinate);
    }
}
