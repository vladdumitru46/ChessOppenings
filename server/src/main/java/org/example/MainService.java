package org.example;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.courses.Course;
import com.example.models.courses.CourseStartedByPlayer;
import com.example.models.courses.Player;
import com.example.models.pieces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MainService implements IService {
    private final CourseService courseService;
    private final PieceService pieceService;
    Logger logger = LoggerFactory.getLogger(MainService.class);

    private static Map<Integer, IServiceObserver> observerMap;

    public MainService(CourseService courseService, PieceService pieceService) {
        this.courseService = courseService;
        this.pieceService = pieceService;
        observerMap = new ConcurrentHashMap<>();
    }

    public void savePlayer(Player player) {
        try {
            courseService.savePlayer(player);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Player> searchPlayerByEmailAndPassword(String email, String password) throws Exception {
        return courseService.searchPlayerByEmailAndPassword(email, password);
    }

    public Optional<Course> findCourseById(Integer id) {
        return courseService.findCourseById(id);
    }

    public void savePlayerThatStartedACourse(CourseStartedByPlayer courseStartedByPlayer) {
        courseService.savePlayerThatStartedACourse(courseStartedByPlayer);
    }

    public boolean canThePawnMove(Board board, CellOnTheBord start, CellOnTheBord end, Pawn pawn) {
        return pieceService.canThePawnMove(board, start, end, pawn);
    }

    public boolean canTheRookMove(Board board, CellOnTheBord start, CellOnTheBord end, Rook rook) {
        return pieceService.canTheRookMove(board, start, end, rook);
    }

    public boolean canTheKnightMove(Board board, CellOnTheBord start, CellOnTheBord end, Knight knight) {
        return pieceService.canTheKnightMove(board, start, end, knight);
    }

    public boolean canTheBishopMove(Board board, CellOnTheBord start, CellOnTheBord end, Bishop bishop) {
        return pieceService.canTheBishopMove(board, start, end, bishop);
    }

    public boolean canTheQueenMove(Board board, CellOnTheBord start, CellOnTheBord end, Queen queen) {
        return pieceService.canTheQueenMove(board, start, end, queen);
    }

    public boolean canTheKingMove(Board board, CellOnTheBord start, CellOnTheBord end, King king) {
        return pieceService.canTheKingMove(board, start, end, king);
    }

    public boolean checkIsTheKingIsInCech(Board board, CellOnTheBord start, CellOnTheBord end, Pieces king) {
        return pieceService.checkIsTheKingIsInCech(board, start, end, king);
    }

    public Optional<Player> searchPlayerById(Integer id) {
        return courseService.searchPlayerById(id);
    }

    public Optional<Player> searchPlayerByUsername(String userName) {
        return courseService.searchPlayerByUsername(userName);
    }

    @Override
    public void logIn(Player player, IServiceObserver client) throws Exception {
        logger.info("log in player {}", player.getUserName());
        if (player == null) {
            throw new Exception("Email or password invalid!");
        } else {
            logger.info("player logged in");
            observerMap.put(player.getId(), client);
        }
    }

    @Override
    public void logOut(Player player, IServiceObserver client) throws Exception {
        logger.info("log out player {}", player);
        observerMap.remove(player.getId());
        logger.info("player logged out!");
    }
}
