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
    private final String errorPagePath;
    private final boolean status;


    public ResponseWriter(BufferedWriter bufferedWriter, String content, String errorPagePath, boolean status) {
        this.bufferedWriter = bufferedWriter;
        this.content = content;
        this.errorPagePath = errorPagePath;
        this.status = status;
    }

    public void response() {
        try {
            if (status) {
                log.info(String.format("Status - %s", HTTP_STATUS_200));
                bufferedWriter.write(HTTP_STATUS_200);
                bufferedWriter.newLine();
                bufferedWriter.newLine();
                bufferedWriter.write(content);
            } else {
                badRequest();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void badRequest() {
        log.info(String.format("Status - %s", HTTP_STATUS_404));
        ResourceReader resourceReader = new ResourceReader(errorPagePath);
        try {
            bufferedWriter.write(HTTP_STATUS_404);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write(resourceReader.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
