package org.example.controllers;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.board.Move;
import com.example.models.courses.Course;
import com.example.models.courses.CourseStartedByPlayer;
import com.example.models.pieces.*;
import com.example.models.player.Player;
import lombok.AllArgsConstructor;
import org.example.*;
import org.example.requests.MovePiecesRequest;
import org.example.requests.StartCourseRequest;
import org.example.requests.VerifyMoveRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/chess/startCourse")
@CrossOrigin(origins = "*")
public class CourseStartedByPlayerController {

    private final CourseStartedByPlayerService courseStartedByPlayerService;
    private final CourseService courseService;
    private final PieceService pieceService;
    private final BoardService boardService;
    private final PlayerService playerService;

    @PostMapping("/start")
    private ResponseEntity<?> startCourse(@RequestBody StartCourseRequest startCourseRequest) {
        try {
            Player player = playerService.searchPlayerByUsernameOrEmail(startCourseRequest.getPlayerUsername());
            Course course = courseService.findCourseByName(startCourseRequest.getCourseName());
            Board board = new Board();
            boardService.save(board);
            CourseStartedByPlayer courseStartedByPlayer = new CourseStartedByPlayer(player, course, board);
            courseStartedByPlayerService.addPlayerThatStartedTheCourse(courseStartedByPlayer);
            return new ResponseEntity<>(board.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verifyMove")
    public ResponseEntity<?> verifyMovePlayedByPlayer(@RequestBody VerifyMoveRequest verifyMoveRequest) {
        try {
            Course course = courseService.findCourseByName(verifyMoveRequest.getCourseName());
            Board board = boardService.findById(verifyMoveRequest.getBoardId());
            Player player = playerService.searchPlayerByUsernameOrEmail(verifyMoveRequest.getPlayerUsernameOrEmail());
            CourseStartedByPlayer courseStartedByPlayer = courseStartedByPlayerService.getCourseStartedByPlayerAfterPlayerIdAndCourseName(player.getId(),
                    verifyMoveRequest.getCourseName(), verifyMoveRequest.getBoardId());

            int moveNumber = courseStartedByPlayer.getMoveNumber();
            String start1 = verifyMoveRequest.getStart();
            int line = start1.charAt(0) - '0';
            int column = start1.charAt(1) - '0';
            String end1 = verifyMoveRequest.getEnd();
            int line1 = end1.charAt(0) - '0';
            int column1 = end1.charAt(1) - '0';

            CellOnTheBoard start = board.getCellOnTheBoardMap()[line][column];
            CellOnTheBoard end = board.getCellOnTheBoardMap()[line1][column1];
            String move = pieceService.transformMoveToCorrectNotation(start, end, board);

            String[] listOfMoves = course.getMovesThatThePlayerShouldPlay().split(", ");

            String moveThatShouldBePlayed = listOfMoves[moveNumber - 1];

            if (move.equals(moveThatShouldBePlayed)) {
                pieceService.makeMove(board, new Move(start, end));
                board.setWhitesTurn(!board.isWhitesTurn());
                boardService.updateBoard(board);
                if (board.isWhitesTurn()) {
                    int a = moveNumber + 1;
                    courseStartedByPlayer.setMoveNumber(a);
                }if (moveNumber + 1 > listOfMoves.length) {
                    courseStartedByPlayerService.finishCourse(courseStartedByPlayer);
                }
                courseStartedByPlayerService.update(courseStartedByPlayer);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>("The move you played is not the correct one! Try again", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/computerMove")
    public ResponseEntity<?> computerMove(@RequestParam String courseName, @RequestParam Integer boardId, @RequestParam String userName) {
        try {
            Course course = courseService.findCourseByName(courseName);
            Board board = boardService.findById(boardId);
            Player player = playerService.searchPlayerByUsernameOrEmail(userName);
            CourseStartedByPlayer courseStartedByPlayer = courseStartedByPlayerService.getCourseStartedByPlayerAfterPlayerIdAndCourseName(player.getId(),
                    courseName, boardId);
            int moveNumber = courseStartedByPlayer.getMoveNumber();

            String[] listOfMoves = course.getMovesThatTheComputerWillPlay().split(", ");

            String moveThatShouldBePlayed = listOfMoves[moveNumber - 1];
            String[] move = moveThatShouldBePlayed.split(" ");
            CellOnTheBoard start = board.getCellOnTheBoardMap()[Character.getNumericValue(move[0].charAt(0))][Character.getNumericValue(move[0].charAt(1))];
            CellOnTheBoard end = board.getCellOnTheBoardMap()[Character.getNumericValue(move[1].charAt(0))][Character.getNumericValue(move[1].charAt(1))];
            pieceService.makeMove(board, new Move(start, end));
            board.setWhitesTurn(!board.isWhitesTurn());
            boardService.updateBoard(board);
            if (board.isWhitesTurn()) {
                int a = moveNumber + 1;
                courseStartedByPlayer.setMoveNumber(a);
            }if (moveNumber + 1 > listOfMoves.length) {
                courseStartedByPlayerService.finishCourse(courseStartedByPlayer);
            }
            courseStartedByPlayerService.update(courseStartedByPlayer);
            return new ResponseEntity<>(moveThatShouldBePlayed, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/isTheMoveLegal")
    public ResponseEntity<?> isTheMoveLegal(@RequestBody MovePiecesRequest movePiecesRequest) {
        Board board = null;
        try {
            board = boardService.findById(Integer.parseInt(movePiecesRequest.getBoardId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';

        CellOnTheBoard start = board.getCellOnTheBoardMap()[line][column];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[line1][column1];

        if (start.getPieces() instanceof King) {
            if (pieceService.canTheKingMove(board, start, end, (King) start.getPieces())) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else if (start.getPieces() instanceof Queen) {
            if (pieceService.canTheQueenMove(board, start, end, (Queen) start.getPieces())) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else if (start.getPieces() instanceof Rook) {
            if (pieceService.canTheRookMove(board, start, end, (Rook) start.getPieces())) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else if (start.getPieces() instanceof Bishop) {
            if (pieceService.canTheBishopMove(board, start, end, (Bishop) start.getPieces())) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else if (start.getPieces() instanceof Knight) {
            if (pieceService.canTheKnightMove(board, start, end, (Knight) start.getPieces())) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else if (start.getPieces() instanceof Pawn) {
            if (pieceService.canThePawnMove(board, start, end, (Pawn) start.getPieces())) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("cannot move", HttpStatus.BAD_REQUEST);
    }

}
