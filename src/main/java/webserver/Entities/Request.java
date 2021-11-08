package webserver.Entities;

import lombok.Data;

import java.util.HashMap;

@Data
public class Request {
    private String uri;
    private HashMap<String, String> headers;
    private Enum<HttpMethod> httpMethod;

}
