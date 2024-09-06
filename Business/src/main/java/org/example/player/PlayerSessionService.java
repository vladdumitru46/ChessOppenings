package org.example.player;

import com.example.models.player.PlayerSession;
import lombok.AllArgsConstructor;
import org.example.exceptions.NoSessionException;
import org.example.repositories.player.PlayerSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service("playerSessionService")
public class PlayerSessionService {

    private final PlayerSessionRepository playerSessionRepository;

    public void add(PlayerSession playerSession) {
        playerSessionRepository.save(playerSession);
    }

    public PlayerSession getByToken(String token) throws NoSessionException {
        return playerSessionRepository.findByToken(token)
                .orElseThrow(() -> new NoSessionException("There is no session with this token"));
    }

    public void deleteSession(PlayerSession playerSession) {
        playerSessionRepository.delete(playerSession);
    }

    public boolean checkIfTokenIsExpired(String token) throws NoSessionException {
        PlayerSession playerSession = getByToken(token);
        if (LocalDateTime.now().isAfter(playerSession.getExpiresAt())) {
            deleteSession(playerSession);
            return true;
        }
        return false;
    }
}
