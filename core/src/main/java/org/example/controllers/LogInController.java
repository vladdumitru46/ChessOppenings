package org.example.controllers;

import com.example.models.courses.Player;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.IService;
import org.example.IServiceObserver;
import org.example.MainApplication;
import org.example.PlayerService;
import org.example.protocols.ObjectProxy;
import org.example.requests.LogInRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class LogInController implements IServiceObserver {
    private final PlayerService playerService;
    private static final int defaultChatPort = 55555;
    private static final String defaultServer = "localhost";


    @PostMapping
    private String logIn(@RequestBody LogInRequest logInRequest) {
        Properties clientProps = new Properties();
        try {
            clientProps.load(MainApplication.class.getResourceAsStream("/client.properties"));
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return "";
        }

        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);
        IService serviceProxy = new ObjectProxy(serverIP, serverPort);
        try {
            Optional<Player> player = playerService.searchPlayerByEmailAndPassword(logInRequest.getEmail(), logInRequest.getPassword());
            serviceProxy.logIn(player.get(), this);
            return "YES";
        } catch (Exception e) {
            return "NO!";
        }
    }
}
