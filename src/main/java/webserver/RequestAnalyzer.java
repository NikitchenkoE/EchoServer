package webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RequestAnalyzer {
    private final String webPath;
    private final String fileName;
    private final BufferedReader bufferedReader;


    public RequestAnalyzer(String webPath, String fileName, BufferedReader bufferedReader) {
        this.webPath = webPath;
        this.fileName = fileName;
        this.bufferedReader = bufferedReader;
    }

    private String getPath() {
        String pathToFile = null;
        String receivedRequestByClient = readRequest();
        String[] stringsByRequest = Pattern.compile(" ").split(receivedRequestByClient);

        List<String> stringsWithWebPath = Arrays.stream(stringsByRequest)
                                                .filter(s -> s.contains(webPath))
                                                .collect(Collectors.toList());

        String pathPart = getPathPart(stringsWithWebPath);
        if (new File(pathPart + fileName).exists()) {
            pathToFile = pathPart.concat(fileName);
        } else if (new File(pathPart).exists()) {
            pathToFile = pathPart;
        }
        return pathToFile;
    }

    public String readRequest() {
        StringBuilder stringBuilder = new StringBuilder();
        String receivedRequest;
        try {
            while (!(receivedRequest = bufferedReader.readLine()).isEmpty()) {
                stringBuilder.append(receivedRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public String readFile() throws IOException {
        String filePath = getPath();
        if (filePath != null) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
                String s;
                StringBuilder stringBuilder = new StringBuilder();
                while ((s = bufferedReader.readLine()) != null) {
                    stringBuilder.append(s);
                }
                return stringBuilder.toString();
            }
        } else return null;
    }

    private String getPathPart(List<String> path) {
        StringBuilder stringBuilder = new StringBuilder();
        String firstPartOfPath = path.stream()
                                    .filter(s -> s.contains(webPath))
                                    .findFirst()
                                    .orElse(null);

        stringBuilder.append(firstPartOfPath);
        //Delete first element "/", because path to file start from letters
        stringBuilder.delete(0, 1);
        return stringBuilder.toString();
    }
}
