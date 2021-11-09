package webserver.model;

import lombok.extern.log4j.Log4j2;
import webserver.entities.Request;
import webserver.exceptions.ServerException;

import java.io.BufferedWriter;

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
            bufferedWriter.write("\r\n");
            bufferedWriter.write("\r\n");
            bufferedWriter.write(content);
        } catch (Exception cause) {
            String message = String.format("Exception in badRequest() method caused by %s", cause);
            throw new ServerException(message, cause, bufferedWriter);
        }
    }

}



