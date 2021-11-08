package webserver.Model;

import lombok.extern.log4j.Log4j2;
import webserver.Entities.HttpMethods;
import webserver.Entities.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
public class RequestAnalyzer {
    private final BufferedReader bufferedReader;

    public RequestAnalyzer(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public Request getRequest() {
        Request request = new Request();
        StringBuilder stringBuilder = new StringBuilder();
        String receivedRequest;

        try {
            while (!(receivedRequest = bufferedReader.readLine()).isEmpty()) {
                stringBuilder.append(receivedRequest).append("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("Exception in getRequest() method in RequestAnalyzer.class by reading next request: %s, with next info %s", stringBuilder, e));
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
                .orElseThrow(() -> new RuntimeException("Null value not supported"));

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
                .orElseThrow(() -> new RuntimeException("Null value not supported"));
        StringBuilder stringBuilder = new StringBuilder(Objects.requireNonNull(uri));

        return stringBuilder.substring(1);
    }

    private HashMap<String, String> getHeaders(List<String> requestLines) {
        HashMap<String, String> headers = new HashMap<>();
        requestLines.stream()
                .filter(s -> s.contains(":"))
                .collect(Collectors.toList())
                .forEach(s -> {
                    String[] partsOfHeader = Pattern.compile(": ").split(s);
                    headers.put(partsOfHeader[0], partsOfHeader[1]);
                });

        return headers;
    }

}
