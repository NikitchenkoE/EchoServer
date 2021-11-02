package rwEchoServer;

import java.io.*;
import java.net.Socket;

public class RWClient {
    private static String readFromConsole() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        char[] buffer = new char[8192];
        int count = bufferedReader.read(buffer);
        return new String(buffer, 0, count);
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String stringByConsole = "";

        while (!stringByConsole.equals("\n")) {
            stringByConsole = readFromConsole();
            bufferedWriter.write(stringByConsole);
            bufferedWriter.flush();

            char[] buffer = new char[8192];
            int count = bufferedReader.read(buffer);
            String stringByServer = new String(buffer, 0, count);
            System.out.println(stringByServer);
        }

        bufferedReader.close();
        bufferedWriter.close();
        socket.close();
    }
}