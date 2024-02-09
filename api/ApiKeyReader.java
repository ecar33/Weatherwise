package api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ApiKeyReader {
    public static String get() {
        // Load the properties from the config.properties file
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("../.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();

        }

        // Get the API key from the loaded properties
        String apiKey = properties.getProperty("api.key");

        // Check if the API key is not null and not empty
        if (apiKey != null && !apiKey.isEmpty()) {
            return apiKey;
        } else {
            return "Not Found";
        }
    }
}
