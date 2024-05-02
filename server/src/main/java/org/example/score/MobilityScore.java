package org.example.score;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.board.Move;
import com.example.models.pieces.*;
import lombok.AllArgsConstructor;
import org.example.repositoryes.pieces.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("mobilityScore")
@AllArgsConstructor
public class MobilityScore {

    private final PawnRepository pawnRepository;
    private final RookRepository rookRepository;
    private final KnightRepository knightRepository;
    private final BishopRepository bishopRepository;
    private final QueenRepository queenRepository;
    private final KingRepository kingRepository;

    private final Map<String, Boolean> pawnPromotion = new HashMap<>();

    public List<Move> getAllPossibleMoves(Board board, boolean isWhite) {
        List<CellOnTheBoard> collect = Arrays.stream(board.getCellOnTheBoardMap())
                .flatMap(Arrays::stream)
                .filter(i -> i.getPieces() != null && i.getPieces().isWhite() == isWhite)
                .toList();

        List<Move> moveList = new ArrayList<>();
        for (var piece : collect) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    CellOnTheBoard endCell = board.getCellOnTheBoardMap()[i][j];
                    if (piece != endCell) {
                        int ok = 0;
                        if (piece.getPieces() instanceof Pawn) {
                            if (pawnRepository.canPromote(board, piece, endCell)) {
                                moveList.addAll(promotePawns(piece, endCell));
                                ok = 1;
                            }
                        }
                        if (ok == 0) {
                            if (possibleMovesForAPiece(board, piece, endCell)) {
                                moveList.add(new Move(piece, endCell));
                            }
                        }

                    }
                }
            }
        }
        return moveList;
    }

    //todo: castling for ai
    public boolean possibleMovesForAPiece(Board board, CellOnTheBoard startCell, CellOnTheBoard endCell) {

        return switch (startCell.getPieces()) {
//            case King ignored -> {
//                if (!kingRepository.canMove(board, startCell, endCell, (King) startCell.getPieces())) {
//                    yield kingRepository.canCastle(board, startCell, endCell, (King) startCell.getPieces());
//                } else {
//                    yield true;
//                }
//            }
            case King ignored -> kingRepository.canMove(board, startCell, endCell, (King) startCell.getPieces());
            case Queen ignored -> queenRepository.canMove(board, startCell, endCell, (Queen) startCell.getPieces());
            case Rook ignored -> rookRepository.canMove(board, startCell, endCell, (Rook) startCell.getPieces());
            case Bishop ignored -> bishopRepository.canMove(board, startCell, endCell, (Bishop) startCell.getPieces());
            case Knight ignored -> knightRepository.canMove(board, startCell, endCell, (Knight) startCell.getPieces());
            case Pawn ignored -> pawnRepository.canMove(board, startCell, endCell, (Pawn) startCell.getPieces());
        };
    }

    public List<Move> getAllPossibleMovesForASpecificPiece(Board board, CellOnTheBoard startCell) {
        List<Move> moveList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                CellOnTheBoard endCell = board.getCellOnTheBoardMap()[i][j];
                if (startCell != endCell) {
                    int ok = 0;
                    if (startCell.getPieces() instanceof Pawn) {
                        if (pawnRepository.canPromote(board, startCell, endCell)) {
                            moveList.addAll(promotePawns(startCell, endCell));
                            ok = 1;
                        }
                    }
                    if (ok == 0) {
                        if (possibleMovesForAPiece(board, startCell, endCell)) {
                            moveList.add(new Move(startCell, endCell));
                        }
                    }

                }
            }
        }
        return moveList;
    }

    private List<Move> promotePawns(CellOnTheBoard startCell, CellOnTheBoard endCell) {

        List<Move> promotePawnsList = new ArrayList<>();

        pawnPromotion.put("knight", false);
        pawnPromotion.put("bishop", false);
        pawnPromotion.put("rook", false);
        pawnPromotion.put("queen", false);
        int i = 0;
        while (i < 4) {
            CellOnTheBoard piecesOnStart = new CellOnTheBoard(null, startCell.getLineCoordinate(), startCell.getColumnCoordinate());
            if (!pawnPromotion.get("knight")) {
                piecesOnStart.setPieces(new Knight(startCell.getPieces().isWhite()));
                pawnPromotion.put("knight", true);
            } else if (!pawnPromotion.get("bishop")) {
                piecesOnStart.setPieces(new Bishop(startCell.getPieces().isWhite()));
                pawnPromotion.put("bishop", true);
            } else if (!pawnPromotion.get("rook")) {
                piecesOnStart.setPieces(new Rook(startCell.getPieces().isWhite()));
                pawnPromotion.put("rook", true);
            } else if (!pawnPromotion.get("queen")) {
                piecesOnStart.setPieces(new Queen(startCell.getPieces().isWhite()));
                pawnPromotion.put("queen", true);
            }
            promotePawnsList.add(new Move(piecesOnStart, endCell));
            i++;
        }
        return promotePawnsList;
    }
}
