package ioEchoServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);
        Socket socket = serverSocket.accept();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        while (socket.isConnected()) {
            byte[] buffer = new byte[8192];
            int count = bufferedInputStream.read(buffer);
            String receivedMessage = new String(buffer, 0, count);
            String messageToSendBack = "Echo: " + receivedMessage;

            bufferedOutputStream.write(messageToSendBack.getBytes());
            bufferedOutputStream.flush();
        }
        bufferedInputStream.close();
        bufferedOutputStream.close();
        socket.close();
        serverSocket.close();
    }
}
