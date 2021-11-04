package webserver.Entities;

import java.util.Map;

public class Request {
    private String uri;
    private Map<String,String> headers;
    private Enum<HttpMethod> roles;

}
