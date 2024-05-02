package org.example.score;

import com.example.models.board.Board;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("capturePieceScore")
@AllArgsConstructor
public class CapturePiecesScore {

    private final MobilityScore mobilityScore;

    public int canAPieceBeCaptured(Board board, boolean colourOfThePieceThatCanCaptureAnEnemyPiece) {
        return (int) mobilityScore.getAllPossibleMoves(board, colourOfThePieceThatCanCaptureAnEnemyPiece).stream()
                .filter(move -> move.getEnd().getPieces() != null)
                .count();
    }
}
