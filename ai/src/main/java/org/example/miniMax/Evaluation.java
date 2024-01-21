package org.example.miniMax;

import com.example.models.board.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.PieceService;

@Getter
@Setter
@AllArgsConstructor
public class Evaluation {

    public Integer evaluationScore(Board board, PieceService pieceService, boolean isWhite) {
        if (isWhite) {
            return ((board.getTotalPointsForWhite() + pieceService.getAllPossibleMovesForWhite(board).size()
                    + pieceService.numberOfCenterSquaresAttackedForWhite(board))
                    - (board.getTotalPointsForBlack() + pieceService.getAllPossibleMovesForBlack(board).size()
                    + pieceService.numberOfCenterSquaresAttackedForBlack(board)))
                    + (board.getTotalPointsForWhite() - board.getTotalPointsForBlack());
        } else {
            return (board.getTotalPointsForWhite() + pieceService.getAllPossibleMovesForWhite(board).size()
                    + pieceService.numberOfCenterSquaresAttackedForWhite(board))
                    + (board.getTotalPointsForBlack() + pieceService.getAllPossibleMovesForBlack(board).size()
                    + pieceService.numberOfCenterSquaresAttackedForBlack(board))
                    + (board.getTotalPointsForWhite() - board.getTotalPointsForBlack());
        }
    }


}
