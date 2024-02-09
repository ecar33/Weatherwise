package data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Timelines {
    private DailyForecast[] daily;

    public DailyForecast[] getDaily() {
        return daily;
    }

    public void setDaily(DailyForecast[] daily) {
        this.daily = daily;
    }
}