package webserver.entities;

import lombok.Data;
import webserver.constans.Constants;

import java.util.HashMap;

@Data
public class ErrorRequest extends Request {
    private final String uri = null;
    private final HashMap<String, String> headers = null;
    private final HttpMethod httpMethod = HttpMethod.NOT_SUPPORTED_METHOD;
    private final ResponseStatus responseStatus = ResponseStatus.HTTP_STATUS_500;
    private String responsePath = Constants.DEFAULT_ERROR_PAGE;

}
