package com.example.models.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Move {
    private CellOnTheBord start;
    private CellOnTheBord end;
}
