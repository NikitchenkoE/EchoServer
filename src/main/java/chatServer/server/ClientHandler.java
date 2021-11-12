package chatServer.server;

import lombok.Data;

import java.io.*;
import java.net.Socket;

@Data
public class ClientHandler {
    private final Socket clientSocket;
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    public synchronized void disconnectClient() {
        try (clientSocket; bufferedReader; bufferedWriter) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized Socket getClientSocket() {
        return clientSocket;
    }

    public synchronized BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public synchronized BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }
}
