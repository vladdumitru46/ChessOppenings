package org.example.controllers;

import com.example.models.player.Player;
import lombok.AllArgsConstructor;
import org.example.data.Data;
import org.example.exceptions.PlayerNotFoundException;
import org.example.player.PlayerService;
import org.example.requests.UpdatePlayerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chess/playerInfo")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class PlayerController {

    private PlayerService playerService;
    private final Data data;

    @GetMapping
    public ResponseEntity<?> getPlayer(@RequestParam String userNameOrEmail) {
        try {
            Player player = playerService.searchPlayerByUsernameOrEmail(userNameOrEmail);
            return new ResponseEntity<>(player, HttpStatus.OK);
        } catch (PlayerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateInfo")
    public ResponseEntity<?> updatePlayer(@RequestBody UpdatePlayerRequest updatePlayerRequest) {
        try {
            Player player = playerService.searchPlayerByUsernameOrEmail(updatePlayerRequest.userName());
            boolean changedEmail = !player.getEmail().equals(updatePlayerRequest.email());
            player.setPassword(updatePlayerRequest.password());
            player.setName(updatePlayerRequest.name());
            if(changedEmail){
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
