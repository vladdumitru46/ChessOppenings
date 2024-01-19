package org.example.miniMax;

import com.example.models.board.Board;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.PieceService;

@Getter
@Setter
@AllArgsConstructor
public class Evaluation {

    public Integer evaluationScore(Board board, PieceService pieceService, boolean colour) {
        if (colour) {
            return (board.getTotalPointsForWhite() + pieceService.numberOfPossibleMovesForWhite(board))
                    - (board.getTotalPointsForBlack() + pieceService.numberOfPossibleMovesForBlack(board));
        } else {
            return (board.getTotalPointsForWhite() + pieceService.numberOfPossibleMovesForWhite(board))
                    + (board.getTotalPointsForBlack() + pieceService.numberOfPossibleMovesForBlack(board));
        }
    }

}
