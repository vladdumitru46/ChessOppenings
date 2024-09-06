package org.example.score;

import com.example.models.board.Board;
import com.example.models.pieces.King;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("kingSafetyScore")
@AllArgsConstructor
public class KingSafetyScore {
    private final MobilityScore mobilityScore;

    public boolean isTheEnemyKingInCheck(Board board, boolean isWhite) {
        King king = (King) board.getKing(isWhite).getPieces();
        return king.isInCheck();
    }

    public boolean checkMateCheck(Board board, boolean isWhite) {
        boolean isInCheck = isTheEnemyKingInCheck(board, isWhite);
        int numberOfMoves = mobilityScore.getAllPossibleMoves(board, isWhite).size();
        return isInCheck && numberOfMoves == 0;
    }

}

