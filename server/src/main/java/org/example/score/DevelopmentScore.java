package org.example.score;

import com.example.models.board.Board;
import com.example.models.pieces.King;
import com.example.models.pieces.Pawn;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service("developmentScore")
public class DevelopmentScore {
    public int developmentBonus(Board board, boolean isWhite) {
        return (int) Arrays.stream(board.getCellOnTheBoardMap())
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getPieces() != null && !(cell.getPieces() instanceof King) && !(cell.getPieces() instanceof Pawn))
                .filter(cell -> (isWhite && cell.getPieces().isWhite() && cell.getLineCoordinate() > 1) ||
                        (!isWhite && !cell.getPieces().isWhite() && cell.getLineCoordinate() < 6))
                .count();
    }
}
