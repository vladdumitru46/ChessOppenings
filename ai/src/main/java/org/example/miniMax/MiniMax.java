package org.example.miniMax;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.board.Move;
import org.example.PieceService;

import java.util.List;

public class MiniMax {

    public Move getBestMove(Board board, int depth, boolean isWhite, PieceService pieceService) {
        if (depth == 0 || isGameOver(board, pieceService)) {
            return null;
        }

        List<Move> moves = pieceService.getAllPossibleMoves(board);
        int bestValue = isWhite ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Move bestMove = null;

        for (var move : moves) {
            Board newBoard = cloneBoard(board);
            pieceService.makeMove(newBoard, move);

            int value = miniMax(newBoard, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, !isWhite, pieceService);

            if ((isWhite && value > bestValue) || (!isWhite && value < bestValue)) {
                bestValue = value;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private int miniMax(Board board, int depth, int alpha, int beta, boolean isMaximizing, PieceService pieceService) {
        if (depth == 0 || isGameOver(board, pieceService)) {
            return new Evaluation().evaluationScore(board, pieceService, isMaximizing);
        }

        List<Move> moves = pieceService.getAllPossibleMoves(board);

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (var move : moves) {
                Board newBoard = cloneBoard(board);
                pieceService.makeMove(newBoard, move);
                int eval = miniMax(newBoard, depth - 1, alpha, beta, false, pieceService);
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
                Board newBoard = cloneBoard(board);
                pieceService.makeMove(newBoard, move);
                int eval = miniMax(newBoard, depth - 1, alpha, beta, true, pieceService);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    private Board cloneBoard(Board board) {
        CellOnTheBord[][] newCellOnTheBordMap = new CellOnTheBord[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                CellOnTheBord originalCell = board.getCellOnTheBordMap()[i][j];
                CellOnTheBord newCell = new CellOnTheBord(originalCell.getPieces(), originalCell.getLineCoordinate(), originalCell.getColumnCoordinate());
                newCellOnTheBordMap[i][j] = newCell;
            }
        }

        return new Board(newCellOnTheBordMap);
    }

    private boolean isGameOver(Board board, PieceService pieceService) {
        return pieceService.numberOfPossibleMovesForWhite(board) == 0 || pieceService.numberOfPossibleMovesForBlack(board) == 0;
    }

}
