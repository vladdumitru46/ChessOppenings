package org.example.repositoryes.player;

import com.example.models.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    @Query("SELECT p FROM Player p WHERE p.email=:email AND p.password=:password")
    Optional<Player> findByEmailAndPassword(String email, String password);

    @Query("SELECT p FROM Player p WHERE p.email = ?1")
    Optional<Player> findByEmail(String email);

    @Query("SELECT p FROM Player p WHERE p.userName = ?1")
    Optional<Player> findByUserName(String userName);

    @Query("UPDATE Player t SET t.locked =:locked WHERE t.id = :id")
    void update(Integer id, boolean locked);
}
