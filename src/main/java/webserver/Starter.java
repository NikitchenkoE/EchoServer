package webserver;

import lombok.extern.log4j.Log4j2;
import webserver.Model.Server;

@Log4j2
public class Starter {
    public static void main(String[] args) {
        log.info("Start");
        Server server = new Server();
        server.setWebAppPath("src/main/resources/webapp/");
        server.setFileName("Hellooooo.html");
        server.setErrorPageName("bat_path.html");
        server.start();
    }
}