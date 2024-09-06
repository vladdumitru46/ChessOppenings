package org.example.repositories.player;

import com.example.models.player.PlayerSession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerSessionRepository extends JpaRepositoryImplementation<PlayerSession, Long> {

    @Query("SELECT ps FROM PlayerSession ps WHERE token= :token")
    Optional<PlayerSession> findByToken(String token);
}
