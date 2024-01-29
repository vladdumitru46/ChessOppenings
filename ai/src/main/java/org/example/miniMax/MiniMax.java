package org.example.miniMax;

import com.example.models.board.Board;
import com.example.models.board.Move;
import com.example.models.pieces.Pieces;
import org.example.PieceService;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MiniMax {
    private int nodesVisited;

    private final Board board;
    private final int depth;
    private final boolean isWhite;
    private final PieceService pieceService;
    private final int numberOfThreads;

    private LinkedHashMap<Integer, Move> mapOfMoves = new LinkedHashMap<>();


    public MiniMax(Board board, int depth, boolean isWhite, PieceService pieceService, int numberOfThreads) {
        this.board = board;
        this.depth = depth;
        this.isWhite = isWhite;
        this.pieceService = pieceService;
        this.numberOfThreads = numberOfThreads;
    }

    public Move getBestMove() {
        nodesVisited = 0;
        mapOfMoves = new LinkedHashMap<>();
        final Move bestMove = new Move();

        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        List<Move> moves = isWhite ? pieceService.getAllPossibleMovesForWhite(board) : pieceService.getAllPossibleMovesForBlack(board);

        int bestValue = isWhite ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        for (Move move : moves) {
            executorService.execute(() -> {

                bestMove(alpha, beta, bestValue, move);
//                if (m != null) {
//                    bestMove.setStart(m.getStart());
//                    bestMove.setEnd(m.getEnd());
//                }
            });
        }
        executorService.shutdown();
        try {
            boolean b = executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Map.Entry<Integer, Move>> list = new LinkedList<>(mapOfMoves.entrySet());

        Collections.sort(list, Map.Entry.comparingByKey());

        LinkedHashMap<Integer, Move> result = new LinkedHashMap<>();
        for (Map.Entry<Integer, Move> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        Move m;
        if (isWhite) {
            Map.Entry<Integer, Move> lastEntry = null;
            for (Map.Entry<Integer, Move> entry : result.entrySet()) {
                lastEntry = entry;
            }
            m = lastEntry.getValue();
        } else {

            m = result.entrySet().iterator().next().getValue();
            result.forEach((i, j) -> System.out.println(i + " " + j));
            System.out.println(m);
        }
        System.out.println("Nodes visited: " + nodesVisited);
        return m;
    }

    private synchronized void bestMove(int alpha, int beta, int bestValue, Move move) {
        Pieces pieceOnStart = move.getStart().getPieces();
        Pieces pieceOnEnd = move.getEnd().getPieces();
        pieceService.makeMove(board, move);
        Move bestMove = null;
        int value = miniMax(board, depth - 1, alpha, beta, !isWhite, pieceService);
        pieceService.undoMove(board, move, pieceOnStart, pieceOnEnd);
        if ((isWhite && value > bestValue) || (!isWhite && value < bestValue)) {
            bestValue = value;
            bestMove = move;
            mapOfMoves.put(bestValue, move);

        }

//        return bestMove;
    }

    private synchronized int miniMax(Board board, int depth, int alpha, int beta, boolean isMaximizing, PieceService pieceService) {
        nodesVisited++;

        if (depth == 0 || isGameOver(board, pieceService, isMaximizing)) {
            return new Evaluation().evaluationScore(board, pieceService, isMaximizing);
        } else if (isCheckMateIn1(board, isMaximizing, pieceService)) {
            return new Evaluation().evaluationScore(board, pieceService, isMaximizing);
        }

        List<Move> moves = isMaximizing ? pieceService.getAllPossibleMovesForWhite(board) : pieceService.getAllPossibleMovesForBlack(board);

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;

            for (Move move : moves) {
                nodesVisited++;
                Pieces pieceOnStart = move.getStart().getPieces();
                Pieces pieceOnEnd = move.getEnd().getPieces();
                pieceService.makeMove(board, move);

                int eval = miniMax(board, depth - 1, alpha, beta, false, pieceService);
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

            for (Move move : moves) {
                nodesVisited++;
                Pieces pieceOnStart = move.getStart().getPieces();
                Pieces pieceOnEnd = move.getEnd().getPieces();
                pieceService.makeMove(board, move);

                int eval = miniMax(board, depth - 1, alpha, beta, true, pieceService);
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
        return isWhite ? pieceService.getAllPossibleMovesForBlack(board).isEmpty() :
                pieceService.getAllPossibleMovesForWhite(board).isEmpty();
    }

    public synchronized boolean isCheckMateIn1(Board board, boolean isWhite, PieceService pieceService) {
        List<Move> moves = (isWhite) ? pieceService.getAllPossibleMovesForWhite(board) :
                pieceService.getAllPossibleMovesForBlack(board);

        for (Move move1 : moves) {
            nodesVisited++;
            Pieces pieceOnStart = move1.getStart().getPieces();
            Pieces pieceOnEnd = move1.getEnd().getPieces();
            pieceService.makeMove(board, move1);

            if (isGameOver(board, pieceService, isWhite)) {
                pieceService.undoMove(board, move1, pieceOnStart, pieceOnEnd);
                return true;
            }

            pieceService.undoMove(board, move1, pieceOnStart, pieceOnEnd);
        }

        return false;
    }

    public boolean isCheckMateIn2(Board board, boolean isWhite, PieceService pieceService) {
        List<Move> moves = (isWhite) ? pieceService.getAllPossibleMovesForWhite(board) : pieceService.getAllPossibleMovesForBlack(board);

        for (Move move1 : moves) {
            Pieces pieceOnStart = move1.getStart().getPieces();
            Pieces pieceOnEnd = move1.getEnd().getPieces();
            pieceService.makeMove(board, move1);

            List<Move> opponentMovesAfterMove1 = (isWhite) ? pieceService.getAllPossibleMovesForWhite(board) : pieceService.getAllPossibleMovesForBlack(board);
            int nr = 0;
            for (Move move2 : opponentMovesAfterMove1) {
                Pieces pieceOnStart2 = move2.getStart().getPieces();
                Pieces pieceOnEnd2 = move2.getEnd().getPieces();
                pieceService.makeMove(board, move2);
                if (!isCheckMateIn1(board, isWhite, pieceService)) {
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
