package src.view;

import java.util.Map;
import src.data.*;

public class WeatherFormatter {
    public void printDayForecast(DailyForecast todaysForecast, int dailyIndex) {

        String day = dailyIndex == 0 ? "today" : "tomorrow";

        Map<String, String> forecastValues = todaysForecast.getValues();
        Double avgTemp = celsiusToFahrenheit(Double.parseDouble(forecastValues.get("temperatureAvg")));
        Double maxTemp = celsiusToFahrenheit(Double.parseDouble(forecastValues.get("temperatureMax")));
        Double minTemp = celsiusToFahrenheit(Double.parseDouble(forecastValues.get("temperatureMin")));
        int uvIndexMin = Integer.parseInt(forecastValues.get("uvIndexMin"));
        int uvIndexAvg = Integer.parseInt(forecastValues.get("uvIndexAvg"));
        int uvIndexMax = Integer.parseInt(forecastValues.get("uvIndexMax"));
        Double humidityMin = Double.parseDouble(forecastValues.get("humidityMin"));
        Double humidityAvg = Double.parseDouble(forecastValues.get("humidityAvg"));
        Double humidityMax = Double.parseDouble(forecastValues.get("humidityMax"));



        System.out.println("Here is the forecast for %s: (format: Low - Average - High)".formatted(day));
        System.out.println(String.format("Temperature: %.2f\u00B0F - %.2f\u00B0F - %.2f\u00B0F", minTemp, avgTemp, maxTemp));
        System.out.println(String.format("UV: %d - %d - %d", uvIndexMin, uvIndexAvg, uvIndexMax));
        System.out.println(String.format("Humidity: %f - %f - %f", humidityMin, humidityAvg, humidityMax));


        System.out.println(String.format(""));



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
