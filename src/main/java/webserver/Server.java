package webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int DEFAULT_PORT = 8080;
    private int port = DEFAULT_PORT;
    private String webAppPath;
    private String fileName;

    public Server() {
    }

    public Server(int port, String fileName, String webAppPath) {
        this.port = port;
        this.fileName = fileName;
        this.webAppPath = webAppPath;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                RequestHandler requestHandler = new RequestHandler(fileName,webAppPath,serverSocket.accept());
                new Thread(() -> {
                    try {
                        requestHandler.handle();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
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


}

