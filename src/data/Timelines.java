package src.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents the timelines of weather forecasts, specifically handling daily
 * forecasts.
 * This class is configured to ignore unknown properties to ensure compatibility
 * with various JSON data formats.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Timelines {
    private DailyForecast[] daily; // Array of daily forecasts.

    /**
     * Retrieves the array of daily weather forecasts.
     * 
     * @return An array of {@link DailyForecast} objects representing the daily
     *         weather forecasts.
     */
    public DailyForecast[] getDailyForecast() {
        return daily;
    }

    /**
     * Sets the array of daily weather forecasts.
     * 
     * @param daily An array of {@link DailyForecast} objects to be set as the daily
     *              forecasts.
     */
    public void setDaily(DailyForecast[] daily) {
        this.daily = daily;
    }
}