package chatServer.clients;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client2 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader bufferedConsoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();

            String stringByConsole = "";
            while (!(stringByConsole = bufferedConsoleReader.readLine()).isEmpty()) {
                bufferedWriter.write(stringByConsole + "\n");
                bufferedWriter.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
