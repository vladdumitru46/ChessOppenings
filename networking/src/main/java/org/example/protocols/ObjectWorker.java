package org.example.protocols;

import com.example.models.courses.Player;
import org.example.IService;
import org.example.IServiceObserver;
import org.example.interfaces.Request;
import org.example.interfaces.Response;
import org.example.requestClasses.LogInRequestPlayer;
import org.example.requestClasses.LogOutRequestPlayer;
import org.example.responseClasses.ErrorResponse;
import org.example.responseClasses.OkResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectWorker implements Runnable, IServiceObserver {
    Logger logger = LoggerFactory.getLogger(ObjectWorker.class);

    private final IService server;
    private final Socket connection;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private volatile boolean connected;

    public ObjectWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (connected) {
            try {
                logger.info("read object");
                Object request = inputStream.readObject();
                Object response = handleRequest((Request) request);
                if (response != null) {
                    logger.info("send response");
                    sendResponse((Response) response);
                }
            } catch (IOException | ClassNotFoundException e) {
                connected = false;
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
            outputStream.close();
            connection.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendResponse(Response response) throws IOException {
        logger.info("send response");
        synchronized (outputStream) {
            outputStream.writeObject(response);
            outputStream.flush();
        }
        logger.info("response sent {}", response);
    }

    private Object handleRequest(Request request) {
        logger.info("handle requests");
        Response response = null;
        if (request instanceof LogInRequestPlayer logInRequestPlayer) {
            Player player = logInRequestPlayer.getPlayer();
            logger.info("log in request for player {}", player.getUserName());
            try {
                logger.info("logIn...");
                server.logIn(player, this);
                logger.info("player logged in!");
                return new OkResponse();
            } catch (Exception e) {
                connected = false;
                logger.error("player couldn't be logged in {}", e.getMessage());
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof LogOutRequestPlayer logOutRequestPlayer) {
            Player player = logOutRequestPlayer.getPlayer();
            logger.info("log out request for player {}", player.getUserName());
            try {
                server.logOut(player, this);
                connected = false;
                logger.info("player logged out!");
                return new OkResponse();
            } catch (Exception e) {
                logger.error("player couldn't be logged out {}", e.getMessage());
                return new ErrorResponse(e.getMessage());
            }
        }
        return response;
    }
}
