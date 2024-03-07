package org.example.controllers;

import com.example.models.board.Board;
import com.example.models.board.Move;
import com.example.models.game.Game;
import lombok.AllArgsConstructor;
import org.example.board.BoardService;
import org.example.board.PieceService;
import org.example.data.Data;
import org.example.game.GameService;
import org.example.miniMax.MiniMax;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController()
@AllArgsConstructor
@RequestMapping("/chess/move/ai")
@CrossOrigin(origins = "*")
public class AiController {

    private final BoardService boardService;
    private final PieceService pieceService;
    private final GameService gameService;
    private final Data data;

    @PostMapping("/bestMove")
    public ResponseEntity<?> bestMove(@RequestBody String isWhite, @RequestBody String boardId) {

        String[] list = boardId.split(":");

        String[] list2 = list[1].split("}");

        int endIndex = list2[0].lastIndexOf("\"");
        String extractedContent = list2[0].substring(1, endIndex);
        Board board = null;
        Game game;
        try {
            game = gameService.getGameById(Integer.valueOf(extractedContent));
            board = boardService.findById(game.getBoardId());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        Move move;
        MiniMax miniMax;
        if (game.isWhitesTurn()) {
            miniMax = new MiniMax(board, 3, true, pieceService);
        } else {
            miniMax = new MiniMax(board, 3, false, pieceService);
            game.setMoveNumber(game.getMoveNumber() + 1);
        }
        move = miniMax.getBestMove();
        System.out.println(move);
        String moveTransformed = pieceService.transformMoveToCorrectNotation(move.getStart(), move.getEnd(), board);
        boardService.updateBoard(board);
        gameService.updateGame(game);
        return new ResponseEntity<>(moveTransformed, HttpStatus.OK);
    }


    @PostMapping("/makeMove")
    public ResponseEntity<?> doAiMove(@RequestBody String boardId) {
        String[] list = boardId.split(":");
        String[] list2 = list[1].split("}");

        int endIndex = list2[0].lastIndexOf("\"");
        String extractedContent = list2[0].substring(1, endIndex);
        Board board = null;
        Game game;
        try {
            game = gameService.getGameById(Integer.valueOf(extractedContent));
            board = boardService.findById(game.getBoardId());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        Move move;
        MiniMax miniMax;
        if (game.isWhitesTurn()) {
            miniMax = new MiniMax(board, data.getDepthForAi(), true, pieceService);
        } else {
            miniMax = new MiniMax(board, data.getDepthForAi(), false, pieceService);
        }
        move = miniMax.getBestMove();
        if (move != null) {
            String moveNotation = pieceService.transformMoveToCorrectNotation(move.getStart(), move.getEnd(), board);
            System.out.println(moveNotation);
            if (game.isWhitesTurn()) {
                game.setWhiteMove(game.getWhiteMove() + ", " + moveNotation);
            } else {
                game.setBlackMove(game.getBlackMove() + ", " + moveNotation);
                game.setMoveNumber(game.getMoveNumber() + 1);
            }
            pieceService.makeMove(board, move);
            String moveToBePlayed = move.getStart() + " " + move.getEnd() + " " + moveNotation;
            setMoveAsString(game, move, "");
            game.setWhitesTurn(!game.isWhitesTurn());
            boardService.updateBoard(board);
            return new ResponseEntity<>(moveToBePlayed, HttpStatus.OK);
        }
        return null;
    }

    private void setMoveAsString(Game game, Move move, String action) {
        String moveAsString = move.getStart().toString() + " " + move.getEnd().toString();
        moveAsString += !Objects.equals(action, "") ? " " + action : "";
        moveAsString += game.isWhitesTurn() ? ";" : ", ";
        game.setMoves(game.getMoves() + moveAsString);
    }
}
