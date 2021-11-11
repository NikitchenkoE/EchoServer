package chatServer.server;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringJoiner;

@Log4j2
public class Handler extends Thread {
    private final ArrayList<ClientInfo> clients;

    public Handler(ArrayList<ClientInfo> clientSockets) {
        this.clients = clientSockets;
    }

    @Override
    public void run() {
        try {
            while (true) {
                while (!clients.isEmpty()) {
                    String message = readMessage();
                    if (!message.isEmpty()) {
                        sendMessage(message);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Client Disconnected",e);
        }
    }


    private String readMessage() throws IOException {
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (int i = 0; i < clients.size(); i++) {
            Socket clientSocket = clients.get(i).getClientSocket();
            BufferedReader bufferedReader = clients.get(i).getBufferedReader();
            synchronized (bufferedReader) {
                if (bufferedReader.ready()) {
                    String message =  bufferedReader.readLine();
                    stringJoiner.add(message);
                    if (message.contains("Disconnect")) {
                        log.info("Socket {} disconnected", clientSocket);
                        disconnect(clients.get(i));
                    }
                }
            }
        }
        return stringJoiner.toString();
    }

    private void sendMessage(String message) throws IOException {
        for (ClientInfo clientSocket : clients) {
            BufferedWriter bufferedWriter = clientSocket.getBufferedWriter();
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    private void disconnect(ClientInfo clientSocket) {
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
        clients.remove(clientSocket);
    }
}
