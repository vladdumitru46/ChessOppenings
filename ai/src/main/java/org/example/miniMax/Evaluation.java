package org.example.miniMax;

import com.example.models.board.Board;
import org.example.board.PieceService;

public class Evaluation {

    public int evaluationScore(Board board, PieceService pieceService, boolean isWhite) {

        return isWhite ?
                scoreForWhite(board, pieceService) - scoreForBlack(board, pieceService) :
                scoreForWhite(board, pieceService) + scoreForBlack(board, pieceService);

    }

    private int scoreForWhite(Board board, PieceService pieceService) {
        return board.getTotalPoints(true)
                + pieceService.getAllPossibleMoves(board, true).size()
//                + pieceService.numberOfCenterSquaresAttacked(board, true)
//                + pieceService.verifyPawnStructure(board, true)
//                + pieceService.isTheEnemyKingInCheck(board, false)
//                - (pieceService.verifyIfThereAreDoublePawns(board, true) * 10)
//                - pieceService.canTheKingBeCheckedInNextMove(board, true)
                - pieceService.canAPieceBeCaptured(board, true) * 10;
    }


    private int scoreForBlack(Board board, PieceService pieceService) {
        return board.getTotalPoints(false)
                + pieceService.getAllPossibleMoves(board, false).size()
//                + pieceService.numberOfCenterSquaresAttacked(board, false)
//                + pieceService.verifyPawnStructure(board, false)
//                + pieceService.isTheEnemyKingInCheck(board, true)
//                - (pieceService.verifyIfThereAreDoublePawns(board, false) * 10)
//                - pieceService.canTheKingBeCheckedInNextMove(board, false)
                + pieceService.canAPieceBeCaptured(board, false) * 10;
    }

}
