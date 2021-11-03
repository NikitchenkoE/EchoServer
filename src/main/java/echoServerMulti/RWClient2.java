package echoServerMulti;

import java.io.*;
import java.net.Socket;

public class RWClient2 {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8080);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader bufferedConsoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            String stringByConsole = "";
            while (!(stringByConsole = bufferedConsoleReader.readLine()).isEmpty()) {
                bufferedWriter.write(stringByConsole + "\n");
                bufferedWriter.flush();
                String responseMessage = bufferedReader.readLine();
                System.out.println(responseMessage);
            }
        }
    }
}