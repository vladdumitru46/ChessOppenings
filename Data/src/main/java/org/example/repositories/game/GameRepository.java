package org.example.repositories.game;

import com.example.models.game.Game;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("SELECT g FROM Game g WHERE g.boardId =:boardId AND g.playerId =:playerId")
    Optional<Game> findByBoardIdAndPlayerId(Integer boardId, Integer playerId);

    @Query("SELECT g FROM Game g WHERE g.playerId =:playerId")
    Optional<List<Game>> findByPlayerId(Integer playerId);
}
