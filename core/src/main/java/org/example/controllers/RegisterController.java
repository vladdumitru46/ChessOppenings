package org.example.controllers;

import com.example.models.player.Player;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.player.PlayerService;
import org.example.data.Data;
import org.example.exceptions.PlayerValidationException;
import org.example.requests.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/chess/register")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class RegisterController {

    private final PlayerService playerService;
    private final Data data;

    @PostMapping()
    public ResponseEntity<?> registerPlayer(@RequestBody RegisterRequest registerRequest) {
        Player player = new Player(registerRequest.name(), registerRequest.userName(), registerRequest.email(), registerRequest.password());
        try {
            String token = playerService.savePlayer(player, data.getEmailFrom());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (MessagingException | PlayerValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/confirmToken")
    public String confirm(@RequestParam("token") String token) {
        try {
            return playerService.confirmToken(token);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
