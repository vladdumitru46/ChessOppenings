package org.example.repositories.player;

import com.example.models.player.registration.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    @Query("SELECT t FROM ConfirmationToken t WHERE t.token = :token")
    Optional<ConfirmationToken> findByToken(String token);

}