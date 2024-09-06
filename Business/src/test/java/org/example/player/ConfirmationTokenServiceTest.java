package org.example.player;

import com.example.models.player.registration.ConfirmationToken;
import org.example.repositories.player.ConfirmationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ConfirmationTokenServiceTest {

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    private ConfirmationTokenService confirmationTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        confirmationTokenService = new ConfirmationTokenService(confirmationTokenRepository);
    }

    @Test
    void confirmToken_existingToken_returnsConfirmationToken() throws Exception {
        // Arrange
        String token = "existingToken";
        ConfirmationToken expectedToken = new ConfirmationToken();
        expectedToken.setToken(token);
        when(confirmationTokenRepository.findByToken(token)).thenReturn(Optional.of(expectedToken));

        // Act
        ConfirmationToken actualToken = confirmationTokenService.confirmToken(token);

        // Assert
        assertEquals(expectedToken, actualToken);
    }

    @Test
    void confirmToken_nonExistingToken_throwsException() {
        // Arrange
        String token = "nonExistingToken";
        when(confirmationTokenRepository.findByToken(token)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(Exception.class, () -> confirmationTokenService.confirmToken(token));
    }
}
