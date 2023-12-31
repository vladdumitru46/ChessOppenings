package org.example.repositoryes.interfaces;

import com.example.models.courses.Player;
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
}
