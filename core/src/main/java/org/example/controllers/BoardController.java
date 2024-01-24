package org.example.controllers;

import com.example.models.board.Board;
import lombok.AllArgsConstructor;
import org.example.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@AllArgsConstructor
@RequestMapping("/board")
@CrossOrigin(origins = "*")
public class BoardController {

    private final BoardService boardService;


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
}
