package org.example.controllers;

import com.example.models.player.Player;
import com.example.models.player.PlayerSession;
import lombok.AllArgsConstructor;
import org.example.data.Data;
import org.example.exceptions.NoSessionException;
import org.example.exceptions.PlayerNotFoundException;
import org.example.player.PlayerService;
import org.example.player.PlayerSessionService;
import org.example.requests.UpdatePlayerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chess/playerInfo")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerSessionService playerSessionService;
    private final Data data;

    @GetMapping
    public ResponseEntity<?> getPlayer(@RequestParam String token) {
        try {
            PlayerSession playerSession = playerSessionService.getByToken(token);
            return new ResponseEntity<>(playerSession.getPlayer(), HttpStatus.OK);
        } catch (NoSessionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateInfo")
    public ResponseEntity<?> updatePlayer(@RequestBody UpdatePlayerRequest updatePlayerRequest) {
        try {
            PlayerSession playerSession = playerSessionService.getByToken(updatePlayerRequest.token());
            Player player = playerSession.getPlayer();
            boolean changedEmail = !player.getEmail().equals(updatePlayerRequest.email());
            player.setPassword(updatePlayerRequest.password());
            player.setName(updatePlayerRequest.name());
            if (changedEmail) {
                playerService.sendEmail(player, data.getEmailFrom());
                player.setEmail(updatePlayerRequest.email());
            }
            playerService.checkPlayer(player, changedEmail);
            playerService.updatePlayer(player);

            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
