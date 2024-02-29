package org.example.miniMax;

import com.example.models.board.Board;
import org.example.board.PieceService;

public class Evaluation {

    public float evaluationScore(Board board, PieceService pieceService, boolean isWhite) {
        return score(board, pieceService, isWhite);

    }

    //development square
    //engameBonus
    private float score(Board board, PieceService pieceService, boolean isWhite) {
        return board.getTotalPoints(isWhite) * 0.3f
                + pieceService.getAllPossibleMoves(board, isWhite).size() * 0.1f
                + pieceService.numberOfCenterSquaresAttacked(board, isWhite) * 0.3f
                + pieceService.verifyPawnStructure(board, isWhite) * 0.1f
                + pieceService.isTheEnemyKingInCheck(board, false) * 0.1f
                + pieceService.canAPieceBeCaptured(board, isWhite) * 0.1f
                + pieceService.canTheKingBeCheckedInNextMove(board, isWhite) * 0.2f
                - pieceService.verifyIfThereAreDoublePawns(board, isWhite) * 0.2f
                - pieceService.canAPieceBeCaptured(board, !isWhite) * 0.5f;
    }


}
