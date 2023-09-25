package org.example;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/move")
@CrossOrigin(origins = "http://localhost:63343")
public class MovePiecesController {
    private final CourseService courseService;
    private final PieceService pieceService;

    public MovePiecesController(CourseService courseService, PieceService pieceService) {
        this.courseService = courseService;
        this.pieceService = pieceService;
    }

    //TODO: actualizeaza tabla, si creeaz-o in functie de ce ai in interfata, vezi tu cum
    @PostMapping("/rook")
    public ResponseEntity<?> moveRook(@RequestBody MovePiecesRequest movePiecesRequest) {
        MainService mainService = new MainService(courseService, pieceService);
        CellOnTheBord[][] cellOnTheBordMap = new CellOnTheBord[8][8];
        cellOnTheBordMap[0][0] = new CellOnTheBord(new Rook(true), 0, 0);
        cellOnTheBordMap[0][1] = new CellOnTheBord(new Knight(true), 0, 1);
        cellOnTheBordMap[0][2] = new CellOnTheBord(new Bishop(true), 0, 2);
        cellOnTheBordMap[0][3] = new CellOnTheBord(new Queen(true), 0, 3);
        cellOnTheBordMap[0][4] = new CellOnTheBord(new King(true), 0, 4);
        cellOnTheBordMap[0][5] = new CellOnTheBord(new Bishop(true), 0, 5);
        cellOnTheBordMap[0][6] = new CellOnTheBord(new Knight(true), 0, 6);
        cellOnTheBordMap[0][7] = new CellOnTheBord(new Rook(true), 0, 7);

        cellOnTheBordMap[1][0] = new CellOnTheBord(null, 1, 0);
        cellOnTheBordMap[1][1] = new CellOnTheBord(null, 1, 1);
        cellOnTheBordMap[1][2] = new CellOnTheBord(null, 1, 2);
        cellOnTheBordMap[1][3] = new CellOnTheBord(null, 1, 3);
        cellOnTheBordMap[1][4] = new CellOnTheBord(null, 1, 4);
        cellOnTheBordMap[1][5] = new CellOnTheBord(null, 1, 5);
        cellOnTheBordMap[1][6] = new CellOnTheBord(null, 1, 6);
        cellOnTheBordMap[1][7] = new CellOnTheBord(null, 1, 7);

        cellOnTheBordMap[7][0] = new CellOnTheBord(new Rook(false), 7, 0);
        cellOnTheBordMap[7][1] = new CellOnTheBord(new Knight(false), 7, 1);
        cellOnTheBordMap[7][2] = new CellOnTheBord(new Bishop(false), 7, 2);
        cellOnTheBordMap[7][3] = new CellOnTheBord(new Queen(false), 7, 3);
        cellOnTheBordMap[7][4] = new CellOnTheBord(new King(false), 7, 4);
        cellOnTheBordMap[7][5] = new CellOnTheBord(new Bishop(false), 7, 5);
        cellOnTheBordMap[7][6] = new CellOnTheBord(new Knight(false), 7, 6);
        cellOnTheBordMap[7][7] = new CellOnTheBord(new Rook(false), 7, 7);

        cellOnTheBordMap[6][0] = new CellOnTheBord(new Pawn(false), 6, 0);
        cellOnTheBordMap[6][1] = new CellOnTheBord(new Pawn(false), 6, 1);
        cellOnTheBordMap[6][2] = new CellOnTheBord(new Pawn(false), 6, 2);
        cellOnTheBordMap[6][3] = new CellOnTheBord(new Pawn(false), 6, 3);
        cellOnTheBordMap[6][4] = new CellOnTheBord(new Pawn(false), 6, 4);
        cellOnTheBordMap[6][5] = new CellOnTheBord(new Pawn(false), 6, 5);
        cellOnTheBordMap[6][6] = new CellOnTheBord(new Pawn(false), 6, 6);
        cellOnTheBordMap[6][7] = new CellOnTheBord(new Pawn(false), 6, 7);

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                cellOnTheBordMap[i][j] = new CellOnTheBord(null, i, j);
            }
        }
        cellOnTheBordMap[2][4] = new CellOnTheBord(new Pawn(false), 2, 4);

        Board board = new Board(cellOnTheBordMap);
        String start1 = movePiecesRequest.getStart();
        int line = start1.charAt(0) - '0';
        int column = start1.charAt(1) - '0';
        String end1 = movePiecesRequest.getEnd();
        int line1 = end1.charAt(0) - '0';
        int column1 = end1.charAt(1) - '0';


        if (Objects.equals(movePiecesRequest.getPieceColour(), "white")) {
            System.out.println(line + "" + column + " " + line1 + "" + column1);
            if (mainService.canTheRookMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], new Rook(true))) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            if (mainService.canTheRookMove(board, board.getCellOnTheBordMap()[line][column], board.getCellOnTheBordMap()[line1][column1], new Rook(false))) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }
}
