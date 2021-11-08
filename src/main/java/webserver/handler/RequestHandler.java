package webserver.handler;

import lombok.extern.log4j.Log4j2;
import webserver.entities.Request;
import webserver.exceptions.ServerException;
import webserver.model.RequestAnalyzer;
import webserver.model.ResourceReader;
import webserver.model.ResponseWriter;

import java.io.*;
import java.net.Socket;

@Log4j2
public class RequestHandler {
    private final String fileName;
    private final String webAppPath;
    private final String errorPagePath;
    private final Socket clientSocket;


    public RequestHandler(String fileName, String resourcesPath, Socket clientSocket, String errorPagePath) {
        this.clientSocket = clientSocket;
        this.fileName = fileName;
        this.webAppPath = resourcesPath;
        this.errorPagePath = errorPagePath;
    }

    public void handle() {
        log.info("Client connected");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            RequestAnalyzer requestAnalyzer = new RequestAnalyzer(bufferedReader, webAppPath, fileName, errorPagePath, bufferedWriter);
            Request request = requestAnalyzer.getRequest();
            ResourceReader resourceReader = new ResourceReader(request, bufferedWriter);
            ResponseWriter responseWriter = new ResponseWriter(bufferedWriter, resourceReader.getContent(), request);
            responseWriter.response();
        } catch (Exception cause) {
            String message = String.format("Exception in handle() method in Request handle caused by %s", cause);
            throw new ServerException(message, cause, clientSocket);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
