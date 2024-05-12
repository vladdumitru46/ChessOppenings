package org.example.miniMax.score;

import com.example.models.board.Board;
import com.example.models.board.Move;
import com.example.models.pieces.Pieces;
import org.example.miniMax.score.bonusesAndPenalties.Penalties;
import org.example.miniMax.score.bonusesAndPenalties.Bonuses;
import org.example.score.*;

public class Evaluation {

    private final CapturePiecesScore capturePiecesScore;
    private final CenterControlScore centerControlScore;
    private final DevelopmentScore developmentScore;
    private final KingSafetyScore kingSafetyScore;
    private final MobilityScore mobilityScore;
    private final PawnStructureScore pawnStructureScore;

    public Evaluation(CapturePiecesScore capturePiecesScore, CenterControlScore centerControlScore, DevelopmentScore developmentScore, KingSafetyScore kingSafetyScore, MobilityScore mobilityScore, PawnStructureScore pawnStructureScore) {
        this.capturePiecesScore = capturePiecesScore;
        this.centerControlScore = centerControlScore;
        this.developmentScore = developmentScore;
        this.kingSafetyScore = kingSafetyScore;
        this.mobilityScore = mobilityScore;
        this.pawnStructureScore = pawnStructureScore;
    }

    public int evaluationScore(Board board) {
        int sw = score(board, true);
        int sb = score(board, false);
        return sw - sb;
    }

    //endgameBonus
    private int score(Board board, boolean isWhite) {
        int m = mobility(board, isWhite);
        int k = kingThreats(board, isWhite);
        int p = pawnStructure(board, isWhite);
        int a = attacks(board, isWhite);
        int ap = attacksPenalty(board, isWhite);
        int cc = centerControl(board, isWhite);
        int d = developmentBonus(board, isWhite);
        int q = queenLossPenalty(board, isWhite);
        int bb = board.getTotalPoints(isWhite) * Bonuses.PIECES_BONUS;
        return m
                + k
                + p
                + a
                + ap
                + cc
                + d
                + q
                + bb;
    }

    private int mobility(Board board, boolean isWhite) {
        int mr = mobilityRatio(board, isWhite);
        return Bonuses.MOBILITY_BONUS * mr;
    }

    private int mobilityRatio(Board board, boolean isWhite) {
//        int ms = ();
        return mobilityScore.getAllPossibleMoves(board, isWhite).size();
    }

    private int kingThreats(Board board, boolean isWhite) {
        return kingSafetyScore.checkMateCheck(board, !isWhite) ? Bonuses.CHECK_MATE_BONUS : check(board, isWhite);
    }

    private int check(Board board, boolean isWhite) {
        return kingSafetyScore.isTheEnemyKingInCheck(board, !isWhite) ? Bonuses.CHECK_BONUS : 0;
    }

    private int pawnStructure(Board board, boolean isWhite) {
        return isolatedPawnPenalty(board, isWhite) + doublePawnPenalty(board, isWhite);
    }

    private int isolatedPawnPenalty(Board board, boolean isWhite) {
        return pawnStructureScore.isolatedPawns(board, isWhite) * Penalties.ISOLATED_PAWN_PENALTY;
    }

    private int doublePawnPenalty(Board board, boolean isWhite) {
        return pawnStructureScore.verifyIfThereAreDoublePawns(board, isWhite) * Penalties.DOUBLE_PAWN_PENALTY;
    }

    private int attacks(Board board, boolean isWhite) {
        int attackScore = 0;
        for (var move : mobilityScore.getAllPossibleMoves(board, isWhite)) {
            Pieces startPiece = move.getStart().getPieces();
            Pieces endPiece = move.getEnd().getPieces();
            if (endPiece != null) {
                int protectedScore = protectedEnemyPiecePenalty(board, isWhite, move);
                if (startPiece.getPoints() <= endPiece.getPoints() || protectedScore == 0) {
                    attackScore += Bonuses.CAPTURE_PIECES_BONUS;
                } else {
                    attackScore += protectedScore;
                }
            }
        }
        return attackScore * Bonuses.ATTACKED_PIECES_BONUS;
    }

    private int attacksPenalty(Board board, boolean isWhite) {
        return Penalties.ATTACKED_PIECES_PENALTY * capturePiecesScore.canAPieceBeCaptured(board, !isWhite);
    }

    private int protectedEnemyPiecePenalty(Board board, boolean isWhite, Move move) {
        return capturePiecesScore.isTheAttackedPieceProtected(board, isWhite, move) * Penalties.ProtectedEnemyPiecePenalty;
    }

    private int centerControl(Board board, boolean isWhite) {
        return Bonuses.CENTER_CONTROL_BONUS * centerControlScore.numberOfCenterSquaresAttacked(board, isWhite);
    }

    private int developmentBonus(Board board, boolean isWhite) {
        return developmentScore.developmentBonus(board, isWhite) * Bonuses.DEVELOPMENT_BONUS;
    }

    private int queenLossPenalty(Board board, boolean isWhite) {
        return capturePiecesScore.canTheQueenBeCaptured(board, isWhite) ? Penalties.QueenLossPenalty : 0;
    }
}

