package com.example.models.board;

import com.example.models.pieces.Pieces;
import lombok.*;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CellOnTheBoard implements Serializable {

    private Pieces pieces;
    private int lineCoordinate;
    private int columnCoordinate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellOnTheBoard cell = (CellOnTheBoard) o;
        return lineCoordinate == cell.lineCoordinate && columnCoordinate == cell.columnCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieces, lineCoordinate, columnCoordinate);
    }

    @Override
    public String toString() {
        return String.valueOf(lineCoordinate) + columnCoordinate;
    }
}
