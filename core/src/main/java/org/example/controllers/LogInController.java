package org.example.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @PostMapping
    private ResponseEntity<?> logIn(@RequestBody LogInRequest logInRequest) {

        try {
//            Player player = playerService.searchPlayerByEmailAndPassword(logInRequest.getEmail(), logInRequest.getPassword());
            playerService.logIn(logInRequest.email(), logInRequest.password());
            return new ResponseEntity<>(generateToken(logInRequest.email()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
    }
}
