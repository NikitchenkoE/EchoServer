package webserver.Controller;

import webserver.Model.RequestAnalyzer;
import webserver.Model.ResourceReader;
import webserver.Model.ResponseWriter;

import java.io.*;
import java.net.Socket;

public class RequestHandler {
    private final String fileName;
    private final String webAppPath;
    private final String errorPagePath;
    private Socket clientSocket;

    public RequestHandler(String fileName, String resourcesPath, Socket clientSocket, String errorPageName) {
        this.clientSocket = clientSocket;
        this.fileName = fileName;
        this.webAppPath = resourcesPath;
        this.errorPagePath = errorPageName;
    }

    public void handle() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            RequestAnalyzer requestAnalyzer = new RequestAnalyzer(webAppPath, fileName, bufferedReader, errorPagePath);
            ResourceReader resourceReader = new ResourceReader(requestAnalyzer.getPath());
            ResponseWriter responseWriter = new ResponseWriter(bufferedWriter, resourceReader.getContent(), errorPagePath, requestAnalyzer.getStatus());

            responseWriter.response();
        }
    }
}
