package src.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read API key from a properties file.
 */
public final class ApiKeyReader {

    /**
     * Retrieves the API key from a properties file.
     * @return The API key if found; "Not Found" otherwise.
     */
    public static String get() {
        Properties properties = new Properties(); // Create a Properties object to hold key-value pairs.
        try (FileInputStream input = new FileInputStream(".properties")) { // Try to open the properties file.
            properties.load(input); // Load the properties from the file.
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if an IOException occurs.
        }

        String apiKey = properties.getProperty("api.key"); // Retrieve the API key using its key.

        // Check if the API key is not null and not empty.
        if (apiKey != null && !apiKey.isEmpty()) {
            return apiKey; // Return the API key if it's valid.
        } else {
            return "Not Found"; // Return "Not Found" if the API key is null or empty.
        }
    }
}
