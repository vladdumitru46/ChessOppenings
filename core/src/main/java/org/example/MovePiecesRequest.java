package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovePiecesRequest {
    //    private Board board;
    private String start;
    private String end;
    private String pieceColour;
}
