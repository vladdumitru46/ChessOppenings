package org.example;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.board.Move;
import com.example.models.pieces.*;
import org.example.repositoryes.repos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service("pieceService")
public class PieceService {
    Logger logger = LoggerFactory.getLogger(PieceService.class);
    private final PawnRepository pawnRepository;
    private final RookRepository rookRepository;
    private final KnightRepository knightRepository;
    private final BishopRepository bishopRepository;
    private final QueenRepository queenRepository;
    private final KingRepository kingRepository;

    public PieceService(PawnRepository pawnRepository, RookRepository rookRepository, KnightRepository knightRepository, BishopRepository bishopRepository, QueenRepository queenRepository, KingRepository kingRepository) {
        this.pawnRepository = pawnRepository;
        this.rookRepository = rookRepository;
        this.knightRepository = knightRepository;
        this.bishopRepository = bishopRepository;
        this.queenRepository = queenRepository;
        this.kingRepository = kingRepository;
    }

    public boolean canThePawnMove(Board board, CellOnTheBord start, CellOnTheBord end, Pawn pawn) {
        logger.info("verify if the pawn can move");
        return pawnRepository.canMove(board, start, end, pawn);
    }

    public boolean canTheRookMove(Board board, CellOnTheBord start, CellOnTheBord end, Rook rook) {
        logger.info("verify if the rook can move");
        return rookRepository.canMove(board, start, end, rook);
    }

    public boolean canTheKnightMove(Board board, CellOnTheBord start, CellOnTheBord end, Knight knight) {
        logger.info("verify if the knight can move");
        return knightRepository.canMove(board, start, end, knight);
    }

    public boolean canTheBishopMove(Board board, CellOnTheBord start, CellOnTheBord end, Bishop bishop) {
        logger.info("verify if the bishop can move");
        return bishopRepository.canMove(board, start, end, bishop);
    }

    public boolean canTheQueenMove(Board board, CellOnTheBord start, CellOnTheBord end, Queen queen) {
        logger.info("verify if the queen can move");
        return queenRepository.canMove(board, start, end, queen);
    }

    public boolean canTheKingMove(Board board, CellOnTheBord start, CellOnTheBord end, King king) {
        logger.info("verify if the king can move");
        return kingRepository.canMove(board, start, end, king);
    }

    public boolean checkIsTheKingInCheck(Board board, CellOnTheBord start, CellOnTheBord end, Pieces king) {
        logger.info("verify is the king is checked");
        return kingRepository.checkIfTheKingIsInCheck(board, start, end, king);
    }

    public boolean canCastle(Board board, CellOnTheBord start, CellOnTheBord end, Pieces king) {
        return kingRepository.canCastle(board, start, end, (King) king);
    }


    public List<Move> getAllPossibleMovesForWhite(Board board) {
        List<CellOnTheBord> collect = Arrays.stream(board.getCellOnTheBordMap())
                .flatMap(Arrays::stream)
                .filter(i -> i.getPieces() != null && i.getPieces().isWhite())
                .collect(Collectors.toList());

        return collect.stream()
                .flatMap(piece -> Arrays.stream(board.getCellOnTheBordMap())
                        .flatMap(Arrays::stream)
                        .filter(endCell -> !piece.equals(endCell) && possibleMovesForAPiece(board, piece, endCell))
                        .map(endCell -> new Move(piece, endCell)))
                .collect(Collectors.toList());
    }

    public List<Move> getAllPossibleMovesForBlack(Board board) {
        List<CellOnTheBord> collect = Arrays.stream(board.getCellOnTheBordMap())
                .flatMap(Arrays::stream)
                .filter(i -> i.getPieces() != null && !i.getPieces().isWhite())
                .collect(Collectors.toList());

        return collect.stream()
                .flatMap(piece -> Arrays.stream(board.getCellOnTheBordMap())
                        .flatMap(Arrays::stream)
                        .filter(endCell -> !piece.equals(endCell) && possibleMovesForAPiece(board, piece, endCell))
                        .map(endCell -> new Move(piece, endCell)))
                .collect(Collectors.toList());
    }

    public List<Move> getAllPossibleMoves(Board board) {
        List<CellOnTheBord> collect = Arrays.stream(board.getCellOnTheBordMap())
                .flatMap(Arrays::stream)
                .filter(i -> i.getPieces() != null)
                .collect(Collectors.toList());

        return collect.stream()
                .flatMap(piece -> Arrays.stream(board.getCellOnTheBordMap())
                        .flatMap(Arrays::stream)
                        .filter(endCell -> !piece.equals(endCell) && possibleMovesForAPiece(board, piece, endCell))
                        .map(endCell -> new Move(piece, endCell)))
                .collect(Collectors.toList());
    }


