package sk.stuba.fei.uim.oop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOManager {

    private static final int MAX_ARRAY = 1024;

    private String getFilePath(String fileName) {
        return new File("src/main/java/resources/" + fileName).getAbsolutePath();
    }

    String[] fileToStringArray(String fileName) {
        String filePath = getFilePath(fileName);
        List<String> output = new ArrayList<>();

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();

            for (int i = 0; line != null; i++) {
                output.add(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toArray(String[]::new);
    }


}
