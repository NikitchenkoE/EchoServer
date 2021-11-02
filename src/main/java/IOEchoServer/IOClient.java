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
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());


        String receivedMessage = "";
        while (!receivedMessage.equals("Echo: \n")) {
            String message = getMessageFromConsole();
            bufferedOutputStream.write(message.getBytes(StandardCharsets.UTF_8));
            bufferedOutputStream.flush();

            byte[] buffer = new byte[8192];
            int count = bufferedInputStream.read(buffer);
            receivedMessage = new String(buffer, 0, count);
            System.out.println(receivedMessage);
        }
        bufferedOutputStream.close();
        bufferedInputStream.close();
    }
}
