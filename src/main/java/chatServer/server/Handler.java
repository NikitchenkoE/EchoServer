package chatServer.server;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

@Log4j2
public class Handler extends Thread {
    private final ArrayList<ClientSocket> clientSockets;

    public Handler(ArrayList<ClientSocket> clientSockets) {
        this.clientSockets = clientSockets;
    }

    @Override
    public void run() {
        try {
            while (true) {
                while (!clientSockets.isEmpty()) {
                    String message = readMessage();
                    if (!message.isEmpty()) {
                        sendMessage(message);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String readMessage() throws IOException {
        String s = "";
        for (ClientSocket clientSocket : clientSockets) {
            BufferedReader bufferedReader = clientSocket.getBufferedReader();
            synchronized (bufferedReader) {
                if (bufferedReader.ready()) {
                    s = bufferedReader.readLine();
                    if (s.contains("Disconnect")) {
                        log.info("Socket {} disconnected", clientSocket.getClientSocket());
                        disconnect(clientSocket);
                    }
                }
            }
        }
        return s;
    }

    private void sendMessage(String message) throws IOException {
        for (ClientSocket clientSocket : clientSockets) {
            BufferedWriter bufferedWriter = clientSocket.getBufferedWriter();
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    private void disconnect(ClientSocket clientSocket) {
        try {
            clientSocket.getBufferedWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clientSocket.getBufferedReader().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clientSocket.getClientSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientSockets.remove(clientSocket);
    }
}
