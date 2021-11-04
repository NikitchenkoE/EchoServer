package webserver.Model;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseWriter {
    private final BufferedWriter bufferedWriter;
    private final String content;

    public ResponseWriter(BufferedWriter bufferedWriter, String content) {
        this.bufferedWriter = bufferedWriter;
        this.content = content;
    }

    public void response() {
        try {
            if (content != null) {
                bufferedWriter.write("HTTP/1.1 200 OK");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
                bufferedWriter.write(content);
                bufferedWriter.flush();
            } else {
                bufferedWriter.write("HTTP/1.1 404 Not Found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
