package org.example.miniMax;

import com.example.models.board.Board;
import com.example.models.board.Move;
import com.example.models.pieces.Pieces;
import org.example.board.PieceService;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MiniMax {

    private final Board board;
    private final int depth;
    private final boolean isWhite;
    private final PieceService pieceService;

    private Map<Float, Move> mapOfMoves;


    public MiniMax(Board board, int depth, boolean isWhite, PieceService pieceService) {
        this.board = board;
        this.depth = depth;
        this.isWhite = isWhite;
        this.pieceService = pieceService;
    }

    public Move getBestMove() {

        if (isGameOver(board, pieceService, isWhite)) {
            return null;
        }
        mapOfMoves = new LinkedHashMap<>();

        float alpha = Float.MIN_VALUE;
        float beta = Float.MAX_VALUE;

        List<Move> moves = pieceService.getAllPossibleMoves(board, isWhite);

        if (moves.size() > 1) {

            try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
                for (var move : moves) {
                    executorService.submit(() -> bestMove(alpha, beta, move));
                }
            }
            List<Map.Entry<Float, Move>> list = new LinkedList<>(mapOfMoves.entrySet());

            list.sort(Map.Entry.comparingByKey());

            LinkedHashMap<Float, Move> result = new LinkedHashMap<>();
            for (var entry : list) {
                result.put(entry.getKey(), entry.getValue());
            }

            Move m;
            m = result.entrySet().iterator().next().getValue();
            for (var entry : result.entrySet()) {
                m = entry.getValue();
            }

            return m;
        } else {
            return moves.get(0);
        }
    }

    private synchronized void bestMove(float alpha, float beta, Move move) {
        Pieces pieceOnStart = move.getStart().getPieces();
        Pieces pieceOnEnd = move.getEnd().getPieces();
        pieceService.makeMove(board, move);
        float value = miniMax(depth - 1, alpha, beta, !isWhite);
        pieceService.undoMove(board, move, pieceOnStart, pieceOnEnd);

        mapOfMoves.put(value, move);
    }

    private synchronized float miniMax(int depth, float alpha, float beta, boolean isWhite) {
        if (depth == 0 || isGameOver(board, pieceService, isWhite)) {
            return new Evaluation().evaluationScore(board, pieceService, isWhite);
        } else if (isCheckMateIn1()) {
            return new Evaluation().evaluationScore(board, pieceService, isWhite);
        }

        List<Move> moves = pieceService.getAllPossibleMoves(board, isWhite);

        if (isWhite) {
            float maxEval = Float.MIN_VALUE;

            for (var move : moves) {

                Pieces pieceOnStart = move.getStart().getPieces();
                Pieces pieceOnEnd = move.getEnd().getPieces();

                pieceService.makeMove(board, move);

                float eval = miniMax(depth - 1, alpha, beta, false);


                pieceService.undoMove(board, move, pieceOnStart, pieceOnEnd);

                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);


                if (beta <= alpha) {
                    break;
                }

            }
            return maxEval;
        } else {
            float minEval = Float.MAX_VALUE;

            for (var move : moves) {

                Pieces pieceOnStart = move.getStart().getPieces();
                Pieces pieceOnEnd = move.getEnd().getPieces();

                pieceService.makeMove(board, move);

                float eval = miniMax(depth - 1, alpha, beta, true);

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

    private boolean isGameOver(Board board, PieceService pieceService, boolean isWhite) {
        return isWhite ? pieceService.getAllPossibleMoves(board, false).isEmpty() : pieceService.getAllPossibleMoves(board, true).isEmpty();
    }

    public synchronized boolean isCheckMateIn1() {
        List<Move> moves = pieceService.getAllPossibleMoves(board, isWhite);

        for (var move : moves) {
            Pieces pieceOnStart = move.getStart().getPieces();
            Pieces pieceOnEnd = move.getEnd().getPieces();
            pieceService.makeMove(board, move);

            if (isGameOver(board, pieceService, isWhite)) {
                pieceService.undoMove(board, move, pieceOnStart, pieceOnEnd);
                return true;
            }

            pieceService.undoMove(board, move, pieceOnStart, pieceOnEnd);
        }

        return false;
    }

    public boolean isCheckMateIn2(Board board, boolean isWhite, PieceService pieceService) {
        List<Move> moves = pieceService.getAllPossibleMoves(board, isWhite);

        for (var move1 : moves) {
            Pieces pieceOnStart = move1.getStart().getPieces();
            Pieces pieceOnEnd = move1.getEnd().getPieces();
            pieceService.makeMove(board, move1);

            List<Move> opponentMovesAfterMove1 = (isWhite) ? pieceService.getAllPossibleMoves(board, true) : pieceService.getAllPossibleMoves(board, false);
            int nr = 0;
            for (Move move2 : opponentMovesAfterMove1) {
                Pieces pieceOnStart2 = move2.getStart().getPieces();
                Pieces pieceOnEnd2 = move2.getEnd().getPieces();
                pieceService.makeMove(board, move2);
                if (!isCheckMateIn1()) {
                    pieceService.undoMove(board, move2, pieceOnStart2, pieceOnEnd2);
                    break;
                }
                nr++;
                pieceService.undoMove(board, move2, pieceOnStart2, pieceOnEnd2);
            }
            if (nr == opponentMovesAfterMove1.size()) {
                pieceService.undoMove(board, move1, pieceOnStart, pieceOnEnd);
                return true;
            }
            pieceService.undoMove(board, move1, pieceOnStart, pieceOnEnd);
        }
        return false;
    }
}
