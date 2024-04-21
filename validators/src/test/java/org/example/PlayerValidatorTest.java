package org.example;

import com.example.models.player.Player;
import org.example.exceptions.PlayerValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class PlayerValidatorTest {

    @Test
    public void testValidatePlayer_withValidPlayer() {
        Player player = new Player( "marcel", "johndoe", "john.doe@example.com","Password123!");

        PlayerValidator validator = new PlayerValidator();

        assertDoesNotThrow(() -> validator.validatePlayer(player));
    }

    @Test
    public void testValidatePlayer_withInvalidUsername() {
        Player player = new Player( "marcel", "", "john.doe@example.com","Password123!");

        PlayerValidator validator = new PlayerValidator();

        PlayerValidationException exception = assertThrows(PlayerValidationException.class,
                () -> validator.validatePlayer(player));

        assertEquals("Username cannot be empty", exception.getMessage());
    }

    @Test
    public void testValidatePlayer_withInvalidEmail() {
        Player player = new Player( "marcel", "johndoe", "email","Password123!");

        PlayerValidator validator = new PlayerValidator();

        PlayerValidationException exception = assertThrows(PlayerValidationException.class,
                () -> validator.validatePlayer(player));

        assertEquals("Email is not in correct format!", exception.getMessage());
    }

    @Test
    public void testValidatePlayer_withInvalidPassword() {
        Player player = new Player( "marcel", "johndoe", "john.doe@example.com","password");

        PlayerValidator validator = new PlayerValidator();

        PlayerValidationException exception = assertThrows(PlayerValidationException.class,
                () -> validator.validatePlayer(player));

        assertEquals("Password must contain at least an uppercase letter, a special character, a number, and must be at least 8 characters",
                exception.getMessage());
    }
}