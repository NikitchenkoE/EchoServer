package chatServer.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            ArrayList<ChatThread> chatThreadArrayList = new ArrayList<>();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                ChatThread chatThread = new ChatThread(chatThreadArrayList,bufferedWriter,bufferedReader);
                chatThreadArrayList.add(chatThread);
                chatThread.start();
            }
        }
    }

}
