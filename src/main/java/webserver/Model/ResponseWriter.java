package webserver.Model;

import lombok.extern.log4j.Log4j2;
import webserver.constans.Constants;

import java.io.BufferedWriter;
import java.io.IOException;

@Log4j2
public class ResponseWriter {
    private final BufferedWriter bufferedWriter;
    private final String content;
    private final String responseStatus;


    public ResponseWriter(BufferedWriter bufferedWriter, String content, String responseStatus) {
        this.bufferedWriter = bufferedWriter;
        this.content = content;
        this.responseStatus = responseStatus;
    }

    public void response() {
        switch (responseStatus) {
            case (Constants.HTTP_STATUS_200) -> oKResponse();
            case (Constants.HTTP_STATUS_404) -> notFoundResponse();
        }
    }

    private void oKResponse(){
        log.info(String.format("Status - %s",Constants.HTTP_STATUS_200));
        try {
            bufferedWriter.write(Constants.HTTP_STATUS_200);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write(content);
        }catch (IOException e) {
            String message = String.format("Exception in badRequest() method caused by %s", e);
            throw new RuntimeException(message,e);
        }
    }

    private void notFoundResponse() {
        log.info(String.format("Status - %s",Constants.HTTP_STATUS_404));
        try {
            bufferedWriter.write(Constants.HTTP_STATUS_404);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write(content);
        } catch (IOException e) {
            String message = String.format("Exception in notFoundRequest() method caused by %s", e);
            throw new RuntimeException(message,e);
        }
    }
}
