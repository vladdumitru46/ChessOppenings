package org.example.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.PlayerNotFoundException;
import org.example.exceptions.PlayerValidationException;
import org.example.player.PlayerService;
import org.example.requests.LogInRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chess/login")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class LogInController {

    private final PlayerService playerService;
    //TODO: take player by JWT instead of after emailOrUsername

    @PostMapping
    private ResponseEntity<?> logIn(@RequestBody LogInRequest logInRequest) {
        try {
            String token = playerService.logIn(logInRequest.emailOrUsername(), logInRequest.password());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (PlayerValidationException | PlayerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
