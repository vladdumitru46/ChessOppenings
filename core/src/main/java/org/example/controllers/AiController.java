package org.example.controllers;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.board.Move;
import com.example.models.game.Game;
import com.example.models.game.GameStatus;
import com.example.models.pieces.King;
import com.example.models.pieces.Rook;
import lombok.AllArgsConstructor;
import org.example.board.BoardService;
import org.example.board.PieceService;
import org.example.data.Data;
import org.example.game.GameService;
import org.example.miniMax.score.Evaluation;
import org.example.miniMax.MiniMax;
import org.example.score.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static java.lang.Math.abs;

@RestController()
@AllArgsConstructor
@RequestMapping("/chess/move/ai")
@CrossOrigin(origins = "*")
public class AiController {

    private final BoardService boardService;
    private final PieceService pieceService;
    private final GameService gameService;
    private final CapturePiecesScore capturePiecesScore;
    private final CenterControlScore centerControlScore;
    private final DevelopmentScore developmentScore;
    private final KingSafetyScore kingSafetyScore;
    private final MobilityScore mobilityScore;
    private final PawnStructureScore pawnStructureScore;
    private final Data data;

    @PostMapping("/bestMove")
    public ResponseEntity<?> bestMove(@RequestBody String boardId) {
        Evaluation evaluation = new Evaluation(capturePiecesScore, centerControlScore, developmentScore, kingSafetyScore, mobilityScore, pawnStructureScore);
        String[] list = boardId.split(":");

        String[] list2 = list[1].split("}");

        int endIndex = list2[0].lastIndexOf("\"");
        String extractedContent = list2[0].substring(1, endIndex);
        Board board;
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
            miniMax = new MiniMax(board, 3, true, mobilityScore, pieceService, evaluation);
        } else {
            miniMax = new MiniMax(board, 3, false, mobilityScore, pieceService, evaluation);
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
        Evaluation evaluation = new Evaluation(capturePiecesScore, centerControlScore, developmentScore, kingSafetyScore, mobilityScore, pawnStructureScore);
        String[] list = boardId.split(":");
        String[] list2 = list[1].split("}");

        int endIndex = list2[0].lastIndexOf("\"");
        String extractedContent = list2[0].substring(1, endIndex);
        Board board;
        Game game;
        try {
            game = gameService.getGameById(Integer.valueOf(extractedContent));
            board = boardService.findById(game.getBoardId());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        Move move;
        MiniMax miniMax;
        if (game.getGameStatus().equals(GameStatus.STARTED)) {
            if (game.isWhitesTurn()) {
                miniMax = new MiniMax(board, data.getDepthForAi(), true, mobilityScore, pieceService, evaluation);
            } else {
                miniMax = new MiniMax(board, data.getDepthForAi(), false, mobilityScore, pieceService, evaluation);
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
                if (move.getStart().getPieces() instanceof King && abs(move.getStart().getColumnCoordinate() - move.getEnd().getColumnCoordinate()) == 2) {
                    didAiCastle(board, move.getStart(), move.getEnd());
                }
                pieceService.makeMove(board, move);
                String moveToBePlayed = move.getStart() + " " + move.getEnd() + " " + moveNotation;
                setMoveAsString(game, move, "");
                game.setWhitesTurn(!game.isWhitesTurn());
                boardService.updateBoard(board);
                return new ResponseEntity<>(moveToBePlayed, HttpStatus.OK);
            }
        }
        return null;
    }

    private void setMoveAsString(Game game, Move move, String action) {
        String moveAsString = move.getStart().toString() + " " + move.getEnd().toString();
        moveAsString += !Objects.equals(action, "") ? " " + action : "";
        moveAsString += game.isWhitesTurn() ? ";" : ", ";
        game.setMoves(game.getMoves() + moveAsString);
    }

    private void didAiCastle(Board board, CellOnTheBoard start, CellOnTheBoard end) {
        King king = (King) start.getPieces();
        if (king.isWhite()) {
            if (end.getColumnCoordinate() == 2) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[0][0].getPieces();
                rook.setHasBeenMoved(true);
                board.getCellOnTheBoardMap()[0][3].setPieces(rook);
                board.getCellOnTheBoardMap()[0][0] = new CellOnTheBoard(null, 0, 0);
            }
            if (end.getColumnCoordinate() == 6) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[0][7].getPieces();
                rook.setHasBeenMoved(true);
                board.getCellOnTheBoardMap()[0][5].setPieces(rook);
                board.getCellOnTheBoardMap()[0][7] = new CellOnTheBoard(null, 0, 0);
            }
        } else {
            if (end.getColumnCoordinate() == 2) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[7][0].getPieces();
                rook.setHasBeenMoved(true);
                board.getCellOnTheBoardMap()[7][3].setPieces(rook);
                board.getCellOnTheBoardMap()[7][0] = new CellOnTheBoard(null, 7, 0);
            } else if (end.getColumnCoordinate() == 6) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[7][7].getPieces();
                rook.setHasBeenMoved(true);
                board.getCellOnTheBoardMap()[7][5].setPieces(rook);
                board.getCellOnTheBoardMap()[7][7] = new CellOnTheBoard(null, 7, 0);
            }
        }
    }
}
