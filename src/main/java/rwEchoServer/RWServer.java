package rwEchoServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RWServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        while (socket.isConnected()) {
            char[] buffer = new char[8192];
            int count = bufferedReader.read(buffer);
            String stringByClient = new String(buffer, 0, count);

            bufferedWriter.write("Echo: " + stringByClient);
            bufferedWriter.flush();
        }
    }
}
