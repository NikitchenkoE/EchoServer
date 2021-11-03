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


    public RequestAnalyzer(String webPath, String fileName, BufferedReader bufferedReader)  {
        this.webPath = webPath;
        this.fileName = fileName;
        this.bufferedReader = bufferedReader;
    }

    private String analyzePath()  {
        String pathToFile = null;
        String receivedRequest;
        receivedRequest = readRequest();
        String[] stringsByRequest = Pattern.compile(" ").split(receivedRequest);

        List<String> stringsWithWebPath = Arrays.stream(stringsByRequest)
                .filter(s -> s.contains(webPath))
                .collect(Collectors.toList());
        String path = webPathToNormalForm(stringsWithWebPath);

        if (new File(path + fileName).exists()) {
            pathToFile = path.concat(fileName);
        } else if (new File(path).exists()) {
            pathToFile = path;
        }
        return pathToFile;
    }

    public String readFile() throws IOException {
        String filePath = analyzePath();
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

    public String readRequest() {
        StringBuilder stringBuilder = new StringBuilder();
        String receivedRequest;
        try {
            while (!(receivedRequest = bufferedReader.readLine()).equals("")) {
                stringBuilder.append(receivedRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String webPathToNormalForm(List<String> path) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : path) {
            if (string.contains(webPath)) {
                for (String s : Pattern.compile("/").split(string)) {
                    stringBuilder.append(s).append("\\");
                }
                break;
            }
        }
        stringBuilder.delete(0, 1);
        return stringBuilder.toString();
    }
}
