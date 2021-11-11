package chatServer.clients;

import java.io.*;
import java.net.Socket;

public class Client2 {
    private final String name;

    public Client2(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        boolean connected = true;
        Client2 client2 = new Client2("Client2");
        try (Socket socket = new Socket("localhost", 8080);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader bufferedConsoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();

            String stringByConsole;
            while (connected) {
                stringByConsole = bufferedConsoleReader.readLine();
                bufferedWriter.write(client2.name + ": " + stringByConsole);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                if (stringByConsole.equals("Disconnect")) {
                    connected = false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
