package data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private Timelines timelines;

    public Timelines getTimelines() {
        return timelines;
    }

    public void setTimelines(Timelines timelines) {
        this.timelines = timelines;
    }
}