package org.example.controllers;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.board.Move;
import com.example.models.game.Game;
import com.example.models.game.GameStatus;
import com.example.models.pieces.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.board.BoardService;
import org.example.board.PieceService;
import org.example.game.GameService;
import org.example.requests.MovePiecesRequest;
import org.example.requests.PromotePawn;
import org.example.score.MobilityScore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/chess/move")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class MovePiecesController {

    private final PieceService pieceService;
    private final MobilityScore mobilityScore;
    private final BoardService boardService;
    private final GameService gameService;

    @PostMapping("/rook")
    public ResponseEntity<?> moveRook(@RequestBody MovePiecesRequest movePiecesRequest) {

        Board board;
        Game game;
        try {
            game = gameService.getGameById(Integer.valueOf(movePiecesRequest.gameId()));
            board = boardService.findById(game.getBoardId());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start = movePiecesRequest.start();
        int startLine = start.charAt(0) - '0';
        int startColumn = start.charAt(1) - '0';
        String end1 = movePiecesRequest.end();
        int endLine = end1.charAt(0) - '0';
        int endColumn = end1.charAt(1) - '0';

        boolean colour = Objects.equals(movePiecesRequest.pieceColour(), "white");
        if (colour == game.isWhitesTurn()) {
            CellOnTheBoard startCell = board.getCellOnTheBoardMap()[startLine][startColumn];
            CellOnTheBoard endCell = board.getCellOnTheBoardMap()[endLine][endColumn];
            if (pieceService.canTheRookMove(board, startCell, endCell, (Rook) startCell.getPieces())) {
                String move = pieceService.transformMoveToCorrectNotation(startCell, endCell, board);

                pieceService.makeMove(board, new Move(startCell, endCell));
                if (colour) {
                    game.setWhiteMove(game.getWhiteMove() + ", " + move);
                } else {
                    game.setBlackMove(game.getBlackMove() + ", " + move);
                    game.setMoveNumber(game.getMoveNumber() + 1);
                }
                setMoveAsString(game, new Move(startCell, endCell), "");
                game.setWhitesTurn(!colour);
                gameService.updateGame(game);
                boardService.updateBoard(board);
                return new ResponseEntity<>(move, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/knight")
    public ResponseEntity<?> moveKnight(@RequestBody MovePiecesRequest movePiecesRequest) {
        Board board;
        Game game;
        try {
            game = gameService.getGameById(Integer.valueOf(movePiecesRequest.gameId()));
            board = boardService.findById(game.getBoardId());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start = movePiecesRequest.start();
        int startLine = start.charAt(0) - '0';
        int startColumn = start.charAt(1) - '0';
        String end = movePiecesRequest.end();
        int endLine = end.charAt(0) - '0';
        int endColumn = end.charAt(1) - '0';
        boolean colour = Objects.equals(movePiecesRequest.pieceColour(), "white");
        if (colour == game.isWhitesTurn()) {
            CellOnTheBoard startCell = board.getCellOnTheBoardMap()[startLine][startColumn];
            CellOnTheBoard endCell = board.getCellOnTheBoardMap()[endLine][endColumn];
            if (pieceService.canTheKnightMove(board, startCell, endCell, (Knight) startCell.getPieces())) {
                String move = pieceService.transformMoveToCorrectNotation(startCell, endCell, board);

                pieceService.makeMove(board, new Move(startCell, endCell));
                if (colour) {
                    game.setWhiteMove(game.getWhiteMove() + ", " + move);
                } else {
                    game.setBlackMove(game.getBlackMove() + ", " + move);
                    game.setMoveNumber(game.getMoveNumber() + 1);
                }
                setMoveAsString(game, new Move(startCell, endCell), "");
                game.setWhitesTurn(!colour);
                boardService.updateBoard(board);
                gameService.updateGame(game);
                return new ResponseEntity<>(move, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/bishop")
    public ResponseEntity<?> moveBishop(@RequestBody MovePiecesRequest movePiecesRequest) {
        Board board;
        Game game;
        try {
            game = gameService.getGameById(Integer.valueOf(movePiecesRequest.gameId()));
            board = boardService.findById(game.getBoardId());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start = movePiecesRequest.start();
        int startLine = start.charAt(0) - '0';
        int startColumn = start.charAt(1) - '0';
        String end1 = movePiecesRequest.end();
        int endLine = end1.charAt(0) - '0';
        int endColumn = end1.charAt(1) - '0';

        boolean colour = Objects.equals(movePiecesRequest.pieceColour(), "white");
        if (colour == game.isWhitesTurn()) {
            CellOnTheBoard startCell = board.getCellOnTheBoardMap()[startLine][startColumn];
            CellOnTheBoard endCell = board.getCellOnTheBoardMap()[endLine][endColumn];
            if (pieceService.canTheBishopMove(board, startCell, endCell, (Bishop) startCell.getPieces())) {
                String move = pieceService.transformMoveToCorrectNotation(startCell, endCell, board);

                pieceService.makeMove(board, new Move(startCell, endCell));
                if (colour) {
                    game.setWhiteMove(game.getWhiteMove() + ", " + move);
                } else {
                    game.setBlackMove(game.getBlackMove() + ", " + move);
                    game.setMoveNumber(game.getMoveNumber() + 1);
                }
                setMoveAsString(game, new Move(startCell, endCell), "");
                game.setWhitesTurn(!colour);
                boardService.updateBoard(board);
                gameService.updateGame(game);
                return new ResponseEntity<>(move, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/king")
    public ResponseEntity<?> moveKing(@RequestBody MovePiecesRequest movePiecesRequest) {
        Board board;
        Game game;
        try {
            game = gameService.getGameById(Integer.valueOf(movePiecesRequest.gameId()));
            board = boardService.findById(game.getBoardId());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start = movePiecesRequest.start();
        int startLine = start.charAt(0) - '0';
        int startColumn = start.charAt(1) - '0';
        String end1 = movePiecesRequest.end();
        int endLine = end1.charAt(0) - '0';
        int endColumn = end1.charAt(1) - '0';

        boolean colour = Objects.equals(movePiecesRequest.pieceColour(), "white");
        if (colour == game.isWhitesTurn()) {
            CellOnTheBoard startCell = board.getCellOnTheBoardMap()[startLine][startColumn];
            CellOnTheBoard endCell = board.getCellOnTheBoardMap()[endLine][endColumn];
            if (pieceService.canTheKingMove(board, startCell, endCell, (King) startCell.getPieces())) {
                String move = pieceService.transformMoveToCorrectNotation(startCell, endCell, board);

                pieceService.makeMove(board, new Move(startCell, endCell));
                if (colour) {
                    game.setWhiteMove(game.getWhiteMove() + ", " + move);
                } else {
                    game.setBlackMove(game.getBlackMove() + ", " + move);
                    game.setMoveNumber(game.getMoveNumber() + 1);
                }
                setMoveAsString(game, new Move(startCell, endCell), "");
                game.setWhitesTurn(!colour);
                boardService.updateBoard(board);
                gameService.updateGame(game);
                return new ResponseEntity<>(move, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/castle")
    public ResponseEntity<?> castle(@RequestBody MovePiecesRequest movePiecesRequest) {
        Board board;
        Game game;
        try {
            game = gameService.getGameById(Integer.valueOf(movePiecesRequest.gameId()));
            board = boardService.findById(game.getBoardId());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start = movePiecesRequest.start();
        int startLine = start.charAt(0) - '0';
        int startColumn = start.charAt(1) - '0';
        String end1 = movePiecesRequest.end();
        int endLine = end1.charAt(0) - '0';
        int endColumn = end1.charAt(1) - '0';

        boolean colour = Objects.equals(movePiecesRequest.pieceColour(), "white");
        if (colour == game.isWhitesTurn()) {
            CellOnTheBoard startCell = board.getCellOnTheBoardMap()[startLine][startColumn];
            CellOnTheBoard endCell = board.getCellOnTheBoardMap()[endLine][endColumn];
            if (pieceService.canCastle(board, startCell, endCell, (King) startCell.getPieces())) {
                String move;
                if (endColumn > 0) {
                    move = "0-0";
                    setMoveAsString(game, new Move(startCell, endCell), "castleShort");
                } else {
                    move = "0-0-0";
                    setMoveAsString(game, new Move(startCell, endCell), "castleLong");
                }
                pieceService.makeMove(board, new Move(startCell, endCell));
                if (colour) {
                    game.setWhiteMove(game.getWhiteMove() + ", " + move);
                } else {
                    game.setBlackMove(game.getBlackMove() + ", " + move);
                    game.setMoveNumber(game.getMoveNumber() + 1);
                }
                game.setWhitesTurn(!colour);
                boardService.updateBoard(board);
                gameService.updateGame(game);
                return new ResponseEntity<>(move, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/queen")
    public ResponseEntity<?> moveQueen(@RequestBody MovePiecesRequest movePiecesRequest) {
        Board board;
        Game game;
        try {
            game = gameService.getGameById(Integer.valueOf(movePiecesRequest.gameId()));
            board = boardService.findById(game.getBoardId());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start = movePiecesRequest.start();
        int startLine = start.charAt(0) - '0';
        int startColumn = start.charAt(1) - '0';
        String end1 = movePiecesRequest.end();
        int endLine = end1.charAt(0) - '0';
        int endColumn = end1.charAt(1) - '0';

        boolean colour = Objects.equals(movePiecesRequest.pieceColour(), "white");
        if (game.isWhitesTurn() == colour) {
            CellOnTheBoard startCell = board.getCellOnTheBoardMap()[startLine][startColumn];
            CellOnTheBoard endCell = board.getCellOnTheBoardMap()[endLine][endColumn];
            if (pieceService.canTheQueenMove(board, startCell, endCell, (Queen) startCell.getPieces())) {
                String move = pieceService.transformMoveToCorrectNotation(startCell, endCell, board);

                pieceService.makeMove(board, new Move(startCell, endCell));
                if (colour) {
                    game.setWhiteMove(game.getWhiteMove() + ", " + move);
                } else {
                    game.setBlackMove(game.getBlackMove() + ", " + move);
                    game.setMoveNumber(game.getMoveNumber() + 1);
                }
                setMoveAsString(game, new Move(startCell, endCell), "");
                game.setWhitesTurn(!colour);
                boardService.updateBoard(board);
                gameService.updateGame(game);
                return new ResponseEntity<>(move, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/pawn")
    public ResponseEntity<?> movePawn(@RequestBody MovePiecesRequest movePiecesRequest) {
        Board board;
        Game game;
        try {
            game = gameService.getGameById(Integer.valueOf(movePiecesRequest.gameId()));
            board = boardService.findById(game.getBoardId());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start = movePiecesRequest.start();
        int startLine = start.charAt(0) - '0';
        int startColumn = start.charAt(1) - '0';
        String end1 = movePiecesRequest.end();
        int endLine = end1.charAt(0) - '0';
        int endColumn = end1.charAt(1) - '0';

        boolean colour = Objects.equals(movePiecesRequest.pieceColour(), "white");
        if (game.isWhitesTurn() == colour) {
            CellOnTheBoard startCell = board.getCellOnTheBoardMap()[startLine][startColumn];
            CellOnTheBoard endCell = board.getCellOnTheBoardMap()[endLine][endColumn];
            if (pieceService.canThePawnPromote(board, startCell, endCell)) {
                pieceService.makeMove(board, new Move(startCell, endCell));
                setMoveAsString(game, new Move(startCell, endCell), "");
                game.setWhitesTurn(!colour);
                boardService.updateBoard(board);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                if (pieceService.canThePawnMove(board, startCell, endCell, (Pawn) startCell.getPieces())) {
                    String move = pieceService.transformMoveToCorrectNotation(startCell, endCell, board);
                    pieceService.makeMove(board, new Move(startCell, endCell));
                    if (colour) {
                        game.setWhiteMove(game.getWhiteMove() + ", " + move);
                    } else {
                        game.setBlackMove(game.getBlackMove() + ", " + move);
                        game.setMoveNumber(game.getMoveNumber() + 1);
                    }
                    setMoveAsString(game, new Move(startCell, endCell), "");
                    game.setWhitesTurn(!colour);
                    gameService.updateGame(game);
                    boardService.updateBoard(board);
                    return new ResponseEntity<>(move, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/promote")
    public ResponseEntity<?> promotePawn(@RequestBody PromotePawn promotePawn) {
        Board board;
        Game game;
        try {
            game = gameService.getGameById(promotePawn.gameId());
            board = boardService.findById(game.getBoardId());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        int line = Integer.parseInt(String.valueOf(promotePawn.coordinates().charAt(0)));
        int column = Integer.parseInt(String.valueOf(promotePawn.coordinates().charAt(1)));

        CellOnTheBoard pawn = board.getCellOnTheBoardMap()[line][column];
        String notation = pieceService.transformColumnToLetters(pawn);
        notation += (column + 1);
        switch (promotePawn.newPiece()) {
            case "Queen" -> {
                pawn.setPieces(new Queen(pawn.getPieces().isWhite()));
                notation += "=Q";
            }
            case "Rook" -> {
                pawn.setPieces(new Rook(pawn.getPieces().isWhite()));
                notation += "=R";
            }
            case "Bishop" -> {
                pawn.setPieces(new Bishop(pawn.getPieces().isWhite()));
                notation += "=B";
            }
            case "Knight" -> {
                pawn.setPieces(new Knight(pawn.getPieces().isWhite()));
                notation += "=N";
            }
        }
        String move = game.getMoves();
        char lastChar = move.charAt(move.length() - 1);
        StringBuilder newMove = new StringBuilder();
        for (int i = 0; i < move.length() - 1; i++) {
            newMove.append(move.charAt(i));
        }
        newMove.append(" ").append(promotePawn.newPiece()).append(lastChar);
        game.setMoves(newMove.toString());
        board.getCellOnTheBoardMap()[line][column].setPieces(pawn.getPieces());
        gameService.updateGame(game);
        boardService.updateBoard(board);
        return new ResponseEntity<>(notation, HttpStatus.OK);
    }


    @PostMapping("/checkmate")
    public ResponseEntity<?> checkIfItIsCheckmateOrStalemate(@RequestBody String boardId) {
        String[] list = boardId.split(":");
        String[] list2 = list[1].split("}");

        int endIndex = list2[0].lastIndexOf("\"");
        String extractedContent = list2[0].substring(1, endIndex);
        Board board;
        Game game;
        try {
            game = gameService.getGameById(Integer.parseInt(extractedContent));
            board = boardService.findById(game.getBoardId());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        CellOnTheBoard blackKingCell = board.getKing(false);
        King blackKing = (King) blackKingCell.getPieces();

        CellOnTheBoard whiteKingCell = board.getKing(true);
        King whiteKing = (King) whiteKingCell.getPieces();

        int nrMovesForWhite = mobilityScore.getAllPossibleMoves(board, true).size();
        int nrMovesForBlack = mobilityScore.getAllPossibleMoves(board, false).size();

        if (nrMovesForWhite == 0 && whiteKing.isInCheck()) {
            game.setGameStatus(GameStatus.BLACK_WON);
            gameService.updateGame(game);
            return ResponseEntity.ok("CheckMate - Black won");
        } else if (nrMovesForBlack == 0 && blackKing.isInCheck()) {
            game.setGameStatus(GameStatus.WHITE_WON);
            gameService.updateGame(game);
            return ResponseEntity.ok("CheckMate - White won");
        } else if (nrMovesForBlack == 0 || nrMovesForWhite == 0) {
            game.setGameStatus(GameStatus.STALEMATE);
            gameService.updateGame(game);
            return ResponseEntity.ok("SlateMate");
        } else {
            return new ResponseEntity<>("continue", HttpStatus.OK);
        }
    }


    private void setMoveAsString(Game game, Move move, String action) {
        String moveAsString = move.getStart().toString() + " " + move.getEnd().toString();
        moveAsString += !Objects.equals(action, "") ? " " + action : "";
        moveAsString += game.isWhitesTurn() ? ";" : ", ";
        game.setMoves(game.getMoves() + moveAsString);
    }


    @GetMapping("/piecePossibleMoves")
    public ResponseEntity<?> getAllPossibleMovesForASpecificPiece(@RequestParam Integer boardId, @RequestParam String position) {
        try {
            Game game = null;
            Board board = null;
            try {
                game = gameService.getGameById(boardId);
                board = boardService.findById(game.getBoardId());
            } catch (Exception e) {
                board = boardService.findById(boardId);
            }
            int startLine = position.charAt(0) - '0';
            int startColumn = position.charAt(1) - '0';

            CellOnTheBoard piece = board.getCellOnTheBoardMap()[startLine][startColumn];
            List<String> possibleMoves = mobilityScore.getAllPossibleMovesForASpecificPiece(board, piece).stream()
                    .map(Move::getEnd)
                    .map(CellOnTheBoard::toString)
                    .toList();

            return new ResponseEntity<>(possibleMoves, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
