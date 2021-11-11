package chatServer.server;

import lombok.Data;

import java.io.*;
import java.net.Socket;

@Data
public class ClientInfo {
    private final Socket clientSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public ClientInfo(Socket clientSocket) {
        this.clientSocket = clientSocket;
        setBufferReaderAndBufferWriter();
    }

    private void setBufferReaderAndBufferWriter() {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "ClientSocket{" +
                "clientSocket=" + clientSocket +
                ", bufferedReader=" + bufferedReader +
                ", bufferedWriter=" + bufferedWriter +
                '}';
    }
}
