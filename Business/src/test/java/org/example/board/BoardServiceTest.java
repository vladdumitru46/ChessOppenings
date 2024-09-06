package org.example.board;

import com.example.models.board.Board;
import org.example.repositories.game.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    private BoardService boardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        boardService = new BoardService(boardRepository);
    }

    @Test
    void save_shouldReturnBoardId() {
        Board board = new Board();
        board.setId(1);

        when(boardRepository.save(board)).thenReturn(board);

        Integer result = boardService.save(board);

        assertEquals(1, result);
        verify(boardRepository, times(1)).save(board);
    }

    @Test
    void findById_existingId_shouldReturnBoard() throws Exception {
        Board board = new Board();
        board.setId(1);

        when(boardRepository.findById(1)).thenReturn(Optional.of(board));

        Board result = boardService.findById(1);

        assertEquals(board, result);
        verify(boardRepository, times(1)).findById(1);
    }

    @Test
    void findById_nonExistingId_shouldThrowException() {
        when(boardRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> boardService.findById(1));
        verify(boardRepository, times(1)).findById(1);
    }

}
