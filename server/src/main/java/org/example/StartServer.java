package org.example;

import org.example.utils.AbstractServer;
import org.example.utils.ObjectConcurrentServer;
import org.example.utils.ServerException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class StartServer {
    private static final int defaultPort = 55555;
    private static PlayerService playerService;

    public static void main(String[] args) {
        Properties serverProperties = new Properties();
        try {
            serverProperties.load(StartServer.class.getResourceAsStream("/server.properties"));
            System.out.println("server properties set");
            serverProperties.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find properties " + e);
            return;
        }

        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(serverProperties.getProperty("server.port"));
        } catch (NumberFormatException e) {
            System.err.println("Wrong port number" + e.getMessage());
        }
        System.out.println("Starting server on port: " + serverPort);
        IService service = playerService;
        System.out.println(service);
        AbstractServer server = new ObjectConcurrentServer(serverPort, service);
        try {
            server.start();
        } catch (ServerException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}