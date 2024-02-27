package weather.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read API key from a properties file.
 */
public final class ApiKeyReader {

    /**
     * Retrieves the API key from a properties file.
     * 
     * @return The API key if found; null otherwise.
     */
    public static String get() {
        Properties properties = new Properties();
        String pathToLib = "./lib/apikey.properties";
        try (FileInputStream input = new FileInputStream(pathToLib)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return properties.getProperty("api.key");
    }
}