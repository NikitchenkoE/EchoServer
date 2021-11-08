package webserver.Model;

import lombok.extern.log4j.Log4j2;
import webserver.Entities.HttpMethod;
import webserver.Entities.Request;

import java.io.BufferedReader;
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
        List<String> stringsByHttpRequest = new ArrayList<>();
        String receivedRequest;

        try {
            while (!(receivedRequest = bufferedReader.readLine()).isEmpty()) {
                stringsByHttpRequest.add(receivedRequest);
            }
        } catch (Exception e) {
            String message = String.format("Exception by reading next request: %s, with next info %s", stringsByHttpRequest, e);
            throw new RuntimeException(message, e);
        }

        request.setHttpMethod(getHttpMethod(stringsByHttpRequest));
        request.setUri(getUri(stringsByHttpRequest, request));
        request.setHeaders(getHeaders(stringsByHttpRequest));

        log.info("Get URI by client - {}", request.getUri());
        return request;
    }

    private Enum<HttpMethod> getHttpMethod(List<String> requestLines) {
        String method = requestLines.stream()
                .map(s -> Pattern.compile(" ").split(s))
                .flatMap(Arrays::stream)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Null value not supported"));

        return HttpMethod.valueOf(method);
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
        StringBuilder stringBuilder = new StringBuilder(uri);

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
