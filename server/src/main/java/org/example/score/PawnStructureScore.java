package org.example.score;

import com.example.models.board.Board;
import com.example.models.pieces.Pawn;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service("pawnStructureScore")
public class PawnStructureScore {

    public Integer verifyPawnStructure(Board board, boolean isWhite) {
        return (int) Arrays.stream(board.getCellOnTheBoardMap())
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getPieces() instanceof Pawn && cell.getPieces().isWhite() == isWhite)
                .filter(cell -> verifyIfThePawnHasAPawnNear(board, cell.getColumnCoordinate() + 1, isWhite))
                .count() + 1;
    }

    private boolean verifyIfThePawnHasAPawnNear(Board board, int column, boolean isWhite) {
        if (column < 8 && column >= 0) {
            for (int i = 0; i < 8; i++) {
                if (board.getCellOnTheBoardMap()[i][column].getPieces() instanceof Pawn && board.getCellOnTheBoardMap()[i][column].getPieces().isWhite() == isWhite) {
                    return true;
                }
            }
        }
        return false;
    }

    public int isolatedPawns(Board board, boolean isWhite) {
        return (int) Arrays.stream(board.getCellOnTheBoardMap())
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getPieces() instanceof Pawn && cell.getPieces().isWhite() == isWhite)
                .filter(cell -> !verifyIfThePawnHasAPawnNear(board, cell.getColumnCoordinate() + 1, isWhite))
                .filter(cell -> !verifyIfThePawnHasAPawnNear(board, cell.getColumnCoordinate() - 1, isWhite))
                .count();
    }


    public int verifyIfThereAreDoublePawns(Board board, boolean isWhite) {
        return (int) Arrays.stream(board.getCellOnTheBoardMap())
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getPieces() instanceof Pawn && cell.getPieces().isWhite() == isWhite)
                .filter(cell -> verifyIfThePawnHasAPawnInFrontOfHim(board, cell.getColumnCoordinate(), cell.getLineCoordinate(), isWhite))
                .count();
    }

    private boolean verifyIfThePawnHasAPawnInFrontOfHim(Board board, int column, int line, boolean isWhite) {
        for (int i = line + 1; i < 8; i++) {
            if (board.getCellOnTheBoardMap()[i][column].getPieces() instanceof Pawn && board.getCellOnTheBoardMap()[i][column].getPieces().isWhite() == isWhite) {
                return true;
            }
        }
        return false;
    }


}

