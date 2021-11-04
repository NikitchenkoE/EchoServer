package webserver.Model;

import webserver.Entities.HttpMethods;
import webserver.Entities.Request;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RequestAnalyzer {
    private final String webPath;
    private final String fileName;
    private final BufferedReader bufferedReader;


    public RequestAnalyzer(String webPath, String fileName, BufferedReader bufferedReader) {
        this.webPath = webPath;
        this.fileName = fileName;
        this.bufferedReader = bufferedReader;
    }

    public String getPath() {
        String pathToFile = null;
        Request request = getRequest();
        String pathPart = request.getUri();
        if (pathPart.contains(webPath)) {
            if (new File(pathPart + fileName).exists()) {
                pathToFile = pathPart.concat(fileName);
            } else if (new File(pathPart).exists()) {
                pathToFile = pathPart;
            }
        }
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

}
