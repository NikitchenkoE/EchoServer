package chatServer.server;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

@Log4j2
public class ChatServer {

    public static void main(String[] args) throws IOException {
        CopyOnWriteArrayList<HandledClient> handledClients = new CopyOnWriteArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            log.info("Server started");
            Handler handler = new Handler(handledClients);
            Thread thread = new Thread(handler);
            thread.start();

            while (true) {
                Socket socket = serverSocket.accept();
                log.info("Client connected");
                HandledClient clientHandler = new HandledClient(socket);
                handledClients.add(clientHandler);
            }
        }finally {
           handledClients.forEach(HandledClient::disconnectClient);
        }
    }
}
