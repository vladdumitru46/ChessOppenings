package org.example;

import com.example.models.player.Player;
import org.example.exceptions.PlayerValidationException;
import org.springframework.stereotype.Service;

@Service
public class PlayerValidator {

    public void validatePlayer(Player player) throws PlayerValidationException {
        ValidationResult result = PlayerValidationInterface
                .isEmailValid()
                .and(PlayerValidationInterface.isUserNameValid())
                .and(PlayerValidationInterface.isPasswordValid())
                .apply(player);

        if (result != ValidationResult.SUCCESS) {
            switch (result) {
                case INVALID_USERNAME -> throw new PlayerValidationException("Username cannot be empty");
                case INVALID_EMAIL -> throw new PlayerValidationException("Email is not in correct format!");
                case INVALID_PASSWORD ->
                        throw new PlayerValidationException("Password must contain at least an uppercase letter, a special character, a number, and must be at least" +
                                " 8 characters");
            }

        }
    }
}