    public boolean possibleMovesForAPiece(Board board, CellOnTheBord startCell, CellOnTheBord endCell) {
        if (startCell.getPieces() instanceof King) {
            return kingRepository.canMove(board, startCell, endCell, (King) startCell.getPieces());
        } else if (startCell.getPieces() instanceof Queen) {
            return queenRepository.canMove(board, startCell, endCell, (Queen) startCell.getPieces());
        } else if (startCell.getPieces() instanceof Bishop) {
            return bishopRepository.canMove(board, startCell, endCell, (Bishop) startCell.getPieces());
        } else if (startCell.getPieces() instanceof Knight) {
            return knightRepository.canMove(board, startCell, endCell, (Knight) startCell.getPieces());
        } else if (startCell.getPieces() instanceof Rook) {
            return rookRepository.canMove(board, startCell, endCell, (Rook) startCell.getPieces());
        } else if (startCell.getPieces() instanceof Pawn) {
            return pawnRepository.canMove(board, startCell, endCell, (Pawn) startCell.getPieces());
        }
        return false;
    }


    public void makeMove(Board board, Move move) {
        board.getCellOnTheBordMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()]
                .setPieces(board.getCellOnTheBordMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()].getPieces());
        board.getCellOnTheBordMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()] =
                new CellOnTheBord(null, move.getStart().getLineCoordinate(), move.getStart().getColumnCoordinate());

    }


    public String transformMoveToCorrectNotation(CellOnTheBord start, CellOnTheBord end, Board board) {
        String notation = "";
        if (start.getPieces() instanceof King) {
            notation += "K";
        } else if (start.getPieces() instanceof Queen) {
            notation += "Q";
        } else if (start.getPieces() instanceof Rook) {
            notation += "R";
        } else if (start.getPieces() instanceof Bishop) {
            notation += "B";
        } else if (start.getPieces() instanceof Knight) {
            notation += "N";
        }

        if (!(start.getPieces() instanceof Pawn)) {

            for (int j = 0; j < 8; j++) {
                CellOnTheBord endCell = board.getCellOnTheBordMap()[start.getLineCoordinate()][j];
                if (start.getPieces() == endCell.getPieces()) {
                    notation += transformColumnToLetters(start);
                    break;
                }
            }
        }
        if (end.getPieces() != null) {
            if (start.getPieces() instanceof Pawn) {
                notation += transformColumnToLetters(start);
            }
            notation += "x";
        }
        notation += transformColumnToLetters(end) + (end.getLineCoordinate() + 1);

        King king = (King) board.getKing(!start.getPieces().isWhite()).getPieces();

        Pieces pieceOnStart = board.getCellOnTheBordMap()[start.getLineCoordinate()][start.getColumnCoordinate()].getPieces();
        Pieces pieceOnEnd = board.getCellOnTheBordMap()[end.getLineCoordinate()][end.getColumnCoordinate()].getPieces();
        board.getCellOnTheBordMap()[end.getLineCoordinate()][end.getColumnCoordinate()].setPieces(pieceOnStart);
        board.getCellOnTheBordMap()[start.getLineCoordinate()][start.getColumnCoordinate()].setPieces(null);

        if (checkIsTheKingInCheck(board, start, end, king)) {
            notation += "+";
        }
        board.getCellOnTheBordMap()[end.getLineCoordinate()][end.getColumnCoordinate()].setPieces(pieceOnEnd);
        board.getCellOnTheBordMap()[start.getLineCoordinate()][start.getColumnCoordinate()].setPieces(pieceOnStart);


        return notation;
    }

    public String transformColumnToLetters(CellOnTheBord cell) {
        switch (cell.getColumnCoordinate()) {
            case 0:
                return "a";
            case 1:
                return "b";
            case 2:
                return "c";
            case 3:
                return "d";
            case 4:
                return "e";
            case 5:
                return "f";
            case 6:
                return "g";
            case 7:
                return "h";
        }
        return "";
    }


    public Integer numberOfCenterSquaresAttackedForWhite(Board board) {
        return (int) Arrays.stream(board.getCellOnTheBordMap())
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getPieces() != null && cell.getPieces().isWhite())
                .filter(cell -> IntStream.range(4, 8)
                        .anyMatch(i -> IntStream.range(0, 8)
                                .anyMatch(j -> possibleMovesForAPiece(board, cell, board.getCellOnTheBordMap()[i][j]))
                        )
                )
                .count();
    }

    public Integer numberOfCenterSquaresAttackedForBlack(Board board) {
        return (int) Arrays.stream(board.getCellOnTheBordMap())
                .flatMap(Arrays::stream)
                .filter(cell -> cell.getPieces() != null && !cell.getPieces().isWhite())
                .filter(cell -> IntStream.range(0, 4)
                        .anyMatch(i -> IntStream.range(0, 8)
                                .anyMatch(j -> possibleMovesForAPiece(board, cell, board.getCellOnTheBordMap()[i][j]))
                        )
                )
                .count();
    }

}
