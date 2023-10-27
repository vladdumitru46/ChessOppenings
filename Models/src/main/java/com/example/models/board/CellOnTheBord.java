package com.example.models.board;

import com.example.models.pieces.Pieces;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CellOnTheBord {
    private Pieces pieces;
    private int lineCoordinate;
    private int columnCoordinate;
}
