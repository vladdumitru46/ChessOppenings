package org.example.protocols;

import com.example.models.courses.Player;
import org.example.IService;
import org.example.IServiceObserver;
import org.example.interfaces.Request;
import org.example.interfaces.Response;
import org.example.interfaces.UpdateResponse;
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
import java.util.concurrent.BlockingQueue;

public class ObjectProxy implements IService {
    Logger logger = LoggerFactory.getLogger(ObjectProxy.class);

    private final String host;
    private final int port;
    private IServiceObserver client;
    private IServiceObserver clientBoss;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket connection;

    private final BlockingQueue<Response> responseBlockingQueue;
    private volatile boolean finished;

    public ObjectProxy(String host, int port, BlockingQueue<Response> responseBlockingQueue) {
        this.host = host;
        this.port = port;
        this.responseBlockingQueue = responseBlockingQueue;
    }

    @Override
    public void logIn(Player player, IServiceObserver client) throws Exception {
        logger.info("start login");
        initializeConnection();
        sendRequest(new LogInRequestPlayer(player));
        Response response = readResponse();
        if (response instanceof OkResponse) {
            this.clientBoss = client;
            logger.info("player logged in!");
            return;
        }
        if (response instanceof ErrorResponse errorResponse) {
            closeConnection();
            logger.info("player couldn't be logged in {}", errorResponse.getMessage());
            throw new Exception(errorResponse.getMessage());
        }
    }

    @Override
    public void logOut(Player player, IServiceObserver client) throws Exception {
        logger.info("log out player {}", player.getUserName());
        sendRequest(new LogOutRequestPlayer(player));
        Response response = readResponse();
        closeConnection();
        logger.info("player logged out");
        if (response instanceof ErrorResponse errorResponse) {
            logger.info("player couldn't be logged out {}", errorResponse.getMessage());
            throw new Exception(errorResponse.getMessage());
        }
    }

    private void closeConnection() {
        logger.info("close connection");
        finished = true;
        try {
            inputStream.close();
            outputStream.close();
            connection.close();
            client = null;
            logger.info("connection closed!");
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("connection cannot be closed {}", e.getMessage());
        }
    }

    private Response readResponse() {
        logger.info("read response");
        Response response = null;
        try {
            response = responseBlockingQueue.take();
            logger.info("response {}", response);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("response cannot be read {}", e.getMessage());
        }
        return response;
    }

    private void sendRequest(Request request) {
        logger.info("send request");
        try {
            outputStream.writeObject(request);
            outputStream.flush();
            logger.info("request sent {}", request);
        } catch (IOException e) {
            logger.error("reguest cannot be sent {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void initializeConnection() {
        try {
            logger.info("initialize connection");
            connection = new Socket(host, port);
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
            logger.info("connection initialized");
        } catch (IOException e) {
            logger.error("connection couldn't be initialized {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void startReader() {
        logger.info("start reader");
        Thread thread = new Thread(new ReaderThread());
        thread.start();
        logger.info("reader started");
    }

    private void handleUpdate(UpdateResponse updateResponse) {

    }

    private class ReaderThread implements Runnable {

        @Override
        public void run() {
            while (!finished) {
                try {
                    Object response = inputStream.readObject();
                    if (response instanceof UpdateResponse) {
                        handleUpdate((UpdateResponse) response);
                    } else {
                        try {
                            responseBlockingQueue.put((Response) response);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
