package org.example;

import com.example.models.player.Player;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface PlayerValidationInterface extends Function<Player, ValidationResult> {


    String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$";
    Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    static PlayerValidationInterface isEmailValid() {
        return player -> {
            String email = player.getEmail();

            if (email == null || email.trim().isEmpty()) {
                return ValidationResult.INVALID_EMAIL;
            }

            Matcher matcher = EMAIL_PATTERN.matcher(email);

            if (!matcher.matches()) {
                return ValidationResult.INVALID_EMAIL;
            }

            return ValidationResult.SUCCESS;
        };
    }

    String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    static PlayerValidationInterface isPasswordValid() {
        return player -> {
            String password = player.getPassword();

            if (password == null || password.trim().isEmpty()) {
                return ValidationResult.INVALID_PASSWORD;
            }

            Matcher matcher = PASSWORD_PATTERN.matcher(password);

            if (!matcher.matches()) {
                return ValidationResult.INVALID_PASSWORD;
            }

            return ValidationResult.SUCCESS;
        };
    }

    static PlayerValidationInterface isUserNameValid() {
        return player -> {
            if (player.getUserName().isEmpty()) {
                return ValidationResult.INVALID_USERNAME;
            }
            return ValidationResult.SUCCESS;
        };
    }


    default PlayerValidationInterface and(PlayerValidationInterface other) {
        return player -> {
            ValidationResult result = this.apply(player);
            return result.equals(ValidationResult.SUCCESS) ? other.apply(player) : result;
        };
    }
}