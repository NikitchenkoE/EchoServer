package webserver.Controller;

import webserver.Model.RequestAnalyzer;
import webserver.Model.ResponseWriter;

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
            ResponseWriter responseWriter = new ResponseWriter(bufferedWriter,requestAnalyzer.readFile());

            responseWriter.response();
        }
    }
}
