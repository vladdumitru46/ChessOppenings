package org.example.board;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.board.Move;
import com.example.models.pieces.*;
import lombok.extern.slf4j.Slf4j;
import org.example.repositoryes.pieces.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.LongStream;


@Service("pieceService")
@Slf4j
public class PieceService {
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


    public boolean canThePawnMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Pawn pawn) {
        log.info("verify if the pawn can move");
        return pawnRepository.canMove(board, start, end, pawn);
    }

    public boolean canTheRookMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Rook rook) {
        log.info("verify if the rook can move");

        if (rookRepository.canMove(board, start, end, rook)) {
            rook.setHasBeenMoved(true);
            start.setPieces(rook);
            return true;
        } else {
            return false;
        }
    }

    public boolean canTheKnightMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Knight knight) {
        log.info("verify if the knight can move");
        return knightRepository.canMove(board, start, end, knight);
    }

    public boolean canTheBishopMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Bishop bishop) {
        log.info("verify if the bishop can move");
        return bishopRepository.canMove(board, start, end, bishop);
    }

    public boolean canTheQueenMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Queen queen) {
        log.info("verify if the queen can move");
        return queenRepository.canMove(board, start, end, queen);
    }

    public boolean canTheKingMove(Board board, CellOnTheBoard start, CellOnTheBoard end, King king) {
        log.info("verify if the king can move");
        if (kingRepository.canMove(board, start, end, king)) {
            king.setHasBeenMoved(true);
            start.setPieces(king);
            return true;
        } else {
            return false;
        }
    }

    public boolean canThePawnPromote(Board board, CellOnTheBoard cell, CellOnTheBoard end) {
        return pawnRepository.canPromote(board, cell, end);
    }


    public boolean canCastle(Board board, CellOnTheBoard start, CellOnTheBoard end, King king) {
        if (kingRepository.canCastle(board, start, end, king)) {
            king.setHasBeenMoved(true);
            start.setPieces(king);

            if (king.isWhite()) {
                if (end.getColumnCoordinate() == 2) {
                    Rook rook = (Rook) board.getCellOnTheBoardMap()[0][0].getPieces();
                    rook.setHasBeenMoved(true);
                    board.getCellOnTheBoardMap()[0][3].setPieces(rook);
                    board.getCellOnTheBoardMap()[0][0] = new CellOnTheBoard(null, 0, 0);
                }
                if (end.getColumnCoordinate() == 6) {
                    Rook rook = (Rook) board.getCellOnTheBoardMap()[0][7].getPieces();
                    rook.setHasBeenMoved(true);
                    board.getCellOnTheBoardMap()[0][5].setPieces(rook);
                    board.getCellOnTheBoardMap()[0][7] = new CellOnTheBoard(null, 0, 0);
                }
            } else {
                if (end.getColumnCoordinate() == 2) {
                    Rook rook = (Rook) board.getCellOnTheBoardMap()[7][0].getPieces();
                    rook.setHasBeenMoved(true);
                    board.getCellOnTheBoardMap()[7][3].setPieces(rook);
                    board.getCellOnTheBoardMap()[7][0] = new CellOnTheBoard(null, 7, 0);
                } else if (end.getColumnCoordinate() == 6) {
                    Rook rook = (Rook) board.getCellOnTheBoardMap()[7][7].getPieces();
                    rook.setHasBeenMoved(true);
                    board.getCellOnTheBoardMap()[7][5].setPieces(rook);
                    board.getCellOnTheBoardMap()[7][7] = new CellOnTheBoard(null, 7, 0);
                }
            }
            return true;
        } else {
            return false;
        }
    }


    public synchronized void makeMove(Board board, Move move) {
        CellOnTheBoard kingsCell = board.getKing(!move.getStart().getPieces().isWhite());
        King king = (King) kingsCell.getPieces();

        board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()]
                .setPieces(move.getStart().getPieces());
        board.getCellOnTheBoardMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()].setPieces(null);

        updateKingStatus(board, move, king);
    }

    private synchronized void updateKingStatus(Board board, Move move, King king) {
        king.setInCheck(!kingRepository.checkIfTheKingIsInCheck(board,
                board.getCellOnTheBoardMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()],
                board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()],
                king));
    }

    public synchronized void undoMove(Board board, Move move, Pieces pieceOnStart, Pieces pieceOnEnd) {
        King king = (King) board.getKing(!board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()]
                .getPieces().isWhite()).getPieces();
        board.getCellOnTheBoardMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()]
                .setPieces(pieceOnStart);
        board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()].setPieces(pieceOnEnd);

        updateKingStatus(board, move, king);
    }


    public String transformMoveToCorrectNotation(CellOnTheBoard start, CellOnTheBoard end, Board board) {
        Pieces pieceOnStart = start.getPieces();
        Pieces pieceOnEnd = end.getPieces();
        String notation = "";

        if (start.getPieces() == null) {
            System.out.println(start);
            System.out.println(board);
        }
        switch (start.getPieces()) {
            case King ignored -> notation += "K";
            case Queen ignored -> notation += "Q";
            case Rook ignored -> notation += "R";
            case Bishop ignored -> notation += "B";
            case Knight ignored -> notation += "N";
            case Pawn ignored -> notation += "";
        }

        if (!(start.getPieces() instanceof Pawn)) {

            for (int j = 0; j < 8; j++) {
                CellOnTheBoard endCell = board.getCellOnTheBoardMap()[start.getLineCoordinate()][j];
                if (start.getPieces() == endCell.getPieces() && start.getColumnCoordinate() != j) {
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

        makeMove(board, new Move(board.getCellOnTheBoardMap()[start.getLineCoordinate()][start.getColumnCoordinate()], board.getCellOnTheBoardMap()[end.getLineCoordinate()][end.getColumnCoordinate()]));
        King king = (King) board.getKing(!pieceOnStart.isWhite()).getPieces();
        if (king.isInCheck()) {
            notation += "+";
        }
        undoMove(board, new Move(board.getCellOnTheBoardMap()[start.getLineCoordinate()][start.getColumnCoordinate()], board.getCellOnTheBoardMap()[end.getLineCoordinate()][end.getColumnCoordinate()]), pieceOnStart, pieceOnEnd);
        return notation;
    }

    public String transformColumnToLetters(CellOnTheBoard cell) {
        return switch (cell.getColumnCoordinate()) {
            case 0 -> "a";
            case 1 -> "b";
            case 2 -> "c";
            case 3 -> "d";
            case 4 -> "e";
            case 5 -> "f";
            case 6 -> "g";
            case 7 -> "h";
            default -> "";
        };
    }

}

//package org.example.board;
//
//import com.example.models.board.Board;
//import com.example.models.board.CellOnTheBoard;
//import com.example.models.board.Move;
//import com.example.models.pieces.*;
//import lombok.extern.slf4j.Slf4j;
//import org.example.repositoryes.pieces.*;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.LongStream;
//
//
//@Service("pieceService")
//@Slf4j
//public class PieceService {
//    private final PawnRepository pawnRepository;
//    private final RookRepository rookRepository;
//    private final KnightRepository knightRepository;
//    private final BishopRepository bishopRepository;
//    private final QueenRepository queenRepository;
//    private final KingRepository kingRepository;
//    private final Map<String, Boolean> pawnPromotion = new HashMap<>();
//
//
//    public PieceService(PawnRepository pawnRepository, RookRepository rookRepository, KnightRepository knightRepository, BishopRepository bishopRepository, QueenRepository queenRepository, KingRepository kingRepository) {
//        this.pawnRepository = pawnRepository;
//        this.rookRepository = rookRepository;
//        this.knightRepository = knightRepository;
//        this.bishopRepository = bishopRepository;
//        this.queenRepository = queenRepository;
//        this.kingRepository = kingRepository;
//    }
//
//
//    public boolean canThePawnMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Pawn pawn) {
//        log.info("verify if the pawn can move");
//        return pawnRepository.canMove(board, start, end, pawn);
//    }
//
//    public boolean canTheRookMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Rook rook) {
//        log.info("verify if the rook can move");
//
//        if (rookRepository.canMove(board, start, end, rook)) {
//            rook.setHasBeenMoved(true);
//            start.setPieces(rook);
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public boolean canTheKnightMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Knight knight) {
//        log.info("verify if the knight can move");
//        return knightRepository.canMove(board, start, end, knight);
//    }
//
//    public boolean canTheBishopMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Bishop bishop) {
//        log.info("verify if the bishop can move");
//        return bishopRepository.canMove(board, start, end, bishop);
//    }
//
//    public boolean canTheQueenMove(Board board, CellOnTheBoard start, CellOnTheBoard end, Queen queen) {
//        log.info("verify if the queen can move");
//        return queenRepository.canMove(board, start, end, queen);
//    }
//
//    public boolean canTheKingMove(Board board, CellOnTheBoard start, CellOnTheBoard end, King king) {
//        log.info("verify if the king can move");
//        if (kingRepository.canMove(board, start, end, king)) {
//            king.setHasBeenMoved(true);
//            start.setPieces(king);
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public boolean canThePawnPromote(Board board, CellOnTheBoard cell, CellOnTheBoard end) {
//        return pawnRepository.canPromote(board, cell, end);
//    }
//
//
//    public boolean canCastle(Board board, CellOnTheBoard start, CellOnTheBoard end, King king) {
//        if (kingRepository.canCastle(board, start, end, king)) {
//            king.setHasBeenMoved(true);
//            start.setPieces(king);
//
//            if (king.isWhite()) {
//                if (end.getColumnCoordinate() == 2) {
//                    Rook rook = (Rook) board.getCellOnTheBoardMap()[0][0].getPieces();
//                    rook.setHasBeenMoved(true);
//                    board.getCellOnTheBoardMap()[0][3].setPieces(rook);
//                    board.getCellOnTheBoardMap()[0][0] = new CellOnTheBoard(null, 0, 0);
//                }
//                if (end.getColumnCoordinate() == 6) {
//                    Rook rook = (Rook) board.getCellOnTheBoardMap()[0][7].getPieces();
//                    rook.setHasBeenMoved(true);
//                    board.getCellOnTheBoardMap()[0][5].setPieces(rook);
//                    board.getCellOnTheBoardMap()[0][7] = new CellOnTheBoard(null, 0, 0);
//                }
//            } else {
//                if (end.getColumnCoordinate() == 2) {
//                    Rook rook = (Rook) board.getCellOnTheBoardMap()[7][0].getPieces();
//                    rook.setHasBeenMoved(true);
//                    board.getCellOnTheBoardMap()[7][3].setPieces(rook);
//                    board.getCellOnTheBoardMap()[7][0] = new CellOnTheBoard(null, 7, 0);
//                } else if (end.getColumnCoordinate() == 6) {
//                    Rook rook = (Rook) board.getCellOnTheBoardMap()[7][7].getPieces();
//                    rook.setHasBeenMoved(true);
//                    board.getCellOnTheBoardMap()[7][5].setPieces(rook);
//                    board.getCellOnTheBoardMap()[7][7] = new CellOnTheBoard(null, 7, 0);
//                }
//            }
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//
//    public List<Move> getAllPossibleMoves(Board board, boolean isWhite) {
//        List<CellOnTheBoard> collect = Arrays.stream(board.getCellOnTheBoardMap())
//                .flatMap(Arrays::stream)
//                .filter(i -> i.getPieces() != null && i.getPieces().isWhite() == isWhite)
//                .toList();
//
//        List<Move> moveList = new ArrayList<>();
//        for (var piece : collect) {
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    CellOnTheBoard endCell = board.getCellOnTheBoardMap()[i][j];
//                    if (piece != endCell) {
//                        int ok = 0;
//                        if (piece.getPieces() instanceof Pawn) {
//                            if (pawnRepository.canPromote(board, piece, endCell)) {
//                                moveList.addAll(promotePawns(piece, endCell));
//                                ok = 1;
//                            }
//                        }
//                        if (ok == 0) {
//                            if (possibleMovesForAPiece(board, piece, endCell)) {
//                                moveList.add(new Move(piece, endCell));
//                            }
//                        }
//
//                    }
//                }
//            }
//        }
//        return moveList;
//    }
//
//    //todo: castling for ai
//    public boolean possibleMovesForAPiece(Board board, CellOnTheBoard startCell, CellOnTheBoard endCell) {
//
//        return switch (startCell.getPieces()) {
//            case King ignored -> {
//                if (!kingRepository.canMove(board, startCell, endCell, (King) startCell.getPieces())) {
//                    yield kingRepository.canCastle(board, startCell, endCell, (King) startCell.getPieces());
//                } else {
//                    yield true;
//                }
//            }
//            case Queen ignored -> queenRepository.canMove(board, startCell, endCell, (Queen) startCell.getPieces());
//            case Rook ignored -> rookRepository.canMove(board, startCell, endCell, (Rook) startCell.getPieces());
//            case Bishop ignored -> bishopRepository.canMove(board, startCell, endCell, (Bishop) startCell.getPieces());
//            case Knight ignored -> knightRepository.canMove(board, startCell, endCell, (Knight) startCell.getPieces());
//            case Pawn ignored -> pawnRepository.canMove(board, startCell, endCell, (Pawn) startCell.getPieces());
//        };
//    }
//
//    public List<Move> getAllPossibleMovesForASpecificPiece(Board board, CellOnTheBoard startCell) {
//        List<Move> moveList = new ArrayList<>();
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                CellOnTheBoard endCell = board.getCellOnTheBoardMap()[i][j];
//                if (startCell != endCell) {
//                    int ok = 0;
//                    if (startCell.getPieces() instanceof Pawn) {
//                        if (pawnRepository.canPromote(board, startCell, endCell)) {
//                            moveList.addAll(promotePawns(startCell, endCell));
//                            ok = 1;
//                        }
//                    }
//                    if (ok == 0) {
//                        if (possibleMovesForAPiece(board, startCell, endCell)) {
//                            moveList.add(new Move(startCell, endCell));
//                        }
//                    }
//
//                }
//            }
//        }
//        return moveList;
//    }
//
//
//    private List<Move> promotePawns(CellOnTheBoard startCell, CellOnTheBoard endCell) {
//
//        List<Move> promotePawnsList = new ArrayList<>();
//
//        pawnPromotion.put("knight", false);
//        pawnPromotion.put("bishop", false);
//        pawnPromotion.put("rook", false);
//        pawnPromotion.put("queen", false);
//        int i = 0;
//        while (i < 4) {
//            CellOnTheBoard piecesOnStart = new CellOnTheBoard(null, startCell.getLineCoordinate(), startCell.getColumnCoordinate());
//            if (!pawnPromotion.get("knight")) {
//                piecesOnStart.setPieces(new Knight(startCell.getPieces().isWhite()));
//                pawnPromotion.put("knight", true);
//            } else if (!pawnPromotion.get("bishop")) {
//                piecesOnStart.setPieces(new Bishop(startCell.getPieces().isWhite()));
//                pawnPromotion.put("bishop", true);
//            } else if (!pawnPromotion.get("rook")) {
//                piecesOnStart.setPieces(new Rook(startCell.getPieces().isWhite()));
//                pawnPromotion.put("rook", true);
//            } else if (!pawnPromotion.get("queen")) {
//                piecesOnStart.setPieces(new Queen(startCell.getPieces().isWhite()));
//                pawnPromotion.put("queen", true);
//            }
//            promotePawnsList.add(new Move(piecesOnStart, endCell));
//            i++;
//        }
//        return promotePawnsList;
//    }
//
//
//    public synchronized void makeMove(Board board, Move move) {
//        CellOnTheBoard kingsCell = board.getKing(!move.getStart().getPieces().isWhite());
//        King king = (King) kingsCell.getPieces();
//
//        board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()]
//                .setPieces(move.getStart().getPieces());
//        board.getCellOnTheBoardMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()].setPieces(null);
//
//        updateKingStatus(board, move, king);
//    }
//
//    private synchronized void updateKingStatus(Board board, Move move, King king) {
//        king.setInCheck(!kingRepository.checkIfTheKingIsInCheck(board,
//                board.getCellOnTheBoardMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()],
//                board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()],
//                king));
//    }
//
//    public synchronized void undoMove(Board board, Move move, Pieces pieceOnStart, Pieces pieceOnEnd) {
//        King king = (King) board.getKing(!board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()]
//                .getPieces().isWhite()).getPieces();
//        board.getCellOnTheBoardMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()]
//                .setPieces(pieceOnStart);
//        board.getCellOnTheBoardMap()[move.getEnd().getLineCoordinate()][move.getEnd().getColumnCoordinate()].setPieces(pieceOnEnd);
//
//        updateKingStatus(board, move, king);
//    }
//
//
//    public String transformMoveToCorrectNotation(CellOnTheBoard start, CellOnTheBoard end, Board board) {
//        Pieces pieceOnStart = start.getPieces();
//        Pieces pieceOnEnd = end.getPieces();
//        String notation = "";
//
//        if (start.getPieces() == null) {
//            System.out.println(start);
//            System.out.println(board);
//        }
//        switch (start.getPieces()) {
//            case King ignored -> notation += "K";
//            case Queen ignored -> notation += "Q";
//            case Rook ignored -> notation += "R";
//            case Bishop ignored -> notation += "B";
//            case Knight ignored -> notation += "N";
//            case Pawn ignored -> notation += "";
//        }
//
//        if (!(start.getPieces() instanceof Pawn)) {
//
//            for (int j = 0; j < 8; j++) {
//                CellOnTheBoard endCell = board.getCellOnTheBoardMap()[start.getLineCoordinate()][j];
//                if (start.getPieces() == endCell.getPieces() && start.getColumnCoordinate() != j) {
//                    notation += transformColumnToLetters(start);
//                    break;
//                }
//            }
//        }
//        if (end.getPieces() != null) {
//            if (start.getPieces() instanceof Pawn) {
//                notation += transformColumnToLetters(start);
//            }
//            notation += "x";
//        }
//        notation += transformColumnToLetters(end) + (end.getLineCoordinate() + 1);
//
//        makeMove(board, new Move(board.getCellOnTheBoardMap()[start.getLineCoordinate()][start.getColumnCoordinate()], board.getCellOnTheBoardMap()[end.getLineCoordinate()][end.getColumnCoordinate()]));
//        King king = (King) board.getKing(!pieceOnStart.isWhite()).getPieces();
//        if (king.isInCheck()) {
//            notation += "+";
//        }
//        undoMove(board, new Move(board.getCellOnTheBoardMap()[start.getLineCoordinate()][start.getColumnCoordinate()], board.getCellOnTheBoardMap()[end.getLineCoordinate()][end.getColumnCoordinate()]), pieceOnStart, pieceOnEnd);
//        return notation;
//    }
//
//    public String transformColumnToLetters(CellOnTheBoard cell) {
//        return switch (cell.getColumnCoordinate()) {
//            case 0 -> "a";
//            case 1 -> "b";
//            case 2 -> "c";
//            case 3 -> "d";
//            case 4 -> "e";
//            case 5 -> "f";
//            case 6 -> "g";
//            case 7 -> "h";
//            default -> "";
//        };
//    }
//
//
//    public Integer numberOfCenterSquaresAttacked(Board board, boolean isWhite) {
//        int center = isWhite ? 4 : 0;
//        int endCenter = isWhite ? 8 : 4;
//
//        int nr = 0;
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                CellOnTheBoard enemyCell = board.getCellOnTheBoardMap()[i][j];
//                if (enemyCell.getPieces() != null && enemyCell.getPieces().isWhite() == isWhite) {
//                    for (int m = center; m < endCenter; m++) {
//                        for (int n = 0; n < 8; n++) {
//                            CellOnTheBoard cell = board.getCellOnTheBoardMap()[m][n];
//                            if (possibleMovesForAPiece(board, enemyCell, cell)) {
//                                nr++;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return nr;
//    }
//
//    public Integer canTheKingBeCheckedInNextMove(Board board, boolean isWhite) {
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                CellOnTheBoard cell = board.getCellOnTheBoardMap()[i][j];
//                if (cell.getPieces() != null && cell.getPieces().isWhite() != isWhite) {
//                    for (int k = 0; k < 8; k++) {
//                        for (int l = 0; l < 8; l++) {
//                            if (kingRepository.checkIfTheKingIsInCheckAfterMove(board, cell,
//                                    board.getCellOnTheBoardMap()[k][l], isWhite)) {
//                                return 2;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return 0;
//    }
//
//    public Integer verifyPawnStructure(Board board, boolean isWhite) {
//        return (int) Arrays.stream(board.getCellOnTheBoardMap())
//                .flatMap(Arrays::stream)
//                .filter(cell -> cell.getPieces() instanceof Pawn && cell.getPieces().isWhite() == isWhite)
//                .filter(cell -> verifyIfThePawnHasAPawnNear(board, cell.getColumnCoordinate() + 1, isWhite))
//                .count() + 1;
//    }
//
//    private boolean verifyIfThePawnHasAPawnNear(Board board, int column, boolean isWhite) {
//        if (column < 8 && column >= 0) {
//            for (int i = 0; i < 8; i++) {
//                if (board.getCellOnTheBoardMap()[i][column].getPieces() instanceof Pawn && board.getCellOnTheBoardMap()[i][column].getPieces().isWhite() == isWhite) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public int verifyIfThereAreDoublePawns(Board board, boolean isWhite) {
//        return (int) Arrays.stream(board.getCellOnTheBoardMap())
//                .flatMap(Arrays::stream)
//                .filter(cell -> cell.getPieces() instanceof Pawn && cell.getPieces().isWhite() == isWhite)
//                .filter(cell -> verifyIfThePawnHasAPawnInFrontOfHim(board, cell.getColumnCoordinate(), cell.getLineCoordinate(), isWhite))
//                .count();
//    }
//
//    private boolean verifyIfThePawnHasAPawnInFrontOfHim(Board board, int column, int line, boolean isWhite) {
//        for (int i = line + 1; i < 8; i++) {
//            if (board.getCellOnTheBoardMap()[i][column].getPieces() instanceof Pawn && board.getCellOnTheBoardMap()[i][column].getPieces().isWhite() == isWhite) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public int canAPieceBeCaptured(Board board, boolean colourOfThePieceThatCanCaptureAnEnemyPiece) {
//        return (int) Arrays.stream(board.getCellOnTheBoardMap())
//                .flatMap(Arrays::stream)
//                .filter(enemyCell -> enemyCell.getPieces() != null && enemyCell.getPieces().isWhite() != colourOfThePieceThatCanCaptureAnEnemyPiece)
//                .flatMapToLong(enemyCell -> LongStream.of(Arrays.stream(board.getCellOnTheBoardMap())
//                        .flatMap(Arrays::stream)
//                        .filter(j -> j.getPieces() != null
//                                && j.getPieces().isWhite() == colourOfThePieceThatCanCaptureAnEnemyPiece
//                                && possibleMovesForAPiece(board, j, enemyCell))
//                        .count()))
//                .sum();
//    }
//
//    public boolean isTheEnemyKingInCheck(Board board, boolean isWhite) {
//        King king = (King) board.getKing(isWhite).getPieces();
//        return king.isInCheck();
//    }
//
////    public int isTheEnemyKingInCheck(Board board, boolean isWhite) {
////        King king = (King) board.getKing(isWhite).getPieces();
////        return king.isInCheck() ? 100 : 0;
////    }
//
//
//    public int developmentBonus(Board board, boolean isWhite) {
//        return (int) Arrays.stream(board.getCellOnTheBoardMap())
//                .flatMap(Arrays::stream)
//                .filter(cell -> cell.getPieces() != null && !(cell.getPieces() instanceof King) && !(cell.getPieces() instanceof Pawn))
//                .filter(cell -> (isWhite && cell.getPieces().isWhite() && cell.getLineCoordinate() > 1) ||
//                        (!isWhite && !cell.getPieces().isWhite() && cell.getLineCoordinate() < 6))
//                .count();
//    }
//
//    public int isolatedPawns(Board board, boolean isWhite) {
//        return (int) Arrays.stream(board.getCellOnTheBoardMap())
//                .flatMap(Arrays::stream)
//                .filter(cell -> cell.getPieces() instanceof Pawn && cell.getPieces().isWhite() == isWhite)
//                .filter(cell -> !verifyIfThePawnHasAPawnNear(board, cell.getColumnCoordinate() + 1, isWhite))
//                .filter(cell -> !verifyIfThePawnHasAPawnNear(board, cell.getColumnCoordinate() - 1, isWhite))
//                .count();
//    }
//
//
//    public boolean checkMateCheck(Board board, boolean isWhite) {
////        boolean isInCheck = isTheEnemyKingInCheck(board, isWhite) == 100;
//        boolean isInCheck = isTheEnemyKingInCheck(board, isWhite);
//        int numberOfMoves = getAllPossibleMoves(board, isWhite).size();
//        return isInCheck && numberOfMoves == 0;
//    }
//}