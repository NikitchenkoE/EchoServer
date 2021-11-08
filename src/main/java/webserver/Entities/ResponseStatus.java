package webserver.Entities;

public enum ResponseStatus {
    HTTP_STATUS_200("HTTP/1.1 200 OK"),
    HTTP_STATUS_400("HTTP/1.1 400 Bad Request"),
    HTTP_STATUS_404("HTTP/1.1 404 Not Found"),
    HTTP_STATUS_405("HTTP/1.1 405 Method Not Allowed"),
    HTTP_STATUS_500("");

    private final String response;

    ResponseStatus(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
