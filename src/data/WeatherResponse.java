package src.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private Timelines timelines;
    private Map<String, String> location;
    private LocalDateTime fetchedAt;
    private final long validityDurationInSeconds = 1800;

    public DailyForecast getDailyForecast(int dayIndex) {
        if (this.timelines != null && this.timelines.getDailyForecast().length > dayIndex) {
            DailyForecast dailyForecast = this.timelines.getDailyForecast()[dayIndex];

            if (dailyForecast != null) {
                return dailyForecast;
            }
        }
        return null;
    }

    public DailyForecast[] getFiveDayForecast() {
        DailyForecast[] fiveDayForecast = this.timelines.getDailyForecast();
        return fiveDayForecast;
        
    }

    public Timelines getTimelines() {
        return this.timelines;
    }

    public String getLocationName() {
        return this.location.get("name");
    }

    public void setLocation(Map<String, String> location) {
        this.location = location;
    }

    public void setTimelines(Timelines timelines) {
        this.timelines = timelines;
    }

    public void setFetchedAt(LocalDateTime now) {
        this.fetchedAt = now;
    }

    public LocalDateTime getFetchedAt() {
        return fetchedAt;
    }

    public boolean isExpired() {
        // Checks if the current time is after the calculated expiration time
        return this.fetchedAt.plusSeconds(validityDurationInSeconds).isBefore(LocalDateTime.now());
    }

}