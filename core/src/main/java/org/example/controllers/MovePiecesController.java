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
import org.example.exceptions.BoardNotFoundException;
import org.example.exceptions.GameNotFoundException;
import org.example.game.GameService;
import org.example.requests.MovePiecesRequest;
import org.example.requests.PromotePawn;
import org.example.score.MobilityScore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
        } catch (GameNotFoundException | BoardNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>(board + " + " + game.isWhitesTurn(), HttpStatus.OK);
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
        } catch (GameNotFoundException | BoardNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>(board + " + " + game.isWhitesTurn(), HttpStatus.OK);
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
        } catch (GameNotFoundException | BoardNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>(board + " + " + game.isWhitesTurn(), HttpStatus.OK);
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
        } catch (GameNotFoundException | BoardNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>(board + " + " + game.isWhitesTurn(), HttpStatus.OK);
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
        } catch (GameNotFoundException | BoardNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>(board + " + " + game.isWhitesTurn(), HttpStatus.OK);
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
        } catch (GameNotFoundException | BoardNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>(board + " + " + game.isWhitesTurn(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/pawn")
    public ResponseEntity<?> movePawn(@RequestBody PromotePawn promotePawn) {
        Board board;
        Game game;
        try {
            game = gameService.getGameById(Integer.valueOf(promotePawn.gameId()));
            board = boardService.findById(game.getBoardId());
        } catch (GameNotFoundException | BoardNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start = promotePawn.start();
        int startLine = start.charAt(0) - '0';
        int startColumn = start.charAt(1) - '0';
        String end1 = promotePawn.end();
        int endLine = end1.charAt(0) - '0';
        int endColumn = end1.charAt(1) - '0';

        boolean colour = Objects.equals(promotePawn.pieceColour(), "white");
        if (game.isWhitesTurn() == colour) {
            CellOnTheBoard startCell = board.getCellOnTheBoardMap()[startLine][startColumn];
            CellOnTheBoard endCell = board.getCellOnTheBoardMap()[endLine][endColumn];
            String notation = "";
            if (game.getMoves().split(", ").length > 1 && verifyLastMoveToBePawnStartMove(game)
                    && pieceService.canEnPassant(board, startCell, endCell)) {
                if (game.isWhitesTurn()) {
                    board.getCellOnTheBoardMap()[endLine - 1][endColumn].setPieces(null);
                } else {
                    board.getCellOnTheBoardMap()[endLine + 1][endColumn].setPieces(null);
                }
                String move = pieceService.transformMoveToCorrectNotation(startCell, endCell, board);
                pieceService.makeMove(board, new Move(startCell, endCell));
                if (colour) {
                    game.setWhiteMove(game.getWhiteMove() + ", " + move);
                } else {
                    game.setBlackMove(game.getBlackMove() + ", " + move);
                    game.setMoveNumber(game.getMoveNumber() + 1);
                }
                setMoveAsString(game, new Move(startCell, endCell), "enPassant");
                game.setWhitesTurn(!colour);
                gameService.updateGame(game);
                boardService.updateBoard(board);
                return new ResponseEntity<>(board + " + " + game.isWhitesTurn(), HttpStatus.OK);
            } else if (pieceService.canThePawnPromote(board, startCell, endCell)) {
                String move = pieceService.transformMoveToCorrectNotation(startCell, endCell, board);
                switch (promotePawn.newPiece()) {
                    case "Queen" -> {
                        startCell.setPieces(new Queen(startCell.getPieces().isWhite()));
                        notation += "=Q";
                    }
                    case "Rook" -> {
                        startCell.setPieces(new Rook(startCell.getPieces().isWhite()));
                        notation += "=R";
                    }
                    case "Bishop" -> {
                        startCell.setPieces(new Bishop(startCell.getPieces().isWhite()));
                        notation += "=B";
                    }
                    case "Knight" -> {
                        startCell.setPieces(new Knight(startCell.getPieces().isWhite()));
                        notation += "=N";
                    }
                }
                move += notation;
                pieceService.makeMove(board, new Move(startCell, endCell));
                if (colour) {
                    game.setWhiteMove(game.getWhiteMove() + ", " + move);
                } else {
                    game.setBlackMove(game.getBlackMove() + ", " + move);
                    game.setMoveNumber(game.getMoveNumber() + 1);
                }
                setMoveAsString(game, new Move(startCell, endCell), promotePawn.newPiece());
                game.setWhitesTurn(!colour);
                boardService.updateBoard(board);
                return new ResponseEntity<>(board + " + " + game.isWhitesTurn(), HttpStatus.OK);
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
                    return new ResponseEntity<>(board + " + " + game.isWhitesTurn(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private boolean verifyLastMoveToBePawnStartMove(Game game) {
        String[] moves = game.getMoves().split(", ");
        String[] lastMoves = moves[moves.length - 1].split(";");
        String ll;
        if (game.isWhitesTurn()) {
            ll = lastMoves[1];
        } else {
            ll = lastMoves[0];
        }
        String lastMove = ll.split(" ")[0];
        int startLine = lastMove.charAt(0) - '0';
        lastMove = ll.split(" ")[1];
        int endLine = lastMove.charAt(0) - '0';
        return (game.isWhitesTurn() && startLine == 6 && endLine == 4) || (!game.isWhitesTurn() && startLine == 1 && endLine == 3);
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
        } catch (GameNotFoundException | BoardNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        CellOnTheBoard blackKingCell = board.getKing(false);
        King blackKing = (King) blackKingCell.getPieces();

        CellOnTheBoard whiteKingCell = board.getKing(true);
        King whiteKing = (King) whiteKingCell.getPieces();

        int nrMovesForWhite = mobilityScore.getAllPossibleMoves(board, true).size();
        int nrMovesForBlack = mobilityScore.getAllPossibleMoves(board, false).size();

        if (nrMovesForWhite == 0 && whiteKing.isInCheck()) {
            game.setGameStatus(GameStatus.BLACK_WON);
            String moves = game.getBlackMove().split(",")[game.getBlackMove().split(",").length - 1] + "#";
            game.setBlackMove(moves);
            gameService.updateGame(game);
            return ResponseEntity.ok("CheckMate - Black won");
        } else if (nrMovesForBlack == 0 && blackKing.isInCheck()) {
            game.setGameStatus(GameStatus.WHITE_WON);
            String moves = game.getWhiteMove().split(",")[game.getWhiteMove().split(",").length - 1] + "#";
            game.setWhiteMove(moves);
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
            Game game;
            Board board;
            try {
                game = gameService.getGameById(boardId);
                board = boardService.findById(game.getBoardId());
            } catch (GameNotFoundException | BoardNotFoundException e) {
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
        } catch (BoardNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
