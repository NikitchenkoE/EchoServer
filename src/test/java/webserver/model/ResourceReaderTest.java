package webserver.model;

import org.junit.Test;
import webserver.entities.HttpMethod;
import webserver.entities.Request;
import webserver.entities.ResponseStatus;

import java.io.BufferedWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class ResourceReaderTest {
    String webPath = "src/test/java/webserver/model";
    String pathToCorrectFile = "src/test/java/webserver/testFiles/testFile.txt";
    String pathToWrongFile = "src/test/java/webserver/testFiles/testErrorFile.txt";
    Request correctRequest = new Request(webPath, null, HttpMethod.GET, ResponseStatus.HTTP_STATUS_200, pathToCorrectFile);
    Request wrongRequest = new Request(webPath, null, HttpMethod.GET, ResponseStatus.HTTP_STATUS_400, pathToWrongFile);
    Request requestToGetException = new Request();

    @Test
    public void getContentByWebPathAbdFileName() {
        ResourceReader resourceReader = new ResourceReader(correctRequest, new BufferedWriter(new StringWriter()));
        String expected = "This is test bro";
        String actual = resourceReader.getContent();
        assertEquals(expected, actual);
    }

    @Test
    public void getContentByProblemPath() {
        ResourceReader resourceReader = new ResourceReader(wrongRequest, new BufferedWriter(new StringWriter()));
        String expected = "This is bad file bro";
        String actual = resourceReader.getContent();
        assertEquals(expected, actual);
    }
}