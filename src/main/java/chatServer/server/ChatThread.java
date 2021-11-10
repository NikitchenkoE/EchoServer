package chatServer.server;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

@Log4j2
public class ChatThread extends Thread {
    private final Socket clientSocket;
    private final ArrayList<ChatThread> chatThreads;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public ChatThread(ArrayList<ChatThread> chatThreads, Socket clientSocket) {
        this.chatThreads = chatThreads;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String stringByConsole;
            while (!(stringByConsole = bufferedReader.readLine()).isEmpty()) {
                writeToChatters(stringByConsole);
            }
        } catch (IOException e) {
            log.info("User exit chat");
        } finally {
            closeWriterAndReader();
        }
    }

    private void writeToChatters(String message) throws IOException {
        for (ChatThread chatThread : chatThreads) {
            chatThread.bufferedWriter.write(message.concat("\n"));
            chatThread.bufferedWriter.flush();
        }
    }

    private void closeWriterAndReader() {
        try {
            bufferedWriter.close();
            bufferedReader.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




