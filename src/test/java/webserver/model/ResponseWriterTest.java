package webserver.model;

import org.junit.Test;
import webserver.entities.HttpMethod;
import webserver.entities.Request;
import webserver.entities.ResponseStatus;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.*;

public class ResponseWriterTest {

    String webPath = "src/test/java/webserver/model";
    String pathToCorrectFile = "src/test/java/webserver/testFiles/testFile.txt";
    String pathToWrongFile = "src/test/java/webserver/testFiles/testErrorFile.txt";
    Request correctRequest = new Request(webPath, null, HttpMethod.GET, ResponseStatus.HTTP_STATUS_200, pathToCorrectFile);
    String content = "content content content content content content content";

    String expected = """
            HTTP/1.1 200 OK\r
            \r
            content content content content content content content""";


    @Test
    public void testResponse() throws IOException {
        StringWriter stringWriter = new StringWriter();
        BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);
        ResponseWriter responseWriter = new ResponseWriter(bufferedWriter, content, correctRequest);
        responseWriter.response();
        bufferedWriter.flush();
        String actual = stringWriter.toString();
        assertEquals(expected, actual);
    }
}