package webserver.Model;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseWriter {
    private final BufferedWriter bufferedWriter;
    private final String content;
    private final String BAD_PATH = "src/main/resources/bat_path.html";

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
                badRequest();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void badRequest(){
        ResourceReader resourceReader = new ResourceReader(BAD_PATH);
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
