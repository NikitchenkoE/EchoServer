package chatServer.server;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.StringJoiner;
import java.util.concurrent.CopyOnWriteArrayList;

@Log4j2
public class Handler implements Runnable {
    private final CopyOnWriteArrayList<HandledClient> clientsOnServer;

    public Handler(CopyOnWriteArrayList<HandledClient> clients) {
        this.clientsOnServer = clients;
    }

    @Override
    public void run() {
        while (true) {
            String message = readMessage();
            if (!message.isEmpty()) {
                sendMessage(message);
            }
        }
    }

    private String readMessage() {
        StringJoiner stringJoiner = new StringJoiner("\n\r");
        for (HandledClient client : clientsOnServer) {
            Socket clientSocket = client.getClientSocket();
            BufferedReader bufferedReader = client.getBufferedReader();
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
                log.error("Client {} disconnected", client);
                throw new RuntimeException(e);
            }
        }
        return stringJoiner.toString();
    }

    private void sendMessage(String message) {
        for (HandledClient clientSocket : clientsOnServer) {
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

    private void disconnect(HandledClient client, Socket clientSocket) {
        log.info("Socket {} disconnected", clientSocket);
        client.disconnectClient();
        clientsOnServer.remove(client);
    }
}
