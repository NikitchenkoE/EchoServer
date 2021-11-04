package webserver.Controller;

import lombok.extern.log4j.Log4j2;
import webserver.Model.RequestAnalyzer;
import webserver.Model.ResourceReader;
import webserver.Model.ResponseWriter;

import java.io.*;
import java.net.Socket;

@Log4j2
public class RequestHandler {
    private final String fileName;
    private final String webAppPath;
    private final String errorPagePath;
    private final Socket clientSocket;

    public RequestHandler(String fileName, String resourcesPath, Socket clientSocket, String errorPageName) {
        this.clientSocket = clientSocket;
        this.fileName = fileName;
        this.webAppPath = resourcesPath;
        this.errorPagePath = errorPageName;
    }

    public void handle() throws IOException {
        log.info("Client connected");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            RequestAnalyzer requestAnalyzer = new RequestAnalyzer(webAppPath, fileName, bufferedReader, errorPagePath);
            ResourceReader resourceReader = new ResourceReader(requestAnalyzer.getPath());
            ResponseWriter responseWriter = new ResponseWriter(bufferedWriter, resourceReader.getContent(), errorPagePath, requestAnalyzer.getStatus());

            responseWriter.response();
        }
    }
}
