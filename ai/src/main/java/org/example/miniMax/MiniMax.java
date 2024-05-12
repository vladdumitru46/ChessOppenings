package org.example.miniMax;

import com.example.models.board.Board;
import com.example.models.board.Move;
import com.example.models.pieces.King;
import com.example.models.pieces.Pawn;
import com.example.models.pieces.Pieces;
import com.example.models.pieces.Queen;
import org.example.board.PieceService;
import org.example.miniMax.score.Evaluation;
import org.example.score.KingSafetyScore;
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
    private final KingSafetyScore kingSafetyScore;
    private final Evaluation evaluation;
    private Map<Integer, Move> mapOfMoves;


    public MiniMax(Board board, int depth, boolean isWhite, MobilityScore mobilityScore, PieceService pieceService, KingSafetyScore kingSafetyScore, Evaluation evaluation) {
        this.board = board;
        this.depth = depth;
        this.isWhite = isWhite;
        this.mobilityScore = mobilityScore;
        this.pieceService = pieceService;
        this.kingSafetyScore = kingSafetyScore;
        this.evaluation = evaluation;
    }

    public Move getBestMove() {

        if (isGameOver(board, isWhite)) {
            return null;
        }
        mapOfMoves = new LinkedHashMap<>();

        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        List<Move> moves = mobilityScore.getAllPossibleMoves(board, isWhite);

        if (moves.size() > 1) {
            try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
                for (var move : moves) {
                    executorService.submit(() -> bestMove(alpha, beta, move));
                }
            }
            List<Map.Entry<Integer, Move>> list = new LinkedList<>(mapOfMoves.entrySet());
            list.sort(Map.Entry.comparingByKey());
            Map<Integer, Move> result = new LinkedHashMap<>();
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

    private synchronized void bestMove(Integer alpha, Integer beta, Move move) {

        Pieces pieceOnStart = move.getStart().getPieces();
        Pieces pieceOnEnd = move.getEnd().getPieces();
        pieceService.makeMove(board, move);
        int value = miniMax(depth - 1, alpha, beta, !isWhite);
        pieceService.undoMove(board, move, pieceOnStart, pieceOnEnd);
        mapOfMoves.put(value, move);
    }

    //TODO case for pawn promotion
    private synchronized int miniMax(int depth, int alpha, int beta, boolean isWhite) {
        King king = (King) board.getKing(!isWhite).getPieces();
        if (depth + 1 == 0) {
            return evaluation.evaluationScore(board);
        } else if (isGameOver(board, !isWhite)) {
//            return !isWhite ? evaluation.evaluationScore(board) * 1000 :
//                    evaluation.evaluationScore(board) * -1000;
            return evaluation.evaluationScore(board);
        } else if (isCheckMateIn1(king, !isWhite)) {
            return evaluation.evaluationScore(board);
        }

        List<Move> moves = mobilityScore.getAllPossibleMoves(board, isWhite);
        if (isWhite) {
            int maxEval = Integer.MIN_VALUE;

            for (var move : moves) {
                Pieces pieceOnStart = move.getStart().getPieces();
                Pieces pieceOnEnd = move.getEnd().getPieces();

                pieceService.makeMove(board, move);
                int eval = miniMax(depth - 1, alpha, beta, false);
                pieceService.undoMove(board, move, pieceOnStart, pieceOnEnd);

                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);

                if (beta <= alpha) {
                    break;
                }

            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;

            for (var move : moves) {
                Pieces pieceOnStart = move.getStart().getPieces();
                Pieces pieceOnEnd = move.getEnd().getPieces();

                pieceService.makeMove(board, move);
                int eval = miniMax(depth - 1, alpha, beta, true);
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
        return kingSafetyScore.checkMateCheck(board, !isWhite);
    }

    public synchronized boolean isCheckMateIn1(King myKing, boolean isWhite) {
        if (myKing.isInCheck()) {
            return false;
        }
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