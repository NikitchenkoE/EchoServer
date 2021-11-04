package webserver.Model;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Log4j2
public class ResourceReader {
    private final String pathToFile;

    public ResourceReader(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getContent() throws IOException {
        log.info(String.format("Read file by path - %s", pathToFile));
        String content = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToFile))) {
            String s;
            StringBuilder stringBuilder = new StringBuilder();
            while ((s = bufferedReader.readLine()) != null) {
                stringBuilder.append(s);
            }
            content = stringBuilder.toString();
        } catch (IOException e) {
            log.error(String.format("Cannot find file with %s path", pathToFile));
            e.printStackTrace();
        }
        return content;
    }

}
