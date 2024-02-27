package src.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Handles reading and writing the user's location preference to a file.
 */
public class LocationReaderWriter {
    private static final String filePath = "./src/data/location_preference.txt"; // Path to the location preference file.
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
        try (Scanner reader = new Scanner(new File(filePath))) { // Try-with-resources to ensure the scanner is closed.
            if (reader.hasNextLine()) { // Check if there is content in the file.
                return reader.nextLine(); // Return the first line (user's location preference).
            }
        } catch (FileNotFoundException e) { // File not found; attempt to create a new one.
            createLocationPreferenceFile(); // Create a new location preference file if not found.
        }
        return null; // Return null if the file was not found or is empty.
    }

    /**
     * Writes the user's location preference to a file.
     * @param location The user's location preference to write to the file.
     */
    public void writeLocationPreference(String location) {
        try (FileWriter writer = new FileWriter(filePath)) { // Try-with-resources to ensure the writer is closed.
            writer.write(location); // Write the location to the file.
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) { // Handle potential IO exceptions.
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Prompts the user to enter their location and validates the input format.
     * @return A string representing the user's location in an accepted format.
     */
    public String getUserLocation() {
        // Prompt the user for their location with examples of valid formats.
        System.out.println("""
                Enter your location in one of the following formats:
                1.) City name "new york"
                2.) Latitude and Longitude (Decimal degree) "42.3478, -71.0466"
                3.) US zip "10001" """);

        // Regex patterns to validate the user input.
        String cityNamePattern = "^[a-zA-Z ]+$"; 
        String latLongPattern = "^-?\\d{1,3}\\.\\d+,\\s*-?\\d{1,3}\\.\\d+$"; 
        String zipCodePattern = "^\\d{5}$"; 

        String userInput = input.nextLine().trim(); // Read and trim user input.

        // Validate the input against the specified patterns.
        if (userInput.matches(cityNamePattern) || userInput.matches(latLongPattern)) {
            return userInput; // Valid city name or latitude/longitude.
        } else if (userInput.matches(zipCodePattern)) {
            return userInput + " US"; // Append " US" to valid zip codes.
        }
        else {
            // Prompt for confirmation if the input does not match any expected formats.
            System.out.println("The input may not fit one of the formats. Do you want to continue with this choice? (y/n)");
            String confirmation = input.nextLine().trim().toLowerCase();
            if (confirmation.equals("y")) {
                return userInput; // User confirms the input despite the format mismatch.
            } else {
                return getUserLocation(); // Recursive call to prompt the user again.
            }
        }
    }

    /**
     * Creates a new file for storing the user's location preference if it does not already exist.
     */
    public void createLocationPreferenceFile() {
        String location;

        try {
            File file = new File(filePath); // Attempt to create a new file at the specified path.
            if (file.createNewFile()) { // File created successfully.
                System.out.println("File created: " + file.getName());
                location = getUserLocation(); // Prompt the user for their location.
                writeLocationPreference(location); // Write the location to the newly created file.
            } else {
                System.out.println("File already exists."); // File already exists.
            }
        } catch (IOException e) { // Handle potential IO exceptions.
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}