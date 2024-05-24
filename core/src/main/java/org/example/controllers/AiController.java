package org.example.controllers;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.board.Move;
import com.example.models.game.Game;
import com.example.models.game.GameStatus;
import com.example.models.pieces.*;
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

    @PostMapping("/makeMove")
    public ResponseEntity<?> doAiMove(@RequestBody String boardId) {
        String action = "";
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
        Board testBoard = new Board();
        createCopyBoard(testBoard, game);
        Move move;
        MiniMax miniMax;
        if (game.getGameStatus().equals(GameStatus.STARTED)) {
            if (game.isWhitesTurn()) {
                miniMax = new MiniMax(testBoard, data.getDepthForAi(), true, mobilityScore, pieceService, kingSafetyScore, evaluation);
            } else {
                miniMax = new MiniMax(testBoard, data.getDepthForAi(), false, mobilityScore, pieceService, kingSafetyScore, evaluation);
            }
            move = miniMax.getBestMove();
            if (move != null) {
                String moveNotation = pieceService.transformMoveToCorrectNotation(move.getStart(), move.getEnd(), board);

                if (move.getStart().getPieces() instanceof King && abs(move.getStart().getColumnCoordinate() - move.getEnd().getColumnCoordinate()) == 2) {
                    action = didAiCastle(board, move.getStart(), move.getEnd());
                    if (action.equals("castleShort")) {
                        moveNotation = "0-0";
                    } else if (action.equals("castleLong")) {
                        moveNotation = "0-0-0";
                    }
                } else {
                    Pieces pieces = board.getCellOnTheBoardMap()[move.getStart().getLineCoordinate()][move.getStart().getColumnCoordinate()].getPieces();
                    if (pieces instanceof Pawn) {
                        if ((pieces.isWhite() && move.getStart().getLineCoordinate() == 6)
                                || (!pieces.isWhite() && move.getStart().getLineCoordinate() == 1)) {
                            moveNotation = moveNotation.substring(1);
                            switch (move.getStart().getPieces()) {
                                case Queen ignored -> {
                                    moveNotation += "=Q";
                                    action = " Queen";
                                }
                                case Rook ignored -> {
                                    moveNotation += "=R";
                                    action = " Rook";
                                }
                                case Bishop ignored -> {
                                    moveNotation += "=B";
                                    action = " Bishop";
                                }
                                case Knight ignored -> {
                                    moveNotation += "=N";
                                    action = " Knight";
                                }
                                default -> {
                                    moveNotation += "";
                                    action = "";
                                }
                            }
                        }
                    }
                }
                if (move.getStart().getPieces() instanceof King king) {
                    king.setHasBeenMoved(true);
                    move.getStart().setPieces(king);
                } else if (move.getStart().getPieces() instanceof Rook rook) {
                    rook.setHasBeenMoved(true);
                    move.getStart().setPieces(rook);
                }
                pieceService.makeMove(board, move);
                if (game.isWhitesTurn()) {
                    game.setWhiteMove(game.getWhiteMove() + ", " + moveNotation);
                } else {
                    game.setBlackMove(game.getBlackMove() + ", " + moveNotation);
                    game.setMoveNumber(game.getMoveNumber() + 1);
                }
                setMoveAsString(game, move, action);
                game.setWhitesTurn(!game.isWhitesTurn());
                boardService.updateBoard(board);
                System.out.println(moveNotation);
                return new ResponseEntity<>(board + " + " + game.isWhitesTurn(), HttpStatus.OK);
            }
        }
        return null;
    }

    private void createCopyBoard(Board board, Game game) {
        String[] moves = game.getMoves().split(", ");
        int moveNumber = game.getMoveNumber();
        for (int i = 0; i < moveNumber; i++) {
            String[] bothMoves = moves[i].split(";");
            String[] move = bothMoves[0].split(" ");
            pieceService.takeMoveDataAndUndoIt(board, move);
            if (bothMoves.length == 2) {
                move = bothMoves[1].split(" ");
                pieceService.takeMoveDataAndUndoIt(board, move);
            }
        }
    }

    private void setMoveAsString(Game game, Move move, String action) {
        String moveAsString = move.getStart().toString() + " " + move.getEnd().toString();
        moveAsString += !Objects.equals(action, "") ? " " + action : "";
        moveAsString += game.isWhitesTurn() ? ";" : ", ";
        game.setMoves(game.getMoves() + moveAsString);
    }

    private String didAiCastle(Board board, CellOnTheBoard start, CellOnTheBoard end) {
        String action = "";
        King king = (King) start.getPieces();
        if (king.isHasBeenMoved()) {
            return action;
        }
        if (king.isWhite()) {
            if (end.getColumnCoordinate() == 2) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[0][0].getPieces();
                rook.setHasBeenMoved(true);
                board.getCellOnTheBoardMap()[0][3].setPieces(rook);
                board.getCellOnTheBoardMap()[0][0] = new CellOnTheBoard(null, 0, 0);
                action = "castleShort";
            }
            if (end.getColumnCoordinate() == 6) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[0][7].getPieces();
                rook.setHasBeenMoved(true);
                board.getCellOnTheBoardMap()[0][5].setPieces(rook);
                board.getCellOnTheBoardMap()[0][7] = new CellOnTheBoard(null, 0, 0);
                action = "castleLong";
            }
        } else {
            if (end.getColumnCoordinate() == 2) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[7][0].getPieces();
                rook.setHasBeenMoved(true);
                board.getCellOnTheBoardMap()[7][3].setPieces(rook);
                board.getCellOnTheBoardMap()[7][0] = new CellOnTheBoard(null, 7, 0);
                action = "castleShort";
            } else if (end.getColumnCoordinate() == 6) {
                Rook rook = (Rook) board.getCellOnTheBoardMap()[7][7].getPieces();
                rook.setHasBeenMoved(true);
                board.getCellOnTheBoardMap()[7][5].setPieces(rook);
                board.getCellOnTheBoardMap()[7][7] = new CellOnTheBoard(null, 7, 0);
                action = "castleLong";
            }
        }
        return action;
    }

}
