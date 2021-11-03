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

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        addHandler(clientSocket,webAppPath,fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void response(BufferedWriter bufferedWriter, String response) throws IOException {
        try {
            if (response!=null) {
                bufferedWriter.write("HTTP/1.1 200 OK");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
                bufferedWriter.write(response);
                bufferedWriter.flush();
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

    public static void addHandler(Socket socket,String webAppPath, String fileName) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            RequestAnalyzer requestAnalyzer = new RequestAnalyzer(webAppPath, fileName, bufferedReader);
            response(bufferedWriter, requestAnalyzer.readFile());
        }
    }

}

