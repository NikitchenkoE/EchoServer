package webserver.Model;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedWriter;
import java.io.IOException;

@Log4j2
public class ResponseWriter {
    private final String HTTP_STATUS_200 = "HTTP/1.1 200 OK";
    private final String HTTP_STATUS_404 = "HTTP/1.1 404 Not Found";
    private final BufferedWriter bufferedWriter;
    private final String content;
    private final String responseStatus;


    public ResponseWriter(BufferedWriter bufferedWriter, String content, String responseStatus) {
        this.bufferedWriter = bufferedWriter;
        this.content = content;
        this.responseStatus = responseStatus;
    }

    public void response() {
        try {
            if (responseStatus.equals(HTTP_STATUS_200)) {
                log.info(String.format("Status - %s", HTTP_STATUS_200));
                bufferedWriter.write(HTTP_STATUS_200);
                bufferedWriter.newLine();
                bufferedWriter.newLine();
                bufferedWriter.write(content);
            } else {
                notFoundRequest();
            }
        } catch (IOException e) {
            String message = String.format("Exception in response() method caused by %s", e);
            throw new RuntimeException(message,e);
        }
    }

    private void notFoundRequest() {
        log.info(String.format("Status - %s", HTTP_STATUS_404));
        try {
            bufferedWriter.write(HTTP_STATUS_404);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write(content);
        } catch (IOException e) {
            String message = String.format("Exception in badRequest() method caused by %s", e);
            throw new RuntimeException(message,e);
        }
    }
}
