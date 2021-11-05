package webserver.Model;


import lombok.extern.log4j.Log4j2;
import webserver.Handler.RequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Log4j2
public class Server {
    private static final String DEFAULT_ERROR_PAGE = "src/main/resources/webapp/bat_path.html";
    private static final int DEFAULT_PORT = 8080;
    private int port = DEFAULT_PORT;
    private String webAppPath;
    private String fileName;
    private String errorPagePath = DEFAULT_ERROR_PAGE;

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
            e.printStackTrace();
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

    public void setErrorPagePath(String errorPagePath) {
        this.errorPagePath = errorPagePath;
    }
}

