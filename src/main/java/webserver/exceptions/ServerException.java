package webserver.exceptions;

import webserver.entities.ErrorRequest;
import webserver.model.ResourceReader;
import webserver.model.ResponseWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerException extends RuntimeException {
    private final ErrorRequest errorRequest = new ErrorRequest();
    private Socket clientSocket;
    private BufferedWriter bufferedWriter;
    private final ResourceReader resourceReader = new ResourceReader(errorRequest, bufferedWriter);

    public ServerException(String message, Throwable cause, Socket clientSocket) {
        super(message, cause);
        this.clientSocket = clientSocket;
        writeResponse();
    }

    public ServerException(String message, Throwable cause, BufferedWriter bufferedWriter) {
        super(message, cause);
        this.bufferedWriter = bufferedWriter;
        writeResponseByBuffer();
    }

    private void writeResponse() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
            ResponseWriter responseWriter = new ResponseWriter(bufferedWriter, resourceReader.getContent(), errorRequest);
            responseWriter.response();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeResponseByBuffer() {
        ResponseWriter responseWriter = new ResponseWriter(bufferedWriter, resourceReader.getContent(), errorRequest);
        responseWriter.response();
    }
}
