package webserver.Model;

import lombok.extern.log4j.Log4j2;
import webserver.Entities.Request;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Log4j2
public class ResourceReader {
    private final Request request;
    private final String webPath;
    private final String fileName;
    private final String errorPagePath;
    private boolean status = false;

    public ResourceReader(Request request, String webPath, String fileName, String errorPagePath) {
        this.request = request;
        this.webPath = webPath;
        this.fileName = fileName;
        this.errorPagePath = errorPagePath;
    }

    public String getContent() throws IOException {
        log.info(String.format("Read file by path - %s", getPath()));
        String content = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(getPath()))) {
            String s;
            StringBuilder stringBuilder = new StringBuilder();
            while ((s = bufferedReader.readLine()) != null) {
                stringBuilder.append(s).append("\n");
            }
            content = stringBuilder.toString();
        } catch (IOException e) {
            log.error(String.format("Cannot find file with %s path", getPath()));
            e.printStackTrace();
        }
        return content;
    }


    private String getPath() {
        String pathToFile = "";
        String uri = request.getUri();

        if (uri.equals(webPath)) {
            pathToFile = uri.concat(fileName);
            status = true;
        } else if (uri.contains(webPath) && new File(uri).exists()) {
            pathToFile = uri;
            status = true;
        } else {
            pathToFile = errorPagePath;
        }
        log.info(String.format("Path sent to ResourceReader - %s", pathToFile));
        return pathToFile;
    }

    public boolean getStatus() {
        return status;
    }

}
