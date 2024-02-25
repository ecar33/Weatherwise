package src.view;

import java.util.Map;
import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;
import src.data.*;
import src.DateFormatter;

public class WeatherFormatter {
    private Map<String, String> weatherDescriptions;
    private Scanner input;

    public WeatherFormatter() {
        this.weatherDescriptions = Map.ofEntries(
                new SimpleEntry<>("0", "Unknown"),
                new SimpleEntry<>("1000", "Clear, Sunny"),
                new SimpleEntry<>("1100", "Mostly Clear"),
                new SimpleEntry<>("1101", "Partly Cloudy"),
                new SimpleEntry<>("1102", "Mostly Cloudy"),
                new SimpleEntry<>("1001", "Cloudy"),
                new SimpleEntry<>("2000", "Fog"),
                new SimpleEntry<>("2100", "Light Fog"),
                new SimpleEntry<>("4000", "Drizzle"),
                new SimpleEntry<>("4001", "Rain"),
                new SimpleEntry<>("4200", "Light Rain"),
                new SimpleEntry<>("4201", "Heavy Rain"),
                new SimpleEntry<>("5000", "Snow"),
                new SimpleEntry<>("5001", "Flurries"),
                new SimpleEntry<>("5100", "Light Snow"),
                new SimpleEntry<>("5101", "Heavy Snow"),
                new SimpleEntry<>("6000", "Freezing Drizzle"),
                new SimpleEntry<>("6001", "Freezing Rain"),
                new SimpleEntry<>("6200", "Light Freezing Rain"),
                new SimpleEntry<>("6201", "Heavy Freezing Rain"),
                new SimpleEntry<>("7000", "Ice Pellets"),
                new SimpleEntry<>("7101", "Heavy Ice Pellets"),
                new SimpleEntry<>("7102", "Light Ice Pellets"),
                new SimpleEntry<>("8000", "Thunderstorm"));
    }

    public void printDayForecast(DailyForecast todaysForecast, int dailyIndex, String location) {

        String day = dailyIndex == 0 ? "today" : "tomorrow";

        Map<String, String> forecastValues = todaysForecast.getValues();

        String weatherCode = forecastValues.get("weatherCodeMin");
        String weatherDescription = getWeatherDescription(weatherCode);

        Double avgTemp = Double.parseDouble(forecastValues.get("temperatureAvg"));
        Double maxTemp = Double.parseDouble(forecastValues.get("temperatureMax"));
        Double minTemp = Double.parseDouble(forecastValues.get("temperatureMin"));
        int uvIndexMin = Integer.parseInt(forecastValues.get("uvIndexMin"));
        int uvIndexAvg = Integer.parseInt(forecastValues.get("uvIndexAvg"));
        int uvIndexMax = Integer.parseInt(forecastValues.get("uvIndexMax"));
        Double humidityMin = Double.parseDouble(forecastValues.get("humidityMin"));
        Double humidityAvg = Double.parseDouble(forecastValues.get("humidityAvg"));
        Double humidityMax = Double.parseDouble(forecastValues.get("humidityMax"));

        System.out.println("Here is %s's forecast for:".formatted(day));
        System.out.println(location + '\n');
        System.out.println("Forecast: %s".formatted(weatherDescription));
        System.out.println(
                String.format("Temperature: %.2f\u00B0F - %.2f\u00B0F - %.2f\u00B0F", minTemp, avgTemp, maxTemp));
        System.out.println(String.format("UV: %d - %d - %d", uvIndexMin, uvIndexAvg, uvIndexMax));
        System.out.println(String.format("Humidity: %.2f - %.2f - %.2f", humidityMin, humidityAvg, humidityMax));
        System.out.println("(format: Low - Average - High)");
        System.out.println("");

        System.out.println("Press q to return to the menu");
    }

    public void printFiveDayForecast(DailyForecast[] fiveDayForecast, String location) {
        String userInput = "";
        Integer pageIndex = 0;
        boolean validInput;
        this.input = new Scanner(System.in);

        System.out.println("Here is the five day forecast for:");
        System.out.println(location + '\n');

        do {
            DailyForecast forecast = fiveDayForecast[pageIndex];

            Map<String, String> forecastValues = forecast.getValues();

            String weatherCode = forecastValues.get("weatherCodeMin");
            String weatherDescription = getWeatherDescription(weatherCode);
            String time = forecast.getTime();
            String formattedTime = DateFormatter.formatToMonthDay(time);

            Double avgTemp = safeParseDouble(forecastValues.get("temperatureAvg"), 0.0);
            Double maxTemp = safeParseDouble(forecastValues.get("temperatureMax"), 0.0);
            Double minTemp = safeParseDouble(forecastValues.get("temperatureMin"), 0.0);
            int uvIndexMin = safeParseInt(forecastValues.get("uvIndexMin"), 0);
            int uvIndexAvg = safeParseInt(forecastValues.get("uvIndexAvg"), 0);
            int uvIndexMax = safeParseInt(forecastValues.get("uvIndexMax"), 0);
            Double humidityMin = safeParseDouble(forecastValues.get("humidityMin"), 0.0);
            Double humidityAvg = safeParseDouble(forecastValues.get("humidityAvg"), 0.0);
            Double humidityMax = safeParseDouble(forecastValues.get("humidityMax"), 0.0);

            System.out.println("Forecast for %s: %s".formatted(formattedTime, weatherDescription));
            System.out.println(
                    String.format("Temperature: %.2f°F - %.2f°F - %.2f°F", minTemp, avgTemp, maxTemp));
            System.out.println(String.format("UV: %d - %d - %d", uvIndexMin, uvIndexAvg, uvIndexMax));
            System.out
                    .println(String.format("Humidity: %.2f - %.2f - %.2f", humidityMin, humidityAvg,
                            humidityMax));
            System.out.println("(format: Low - Average - High)");
            System.out.println("");

            System.out.println("Press p to page, or q to return to the menu");
            userInput = input.nextLine().toLowerCase().strip();
            validInput = false;

            if (userInput.equals("p")) {
                pageIndex = (pageIndex + 1) % (fiveDayForecast.length - 1);
            } else if (userInput.equals("q")) {
                break;
            } else {
                validInput = false;
                while (!validInput) {
                    System.out.println("Please only input 'p' or 'q'");
                    userInput = input.nextLine().toLowerCase().strip();
                    if (userInput.equals("p") || userInput.equals("q")) {
                        validInput = true;
                    }
                }
            }
        } while (!userInput.equals("q"));
    }

    public void printAlertsAndWarnings() {

    }

    public String getWeatherDescription(String weatherCode) {
        return weatherDescriptions.get(weatherCode);
    }

    private int safeParseInt(String str, int defaultValue) {
        if (str != null && !str.isEmpty()) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing integer: " + e.getMessage());
            }
        }
        return defaultValue;
    }

    private double safeParseDouble(String str, double defaultValue) {
        try {
            return str != null ? Double.parseDouble(str) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
