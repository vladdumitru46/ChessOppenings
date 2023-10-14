package org.example.controllers;

import com.example.models.courses.Player;
import org.example.*;
import org.example.protocols.ObjectProxy;
import org.example.requests.LogInRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:63343")
public class LogInController implements IServiceObserver {
    private final CourseService courseService;
    private final PieceService pieceService;
    private static final int defaultChatPort = 55555;
    private static final String defaultServer = "localhost";

    public LogInController(CourseService courseService, PieceService pieceService) {
        this.courseService = courseService;
        this.pieceService = pieceService;
    }

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
        MainService mainService = new MainService(courseService, pieceService);
        IService serviceProxy = new ObjectProxy(serverIP, serverPort);
        try {
            Optional<Player> player = mainService.searchPlayerByEmailAndPassword(logInRequest.getEmail(), logInRequest.getPassword());
            serviceProxy.logIn(player.get(), this);
            return "YES";
        } catch (Exception e) {
            return "NO!";
        }
    }
}
