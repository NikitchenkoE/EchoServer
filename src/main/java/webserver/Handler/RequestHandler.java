package webserver.Handler;

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

    public void handle() {
        log.info("Client connected");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            RequestAnalyzer requestAnalyzer = new RequestAnalyzer(bufferedReader);
            ResourceReader resourceReader = new ResourceReader(requestAnalyzer.getRequest(), webAppPath, fileName, errorPagePath);
            ResponseWriter responseWriter = new ResponseWriter(bufferedWriter, resourceReader.getContent(), resourceReader.getStatus());

            responseWriter.response();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
