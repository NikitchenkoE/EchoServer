package webserver.Model;

import lombok.extern.log4j.Log4j2;
import webserver.Entities.Request;

import java.io.BufferedWriter;
import java.io.IOException;

@Log4j2
public class ResponseWriter {
    private final BufferedWriter bufferedWriter;
    private final String content;
    private final Request request;

    public ResponseWriter(BufferedWriter bufferedWriter, String content, Request request) {
        this.bufferedWriter = bufferedWriter;
        this.content = content;
        this.request = request;
    }

    public void response() {
        log.info("Status - {}", request.getResponseStatus());
        try {
            bufferedWriter.write(request.getResponseStatus().getResponse());
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write(content);
        } catch (IOException e) {
            String message = String.format("Exception in badRequest() method caused by %s", e);
            throw new RuntimeException(message, e);
        }
    }

}



