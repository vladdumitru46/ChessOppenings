package org.example.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PromotePawn {

    private final Integer boardId;
    private final String newPiece;
    private final String coordinates;
}
