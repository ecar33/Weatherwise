package src.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LocationReaderWriter {
    private static final String filePath = "./src/data/location_preference.txt";
    private Scanner input;

    public LocationReaderWriter() {
        this.input = new Scanner(System.in);
    }

    public String loadLocationPreference() {
        try (Scanner reader = new Scanner(new File(filePath))) {
            if (reader.hasNextLine()) {
                return reader.nextLine();
            }
        } catch (FileNotFoundException e) {
            createLocationPreferenceFile();
        }
        return null;
    }

    public void writeLocationPreference(String location) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(location);
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

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


        // Check if the input matches any of the given formats
        if (userInput.matches(cityNamePattern) || userInput.matches(latLongPattern)) {
            return userInput; // Input is valid
        } else if (userInput.matches(zipCodePattern)) {
            return userInput + " US";
        }
        else {
            System.out.println("The input may not fit one of the formats. Do you want to continue with this choice? (y/n)");
            String confirmation = input.nextLine().trim().toLowerCase();
            if (confirmation.equals("y")) {
                return userInput;
            } else {
                return getUserLocation(); 
            }
        }
    }


    public void createLocationPreferenceFile() {
        String location;

        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                location = getUserLocation();
                writeLocationPreference(location);
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
