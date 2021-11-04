package webserver;

import webserver.Model.Server;

public class Starter {
    public static void main(String[] args) {
        Server server = new Server();
        server.setWebAppPath("src/main/resources/webapp/");
        server.setFileName("Hellooooo.html");
        server.start();
    }
}