package webserver.Entities;

import lombok.Data;

import java.util.Map;

@Data
public class Request {
    private String uri;
    private Map<String, String> headers;
    private Enum<HttpMethods> httpMethod;

}
