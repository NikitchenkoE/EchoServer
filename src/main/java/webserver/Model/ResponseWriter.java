package webserver.Model;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseWriter {
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
                bufferedWriter.write("HTTP/1.1 200 OK");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
                bufferedWriter.write(content);
                bufferedWriter.flush();
            } else {
                badRequest();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void badRequest() {
        ResourceReader resourceReader = new ResourceReader(errorPagePath);
        try {
            bufferedWriter.write("HTTP/1.1 404 Not Found");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write(resourceReader.getContent());
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
