package data;

import java.util.Map;

public class DailyForecast {
    public String time;
    private Map<String, Object> values;

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    public String getTime() {
        return time;
    }

    public String setTime() {
        return time;
    }
}