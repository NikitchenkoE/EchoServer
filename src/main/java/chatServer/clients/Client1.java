package chatServer.clients;

import java.io.*;
import java.net.Socket;

public class Client1 {
    private final String name;

    public Client1(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        boolean connected = true;
        Client1 client1 = new Client1("Client1");
        try (Socket socket = new Socket("localhost", 8080);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader bufferedConsoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();

            String stringByConsole;
            while (connected) {
                stringByConsole = bufferedConsoleReader.readLine();
                bufferedWriter.write(client1.name + ": " + stringByConsole);
                bufferedWriter.write("\n\r");
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
