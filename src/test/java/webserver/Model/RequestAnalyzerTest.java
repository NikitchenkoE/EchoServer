package webserver.Model;

import org.junit.Test;
import webserver.Entities.Request;

import java.io.*;
import java.util.HashMap;

import static org.junit.Assert.*;

public class RequestAnalyzerTest {
    RequestAnalyzer requestAnalyzer;

    String getRequest = "GET /src/this/is/GET.html HTTP/1.1 \n" +
            "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\n" +
            "Host: www.tutorialspoint.com\n" +
            "\n";

    String postRequest = "POST /hello.htm HTTP/1.1 \n" +
            "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\n" +
            "Host: www.tutorialspoint.com\n" +
            "\n";

    String badRequest = "Some idiotic text";

    @Test
    public void testGetRequestHttpMethodGet() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(getRequest));
        requestAnalyzer = new RequestAnalyzer(bufferedReader);
        Request request = requestAnalyzer.getRequest();
        String expected = "GET";
        String actual = request.getHttpMethod().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestHttpMethodPost() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(postRequest));
        requestAnalyzer = new RequestAnalyzer(bufferedReader);
        Request request = requestAnalyzer.getRequest();
        String expected = "POST";
        String actual = request.getHttpMethod().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestURI() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(getRequest));
        requestAnalyzer = new RequestAnalyzer(bufferedReader);
        Request request = requestAnalyzer.getRequest();
        String expected = "src/this/is/GET.html";
        String actual = request.getUri();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestHeaders() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(getRequest));
        requestAnalyzer = new RequestAnalyzer(bufferedReader);
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
        requestAnalyzer = new RequestAnalyzer(bufferedReader);
        assertThrows(RuntimeException.class, () -> requestAnalyzer.getRequest());
    }


}