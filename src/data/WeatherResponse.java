package src.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Model class to represent a weather response, annotated to ignore unknown JSON
 * properties.
 * This class stores weather data, including timelines and location information.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private Timelines timelines; // Holds weather forecast data for various timelines.
    private Map<String, String> location; // Stores location details, such as name.
    private LocalDateTime fetchedAt; // Timestamp of when the data was fetched.
    private final long validityDurationInSeconds = 1800; // Duration (in seconds) after which the data is considered
                                                         // expired.

    /**
     * Retrieves the daily forecast for a specified day index.
     * 
     * @param dayIndex The index of the day for which the forecast is requested.
     * @return The daily forecast for the given day index, or null if not available.
     */
    public DailyForecast getDailyForecast(int dayIndex) {
        // Ensure timelines and the specified index are valid.
        if (this.timelines != null && this.timelines.getDailyForecast().length > dayIndex) {
            DailyForecast dailyForecast = this.timelines.getDailyForecast()[dayIndex];

            // Return the forecast if found; otherwise, return null.
            return dailyForecast;
        }
        return null; // Return null if the conditions are not met.
    }

    /**
     * Retrieves the five-day weather forecast.
     * 
     * @return An array of DailyForecast objects representing the forecast for the
     *         next five days.
     */
    public DailyForecast[] getFiveDayForecast() {
        // Simply returns the daily forecast from the timelines.
        return this.timelines.getDailyForecast();
    }

    /**
     * Getter for timelines.
     * 
     * @return The Timelines object containing weather forecast data.
     */
    public Timelines getTimelines() {
        return this.timelines;
    }

    /**
     * Retrieves the name of the location for the weather data.
     * 
     * @return The name of the location.
     */
    public String getLocationName() {
        // Returns the "name" field from the location map.
        return this.location.get("name");
    }

    /**
     * Sets the location details.
     * 
     * @param location A map containing location details.
     */
    public void setLocation(Map<String, String> location) {
        this.location = location;
    }

    /**
     * Sets the timelines for weather data.
     * 
     * @param timelines The Timelines object containing forecast data.
     */
    public void setTimelines(Timelines timelines) {
        this.timelines = timelines;
    }

    /**
     * Sets the fetchedAt timestamp to the specified time.
     * 
     * @param now The timestamp of when the data was fetched.
     */
    public void setFetchedAt(LocalDateTime now) {
        this.fetchedAt = now;
    }

    /**
     * Retrieves the timestamp of when the data was fetched.
     * 
     * @return The fetchedAt timestamp.
     */
    public LocalDateTime getFetchedAt() {
        return fetchedAt;
    }

    /**
     * Determines whether the stored weather data is expired based on the validity
     * duration.
     * 
     * @return true if the data is expired, false otherwise.
     */
    public boolean isExpired() {
        // Checks if the current time is after the calculated expiration time.
        return this.fetchedAt.plusSeconds(validityDurationInSeconds).isBefore(LocalDateTime.now());
    }
}
