package org.example.miniMax;

import com.example.models.board.Board;
import com.example.models.board.Move;
import com.example.models.pieces.Pieces;
import org.example.board.PieceService;
import org.example.miniMax.score.Evaluation;
import org.example.score.MobilityScore;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MiniMax {
    private final Board board;
    private final int depth;
    private final boolean isWhite;
    private final MobilityScore mobilityScore;
    private final PieceService pieceService;
    private final Evaluation evaluation;
    private Map<Double, Move> mapOfMoves;


    public MiniMax(Board board, int depth, boolean isWhite, MobilityScore mobilityScore, PieceService pieceService, Evaluation evaluation) {
        this.board = board;
        this.depth = depth;
        this.isWhite = isWhite;
        this.mobilityScore = mobilityScore;
        this.pieceService = pieceService;
        this.evaluation = evaluation;
    }

    public Move getBestMove() {

        if (isGameOver(board, isWhite)) {
            return null;
        }
        mapOfMoves = new LinkedHashMap<>();

        float alpha = Float.MIN_VALUE;
        float beta = Float.MAX_VALUE;

        List<Move> moves = mobilityScore.getAllPossibleMoves(board, isWhite);

        if (moves.size() > 1) {
            int pointsBefore = board.getTotalPoints(!isWhite);
            try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
                for (var move : moves) {
                    executorService.submit(() -> bestMove(alpha, beta, move, pointsBefore));
                }
            }
            List<Map.Entry<Double, Move>> list = new LinkedList<>(mapOfMoves.entrySet());
            list.sort(Map.Entry.comparingByKey());
            Map<Double, Move> result = new LinkedHashMap<>();
            for (var entry : list) {
                result.put(entry.getKey(), entry.getValue());
            }
            Move m;
            result.forEach((e, k) -> System.out.println(e + " " + k));
            if (isWhite) {
                m = result.entrySet().iterator().next().getValue();
                for (var entry : result.entrySet()) {
                    m = entry.getValue();
                }
            } else {
                m = result.entrySet().iterator().next().getValue();
            }
            return m;
        } else {
            return moves.get(0);
        }
    }

    private synchronized void bestMove(float alpha, float beta, Move move, int points) {
        Pieces pieceOnStart = move.getStart().getPieces();
        Pieces pieceOnEnd = move.getEnd().getPieces();
        pieceService.makeMove(board, move);
        double value = miniMax(depth - 1, alpha, beta, !isWhite, points);
        pieceService.undoMove(board, move, pieceOnStart, pieceOnEnd);
        String valueAsString = String.valueOf(value);
        valueAsString = valueAsString.substring(0, 4);
        value = Double.parseDouble(valueAsString);
        mapOfMoves.put(value, move);
    }

    private synchronized double miniMax(int depth, double alpha, double beta, boolean isWhite, int points) {
        if (depth == 0 || isGameOver(board, isWhite)) {
            return evaluation.evaluationScore(board, points);
        } else if (isCheckMateIn1()) {
            return evaluation.evaluationScore(board, points);
        }

        List<Move> moves = mobilityScore.getAllPossibleMoves(board, isWhite);
        if (isWhite) {
            double maxEval = Float.MIN_VALUE;

            int pointsBefore = board.getTotalPoints(false);
            for (var move : moves) {
                Pieces pieceOnStart = move.getStart().getPieces();
                Pieces pieceOnEnd = move.getEnd().getPieces();

                pieceService.makeMove(board, move);
                double eval = miniMax(depth - 1, alpha, beta, false, pointsBefore);
                pieceService.undoMove(board, move, pieceOnStart, pieceOnEnd);

                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);

                if (beta <= alpha) {
                    break;
                }

            }
            return maxEval;
        } else {
            double minEval = Float.MAX_VALUE;

            int pointsBefore = board.getTotalPoints(true);
            for (var move : moves) {
                Pieces pieceOnStart = move.getStart().getPieces();
                Pieces pieceOnEnd = move.getEnd().getPieces();

                pieceService.makeMove(board, move);
                double eval = miniMax(depth - 1, alpha, beta, true, pointsBefore);
                pieceService.undoMove(board, move, pieceOnStart, pieceOnEnd);

                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);

                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    private boolean isGameOver(Board board, boolean isWhite) {
        return isWhite ? mobilityScore.getAllPossibleMoves(board, false).isEmpty() : mobilityScore.getAllPossibleMoves(board, true).isEmpty();
    }

    public synchronized boolean isCheckMateIn1() {
        List<Move> moves = mobilityScore.getAllPossibleMoves(board, isWhite);

        for (var move : moves) {
            Pieces pieceOnStart = move.getStart().getPieces();
            Pieces pieceOnEnd = move.getEnd().getPieces();
            pieceService.makeMove(board, move);

            if (isGameOver(board, isWhite)) {
                pieceService.undoMove(board, move, pieceOnStart, pieceOnEnd);
                return true;
            }

            pieceService.undoMove(board, move, pieceOnStart, pieceOnEnd);
        }

        return false;
    }

}