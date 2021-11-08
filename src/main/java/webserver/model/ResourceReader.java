package webserver.model;

import lombok.extern.log4j.Log4j2;
import webserver.entities.Request;
import webserver.exceptions.ServerException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringJoiner;

@Log4j2
public class ResourceReader {
    private final Request request;
    private final BufferedWriter bufferedWriter;

    public ResourceReader(Request request, BufferedWriter bufferedWriter) {
        this.request = request;
        this.bufferedWriter = bufferedWriter;
    }

    public String getContent() {
        log.info("Read file by path - {}", request.getResponsePath());
        String path = request.getResponsePath();
        String content;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String s;
            StringJoiner stringJoiner = new StringJoiner("\n");
            while ((s = bufferedReader.readLine()) != null) {
                stringJoiner.add(s);
            }
            content = stringJoiner.toString();
        } catch (Exception e) {
            log.error("Cannot find file with {} path", request.getResponsePath());
            String message = String.format("Exception in getContent() in ResourceReader.class caused by %s", e);
            throw new ServerException(message, e, bufferedWriter);
        }
        return content;
    }

}
