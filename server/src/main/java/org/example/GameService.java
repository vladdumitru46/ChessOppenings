package org.example;

import com.example.models.game.Game;
import lombok.AllArgsConstructor;
import org.example.repositoryes.interfaces.game.GameRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("gameService")
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Game getGameById(Integer id) throws Exception {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isPresent()) {
            return game.get();
        }
        throw new Exception("There is no game with that id!");
    }

    public void addANewGame(Game game) {
        gameRepository.save(game);
    }

    public Game getGameByBoardIdAndPlayerId(Integer boardId, Integer playerId) throws Exception {
        Optional<Game> game = gameRepository.findByBoardIdAndPlayerId(boardId, playerId);
        if (game.isPresent()) {
            return game.get();
        }
        throw new Exception("There is no game with that boardId and playerId!");
    }

}
