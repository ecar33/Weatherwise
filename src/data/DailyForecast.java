package src.data;

import java.util.Map;

public class DailyForecast {
    public String time;
    private Map<String, String> values;

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public String getTime() {
        return time;
    }

    public String setTime() {
        return time;
    }
}