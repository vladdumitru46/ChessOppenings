package org.example.score;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.Pawn;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("centerControlScore")
@AllArgsConstructor
public class CenterControlScore {


    private final MobilityScore mobilityScore;

    public Integer numberOfCenterSquaresAttacked(Board board, boolean isWhite) {
        int center = isWhite ? 4 : 0;
        int endCenter = isWhite ? 8 : 4;

        int nr = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                CellOnTheBoard myCell = board.getCellOnTheBoardMap()[i][j];
                if (myCell.getPieces() != null && myCell.getPieces().isWhite() == isWhite) {
                    if (myCell.getPieces() instanceof Pawn) {
                        if (myCell.getColumnCoordinate() != 0 && myCell.getColumnCoordinate() != 7) {
                            nr += 2;
                        } else
                            nr++;
                    } else {
                        for (int m = center; m < endCenter; m++) {
                            for (int n = 0; n < 8; n++) {
                                CellOnTheBoard cell = board.getCellOnTheBoardMap()[m][n];
                                if (mobilityScore.possibleMovesForAPiece(board, myCell, cell)) {
                                    nr++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return nr;
    }
}
