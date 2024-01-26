package org.example.controllers;

import com.example.models.board.Board;
import com.example.models.board.Move;
import lombok.AllArgsConstructor;
import org.example.BoardService;
import org.example.PieceService;
import org.example.data.Data;
import org.example.miniMax.MiniMax;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@AllArgsConstructor
@RequestMapping("/chess/move/ai")
@CrossOrigin(origins = "*")
public class AiController {

    private final BoardService boardService;
    private final PieceService pieceService;
    private final Data data;

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
        Move move;
        if (board.isWhitesTurn()) {
            MiniMax miniMax = new MiniMax(board, 3, true, pieceService, data.getNumberOfThreads());
            move = miniMax.getBestMove();
        } else {
            MiniMax miniMax = new MiniMax(board, 3, false, pieceService, data.getNumberOfThreads());
            move = miniMax.getBestMove();
        }
        System.out.println(move);
        String moveTransformed = pieceService.transformMoveToCorrectNotation(move.getStart(), move.getEnd(), board);
        boardService.updateBoard(board);
        return new ResponseEntity<>(moveTransformed, HttpStatus.OK);
    }


    @PostMapping("/makeMove")
    public ResponseEntity<?> doAiMove(@RequestBody String boardId) {
        System.out.println("ma-ta: " + data.getNumberOfThreads());
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
        Move move;
        System.out.println(board.isWhitesTurn());
        if (board.isWhitesTurn()) {
            MiniMax miniMax = new MiniMax(board, 3, true, pieceService, data.getNumberOfThreads());
            move = miniMax.getBestMove();
        } else {
            MiniMax miniMax = new MiniMax(board, 3, false, pieceService, data.getNumberOfThreads());
            move = miniMax.getBestMove();
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
