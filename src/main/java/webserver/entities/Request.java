package webserver.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private String uri;
    private HashMap<String, String> headers;
    private HttpMethod httpMethod;
    private ResponseStatus responseStatus;
    private String responsePath;

}
