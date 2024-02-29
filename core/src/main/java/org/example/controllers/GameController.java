package org.example.controllers;

import com.example.models.board.Board;
import com.example.models.game.Game;
import com.example.models.game.GameStatus;
import com.example.models.player.Player;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.example.board.BoardService;
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
}
