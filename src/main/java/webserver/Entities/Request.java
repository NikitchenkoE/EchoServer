package webserver.Entities;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Request {
    private String uri;
    private HashMap<String, String> headers;
    private Enum<HttpMethods> httpMethod;

}
