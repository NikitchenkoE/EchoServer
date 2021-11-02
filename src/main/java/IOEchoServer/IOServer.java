package IOEchoServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);
        Socket socket = serverSocket.accept();
        while (!socket.isClosed()) {
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[8192];
            int count = inputStream.read(buffer);
            String receivedMessage = new String(buffer, 0, count);
            String messageToSendBack = "Echo: " + receivedMessage;
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(messageToSendBack.getBytes());
        }
    }
}
