package webserver.model;

import org.junit.Test;
import webserver.entities.Request;
import webserver.entities.ResponseStatus;
import webserver.exceptions.ServerException;

import java.io.*;
import java.util.HashMap;

import static org.junit.Assert.*;

public class RequestAnalyzerTest {
    RequestAnalyzer requestAnalyzer;
    String webPath = "src/test/java/webserver/testFiles/";
    String fileName = "testFile.txt";
    String errorPagePath = "src/test/java/webserver/testFiles/testErrorFile.txt";

    String getRequest = """
            GET /src/test/java/webserver/testFiles/ HTTP/1.1\s
            User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)
            Host: www.tutorialspoint.com

            """;

    String getRequestFullLink = """
            GET /src/test/java/webserver/testFiles/testErrorFile.txt HTTP/1.1\s
            User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)
            Host: www.tutorialspoint.com

            """;

    String postRequest = """
            POST /hello.htm HTTP/1.1\s
            User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)

            """;

    String badRequest = "Some idiotic text";

    String badLinkRequest = """
            GET /srst/java/wver/tesles/ HTTP/1.1\s
            User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)

            """;

    String emptyLinkRequest = """
            GET HTTP/1.1\s
            User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)

            """;

    String getRequestWithWrongFail = """
            GET /src/test/java/webserver/testFiles/testle.txt HTTP/1.1\s
            Host: www.tutorialspoint.com

            """;

    String getRequestWithWrongMethod = """
            WRONG /src/test/java/webserver/testFiles/testle.txt HTTP/1.1\s
            Host: www.tutorialspoint.com
            Host: www.tutorialspoint.com

            """;


    @Test
    public void testGetRequestHttpMethodGet() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(getRequest));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        Request request = requestAnalyzer.getRequest();
        String expected = "GET";
        String actual = request.getHttpMethod().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestHttpMethodPost() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(postRequest));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        Request request = requestAnalyzer.getRequest();
        String expected = "POST";
        String actual = request.getHttpMethod().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestURI() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(getRequest));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        Request request = requestAnalyzer.getRequest();
        String expected = "src/test/java/webserver/testFiles/";
        String actual = request.getUri();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestHeaders() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(getRequest));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        Request request = requestAnalyzer.getRequest();
        HashMap<String, String> expected = new HashMap<>();
        expected.put("User-Agent", "Mozilla/4.0 (compatible; MSIE5.01; Windows NT)");
        expected.put("Host", "www.tutorialspoint.com");
        HashMap<String, String> actual = request.getHeaders();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testGetRequestException() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(badRequest));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        assertThrows(RuntimeException.class, () -> requestAnalyzer.getRequest());
    }

    @Test
    public void testGetRequestResponse200() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(getRequest));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        Request request = requestAnalyzer.getRequest();
        ResponseStatus expected = ResponseStatus.HTTP_STATUS_200;
        ResponseStatus actual = request.getResponseStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestResponse200WithFullLink() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(getRequestFullLink));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        Request request = requestAnalyzer.getRequest();
        ResponseStatus expected = ResponseStatus.HTTP_STATUS_200;
        ResponseStatus actual = request.getResponseStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestResponse400() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(badLinkRequest));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        Request request = requestAnalyzer.getRequest();
        ResponseStatus expected = ResponseStatus.HTTP_STATUS_400;
        ResponseStatus actual = request.getResponseStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestResponse400ExceptionPath() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(badLinkRequest));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        Request request = requestAnalyzer.getRequest();
        String expected = errorPagePath;
        String actual = request.getResponsePath();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestResponse400ByEmptyURL() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(emptyLinkRequest));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        Request request = requestAnalyzer.getRequest();
        ResponseStatus expected = ResponseStatus.HTTP_STATUS_400;
        ResponseStatus actual = request.getResponseStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestResponse400ByEmptyURLExceptionPath() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(emptyLinkRequest));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        Request request = requestAnalyzer.getRequest();
        String expected = errorPagePath;
        String actual = request.getResponsePath();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestResponse404() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(getRequestWithWrongFail));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        Request request = requestAnalyzer.getRequest();
        ResponseStatus expected = ResponseStatus.HTTP_STATUS_404;
        ResponseStatus actual = request.getResponseStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestResponse404ExceptionPage() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(getRequestWithWrongFail));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        Request request = requestAnalyzer.getRequest();
        String expected = errorPagePath;
        String actual = request.getResponsePath();
        assertEquals(expected, actual);
    }


    @Test
    public void testGetRequestResponse405() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(getRequestWithWrongMethod));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        Request request = requestAnalyzer.getRequest();
        ResponseStatus expected = ResponseStatus.HTTP_STATUS_405;
        ResponseStatus actual = request.getResponseStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestResponse405PathToExceptionPage() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(getRequestWithWrongMethod));
        requestAnalyzer = new RequestAnalyzer(bufferedReader, webPath, fileName, errorPagePath, new BufferedWriter(new StringWriter()));
        Request request = requestAnalyzer.getRequest();
        String expected = errorPagePath;
        String actual = request.getResponsePath();
        assertEquals(expected, actual);
    }

}