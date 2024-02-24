package src.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LocationReaderWriter {
    private static final String filePath = "./src/data/location_preference.txt";
    private static final String locationPattern = "^[a-zA-Z\\s]+$";
    private Scanner input;

    public LocationReaderWriter() {
        this.input = new Scanner(System.in) ;
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
        System.out.println("Enter the location:");
        String userInput = input.nextLine().trim();

        if (!Pattern.matches(locationPattern, userInput)) {
            System.out.println("Your input '" + userInput + "' might be incorrect. Do you want to proceed? (yes/no)");
            String confirmation = input.nextLine().trim().toLowerCase();

            if (!confirmation.equals("yes")) {
                System.out.println("Please enter the location again.");
                return getUserLocation(); // Recursively prompt for input again
            }
        }
        return userInput;
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
