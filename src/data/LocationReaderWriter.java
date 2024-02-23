package src.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class LocationReaderWriter {
    private String location;
    private final String filePath = "./src/data/location_preference.txt";

    public String loadLocationPreference() {
        return null;
    }

    public void writeLocationPreference(String location) {

    }

    public void createLocationPreferenceFile() {
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}


