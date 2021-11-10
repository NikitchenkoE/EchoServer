package chatServer.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ChatThread extends Thread {
    private final ArrayList<ChatThread> chatThreads;
    private PrintWriter printWriter;
    private final Socket socket;

    public ChatThread(Socket socket, ArrayList<ChatThread> chatThreads) {
        this.socket = socket;
        this.chatThreads = chatThreads;
    }

    @Override
    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            printWriter = new PrintWriter(socket.getOutputStream(),true);
            String stringByConsole;
            while (!(stringByConsole = bufferedReader.readLine()).isEmpty()) {
                writeToChatters(stringByConsole);
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToChatters(String message) throws IOException {
        for (ChatThread chatThread : chatThreads) {
            chatThread.printWriter.println(message);
        }
    }
}




