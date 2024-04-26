package org.example.controllers;

import com.example.models.board.Board;
import com.example.models.game.Game;
import lombok.AllArgsConstructor;
import org.example.board.BoardService;
import org.example.game.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@AllArgsConstructor
@RequestMapping("/chess/board")
@CrossOrigin(origins = "*")
public class BoardController {

    private final BoardService boardService;
    private final GameService gameService;

    @PostMapping("/save")
    public ResponseEntity<?> saveBoard() {
        try {
            Integer id = boardService.save(new Board());
            System.out.println(id);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving board");
        }
    }

    @GetMapping("/getBoardConfiguration")
    public ResponseEntity<?> getBoardConfiguration(@RequestParam Integer gameId) {
        try {
            Game game = gameService.getGameById(gameId);
            Board board = boardService.findById(game.getBoardId());
            return ResponseEntity.ok(board.toString() + " + " + game.isWhitesTurn());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/boardConfiguration")
    public ResponseEntity<?> boardConfiguration(@RequestParam Integer boardId) {
        try {
            Board board = boardService.findById(boardId);
            return ResponseEntity.ok(board.toString());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
