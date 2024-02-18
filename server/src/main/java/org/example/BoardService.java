package org.example;

import com.example.models.board.Board;
import lombok.AllArgsConstructor;
import org.example.repositoryes.interfaces.game.BoardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service("boardService")
@AllArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Integer save(Board board) {
        Board b = boardRepository.save(board);
        return b.getId();
    }

    public Board findById(Integer id) throws Exception {

        Optional<Board> board = boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        }
        throw new Exception("Board with that id does not exist!");
    }

    @Transactional
    public void updateBoard(Board board) {
//        boardRepository.update(board.getId(), board.getCellOnTheBoardMap(), board.isWhitesTurn());
        board.setCellOnTheBoardMap(board.getCellOnTheBoardMap());
        board.setWhitesTurn(board.isWhitesTurn());
    }
}
