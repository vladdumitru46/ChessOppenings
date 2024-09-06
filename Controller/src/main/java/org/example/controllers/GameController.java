package org.example.controllers;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.board.Move;
import com.example.models.game.Game;
import com.example.models.game.GameStatus;
import com.example.models.pieces.Bishop;
import com.example.models.pieces.Knight;
import com.example.models.pieces.Queen;
import com.example.models.pieces.Rook;
import com.example.models.player.Player;
import com.example.models.player.PlayerSession;
import lombok.AllArgsConstructor;
import org.example.board.BoardService;
import org.example.board.PieceService;
import org.example.exceptions.GameNotFoundException;
import org.example.exceptions.NoSessionException;
import org.example.exceptions.PlayerNotFoundException;
import org.example.game.GameService;
import org.example.player.PlayerService;
import org.example.player.PlayerSessionService;
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
    private final BoardService boardService;
    private final PieceService pieceService;
    private final PlayerSessionService playerSessionService;

    @PostMapping
    public ResponseEntity<?> saveGame(@RequestBody GameRequest gameRequest) {
        try {
            PlayerSession playerSession = playerSessionService.getByToken(gameRequest.token());
            Player player = playerSession.getPlayer();
            Board board = new Board();
            boardService.save(board);
            Game game = new Game(player.getId(), board.getId(), GameStatus.STARTED);
            game.setPlayerColour(gameRequest.playerColour());
            Long id = gameService.addANewGame(game);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (NoSessionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> gameHistory(@RequestParam String token) {
        try {
            PlayerSession playerSession = playerSessionService.getByToken(token);
            Player player = playerSession.getPlayer();
            List<Game> games = gameService.getAfterPlayerId(player.getId());
            return new ResponseEntity<>(games, HttpStatus.OK);
        } catch (GameNotFoundException | NoSessionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
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
        } catch (GameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/moveBefore")
    public ResponseEntity<?> getMoveBefore(@RequestParam int gameId, @RequestParam int moveNumber) {
        try {
            Game game = gameService.getGameById(gameId);
            Board newBoard = new Board();
            String[] moves = game.getMoves().split(", ");

            int moveNr = moveNumber / 2 + moveNumber % 2;
            for (int i = 0; i < moveNr; i++) {
                String[] bothMoves = moves[i].split(";");
                if (bothMoves.length == 2) {
                    String[] move = bothMoves[0].split(" ");
                    pieceService.takeMoveDataAndUndoIt(newBoard, move);
                    if (moveNumber % 2 == 1 && i == moveNr - 1) {
                        break;
                    }
                    move = bothMoves[1].split(" ");
                    pieceService.takeMoveDataAndUndoIt(newBoard, move);
                }
            }
            return new ResponseEntity<>(newBoard.toString(), HttpStatus.OK);
        } catch (GameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/moveForward")
    public ResponseEntity<?> getMoveForward(@RequestParam int gameId, @RequestParam int moveNumber) {
        try {
            Game game = gameService.getGameById(gameId);
            Board newBoard = new Board();
            String[] moves = game.getMoves().split(", ");

            int moveNr = moveNumber / 2 + moveNumber % 2;
            for (int i = 0; i < moveNr; i++) {
                String[] bothMoves = moves[i].split(";");
                if (bothMoves.length == 2) {
                    String[] move = bothMoves[0].split(" ");
                    pieceService.takeMoveDataAndUndoIt(newBoard, move);
                    if (moveNumber % 2 == 1 && i == moveNr - 1) {
                        break;
                    }
                    move = bothMoves[1].split(" ");
                    pieceService.takeMoveDataAndUndoIt(newBoard, move);
                }
            }
            return new ResponseEntity<>(newBoard.toString(), HttpStatus.OK);
        } catch (GameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
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
                pieceService.takeMoveDataAndUndoIt(newBoard, move);
                if (bothMoves.length == 2) {
                    move = bothMoves[1].split(" ");
                    pieceService.takeMoveDataAndUndoIt(newBoard, move);
                }
            }
            return new ResponseEntity<>(newBoard.toString(), HttpStatus.OK);
        } catch (GameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getMoveNumber")
    public ResponseEntity<?> getMoveNumber(@RequestParam Integer gameId) {
        try {
            Game game = gameService.getGameById(gameId);
            String[] moves = game.getMoves().split(", ");
            String[] lastMove = moves[moves.length - 1].split(";");
            int moveNumber = lastMove.length == 2 ? (game.getMoveNumber() - 1) * 2 : game.getMoveNumber() * 2 - 1;
            return new ResponseEntity<>(moveNumber, HttpStatus.OK);
        } catch (GameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public void deleteAllGames() {
        gameService.deleteAll();
    }
}
