package org.example.repositoryes.pieces;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.pieces.King;
import com.example.models.pieces.Queen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.stream.IntStream;

@Repository
public non-sealed class QueenRepository implements IRepository<Queen> {
    Logger logger = LoggerFactory.getLogger(QueenRepository.class);

    @Override
    public boolean canMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Queen pieces) {
        if (end.getPieces() != null) {
            if (end.getPieces().isWhite() == pieces.isWhite()) {
                return false;
            }
        }

        if (end.getPieces() instanceof King) {
            return false;
        }
        Integer line = Math.abs(start.getLineCoordinate() - end.getLineCoordinate());
        Integer column = Math.abs(start.getColumnCoordinate() - end.getColumnCoordinate());
        if (Math.abs(line - column) != 0) {
            logger.info("queen cannot move to this coordinates {}{}, because it's not diagonal", end.getLineCoordinate(), end.getColumnCoordinate());
            if (start.getLineCoordinate() != end.getLineCoordinate() && start.getColumnCoordinate() != end.getColumnCoordinate()) {
                logger.info("queen cannot move to this coordinates {}{}, because it's not a straight line", end.getLineCoordinate(), end.getColumnCoordinate());
                return false;
            }
            int startLine = start.getLineCoordinate();
            int startColumn = start.getColumnCoordinate();
            int endLine = end.getLineCoordinate();
            int endColumn = end.getColumnCoordinate();

            int rowIncrement = Integer.compare(endLine, startLine);
            int colIncrement = Integer.compare(endColumn, startColumn);

            int currentLine = startLine + rowIncrement;
            int currentColumn = startColumn + colIncrement;

            while (currentLine != endLine || currentColumn != endColumn) {
                CellOnTheBoard currentCell = board.getCellOnTheBoardMap()[currentLine][currentColumn];
                if (currentCell.getPieces() != null) {
                    logger.info("queen cannot move to this coordinates {}{}, because there is a piece blocking the way", end.getLineCoordinate(), end.getColumnCoordinate());
                    return false;
                }

                currentLine += rowIncrement;
                currentColumn += colIncrement;
            }

        } else {
            int startLine = start.getLineCoordinate();
            int startColumn = start.getColumnCoordinate();
            int endLine = end.getLineCoordinate();
            int endColumn = end.getColumnCoordinate();

            int rowIncrement = (endLine > startLine) ? 1 : -1;
            int colIncrement = (endColumn > startColumn) ? 1 : -1;

            int currentLine = startLine + rowIncrement;
            int currentColumn = startColumn + colIncrement;
            while (currentLine != endLine && currentColumn != endColumn) {
                CellOnTheBoard currentCell = board.getCellOnTheBoardMap()[currentLine][currentColumn];
                if (currentCell.getPieces() != null) {
                    logger.info("queen cannot move to this coordinates {}{} from {}{}, because there is a piece that blocks the way", end.getLineCoordinate(), end.getColumnCoordinate(), start.getLineCoordinate(), start.getColumnCoordinate());
                    return false;
                }

                currentLine += rowIncrement;
                currentColumn += colIncrement;
            }
        }
        KingRepository kingRepository = new KingRepository();
        return kingRepository.checkIfTheKingIsInCheckAfterMove(board, start, end, pieces.isWhite());

    }


    @Override
    public int getNrOfMoves(Board board, CellOnTheBoard cell, int nr) {
        return Math.toIntExact(IntStream.range(0, 8)
                .flatMap(i -> IntStream.range(0, 8)
                        .filter(j -> canMove(board, cell, board.getCellOnTheBoardMap()[i][j], (Queen) cell.getPieces())))
                .count());
    }
}

