package org.example.game;

import com.example.models.game.Game;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.exceptions.GameNotFoundException;
import org.example.repositories.game.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("gameService")
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Game getGameById(Integer id) throws GameNotFoundException {
        return gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("There is no game with that id!"));
    }

    public Long addANewGame(Game game) {
        gameRepository.save(game);
        return game.getId();
    }

    public Game getGameByBoardIdAndPlayerId(Integer boardId, Integer playerId) throws GameNotFoundException {
        Optional<Game> game = gameRepository.findByBoardIdAndPlayerId(boardId, playerId);
        if (game.isPresent()) {
            return game.get();
        }
        throw new GameNotFoundException("There is no game with that boardId and playerId!");
    }

    public List<Game> getAfterPlayerId(Integer playerId) throws GameNotFoundException {
        return gameRepository.findByPlayerId(playerId)
                .orElseThrow(()->new GameNotFoundException("There are no games played by this player"));
    }

    @Transactional
    public void updateGame(Game game) {
        game.setWhiteMove(game.getWhiteMove());
        game.setBlackMove(game.getBlackMove());
        game.setMoveNumber(game.getMoveNumber());
        game.setGameStatus(game.getGameStatus());
        game.setWhitesTurn(game.isWhitesTurn());
        game.setMoves(game.getMoves());
    }

    public void deleteAll() {
        gameRepository.deleteAll();
    }
}
