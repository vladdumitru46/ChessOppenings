package org.example.controllers;

import com.example.models.board.Board;
import com.example.models.board.Move;
import lombok.AllArgsConstructor;
import org.example.BoardService;
import org.example.PieceService;
import org.example.miniMax.MiniMax;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@AllArgsConstructor
@RequestMapping("/move/ai")
@CrossOrigin(origins = "*")
public class AiController {

    private final BoardService boardService;
    private final PieceService pieceService;

    @PostMapping("/bestMove")
    public ResponseEntity<?> bestMove(@RequestBody String isWhite, @RequestBody String boardId) {
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

        MiniMax miniMax = new MiniMax();
        Move move;
        if (isWhite.contains("w")) {
            move = miniMax.getBestMove(board, 3, true, pieceService);
        } else {
            move = miniMax.getBestMove(board, 3, false, pieceService);
        }
        System.out.println(move);
        String moveTransformed = pieceService.transformMoveToCorrectNotation(move.getStart(), move.getEnd(), board);
        boardService.updateBoard(board);
        return new ResponseEntity<>(moveTransformed, HttpStatus.OK);
    }


    @PostMapping("/makeMove")
    public ResponseEntity<?> doAiMove(@RequestBody String boardId) {
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
        MiniMax miniMax = new MiniMax();
        Move move;
        System.out.println(board.isWhitesTurn());
        if (board.isWhitesTurn()) {
            move = miniMax.getBestMove(board, 1, true, pieceService);
        } else {
            move = miniMax.getBestMove(board, 3, false, pieceService);
        }
        System.out.println(move);
        if (move != null) {
            pieceService.makeMove(board, move);
            String moveToBePlayed = move.getStart() + " " + move.getEnd();
            board.setWhitesTurn(!board.isWhitesTurn());
            boardService.updateBoard(board);
            return new ResponseEntity<>(moveToBePlayed, HttpStatus.OK);
        }
        return null;
    }
}
