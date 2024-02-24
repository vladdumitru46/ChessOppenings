package org.example.repositoryes.game;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBoard;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE Board SET cellOnTheBoardMap = :cellOnTheBoardMap, whitesTurn = :whitesTurn WHERE id = :id")
    void update(@Param("id") Integer id, @Param("cellOnTheBoardMap") CellOnTheBoard[][] cellOnTheBoardMap, @Param("whitesTurn") boolean whitesTurn);
}
