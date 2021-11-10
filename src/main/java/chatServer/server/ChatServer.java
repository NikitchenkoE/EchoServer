package chatServer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            ArrayList<ChatThread> chatThreadArrayList = new ArrayList<>();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ChatThread chatThread = new ChatThread(clientSocket, chatThreadArrayList);
                chatThreadArrayList.add(chatThread);
                chatThread.start();
            }
        }
    }

}
