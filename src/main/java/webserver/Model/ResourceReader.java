package webserver.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ResourceReader {
    private final String pathToFile;

    public ResourceReader(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getContent() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToFile))) {
            String s;
            StringBuilder stringBuilder = new StringBuilder();
            while ((s = bufferedReader.readLine()) != null) {
                stringBuilder.append(s);
            }
            return stringBuilder.toString();
        }
    }

}
