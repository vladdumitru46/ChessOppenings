package org.example.controllers;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.board.Move;
import com.example.models.game.Game;
import com.example.models.game.GameStatus;
import com.example.models.pieces.*;
import com.example.models.player.Player;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.example.board.BoardService;
import org.example.board.PieceService;
import org.example.game.GameService;
import org.example.player.PlayerService;
import org.example.requests.GameRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chess/game")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;
    private final PlayerService playerService;
    private final BoardService boardService;
    private final PieceService pieceService;

    @PostMapping
    public ResponseEntity<?> saveGame(@RequestBody GameRequest gameRequest) {
        try {
            Player player = playerService.searchPlayerByUsernameOrEmail(gameRequest.playerEmailOrUsername());
            Board board = new Board();
            boardService.save(board);
            Long id = gameService.addANewGame(new Game(player.getId(), board.getId(), GameStatus.STARTED));
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> gameHistory(@RequestParam String playerUsernameOrEmail) {
        try {
            Player player = playerService.searchPlayerByUsernameOrEmail(playerUsernameOrEmail);
            List<Game> games = gameService.getAfterPlayerId(player.getId());
            return new ResponseEntity<>(games, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/moves")
    public ResponseEntity<?> gameMoves(@RequestParam Integer gameId) {
        try {
            Game game = gameService.getGameById(gameId);
            String[] movesByWhite = game.getWhiteMove().split(", ");
            String[] movesByBlack = game.getBlackMove().split(", ");
            List<String> moves = new ArrayList<>();
            for (int i = 0; i < movesByBlack.length; i++) {
                moves.add(movesByWhite[i] + " " + movesByBlack[i]);
            }

            if (movesByBlack.length < movesByWhite.length) {
                moves.add(movesByWhite[movesByWhite.length - 1]);
            }
            return new ResponseEntity<>(moves, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //TODO: make a move forward + see how you can make it so you can go until the start of the game with move before
    @GetMapping("/moveBefore")
    public ResponseEntity<?> getMoveBefore(@RequestParam int gameId) {
        try {
            Game game = gameService.getGameById(gameId);
            Board newBoard = new Board();
            String[] moves = game.getMoves().split(", ");

            int moveNumber = game.getMoveNumber() - 1;
            if (moves[moves.length - 1].split(";").length == 1) {
                moveNumber++;
            }
            for (int i = 0; i < moveNumber; i++) {
                String[] bothMoves = moves[i].split(";");
                if (bothMoves.length == 2) {
                    String[] move = bothMoves[0].split(" ");
                    takeMoveDataAndUndoIt(newBoard, move);
                    if (i != moveNumber - 1) {
                        move = bothMoves[1].split(" ");
                        takeMoveDataAndUndoIt(newBoard, move);
                    }
                }
            }
            return new ResponseEntity<>(newBoard.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/moveForward")
    public ResponseEntity<?> getMoveForward(@RequestParam int gameId) {
        try {
            Game game = gameService.getGameById(gameId);
            Board newBoard = new Board();
            String[] moves = game.getMoves().split(", ");

            return new ResponseEntity<>(newBoard.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/movesHistory")
    public ResponseEntity<?> gameHistory(@RequestParam int gameId, @RequestParam int moveNumber) {
        try {
            Game game = gameService.getGameById(gameId);
            Board newBoard = new Board();
            String[] moves = game.getMoves().split(", ");
            for (int i = 0; i < moveNumber; i++) {
                String[] bothMoves = moves[i].split(";");
                String[] move = bothMoves[0].split(" ");
                takeMoveDataAndUndoIt(newBoard, move);
                if (bothMoves.length == 2) {
                    move = bothMoves[1].split(" ");
                    takeMoveDataAndUndoIt(newBoard, move);
                }
            }
            return new ResponseEntity<>(newBoard.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void takeMoveDataAndUndoIt(Board board, String[] move) {
        int startLine = move[0].charAt(0) - '0';
        int startColumn = move[0].charAt(1) - '0';
        int endLine = move[1].charAt(0) - '0';
        int endColumn = move[1].charAt(1) - '0';

        CellOnTheBoard startCell = board.getCellOnTheBoardMap()[startLine][startColumn];
        CellOnTheBoard endCell = board.getCellOnTheBoardMap()[endLine][endColumn];

        if (move.length == 3) {
            switch (move[2]) {
                case "castleShort" -> {
                    board.getCellOnTheBoardMap()[startLine][5].setPieces(board.getCellOnTheBoardMap()[startLine][7].getPieces());
                    board.getCellOnTheBoardMap()[startLine][7].setPieces(null);
                }
                case "castleLong" -> {
                    board.getCellOnTheBoardMap()[startLine][3].setPieces(board.getCellOnTheBoardMap()[startLine][0].getPieces());
                    board.getCellOnTheBoardMap()[startLine][0].setPieces(null);
                }
                case "Queen" -> endCell.setPieces(new Queen(startCell.getPieces().isWhite()));
                case "Rook" -> endCell.setPieces(new Rook(startCell.getPieces().isWhite()));
                case "Bishop" -> endCell.setPieces(new Bishop(startCell.getPieces().isWhite()));
                case "Knight" -> endCell.setPieces(new Knight(startCell.getPieces().isWhite()));

            }
        }

        Move moveMade = new Move(startCell, endCell);
        pieceService.makeMove(board, moveMade);
    }

    @GetMapping("/getMoveNumber")
    public ResponseEntity<?> getMoveNumber(@RequestParam Integer gameId) {
        try {
            Game game = gameService.getGameById(gameId);
            return new ResponseEntity<>(game.getMoveNumber(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
