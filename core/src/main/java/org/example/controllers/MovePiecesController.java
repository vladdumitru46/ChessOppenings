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


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white")) {
            if (pieceService.canTheRookMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], new Rook(true))) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(new Rook(true));
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            if (pieceService.canTheRookMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], new Rook(false))) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(new Rook(false));
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/knight")
    public ResponseEntity<?> moveKnight(@RequestBody MovePiecesRequest movePiecesRequest) {
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white")) {
            if (pieceService.canTheKnightMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], new Knight(true))) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(new Knight(true));
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            if (pieceService.canTheKnightMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], new Knight(false))) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(new Knight(false));
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/bishop")
    public ResponseEntity<?> moveBishop(@RequestBody MovePiecesRequest movePiecesRequest) {
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white")) {
            if (pieceService.canTheBishopMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], new Bishop(true))) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(new Bishop(true));
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            if (pieceService.canTheBishopMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], new Bishop(false))) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(new Bishop(false));
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/king")
    public ResponseEntity<?> moveKing(@RequestBody MovePiecesRequest movePiecesRequest) {
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white")) {
            if (pieceService.canTheKingMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], new King(true))) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(new King(true));
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            if (pieceService.canTheKingMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], new King(false))) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(new King(false));
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/queen")
    public ResponseEntity<?> moveQueen(@RequestBody MovePiecesRequest movePiecesRequest) {
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white")) {
            if (pieceService.canTheQueenMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], new Queen(true))) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(new Queen(true));
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                System.out.println(board.getCellOnTheBordMap()[line1][column1]);
                System.out.println(board.getCellOnTheBordMap()[line][column]);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            if (pieceService.canTheQueenMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], new Queen(false))) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(new Queen(false));
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/pawn")
    public ResponseEntity<?> movePawn(@RequestBody MovePiecesRequest movePiecesRequest) {
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white")) {
            if (pieceService.canThePawnMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], new Pawn(true))) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(new Pawn(true));
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            if (pieceService.canThePawnMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], new Pawn(false))) {
                board.getCellOnTheBordMap()[line1][column1].setPieces(new Pawn(false));
                board.getCellOnTheBordMap()[line][column] = new CellOnTheBord(null, line, column);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/reset")
    public void resetBoard() {
        board.setCellOnTheBordMap(board.setBoard());
        log.info(board.toString());
    }
}
