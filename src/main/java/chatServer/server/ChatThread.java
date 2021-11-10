package chatServer.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ChatThread extends Thread {
    private final ArrayList<ChatThread> chatThreads;
    private final BufferedWriter bufferedWriter;
    private final BufferedReader bufferedReader;

    public ChatThread(ArrayList<ChatThread> chatThreads, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        this.chatThreads = chatThreads;
        this.bufferedWriter = bufferedWriter;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run() {
        try {
            String stringByConsole;
            while (!(stringByConsole = bufferedReader.readLine()).isEmpty()) {
                writeToChatters(stringByConsole);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToChatters(String message) throws IOException {
        for (ChatThread chatThread : chatThreads) {
            chatThread.bufferedWriter.write(message.concat("\n"));
            chatThread.bufferedWriter.flush();
        }
    }
}




