package org.example.game;

import com.example.models.game.Game;
import org.example.repositoryes.game.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameService = new GameService(gameRepository);
    }

    @Test
    void testGetGameById() throws Exception {
        // Arrange
        int gameId = 1;
        Game game = new Game();
        game.setId((long) gameId);
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        // Act
        Game result = gameService.getGameById(gameId);

        // Assert
        assertEquals(game, result);
    }

    @Test
    void testGetGameById_ThrowsException() {
        // Arrange
        int gameId = 1;
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(Exception.class, () -> gameService.getGameById(gameId));
    }

    @Test
    void testAddANewGame() {
        // Arrange
        Game game = new Game();

        // Act
        Long result = gameService.addANewGame(game);

        // Assert
        assertEquals(game.getId(), result);
    }

    @Test
    void testGetGameByBoardIdAndPlayerId() throws Exception {
        // Arrange
        int boardId = 1;
        int playerId = 1;
        Game game = new Game();
        game.setId(1l);
        when(gameRepository.findByBoardIdAndPlayerId(boardId, playerId)).thenReturn(Optional.of(game));

        // Act
        Game result = gameService.getGameByBoardIdAndPlayerId(boardId, playerId);

        // Assert
        assertEquals(game, result);
    }

    @Test
    void testGetGameByBoardIdAndPlayerId_ThrowsException() {
        // Arrange
        int boardId = 1;
        int playerId = 1;
        when(gameRepository.findByBoardIdAndPlayerId(boardId, playerId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(Exception.class, () -> gameService.getGameByBoardIdAndPlayerId(boardId, playerId));
    }

    @Test
    void testGetAfterPlayerId() throws Exception {
        // Arrange
        int playerId = 1;
        List<Game> games = new ArrayList<>();
        games.add(new Game());
        when(gameRepository.findByPlayerId(playerId)).thenReturn(Optional.of(games));

        // Act
        List<Game> result = gameService.getAfterPlayerId(playerId);

        // Assert
        assertEquals(games, result);
    }

    @Test
    void testGetAfterPlayerId_ThrowsException() {
        // Arrange
        int playerId = 1;
        when(gameRepository.findByPlayerId(playerId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(Exception.class, () -> gameService.getAfterPlayerId(playerId));
    }


}
