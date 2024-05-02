package org.example.score;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.King;
import lombok.AllArgsConstructor;
import org.example.repositoryes.pieces.KingRepository;
import org.springframework.stereotype.Service;

@Service("kingSafetyScore")
@AllArgsConstructor
public class KingSafetyScore {

    private final KingRepository kingRepository;
    private final MobilityScore mobilityScore;

    public Integer canTheKingBeCheckedInNextMove(Board board, boolean isWhite) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                CellOnTheBoard cell = board.getCellOnTheBoardMap()[i][j];
                if (cell.getPieces() != null && cell.getPieces().isWhite() != isWhite) {
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            if (kingRepository.checkIfTheKingIsInCheckAfterMove(board, cell,
                                    board.getCellOnTheBoardMap()[k][l], isWhite)) {
                                return 2;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

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

