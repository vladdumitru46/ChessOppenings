package org.example.board.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.Pieces;

sealed interface IService<E extends Pieces> permits IKingService, BishopService, KnightService,
        PawnService, QueenService, RookService {
    boolean canMove(Board board,
                    CellOnTheBoard start, CellOnTheBoard end, E pieces);

}
