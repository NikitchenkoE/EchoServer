package chatServer.server;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.StringJoiner;
import java.util.concurrent.CopyOnWriteArrayList;

@Log4j2
public class Handler extends Thread {
    private final CopyOnWriteArrayList<ClientHandler> clientOnServer;

    public Handler(CopyOnWriteArrayList<ClientHandler> clients) {
        this.clientOnServer = clients;
    }

    @Override
    public void run() {
        try {
            while (!clientOnServer.isEmpty()) {
                String message = readMessage();
                if (!message.isEmpty()) {
                    sendMessage(message);
                }
            }
        } finally {
            disconnectAll();
        }
    }


    private String readMessage() {
        StringJoiner stringJoiner = new StringJoiner("\n\r");
        for (ClientHandler client : clientOnServer) {
            Socket clientSocket = client.getClientSocket();
            BufferedReader bufferedReader = client.getBufferedReader();
            synchronized (bufferedReader) {
                try {
                    String message;
                    if (bufferedReader.ready()) {
                        while (!(message = bufferedReader.readLine()).isEmpty()) {
                            stringJoiner.add(message);
                            if (message.contains("Disconnect")) {
                                disconnect(client, clientSocket);
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    log.info("Client {} disconnected", client);
                    throw new RuntimeException(e);
                }
            }

        }
        return stringJoiner.toString();
    }

    private void sendMessage(String message) {
        for (ClientHandler clientSocket : clientOnServer) {
            try {
                BufferedWriter bufferedWriter = clientSocket.getBufferedWriter();
                bufferedWriter.write(message);
                bufferedWriter.write("\n\r");
                bufferedWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void disconnect(ClientHandler client, Socket clientSocket) {
        log.info("Socket {} disconnected", clientSocket);
        client.disconnectClient();
        clientOnServer.remove(client);
    }

    private void disconnectAll() {
        clientOnServer.forEach(client -> disconnect(client, client.getClientSocket()));
    }


}
