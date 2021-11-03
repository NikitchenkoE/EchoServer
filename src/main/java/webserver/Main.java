package webserver;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.setWebAppPath("/src/main/resources/webapp/");
        server.setFileName("index.html");
        server.start();
    }
}