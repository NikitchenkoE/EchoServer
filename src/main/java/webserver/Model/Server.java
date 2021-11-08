package webserver.Model;


import lombok.extern.log4j.Log4j2;
import webserver.Handler.RequestHandler;
import webserver.constans.Constants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Log4j2
public class Server {
    private int port = Constants.DEFAULT_PORT;
    private String webAppPath;
    private String fileName;
    private String errorPagePath = Constants.DEFAULT_ERROR_PAGE;

    public Server() {
    }

    public Server(int port, String fileName, String webAppPath) {
        this.port = port;
        this.fileName = fileName;
        this.webAppPath = webAppPath;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Server started");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread thread = new Thread(() -> {
                    RequestHandler requestHandler = new RequestHandler(fileName, webAppPath, clientSocket, errorPagePath);
                    requestHandler.handle();
                });
                thread.start();
                thread.interrupt();
            }
        } catch (IOException e) {
            String message = String.format("Exception in start() method caused by %s", e);
            throw new RuntimeException(message,e);
        }
    }

    public String getWebAppPath() {
        return webAppPath;
    }

    public void setWebAppPath(String webAppPath) {
        this.webAppPath = webAppPath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setErrorPageName(String errorPageName) {
        String errorPagePathByClient = webAppPath.concat(errorPageName);
        File errorPageFile = new File(errorPagePathByClient);
        if (errorPageFile.exists()){
            this.errorPagePath = errorPagePathByClient;
        }
    }
}

