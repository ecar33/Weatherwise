package src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for formatting date and time strings.
 */
public class DateFormatter {

    /**
     * Formats a given date time string to "MM-dd" format, removing leading zeros.
     * 
     * @param dateTimeStr The date time string in ISO_DATE_TIME format ("yyyy-MM-ddTHH:mm:ss").
     * @return A formatted date string in "MM-dd" format with leading zeros removed.
     */
    public static String formatToMonthDay(String dateTimeStr) {
        // Parse the input string to a LocalDateTime object
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_DATE_TIME);
        
        // Define the new format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        
        // Format the LocalDateTime object to the new format
        String formattedTime = dateTime.format(formatter);
        
        // Remove leading zeros from the formatted date string
        String formattedTimeWithLeadingZerosRemoved = formattedTime.replaceFirst("^0*", "");

        return formattedTimeWithLeadingZerosRemoved;
    }
}
