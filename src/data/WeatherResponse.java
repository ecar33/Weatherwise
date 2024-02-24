package src.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private Timelines timelines;
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

    public Timelines getTimelines() {
        return timelines;
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