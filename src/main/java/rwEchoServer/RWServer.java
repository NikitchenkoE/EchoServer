package rwEchoServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RWServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080);
             Socket socket = serverSocket.accept();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            while (socket.isConnected()) {
                String stringByClient = bufferedReader.readLine();
                bufferedWriter.write("Echo: " + stringByClient + "\n");
                bufferedWriter.flush();
            }
        }
    }
}
