package webserver.Model;

import org.junit.Test;
import webserver.Entities.Request;

import static org.junit.Assert.*;

public class ResourceReaderTest {
//
//    String webPath = "src/test/java/webserver/testFiles/";
//    String pathToFile = "src/test/java/webserver/testFiles/testFile.txt";
//    String fileName = "testFile.txt";
//    String errorPagePath = "src/test/java/webserver/testFiles/testErrorFile.txt";
//    private final String HTTP_STATUS_200 = "HTTP/1.1 200 OK";
//    private final String HTTP_STATUS_404 = "HTTP/1.1 404 Not Found";
//
//    @Test
//    public void getContentByWebPathAbdFileName() {
//        Request request = new Request();
//        request.setUri(webPath);
//        ResourceReader resourceReader = new ResourceReader(request, webPath, fileName, errorPagePath);
//        String expected = "This is test bro";
//        String actual = resourceReader.getContent();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void getContentByFilePath() {
//        Request request = new Request();
//        request.setUri(pathToFile);
//        ResourceReader resourceReader = new ResourceReader(request, webPath, fileName, errorPagePath);
//        String expected = "This is test bro";
//        String actual = resourceReader.getContent();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void getContentByProblemPath() {
//        Request request = new Request();
//        request.setUri("someProblemPath");
//        ResourceReader resourceReader = new ResourceReader(request, webPath, fileName, errorPagePath);
//        String expected = "This is bad file bro";
//        String actual = resourceReader.getContent();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void getContentByPathThatDifferentWithWebPath() {
//        Request request = new Request();
//        request.setUri("src/test/java/webserver/testFiles/testFile.txt");
//        String webPathDifferent = "src/main/resources/webapp/";
//        ResourceReader resourceReader = new ResourceReader(request, webPathDifferent, fileName, errorPagePath);
//        String expected = "This is bad file bro";
//        String actual = resourceReader.getContent();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void getContentByRightPathButDifferentName() {
//        Request request = new Request();
//        request.setUri(webPath);
//        String differentName = "notThatFile.txt";
//        ResourceReader resourceReader = new ResourceReader(request, webPath, differentName, errorPagePath);
//        String expected = "This is bad file bro";
//        String actual = resourceReader.getContent();
//        assertEquals(expected, actual);
//    }
//
//
//    @Test
//    public void getStatusByWebPathAbdFileName() {
//        Request request = new Request();
//        request.setUri(webPath);
//        ResourceReader resourceReader = new ResourceReader(request, webPath, fileName, errorPagePath);
//        resourceReader.getContent();
//        assertEquals(HTTP_STATUS_200, resourceReader.getResponseStatus());
//    }
//
//    @Test
//    public void getStatusByFilePath() {
//        Request request = new Request();
//        request.setUri(pathToFile);
//        ResourceReader resourceReader = new ResourceReader(request, webPath, fileName, errorPagePath);
//        resourceReader.getContent();
//        assertEquals(HTTP_STATUS_200, resourceReader.getResponseStatus());
//    }
//
//    @Test
//    public void getStatusByProblemPath() {
//        Request request = new Request();
//        request.setUri("someProblemPath");
//        ResourceReader resourceReader = new ResourceReader(request, webPath, fileName, errorPagePath);
//        resourceReader.getContent();
//        assertEquals(HTTP_STATUS_404, resourceReader.getResponseStatus());
//    }
//
//    @Test
//    public void getStatusByPathThatDifferentWithWebPath() {
//        Request request = new Request();
//        request.setUri("src/test/java/webserver/testFiles/testFile.txt");
//        String webPathDifferent = "src/main/resources/webapp/";
//        ResourceReader resourceReader = new ResourceReader(request, webPathDifferent, fileName, errorPagePath);
//        resourceReader.getContent();
//        assertEquals(HTTP_STATUS_404, resourceReader.getResponseStatus());
//    }
//
//    @Test
//    public void getStatustByRightPathButDifferentName() {
//        Request request = new Request();
//        request.setUri(webPath);
//        String differentName = "notThatFile.txt";
//        ResourceReader resourceReader = new ResourceReader(request, webPath, differentName, errorPagePath);
//        resourceReader.getContent();
//        assertEquals(HTTP_STATUS_404, resourceReader.getResponseStatus());
//    }
}