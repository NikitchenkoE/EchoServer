package webserver.Model;

import lombok.extern.log4j.Log4j2;
import webserver.Entities.Request;
import webserver.constans.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringJoiner;

@Log4j2
public class ResourceReader {
    private final Request request;
    private final String webPath;
    private final String fileName;
    private final String errorPagePath;
    private String responseStatus;

    public ResourceReader(Request request, String webPath, String fileName, String errorPagePath) {
        this.request = request;
        this.webPath = webPath;
        this.fileName = fileName;
        this.errorPagePath = errorPagePath;
    }

    public String getContent() {
        log.info("Read file by path - {}", getPath());
        String content;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(getPath()))) {
            String s;
            StringJoiner stringJoiner = new StringJoiner("\n");
            while ((s = bufferedReader.readLine()) != null) {
                stringJoiner.add(s);
            }
            content = stringJoiner.toString();
        } catch (IOException e) {
            log.error("Cannot find file with {} path", getPath());
            String message = String.format("Exception in getContent() in ResourceReader.class caused by %s", e);
            throw new RuntimeException(message, e);
        }
        return content;
    }


    private String getPath() {
        String pathToFile = errorPagePath;
        responseStatus = Constants.HTTP_STATUS_404;

        String uri = request.getUri();
        if (uri.equals(webPath)) {

            if (new File(uri.concat(fileName)).exists()) {
                pathToFile = uri.concat(fileName);
                responseStatus = Constants.HTTP_STATUS_200;
            }

        } else if (uri.contains(webPath) && new File(uri).exists()) {
            pathToFile = uri;
            responseStatus = Constants.HTTP_STATUS_200;
        }
        log.info("Path sent to ResourceReader - {}", pathToFile);
        return pathToFile;
    }


    public String getResponseStatus() {
        return responseStatus;
    }

}
