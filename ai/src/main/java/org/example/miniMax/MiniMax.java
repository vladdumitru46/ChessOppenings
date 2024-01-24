package org.example.miniMax;

import com.example.models.board.Board;
import com.example.models.board.Move;
import com.example.models.pieces.Pieces;
import org.example.PieceService;

import java.util.List;

public class MiniMax {

    private int nodesVisited;

    public Move getBestMove(Board board, int depth, boolean isWhite, PieceService pieceService) {
        nodesVisited = 0;
        Move bestMove = null;

        for (int currentDepth = 1; currentDepth <= depth; currentDepth++) {
            int alpha = Integer.MIN_VALUE;
            int beta = Integer.MAX_VALUE;

            List<Move> moves = isWhite ? pieceService.getAllPossibleMovesForWhite(board) : pieceService.getAllPossibleMovesForBlack(board);

            int bestValue = isWhite ? Integer.MIN_VALUE : Integer.MAX_VALUE;

            for (Move move : moves) {
                Pieces pieceOnEnd = move.getEnd().getPieces();
                pieceService.makeMove(board, move);

                int value = miniMax(board, currentDepth - 1, alpha, beta, !isWhite, pieceService);

                pieceService.undoMove(board, move, pieceOnEnd);

                if ((isWhite && value > bestValue) || (!isWhite && value < bestValue)) {
                    bestValue = value;
                    bestMove = move;
                }

                if (isWhite) {
                    alpha = Math.max(alpha, bestValue);
                } else {
                    beta = Math.min(beta, bestValue);
                }

                if (beta <= alpha) {
                    break;
                }
            }
        }

        System.out.println("Nodes visited: " + nodesVisited);
        return bestMove;
    }

    private int miniMax(Board board, int depth, int alpha, int beta, boolean isMaximizing, PieceService pieceService) {
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
                Pieces pieceOnEnd = move.getEnd().getPieces();
                pieceService.makeMove(board, move);

                int eval = miniMax(board, depth - 1, alpha, beta, false, pieceService);
                pieceService.undoMove(board, move, pieceOnEnd);

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
                Pieces pieceOnEnd = move.getEnd().getPieces();
                pieceService.makeMove(board, move);

                int eval = miniMax(board, depth - 1, alpha, beta, true, pieceService);
                pieceService.undoMove(board, move, pieceOnEnd);

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

    public boolean isCheckMateIn1(Board board, boolean isWhite, PieceService pieceService) {
        List<Move> moves = (isWhite) ? pieceService.getAllPossibleMovesForWhite(board) :
                pieceService.getAllPossibleMovesForBlack(board);

        for (Move move1 : moves) {
            nodesVisited++;
            Pieces pieceOnEnd = move1.getEnd().getPieces();
            pieceService.makeMove(board, move1);

            if (isGameOver(board, pieceService, isWhite)) {
                pieceService.undoMove(board, move1, pieceOnEnd);
                return true;
            }

            pieceService.undoMove(board, move1, pieceOnEnd);
        }

        return false;
    }

    public boolean isCheckmateIn2(Board board, boolean isWhite, PieceService pieceService) {
        List<Move> moves = (isWhite) ? pieceService.getAllPossibleMovesForWhite(board) : pieceService.getAllPossibleMovesForBlack(board);

        for (Move move1 : moves) {
            Pieces pieceOnEnd = move1.getEnd().getPieces();
            pieceService.makeMove(board, move1);

            List<Move> opponentMovesAfterMove1 = (isWhite) ? pieceService.getAllPossibleMovesForWhite(board) : pieceService.getAllPossibleMovesForBlack(board);
            int nr = 0;
            for (Move move2 : opponentMovesAfterMove1) {
                Pieces pieceOnEnd2 = move2.getEnd().getPieces();
                pieceService.makeMove(board, move2);
                if (!isCheckMateIn1(board, isWhite, pieceService)) {
                    pieceService.undoMove(board, move2, pieceOnEnd2);
                    break;
                }
                nr++;
                pieceService.undoMove(board, move2, pieceOnEnd2);
            }
            if (nr == opponentMovesAfterMove1.size()) {
                pieceService.undoMove(board, move1, pieceOnEnd);
                return true;
            }
            pieceService.undoMove(board, move1, pieceOnEnd);
        }
        return false;
    }
}
