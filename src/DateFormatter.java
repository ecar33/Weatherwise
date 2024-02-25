package src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateFormatter {
    public static String formatToMonthDay(String dateTimeStr) {
        // Parse the input string to a LocalDateTime object
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_DATE_TIME);
        
        // Define the new format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        
        // Format the LocalDateTime object to the new format
        String formattedTime = dateTime.format(formatter);
        String formattedTimeWithLeadingZerosRemoved =  formattedTime.replaceFirst ("^0*", "");

        return formattedTimeWithLeadingZerosRemoved;
    }
}