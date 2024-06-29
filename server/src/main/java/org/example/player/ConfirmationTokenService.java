package org.example.player;

import com.example.models.player.registration.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.example.exceptions.ConfirmationTokenException;
import org.example.repositoryes.player.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("confirmationTokenService")
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }


    public ConfirmationToken confirmToken(String token) throws ConfirmationTokenException {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);
        if (confirmationToken.isPresent()) {
            return confirmationToken.get();
        }
        throw new ConfirmationTokenException("token does not exist!");
    }

}

