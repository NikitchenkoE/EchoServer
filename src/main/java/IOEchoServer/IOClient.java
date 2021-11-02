package IOEchoServer;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class IOClient {
    private static String getMessageFromConsole() throws IOException {
        InputStream fromConsole = System.in;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fromConsole);
        byte[] bufferFromConsole = new byte[8192];
        int countFromConsole = bufferedInputStream.read(bufferFromConsole);
        return new String(bufferFromConsole, 0, countFromConsole);
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 3000);
        String receivedMessage = "";
        while (!receivedMessage.equals("Echo: \n")) {
            OutputStream outputStream = socket.getOutputStream();
            String message = getMessageFromConsole();
            outputStream.write(message.getBytes(StandardCharsets.UTF_8));

            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[8192];
            int count = inputStream.read(buffer);
            receivedMessage = new String(buffer, 0, count);

            System.out.println(receivedMessage);
        }
    }
}
