package org.example.miniMax;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.board.Move;
import org.example.PieceService;

import java.util.ArrayList;
import java.util.List;

public class MiniMax {

    public Move getBestMove(Board board, int depth, boolean isWhite, PieceService pieceService) {
        if (depth == 0 || isGameOver(board, pieceService)) {
            return null;
        }

        List<Move> moves = pieceService.getAllPossibleMoves(board);
        int bestValue = isWhite ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Move bestMove = null;

        for (Move move : moves) {
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
        if (depth == 0 || isGameOver(board, pieceService)) {// || isCheckmateIn2(board, isMaximizing, pieceService)) {
            return new Evaluation().evaluationScore(board, pieceService, isMaximizing);
        }


        if (isMaximizing) {

            List<Move> moves = pieceService.getAllPossibleMovesForWhite(board);

            int maxEval = Integer.MIN_VALUE;
            for (Move move : moves) {
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
            List<Move> moves = pieceService.getAllPossibleMovesForBlack(board);
            int minEval = Integer.MAX_VALUE;
            for (Move move : moves) {
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
        return pieceService.getAllPossibleMovesForWhite(board).size() == 0
                || pieceService.getAllPossibleMovesForBlack(board).size() == 0;
    }

    public boolean isCheckmateIn2(Board board, boolean isWhite, PieceService pieceService) {
        List<Move> moves = new ArrayList<>();
        if (isWhite) {
            moves = pieceService.getAllPossibleMovesForWhite(board);
        } else {
            moves = pieceService.getAllPossibleMovesForBlack(board);
        }
        for (Move move1 : moves) {
            Board boardAfterMove1 = cloneBoard(board);
            pieceService.makeMove(boardAfterMove1, move1);

            List<Move> opponentMovesAfterMove1 = new ArrayList<>();
            if (isWhite) {
                opponentMovesAfterMove1 = pieceService.getAllPossibleMovesForBlack(board);
            } else {
                opponentMovesAfterMove1 = pieceService.getAllPossibleMovesForWhite(board);
            }
            for (Move move2 : opponentMovesAfterMove1) {
                Board boardAfterMove2 = cloneBoard(boardAfterMove1);
                pieceService.makeMove(boardAfterMove2, move2);

                List<Move> opponentMovesAfterMove2 = new ArrayList<>();
                if (isWhite) {
                    opponentMovesAfterMove2 = pieceService.getAllPossibleMovesForWhite(board);
                } else {
                    opponentMovesAfterMove2 = pieceService.getAllPossibleMovesForBlack(board);
                }
                int nr = 0;
                for (Move move3 : opponentMovesAfterMove2) {
                    Board boardAfterMove3 = cloneBoard(boardAfterMove1);
                    pieceService.makeMove(boardAfterMove3, move2);

                    if (isGameOver(boardAfterMove2, pieceService)) {
                        nr++;
                    }

                }
                if (nr == opponentMovesAfterMove2.size()) {
                    return true;
                }
            }

        }
        return false;

    }
}
