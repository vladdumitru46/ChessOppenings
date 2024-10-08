package org.example.controllers;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import com.example.models.board.Move;
import com.example.models.courses.Course;
import com.example.models.courses.CourseStartedByPlayer;
import com.example.models.courses.CourseStatus;
import com.example.models.courses.SubCourse;
import com.example.models.pieces.*;
import com.example.models.player.Player;
import com.example.models.player.PlayerSession;
import lombok.AllArgsConstructor;
import org.example.board.BoardService;
import org.example.board.PieceService;
import org.example.course.CourseService;
import org.example.course.CourseStartedByPlayerService;
import org.example.course.SubCourseService;
import org.example.exceptions.*;
import org.example.player.PlayerService;
import org.example.player.PlayerSessionService;
import org.example.requests.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/chess/startCourse")
@CrossOrigin(origins = "*")
public class CourseStartedByPlayerController {

    private final CourseStartedByPlayerService courseStartedByPlayerService;
    private final CourseService courseService;
    private final SubCourseService subCourseService;
    private final PieceService pieceService;
    private final BoardService boardService;
    private final PlayerSessionService playerSessionService;

    @PostMapping("/start")
    private ResponseEntity<?> startCourse(@RequestBody StartCourseRequest startCourseRequest) {
        try {
            PlayerSession playerSession = playerSessionService.getByToken(startCourseRequest.token());
            Player player = playerSession.getPlayer();
            Course course = courseService.findCourseByName(startCourseRequest.courseName());
            try {
                CourseStartedByPlayer courseStartedByPlayer = courseStartedByPlayerService.getCourseStartedByPlayerAfterPlayerIdAndCourseName(player.getId(),
                        course.getName());
                Board board = courseStartedByPlayer.getBoardId();
                return new ResponseEntity<>(board.getId(), HttpStatus.OK);
            } catch (CourseStartedByPlayerNotFoundException e) {
                Board board = new Board();
                boardService.save(board);
                CourseStartedByPlayer courseStartedByPlayer = new CourseStartedByPlayer(player, course, board);
                courseStartedByPlayerService.addPlayerThatStartedTheCourse(courseStartedByPlayer);
                return new ResponseEntity<>(board.getId(), HttpStatus.OK);
            }
        } catch (NoSessionException | CourseNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verifyMove")
    public ResponseEntity<?> verifyMovePlayedByPlayer(@RequestBody VerifyMoveRequest verifyMoveRequest) {
        try {
            SubCourse course = subCourseService.getByName(verifyMoveRequest.subCourseName(), verifyMoveRequest.courseName());
            Board board = boardService.findById(verifyMoveRequest.boardId());
            PlayerSession playerSession = playerSessionService.getByToken(verifyMoveRequest.token());
            Player player = playerSession.getPlayer();
            CourseStartedByPlayer courseStartedByPlayer = courseStartedByPlayerService.getCourseStartedByPlayerAfterPlayerIdAndCourseNameAndBoardId(player.getId(),
                    verifyMoveRequest.courseName(), verifyMoveRequest.boardId());

            int moveNumber = courseStartedByPlayer.getMoveNumber();
            String start1 = verifyMoveRequest.start();
            int line = start1.charAt(0) - '0';
            int column = start1.charAt(1) - '0';
            String end1 = verifyMoveRequest.end();
            int line1 = end1.charAt(0) - '0';
            int column1 = end1.charAt(1) - '0';

            CellOnTheBoard start = board.getCellOnTheBoardMap()[line][column];
            CellOnTheBoard end = board.getCellOnTheBoardMap()[line1][column1];
            String move = pieceService.transformMoveToCorrectNotation(start, end, board);

            String[] listOfMoves = course.getMovesThatThePlayerShouldPlay().split(", ");

            String moveThatShouldBePlayed = listOfMoves[moveNumber - 1];

            if (move.equals(moveThatShouldBePlayed)) {
                if (moveThatShouldBePlayed.contains("0")) {
                    pieceService.doCastle(board, end, (King) start.getPieces());
                }
                pieceService.makeMove(board, new Move(start, end));
                courseStartedByPlayer.setWhitesTurn(!courseStartedByPlayer.isWhitesTurn());
                boardService.updateBoard(board);
                if (courseStartedByPlayer.isWhitesTurn()) {
                    int a = moveNumber + 1;
                    courseStartedByPlayer.setMoveNumber(a);
                }
                if (moveNumber + 1 > listOfMoves.length) {
                    courseStartedByPlayer.setSubCoursesCompleted(courseStartedByPlayer.getSubCoursesCompleted() + 1);
                    if (courseStartedByPlayer.getSubCoursesCompleted() == subCourseService.getAllSubCourses(verifyMoveRequest.courseName()).size()) {
                        courseStartedByPlayerService.finishCourse(courseStartedByPlayer);
                    }
                    courseStartedByPlayerService.update(courseStartedByPlayer);
                    return new ResponseEntity<>("You have finished the course!", HttpStatus.OK);
                }
                courseStartedByPlayerService.update(courseStartedByPlayer);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>("The move you played is not the correct one! Try again", HttpStatus.BAD_REQUEST);
            }
        } catch (BoardNotFoundException | CourseStartedByPlayerNotFoundException | SubCourseNotFoundException |
                 NoSessionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/computerMove")
    public ResponseEntity<?> computerMove(@RequestParam String courseName, @RequestParam String subCourseName, @RequestParam Integer boardId, @RequestParam String token) {
        try {
            SubCourse course = subCourseService.getByName(subCourseName, courseName);
            Board board = boardService.findById(boardId);
            PlayerSession playerSession = playerSessionService.getByToken(token);
            Player player = playerSession.getPlayer();
            CourseStartedByPlayer courseStartedByPlayer = courseStartedByPlayerService.getCourseStartedByPlayerAfterPlayerIdAndCourseNameAndBoardId(player.getId(),
                    courseName, boardId);
            int moveNumber = courseStartedByPlayer.getMoveNumber();
            String[] listOfMoves = course.getMovesThatTheComputerWillPlay().split(", ");
            if (moveNumber > listOfMoves.length) {
                courseStartedByPlayer.setSubCoursesCompleted(courseStartedByPlayer.getSubCoursesCompleted() + 1);
                if (courseStartedByPlayer.getSubCoursesCompleted() == subCourseService.getAllSubCourses(courseName).size()) {
                    courseStartedByPlayerService.finishCourse(courseStartedByPlayer);
                }
                courseStartedByPlayerService.update(courseStartedByPlayer);
                return new ResponseEntity<>("You have finished the course!", HttpStatus.OK);
            }
            String moveThatShouldBePlayed = listOfMoves[moveNumber - 1];
            String[] move = moveThatShouldBePlayed.split(" ");
            CellOnTheBoard start = board.getCellOnTheBoardMap()[Character.getNumericValue(move[0].charAt(0))][Character.getNumericValue(move[0].charAt(1))];
            CellOnTheBoard end = board.getCellOnTheBoardMap()[Character.getNumericValue(move[1].charAt(0))][Character.getNumericValue(move[1].charAt(1))];
            pieceService.makeMove(board, new Move(start, end));
            courseStartedByPlayer.setWhitesTurn(!courseStartedByPlayer.isWhitesTurn());
            boardService.updateBoard(board);
            if (courseStartedByPlayer.isWhitesTurn()) {
                int a = moveNumber + 1;
                courseStartedByPlayer.setMoveNumber(a);
            }
            courseStartedByPlayerService.update(courseStartedByPlayer);
            return new ResponseEntity<>(moveThatShouldBePlayed, HttpStatus.OK);
        } catch (BoardNotFoundException | CourseStartedByPlayerNotFoundException | SubCourseNotFoundException |
                 NoSessionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/isTheMoveLegal")
    public ResponseEntity<?> isTheMoveLegal(@RequestBody MovePiecesRequest movePiecesRequest) {
        Board board = null;
        try {
            board = boardService.findById(Integer.parseInt(movePiecesRequest.gameId()));
        } catch (BoardNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String start1 = movePiecesRequest.start();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.end();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';

        CellOnTheBoard start = board.getCellOnTheBoardMap()[line][column];
        CellOnTheBoard end = board.getCellOnTheBoardMap()[line1][column1];

        if (start.getPieces() instanceof King) {
            if (pieceService.canTheKingMove(board, start, end, (King) start.getPieces())) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else if (pieceService.canCastle(board, start, end, (King) start.getPieces())) {
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

    @PostMapping("/reset")
    public void reset(@RequestBody ResetBoard resetBoard) {
        try {
            PlayerSession playerSession = playerSessionService.getByToken(resetBoard.token());
            Player player = playerSession.getPlayer();
            CourseStartedByPlayer courseStartedByPlayer = courseStartedByPlayerService.getCourseStartedByPlayerAfterPlayerIdAndCourseNameAndBoardId(player.getId(),
                    resetBoard.courseName(), resetBoard.boardId());
            courseStartedByPlayer.setMoveNumber(1);
            courseStartedByPlayer.setWhitesTurn(true);
            Board board = boardService.findById(resetBoard.boardId());
            CellOnTheBoard[][] cell = board.setBoard();
            board.setCellOnTheBoardMap(cell);
            boardService.updateBoard(board);
            courseStartedByPlayerService.update(courseStartedByPlayer);
        } catch (BoardNotFoundException | CourseStartedByPlayerNotFoundException | NoSessionException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error!\n" + e.getMessage());
        }
    }

    @GetMapping("/getFinishedCourses")
    public ResponseEntity<?> getFinishedCourses(@RequestParam String token) {
        try {
            PlayerSession playerSession = playerSessionService.getByToken(token);
            Integer playerId = playerSession.getPlayer().getId();
            List<String> coursesFinished = courseStartedByPlayerService.getAllCoursesByCourseStatus(CourseStatus.COMPLETED, playerId)
                    .stream()
                    .map(CourseStartedByPlayer::getCourseName)
                    .map(Course::getName)
                    .toList();

            return new ResponseEntity<>(coursesFinished, HttpStatus.OK);
        } catch (CourseStartedByPlayerNotFoundException | NoSessionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/hint")
    public ResponseEntity<?> getHint(@RequestBody GetHintRequest getHintRequest) {
        try {
            SubCourse course = subCourseService.getByName(getHintRequest.subCourseName(), getHintRequest.courseName());
            PlayerSession playerSession = playerSessionService.getByToken(getHintRequest.token());
            Player player = playerSession.getPlayer();
            CourseStartedByPlayer courseStartedByPlayer = courseStartedByPlayerService.getCourseStartedByPlayerAfterPlayerIdAndCourseNameAndBoardId(player.getId(),
                    getHintRequest.courseName(), getHintRequest.boardId());
            int moveNumber = courseStartedByPlayer.getMoveNumber();

            String[] listOfMoves = course.getMovesThatThePlayerShouldPlay().split(", ");
            String nextMove = listOfMoves[moveNumber - 1];
            return new ResponseEntity<>("Try " + nextMove, HttpStatus.OK);
        } catch (CourseStartedByPlayerNotFoundException | SubCourseNotFoundException | NoSessionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public void deleteAllCoursesStartedByPlayer() {
        courseStartedByPlayerService.deleteAll();
    }

}
