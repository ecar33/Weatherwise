package src.view;

import java.util.Map;
import src.data.*;

public class WeatherFormatter {
    public void printCurrentForecast(DailyForecast todaysForecast) {
        Map<String, String> forecastValues = todaysForecast.getValues();
        Double avgTemp = celsiusToFahrenheit(Double.parseDouble(forecastValues.get("temperatureAvg")));
        Double maxTemp = celsiusToFahrenheit(Double.parseDouble(forecastValues.get("temperatureMax")));
        Double minTemp = celsiusToFahrenheit(Double.parseDouble(forecastValues.get("temperatureMin")));


        System.out.println("Here is today's forecast for %s");
        System.out.println(String.format("Low - Average - High Temperatures: %.2f\u00B0F - %.2f\u00B0F - %.2f\u00B0F", minTemp, avgTemp, maxTemp));


    }

    public void printShortTermForecast() {

    }

    public void printWeeklyForecast() {

    }

    public void printAlertsAndWarnings() {

    }

    private double celsiusToFahrenheit(double celsius) {
        double fahrenheit;
        fahrenheit = ((celsius*9)/ 5) + 32;
        return fahrenheit;
    }

}
