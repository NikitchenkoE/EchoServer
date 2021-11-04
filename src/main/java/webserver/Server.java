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
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        addHandler(clientSocket, webAppPath, fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHandler(Socket clientSocket, String webAppPath, String fileName) throws IOException {
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

