package org.example.miniMax.score;

import com.example.models.board.Board;
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

    public double evaluationScore(Board board, int points) {
        return score(board, true, points) - score(board, false, points);
    }

    //endgameBonus
    private double score(Board board, boolean isWhite, int points) {
        return mobility(board, isWhite)
                + kingThreats(board, isWhite)
                + pawnStructure(board, isWhite)
                + attacks(board, isWhite, points)
                + attacksPenalty(board, isWhite)
                + centerControl(board, isWhite)
                + developmentBonus(board, isWhite)
                + board.getTotalPoints(isWhite) * Bonuses.PIECES_BONUS;
    }

    private int mobility(Board board, boolean isWhite) {
        return Bonuses.MOBILITY_BONUS * mobilityRatio(board, isWhite);
    }

    private int mobilityRatio(Board board, boolean isWhite) {
        return (int) ((mobilityScore.getAllPossibleMoves(board, isWhite).size() * 10f) / mobilityScore.getAllPossibleMoves(board, !isWhite).size());
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

    private int attacks(Board board, boolean isWhite, int pointsBefore) {
        int pointsNow = board.getTotalPoints(!isWhite);
        return Bonuses.ATTACKED_PIECES_BONUS * capturePiecesScore.canAPieceBeCaptured(board, isWhite)
                + pointsBefore > pointsNow ? Bonuses.CAPTURE_PIECES_BONUS * (pointsBefore - pointsNow) : 0;
    }

    private int attacksPenalty(Board board, boolean isWhite) {
        return Penalties.ATTACKED_PIECES_PENALTY * capturePiecesScore.canAPieceBeCaptured(board, !isWhite);
    }

    private int centerControl(Board board, boolean isWhite) {
        return Bonuses.CENTER_CONTROL_BONUS * centerControlScore.numberOfCenterSquaresAttacked(board, isWhite);
    }

    private int developmentBonus(Board board, boolean isWhite) {
        return developmentScore.developmentBonus(board, isWhite) * Bonuses.DEVELOPMENT_BONUS;
    }
}

