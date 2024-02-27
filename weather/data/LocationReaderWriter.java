package weather.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Handles reading and writing the user's location preference to and from a file.
 * The file is stored externally, in the user's home directory, to ensure the application
 * can operate dynamically with a JAR file.
 */
public class LocationReaderWriter {
    private static final String FILE_NAME = "location_preference.txt";
    // Determines the file path dynamically to store in the user's home directory.
    private static final String filePath = System.getProperty("user.home") + File.separator + ".weatherwise" + File.separator + FILE_NAME;
    private Scanner input; // Scanner to read user input from the console.

    /**
     * Constructor that initializes a new Scanner to read input from the console.
     */
    public LocationReaderWriter() {
        this.input = new Scanner(System.in);
    }

    /**
     * Loads the user's location preference from a file.
     * @return The location preference if the file exists and has content; null otherwise.
     */
    public String loadLocationPreference() {
        try {
            File file = new File(filePath);
            // Ensure the parent directory exists before attempting to read.
            file.getParentFile().mkdirs();
            
            Scanner reader = new Scanner(file);
            if (reader.hasNextLine()) {
                return reader.nextLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            // If file does not exist, attempt to create a new one.
            createLocationPreferenceFile();
        }
        return null;
    }

    /**
     * Writes the user's location preference to a file.
     * @param location The user's location preference to write to the file.
     */
    public void writeLocationPreference(String location) {
        try {
            File file = new File(filePath);
            // Ensure the parent directory exists before attempting to write.
            file.getParentFile().mkdirs();
            
            FileWriter writer = new FileWriter(file);
            writer.write(location);
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Prompts the user to enter their location and validates the input format.
     * @return A string representing the user's location in an accepted format.
     */
    public String getUserLocation() {
        System.out.println("""
                Enter your location in one of the following formats:
                1.) City name "new york"
                2.) Latitude and Longitude (Decimal degree) "42.3478, -71.0466"
                3.) US zip "10001" """);

        String cityNamePattern = "^[a-zA-Z ]+$";
        String latLongPattern = "^-?\\d{1,3}\\.\\d+,\\s*-?\\d{1,3}\\.\\d+$";
        String zipCodePattern = "^\\d{5}$";

        String userInput = input.nextLine().trim();

        if (userInput.matches(cityNamePattern) || userInput.matches(latLongPattern) || userInput.matches(zipCodePattern)) {
            return userInput;
        } else {
            System.out.println("The input does not match any expected formats. Please try again.");
            return getUserLocation();
        }
    }

    /**
     * Creates a new file for storing the user's location preference if it does not already exist.
     */
    private void createLocationPreferenceFile() {
        try {
            File file = new File(filePath);
            // Ensure the parent directory exists before attempting to create the file.
            if (file.getParentFile().mkdirs() || file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                // Prompt the user for their location after file creation.
                String location = getUserLocation();
                // Write the location to the newly created file.
                writeLocationPreference(location);
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
    }
}