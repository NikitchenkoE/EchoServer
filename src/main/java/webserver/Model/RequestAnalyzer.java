package webserver.Model;

import lombok.extern.log4j.Log4j2;
import webserver.Entities.HttpMethods;
import webserver.Entities.Request;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
public class RequestAnalyzer {
    private final String webPath;
    private final String fileName;
    private final BufferedReader bufferedReader;
    private final String errorPagePath;
    private boolean status = false;


    public RequestAnalyzer(String webPath, String fileName, BufferedReader bufferedReader, String errorPagePath) {
        this.webPath = webPath;
        this.fileName = fileName;
        this.bufferedReader = bufferedReader;
        this.errorPagePath = errorPagePath;
    }

    public String getPath() {
        String pathToFile = errorPagePath;
        Request request = getRequest();
        String pathPart = request.getUri();
        if (pathPart.contains(webPath)) {

            if (new File(pathPart + fileName).exists()) {
                pathToFile = pathPart.concat(fileName);
                status = true;

            } else if (new File(pathPart).exists()) {
                pathToFile = pathPart;
                status = true;
            }
        }
        log.info(String.format("Path sent to ResourceReader - %s", pathToFile));
        return pathToFile;
    }

    private Request getRequest() {
        Request request = new Request();
        StringBuilder stringBuilder = new StringBuilder();
        String receivedRequest;

        try {
            while (!(receivedRequest = bufferedReader.readLine()).isEmpty()) {
                stringBuilder.append(receivedRequest).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        receivedRequest = stringBuilder.toString();
        List<String> stringsByHttpRequest = Arrays.asList(Pattern.compile("\n").split(receivedRequest));

        request.setHttpMethod(getHttpMethod(stringsByHttpRequest));
        request.setUri(getUri(stringsByHttpRequest, request));
        request.setHeaders(getHeaders(stringsByHttpRequest));
        log.info(String.format("Get URI by client - %s", request.getUri()));
        return request;
    }

    private Enum<HttpMethods> getHttpMethod(List<String> requestLines) {
        String method = requestLines.stream()
                                    .map(s -> Pattern.compile(" ").split(s))
                                    .flatMap(Arrays::stream)
                                    .findFirst()
                                    .orElse(null);

        return HttpMethods.valueOf(method);
    }

    private String getUri(List<String> requestLines, Request request) {
        String uri = requestLines.stream()
                                .filter(s -> s.contains(request.getHttpMethod().toString()))
                                .collect(Collectors.toList())
                                .stream()
                                .map(s -> Pattern.compile(" ").split(s))
                                .flatMap(Arrays::stream)
                                .filter(str -> str.contains("/"))
                                .findFirst()
                                .orElse(null);
        StringBuilder stringBuilder = new StringBuilder(Objects.requireNonNull(uri));

        stringBuilder.delete(0, 1);
        return stringBuilder.toString();
    }

    private Map<String, String> getHeaders(List<String> requestLines) {
        Map<String, String> headers = new HashMap<>();
        requestLines.stream()
                    .filter(s -> s.contains(":"))
                    .collect(Collectors.toList())
                    .forEach(s -> {
                        String[] partsOfHeader = Pattern.compile(":").split(s);
                        headers.put(partsOfHeader[0], partsOfHeader[1]);
                    });

        return headers;
    }

    public boolean getStatus() {
        return status;
    }
}
