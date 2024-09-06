package org.example.player;

import com.example.models.player.Player;
import jakarta.mail.MessagingException;
import org.example.PlayerValidator;
import org.example.exceptions.PlayerValidationException;
import org.example.player.email.EmailService;
import org.example.repositories.player.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerValidator playerValidator;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private EmailService emailSender;

    private PlayerService playerService;
    @Mock
    private PlayerSessionService playerSessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerService(playerRepository, playerValidator, confirmationTokenService, emailSender, playerSessionService);
    }

    @Test
    void savePlayer_ValidPlayer_Success() throws PlayerValidationException, MessagingException {
        // Arrange
        Player player = new Player();
        player.setEmail("test@example.com");
        player.setUserName("testuser");
        player.setPassword("password");

        when(playerRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(playerRepository.findByUserName(anyString())).thenReturn(Optional.empty());
        when(playerRepository.save(any())).thenReturn(player);

        // Act
        String token = playerService.savePlayer(player, "from");

        // Assert
        assertNotNull(token);
        verify(playerRepository, times(1)).findByEmail(anyString());
        verify(playerRepository, times(1)).findByUserName(anyString());
        verify(playerValidator, times(1)).validatePlayer(any());
        verify(playerRepository, times(1)).save(any());
        verify(confirmationTokenService, times(1)).saveToken(any());
        verify(emailSender, times(1)).send(anyString(), anyString(), anyString());
    }

    @Test
    void searchPlayerByEmailAndPassword_ExistingPlayer_Success() throws Exception {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        Player player = new Player();
        player.setUserName("testuser");

        when(playerRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(Optional.of(player));

        // Act
        Player result = playerService.searchPlayerByEmailAndPassword(email, password);

        // Assert
        assertNotNull(result);
        assertEquals(player.getUserName(), result.getUserName());
        verify(playerRepository, times(1)).findByEmailAndPassword(anyString(), anyString());
    }

}
