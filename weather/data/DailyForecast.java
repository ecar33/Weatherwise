package weather.data;

import java.util.Map;

/**
 * Represents a daily weather forecast, including the time of the forecast and a
 * map of forecast values.
 */
public class DailyForecast {
    public String time; // The forecast time as a string.
    private Map<String, String> values; // A map of forecast values (e.g., temperature, humidity).

    /**
     * Gets the forecast values.
     * 
     * @return A map of forecast values.
     */
    public Map<String, String> getValues() {
        return values;
    }

    /**
     * Sets the forecast values.
     * 
     * @param values A map of forecast values to set.
     */
    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    /**
     * Gets the time of the forecast.
     * 
     * @return The forecast time.
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the time of the forecast.
     * Note: This method should return void to properly act as a setter.
     * 
     * @param time The forecast time to set.
     */
    public void setTime(String time) { // Modified to return void, as it's a setter.
        this.time = time;
    }
}
