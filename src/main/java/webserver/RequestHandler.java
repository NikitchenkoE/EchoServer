package webserver;

import java.io.*;
import java.net.Socket;

public class RequestHandler {
    private final String fileName;
    private final String webAppPath;
    private Socket clientSocket;

    public RequestHandler(String fileName, String resourcesPath, Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.fileName = fileName;
        this.webAppPath = resourcesPath;
    }

    public void handle() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            RequestAnalyzer requestAnalyzer = new RequestAnalyzer(webAppPath, fileName, bufferedReader);
            response(bufferedWriter, requestAnalyzer.readFile());
            clientSocket.close();
        }
    }

    private void response(BufferedWriter bufferedWriter, String fileText) {
        try {
            if (fileText != null) {
                bufferedWriter.write("HTTP/1.1 200 OK");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
                bufferedWriter.write(fileText);
                bufferedWriter.flush();
            } else {
                bufferedWriter.write("HTTP/1.1 404 Not Found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
