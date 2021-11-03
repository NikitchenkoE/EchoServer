package ioEchoServer;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class IOClient {
    public static void main(String[] args) throws IOException {
        byte[] buffer = new byte[8192];

        try (Socket socket = new Socket("localhost", 3000);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
             BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream())) {

            int count;
            while ((count = System.in.read(buffer)) != 1) {
                bufferedOutputStream.write(buffer,0,count);
                bufferedOutputStream.flush();

                int countRecieved = bufferedInputStream.read(buffer);
                String receivedMessage = new String(buffer, 0, countRecieved);
                System.out.println(receivedMessage);
            }
        }
    }
}
