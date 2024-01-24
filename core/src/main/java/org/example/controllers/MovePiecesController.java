package org.example.controllers;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.board.Move;
import com.example.models.pieces.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.BoardService;
import org.example.PieceService;
import org.example.miniMax.MiniMax;
import org.example.requests.MovePiecesRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/move")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class MovePiecesController {
    private final PieceService pieceService;
//    private final Board board = new Board();

    private final BoardService boardService;

    @PostMapping("/rook")
    public ResponseEntity<?> moveRook(@RequestBody MovePiecesRequest movePiecesRequest) {

        Board board = null;
        try {
            board = boardService.findById(Integer.parseInt(movePiecesRequest.getBoardId()));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white") && board.isWhitesTurn()) {

            if (pieceService.canTheRookMove(board, board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1], (Rook) board.getCellOnTheBoardMap()[line][column].getPieces())) {
                pieceService.makeMove(board, new Move(board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1]));
                board.setWhitesTurn(false);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (Objects.equals(movePiecesRequest.getPieceColour(), "black") && !board.isWhitesTurn()) {
            if (pieceService.canTheRookMove(board, board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1], (Rook) board.getCellOnTheBoardMap()[line][column].getPieces())) {

                pieceService.makeMove(board, new Move(board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1]));
                board.setWhitesTurn(true);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/knight")
    public ResponseEntity<?> moveKnight(@RequestBody MovePiecesRequest movePiecesRequest) {
        Board board = null;
        try {
            board = boardService.findById(Integer.parseInt(movePiecesRequest.getBoardId()));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white") && board.isWhitesTurn()) {

            if (pieceService.canTheKnightMove(board, board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1], (Knight) board.getCellOnTheBoardMap()[line][column].getPieces())) {

                pieceService.makeMove(board, new Move(board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1]));

                board.setWhitesTurn(false);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (Objects.equals(movePiecesRequest.getPieceColour(), "black") && !board.isWhitesTurn()) {
            if (pieceService.canTheKnightMove(board, board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1], (Knight) board.getCellOnTheBoardMap()[line][column].getPieces())) {
                pieceService.makeMove(board, new Move(board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1]));

                board.setWhitesTurn(true);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/bishop")
    public ResponseEntity<?> moveBishop(@RequestBody MovePiecesRequest movePiecesRequest) {
        Board board = null;
        try {
            board = boardService.findById(Integer.parseInt(movePiecesRequest.getBoardId()));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white") && board.isWhitesTurn()) {

            if (pieceService.canTheBishopMove(board, board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1], (Bishop) board.getCellOnTheBoardMap()[line][column].getPieces())) {

                pieceService.makeMove(board, new Move(board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1]));

                board.setWhitesTurn(false);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (Objects.equals(movePiecesRequest.getPieceColour(), "black") && !board.isWhitesTurn()) {
            if (pieceService.canTheBishopMove(board, board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1], (Bishop) board.getCellOnTheBoardMap()[line][column].getPieces())) {
                pieceService.makeMove(board, new Move(board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1]));

                board.setWhitesTurn(true);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/king")
    public ResponseEntity<?> moveKing(@RequestBody MovePiecesRequest movePiecesRequest) {
        Board board = null;
        try {
            board = boardService.findById(Integer.parseInt(movePiecesRequest.getBoardId()));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white") && board.isWhitesTurn()) {
            if (pieceService.canTheKingMove(board, board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1], (King) board.getCellOnTheBoardMap()[line][column].getPieces())) {
                pieceService.makeMove(board, new Move(board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1]));
                board.setWhitesTurn(false);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (Objects.equals(movePiecesRequest.getPieceColour(), "black") && !board.isWhitesTurn()) {
            if (pieceService.canTheKingMove(board, board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1], (King) board.getCellOnTheBoardMap()[line][column].getPieces())) {
                pieceService.makeMove(board, new Move(board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1]));
                board.setWhitesTurn(true);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/castle")
    public ResponseEntity<?> castle(@RequestBody MovePiecesRequest movePiecesRequest) {
        Board board = null;
        try {
            board = boardService.findById(Integer.parseInt(movePiecesRequest.getBoardId()));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white") && board.isWhitesTurn()) {
            if (pieceService.canCastle(board, board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1], (King) board.getCellOnTheBoardMap()[line][column].getPieces())) {
                pieceService.makeMove(board, new Move(board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1]));
                board.setWhitesTurn(false);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (Objects.equals(movePiecesRequest.getPieceColour(), "black") && !board.isWhitesTurn()) {
            if (pieceService.canCastle(board, board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1], (King) board.getCellOnTheBoardMap()[line][column].getPieces())) {
                pieceService.makeMove(board, new Move(board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1]));
                board.setWhitesTurn(true);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/queen")
    public ResponseEntity<?> moveQueen(@RequestBody MovePiecesRequest movePiecesRequest) {
        Board board = null;
        try {
            board = boardService.findById(Integer.parseInt(movePiecesRequest.getBoardId()));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white") && board.isWhitesTurn()) {
            if (pieceService.canTheQueenMove(board, board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1], (Queen) board.getCellOnTheBoardMap()[line][column].getPieces())) {
                pieceService.makeMove(board, new Move(board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1]));

                board.setWhitesTurn(false);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (Objects.equals(movePiecesRequest.getPieceColour(), "black") && !board.isWhitesTurn()) {
            if (pieceService.canTheQueenMove(board, board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1], (Queen) board.getCellOnTheBoardMap()[line][column].getPieces())) {
                pieceService.makeMove(board, new Move(board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1]));
                System.out.println(pieceService.getAllPossibleMovesForBlack(board).size());

                board.setWhitesTurn(true);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/pawn")
    public ResponseEntity<?> movePawn(@RequestBody MovePiecesRequest movePiecesRequest) {
        Board board = null;
        try {
            board = boardService.findById(Integer.parseInt(movePiecesRequest.getBoardId()));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white") && board.isWhitesTurn()) {
            if (pieceService.canThePawnMove(board, board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1], (Pawn) board.getCellOnTheBoardMap()[line][column].getPieces())) {
                pieceService.makeMove(board, new Move(board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1]));
                board.setWhitesTurn(false);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (Objects.equals(movePiecesRequest.getPieceColour(), "black") && !board.isWhitesTurn()) {
            if (pieceService.canThePawnMove(board, board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1], (Pawn) board.getCellOnTheBoardMap()[line][column].getPieces())) {
                pieceService.makeMove(board, new Move(board.getCellOnTheBoardMap()[line][column], board.getCellOnTheBoardMap()[line1][column1]));
                board.setWhitesTurn(true);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/reset")
    public void resetBoard(@RequestBody Integer boardId) {
        Board board = null;
        try {
            board = boardService.findById(boardId);
        } catch (Exception e) {

        }
        board.setWhitesTurn(true);
        board.setCellOnTheBoardMap(board.setBoard());

        boardService.updateBoard(board);
        log.info(board.toString());
    }


    @PostMapping("/checkmate")
    public ResponseEntity<?> checkIfItIsCheckmateOrStalemate(@RequestBody String boardId) {
        String[] list = boardId.split(":");
        String[] list2 = list[1].split("}");

        int endIndex = list2[0].lastIndexOf("\"");
        String extractedContent = list2[0].substring(1, endIndex);
        Board board = null;
        try {
            board = boardService.findById(Integer.parseInt(extractedContent));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        CellOnTheBoard blackKingCell = board.getKing(false);
        King blackKing = (King) blackKingCell.getPieces();
        CellOnTheBoard whiteKingCell = board.getKing(true);
        King whiteKing = (King) whiteKingCell.getPieces();
        int nrMovesForWhite = pieceService.getAllPossibleMovesForWhite(board).size();
        int nrMovesForBlack = pieceService.getAllPossibleMovesForBlack(board).size();
        if ((nrMovesForWhite == 0 && whiteKing.isInCheck())) {
            return ResponseEntity.ok("CheckMate - Black won");
        } else if (nrMovesForBlack == 0 && blackKing.isInCheck()) {
            return ResponseEntity.ok("CheckMate - White won");
        } else if (nrMovesForBlack == 0 || nrMovesForWhite == 0) {
            return ResponseEntity.ok("SlateMate");
        } else {
            return new ResponseEntity<>("continue", HttpStatus.OK);
        }
    }



}
