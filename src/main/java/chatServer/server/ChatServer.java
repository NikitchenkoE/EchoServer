package chatServer.server;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

@Log4j2
public class ChatServer {

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            log.info("Server started");
            ArrayList<ClientSocket> clientSockets = new ArrayList<>();
            while (true) {
                Socket socket = serverSocket.accept();
                log.info("Client connected");
                ClientSocket clientSocket = new ClientSocket(socket);
                clientSockets.add(clientSocket);
                Handler handler = new Handler(clientSockets);
                handler.start();
            }
        }
    }
}
