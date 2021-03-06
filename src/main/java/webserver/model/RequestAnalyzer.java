package webserver.model;

import lombok.extern.log4j.Log4j2;
import webserver.entities.HttpMethod;
import webserver.entities.Request;
import webserver.entities.ResponseStatus;
import webserver.exceptions.ServerException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@Log4j2
public class RequestAnalyzer {
    private final Pattern patternDeleteSpaces = Pattern.compile(" ");
    private final Pattern patternDeleteColonAndOneSpace = Pattern.compile(": ");
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;
    private final String webPath;
    private final String fileName;
    private final String errorPagePath;

    public RequestAnalyzer(BufferedReader bufferedReader, String webPath, String fileName, String errorPagePath, BufferedWriter bufferedWriter) {
        this.bufferedReader = bufferedReader;
        this.webPath = webPath;
        this.fileName = fileName;
        this.errorPagePath = errorPagePath;
        this.bufferedWriter = bufferedWriter;
    }

    public Request getRequest() {
        Request request = new Request();
        List<String> stringsByHttpRequest = new ArrayList<>();
        String receivedRequest;

        try {
            while (!(receivedRequest = bufferedReader.readLine()).isEmpty()) {
                stringsByHttpRequest.add(receivedRequest);
            }
        } catch (Exception e) {
            String message = String.format("Exception by reading next request: %s, with next info %s", stringsByHttpRequest, e);
            throw new ServerException(message, e, bufferedWriter);
        }
        request.setHttpMethod(getHttpMethod(stringsByHttpRequest));
        request.setUri(getUri(stringsByHttpRequest, request));
        request.setHeaders(getHeaders(stringsByHttpRequest));
        addPathAndResponseStatusToRequest(request);
        request.setResponseStatus(getResponseStatus(request));
        log.info("Get URI by client - {}", request.getUri());
        return request;
    }


    private void addPathAndResponseStatusToRequest(Request request) {
        request.setResponsePath(errorPagePath);
        String uri = request.getUri();
        if (!request.getHttpMethod().equals(HttpMethod.NOT_SUPPORTED_METHOD)) {
            if (uri.equals(webPath)) {
                if (new File(uri.concat(fileName)).exists()) {
                    request.setResponsePath(uri.concat(fileName));
                }
            } else if (uri.contains(webPath) && new File(uri).exists()) {
                request.setResponsePath(uri);
            }
        }
        log.info("Path sent to ResourceReader - {}", request.getResponsePath());
    }

    private HttpMethod getHttpMethod(List<String> requestLines) {
        HttpMethod httpMethod;
        String method = requestLines.stream()
                .map(s -> Pattern.compile(" ").split(s))
                .flatMap(Arrays::stream)
                .findFirst()
                .orElseThrow(() -> new ServerException("Null value not supported", new Throwable("null not supported"), bufferedWriter));

        try {
            httpMethod = HttpMethod.valueOf(method);
        } catch (Exception e) {
            httpMethod = HttpMethod.NOT_SUPPORTED_METHOD;
        }
        return httpMethod;
    }

    private String getUri(List<String> requestLines, Request request) {
        String uri = requestLines.stream()
                .findFirst()
                .stream()
                .map(patternDeleteSpaces::split)
                .flatMap(Arrays::stream)
                .filter(str -> str.contains("/"))
                .findFirst()
                .orElseThrow(() -> new ServerException("Null value not supported", new Throwable("null not supported"), bufferedWriter));
        StringBuilder stringBuilder = new StringBuilder(uri);

        return stringBuilder.substring(1);
    }

    private HashMap<String, String> getHeaders(List<String> requestLines) {
        HashMap<String, String> headers = new HashMap<>();
        requestLines.stream()
                .filter(s -> s.contains(":"))
                .map(patternDeleteColonAndOneSpace::split)
                .forEach(s -> headers.put(s[0], s[1]));

        return headers;
    }

    private ResponseStatus getResponseStatus(Request request) {
        String uri = request.getUri();
        if (!uri.contains(webPath)) {
            return ResponseStatus.HTTP_STATUS_400;
        } else if (uri.equals("")) {
            return ResponseStatus.HTTP_STATUS_400;
        } else if (request.getHttpMethod().equals(HttpMethod.NOT_SUPPORTED_METHOD)) {
            return ResponseStatus.HTTP_STATUS_405;
        } else if (!(new File(uri).exists()) && !(new File(uri.concat(fileName)).exists())) {
            return ResponseStatus.HTTP_STATUS_404;

        } else return ResponseStatus.HTTP_STATUS_200;
    }
}


