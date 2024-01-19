package org.example.controllers;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.PieceService;
import org.example.requests.MovePiecesRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/move")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class MovePiecesController {
    private final PieceService pieceService;
    private final Board board = new Board();

    @PostMapping("/rook")
    public ResponseEntity<?> moveRook(@RequestBody MovePiecesRequest movePiecesRequest) {
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white") && board.isWhitesTurn()) {

            if (pieceService.canTheRookMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], (Rook) board.getCellOnTheBordMap()[line][column].getPieces())) {

                board.getCellOnTheBordMap()[line1][column1].setPieces(board.getCellOnTheBordMap()[line][column].getPieces());
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);

                Rook rook = (Rook) board.getCellOnTheBordMap()[line1][column1].getPieces();
                CellOnTheBord blackKing = board.getKing(false);
                if (rook.canAttackTheKing(board, board.getCellOnTheBordMap()[line1][column1], blackKing, rook)) {
                    King k = (King) blackKing.getPieces();
                    k.setInCheck(true);
                }
                board.setWhitesTurn(false);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (Objects.equals(movePiecesRequest.getPieceColour(), "black") && !board.isWhitesTurn()) {
            if (pieceService.canTheRookMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], (Rook) board.getCellOnTheBordMap()[line][column].getPieces())) {

                board.getCellOnTheBordMap()[line1][column1].setPieces(board.getCellOnTheBordMap()[line][column].getPieces());
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);

                Rook rook = (Rook) board.getCellOnTheBordMap()[line1][column1].getPieces();
                CellOnTheBord blackKing = board.getKing(true);
                if (rook.canAttackTheKing(board, board.getCellOnTheBordMap()[line1][column1], blackKing, rook)) {
                    King k = (King) blackKing.getPieces();
                    k.setInCheck(true);
                }
                board.setWhitesTurn(true);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/knight")
    public ResponseEntity<?> moveKnight(@RequestBody MovePiecesRequest movePiecesRequest) {
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white") && board.isWhitesTurn()) {

            if (pieceService.canTheKnightMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], (Knight) board.getCellOnTheBordMap()[line][column].getPieces())) {

                board.getCellOnTheBordMap()[line1][column1].setPieces(board.getCellOnTheBordMap()[line][column].getPieces());
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);

                Knight knight = (Knight) board.getCellOnTheBordMap()[line1][column1].getPieces();
                CellOnTheBord blackKing = board.getKing(false);
                if (knight.canAttackTheKing(board, board.getCellOnTheBordMap()[line1][column1], blackKing, knight)) {
                    King k = (King) blackKing.getPieces();
                    k.setInCheck(true);
                }
                board.setWhitesTurn(false);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (Objects.equals(movePiecesRequest.getPieceColour(), "black") && !board.isWhitesTurn()) {
            if (pieceService.canTheKnightMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], (Knight) board.getCellOnTheBordMap()[line][column].getPieces())) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(board.getCellOnTheBordMap()[line][column].getPieces());
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                Knight knight = (Knight) board.getCellOnTheBordMap()[line1][column1].getPieces();
                CellOnTheBord whiteKing = board.getKing(false);
                if (knight.canAttackTheKing(board, board.getCellOnTheBordMap()[line1][column1], whiteKing, knight)) {
                    King k = (King) whiteKing.getPieces();
                    k.setInCheck(true);
                }
                board.setWhitesTurn(true);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/bishop")
    public ResponseEntity<?> moveBishop(@RequestBody MovePiecesRequest movePiecesRequest) {
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white") && board.isWhitesTurn()) {

            if (pieceService.canTheBishopMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], (Bishop) board.getCellOnTheBordMap()[line][column].getPieces())) {

                board.getCellOnTheBordMap()[line1][column1].setPieces(board.getCellOnTheBordMap()[line][column].getPieces());
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);

                Bishop bishop = (Bishop) board.getCellOnTheBordMap()[line1][column1].getPieces();
                CellOnTheBord blackKing = board.getKing(false);
                if (bishop.canAttackTheKing(board, board.getCellOnTheBordMap()[line1][column1], blackKing, bishop)) {
                    King k = (King) blackKing.getPieces();
                    k.setInCheck(true);
                }
                board.setWhitesTurn(false);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (Objects.equals(movePiecesRequest.getPieceColour(), "black") && !board.isWhitesTurn()) {
            if (pieceService.canTheBishopMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], (Bishop) board.getCellOnTheBordMap()[line][column].getPieces())) {

                board.getCellOnTheBordMap()[line1][column1].setPieces(board.getCellOnTheBordMap()[line][column].getPieces());
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);

                Bishop bishop = (Bishop) board.getCellOnTheBordMap()[line1][column1].getPieces();
                CellOnTheBord blackKing = board.getKing(true);
                if (bishop.canAttackTheKing(board, board.getCellOnTheBordMap()[line1][column1], blackKing, bishop)) {
                    King k = (King) blackKing.getPieces();
                    k.setInCheck(true);
                }
                board.setWhitesTurn(true);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/king")
    public ResponseEntity<?> moveKing(@RequestBody MovePiecesRequest movePiecesRequest) {
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white") && board.isWhitesTurn()) {
            if (pieceService.canTheKingMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], (King) board.getCellOnTheBordMap()[line][column].getPieces())) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(board.getCellOnTheBordMap()[line][column].getPieces());
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                board.setWhitesTurn(false);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (Objects.equals(movePiecesRequest.getPieceColour(), "black") && !board.isWhitesTurn()) {
            if (pieceService.canTheKingMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], (King) board.getCellOnTheBordMap()[line][column].getPieces())) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(board.getCellOnTheBordMap()[line][column].getPieces());
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                board.setWhitesTurn(true);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/castle")
    public ResponseEntity<?> castle(@RequestBody MovePiecesRequest movePiecesRequest) {
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white") && board.isWhitesTurn()) {
            if (pieceService.canCastle(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], (King) board.getCellOnTheBordMap()[line][column].getPieces())) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(board.getCellOnTheBordMap()[line][column].getPieces());
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                board.setWhitesTurn(false);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (Objects.equals(movePiecesRequest.getPieceColour(), "black") && !board.isWhitesTurn()) {
            if (pieceService.canCastle(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], (King) board.getCellOnTheBordMap()[line][column].getPieces())) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(board.getCellOnTheBordMap()[line][column].getPieces());
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                board.setWhitesTurn(true);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/queen")
    public ResponseEntity<?> moveQueen(@RequestBody MovePiecesRequest movePiecesRequest) {
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white") && board.isWhitesTurn()) {
            if (pieceService.canTheQueenMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], (Queen) board.getCellOnTheBordMap()[line][column].getPieces())) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(board.getCellOnTheBordMap()[line][column].getPieces());
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                System.out.println(pieceService.numberOfPossibleMovesForBlack(board));
                pieceService.getAllPossibleMovesForBlack(board).forEach(System.out::println);
                System.out.println(pieceService.numberOfPossibleMovesForWhite(board));
                pieceService.getAllPossibleMovesForWhite(board).forEach(System.out::println);

                Queen queen = (Queen) board.getCellOnTheBordMap()[line1][column1].getPieces();
                CellOnTheBord blackKing = board.getKing(false);
                if (queen.canAttackTheKing(board, board.getCellOnTheBordMap()[line1][column1], blackKing, queen)) {
                    King k = (King) blackKing.getPieces();
                    k.setInCheck(true);
                }
                board.setWhitesTurn(false);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (Objects.equals(movePiecesRequest.getPieceColour(), "black") && !board.isWhitesTurn()) {
            if (pieceService.canTheQueenMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], (Queen) board.getCellOnTheBordMap()[line][column].getPieces())) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(board.getCellOnTheBordMap()[line][column].getPieces());
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                System.out.println(pieceService.numberOfPossibleMovesForBlack(board));
                System.out.println(pieceService.numberOfPossibleMovesForWhite(board));

                Queen queen = (Queen) board.getCellOnTheBordMap()[line1][column1].getPieces();
                CellOnTheBord blackKing = board.getKing(true);
                if (queen.canAttackTheKing(board, board.getCellOnTheBordMap()[line1][column1], blackKing, queen)) {
                    King k = (King) blackKing.getPieces();
                    k.setInCheck(true);
                }
                board.setWhitesTurn(true);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/pawn")
    public ResponseEntity<?> movePawn(@RequestBody MovePiecesRequest movePiecesRequest) {
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white") && board.isWhitesTurn()) {
            if (pieceService.canThePawnMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], (Pawn) board.getCellOnTheBordMap()[line][column].getPieces())) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(board.getCellOnTheBordMap()[line][column].getPieces());
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                board.setWhitesTurn(false);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (Objects.equals(movePiecesRequest.getPieceColour(), "black") && !board.isWhitesTurn()) {
            if (pieceService.canThePawnMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], (Pawn) board.getCellOnTheBordMap()[line][column].getPieces())) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(board.getCellOnTheBordMap()[line][column].getPieces());
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                board.setWhitesTurn(true);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/reset")
    public void resetBoard() {
        board.setWhitesTurn(true);
        board.setCellOnTheBordMap(board.setBoard());
        log.info(board.toString());
    }


    @PostMapping("/checkmate")
    public ResponseEntity<?> checkIfItIsCheckmateOrStalemate() {
        CellOnTheBord blackKingCell = board.getKing(false);
        King blackKing = (King) blackKingCell.getPieces();
        CellOnTheBord whiteKingCell = board.getKing(true);
        King whiteKing = (King) whiteKingCell.getPieces();
        int nrMovesForWhite = pieceService.numberOfPossibleMovesForWhite(board);
        int nrMovesForBlack = pieceService.numberOfPossibleMovesForBlack(board);
        if ((nrMovesForWhite == 0 && whiteKing.isInCheck())) {
            return ResponseEntity.ok("CheckMate - Black won");
        } else if (nrMovesForBlack == 0 && blackKing.isInCheck()) {
            return ResponseEntity.ok("CheckMate - White won");
        } else if (nrMovesForBlack == 0 || nrMovesForWhite == 0) {
            return ResponseEntity.ok("SlateMate");
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
