package org.example.score;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.board.Move;
import com.example.models.pieces.Pieces;
import com.example.models.pieces.Queen;
import lombok.AllArgsConstructor;
import org.example.board.PieceService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("capturePieceScore")
@AllArgsConstructor
public class CapturePiecesScore {

    private final MobilityScore mobilityScore;
    private final PieceService pieceService;

    /**
     * @param board
     * @param currentColour colour of the piece that can capture the enemy pieces (if you play with white, it verifies if white can capture black pieces)
     * @return
     */
    public int canAPieceBeCaptured(Board board, boolean currentColour) {
        return (int) mobilityScore.getAllPossibleMoves(board, currentColour).stream()
                .filter(move -> move.getEnd().getPieces() != null)
                .count();
    }

    public List<Move> captureMoves(Board board, boolean currentColour) {
        return mobilityScore.getAllPossibleMoves(board, currentColour).stream()
                .filter(move -> move.getEnd().getPieces() != null)
                .toList();
    }

    public int isTheAttackedPieceProtected(Board board, boolean isWhite, Move move) {
        int numberOfProtectedPieces;
        Pieces piecesOnStart = move.getStart().getPieces();
        Pieces piecesOnEnd = move.getEnd().getPieces();
        pieceService.makeMove(board, move);
        numberOfProtectedPieces = (int) mobilityScore.getAllPossibleMoves(board, !isWhite).stream()
                .filter(defendigMove -> defendigMove.getEnd().equals(move.getEnd()))
                .count();
        pieceService.undoMove(board, move, piecesOnStart, piecesOnEnd);
        return numberOfProtectedPieces;
    }


    public boolean canTheQueenBeCaptured(Board board, boolean isWhite) {
        CellOnTheBoard queenCell = Arrays.stream(board.getCellOnTheBoardMap())
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getPieces() != null)
                .filter(cell -> cell.getPieces() instanceof Queen && cell.getPieces().isWhite() == isWhite)
                .findFirst()
                .orElse(null);

        if (queenCell == null) {
            return false;
        }
        List<Move> listOfEnemyMoves = mobilityScore.getAllPossibleMoves(board, !isWhite);
        long count = listOfEnemyMoves.stream()
                .filter(move -> move.getEnd().equals(queenCell))
                .count();
        return count != 0;
    }

    public boolean hesMyQueenBeenCaptured(Board board, boolean isWhite) {
        CellOnTheBoard queenCell = Arrays.stream(board.getCellOnTheBoardMap())
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getPieces() != null)
                .filter(cell -> cell.getPieces() instanceof Queen && cell.getPieces().isWhite() == isWhite)
                .findFirst()
                .orElse(null);

        return queenCell == null;

    }
}
