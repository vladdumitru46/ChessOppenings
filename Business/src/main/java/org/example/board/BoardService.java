package org.example.board;

import com.example.models.board.Board;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.exceptions.BoardNotFoundException;
import org.example.repositories.game.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("boardService")
@AllArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Integer save(Board board) {
        Board b = boardRepository.save(board);
        return b.getId();
    }

    public Board findById(Integer id) throws BoardNotFoundException {
        return boardRepository.findById(id)
                .orElseThrow(()->new BoardNotFoundException("Board with that id does not exist!"));
    }

    @Transactional
    public void updateBoard(Board board) {
        board.setCellOnTheBoardMap(board.getCellOnTheBoardMap());
    }

    public void deleteAll() {
        boardRepository.deleteAll();
    }
}