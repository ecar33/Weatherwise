package src;

import java.io.IOException;
import java.util.Scanner;

import src.api.*;
import src.data.*;
import src.view.WeatherFormatter;

/**
 * Main class for the command line interface (CLI) version of the Weather
 * application.
 * It provides functionalities such as displaying current weather, forecasts,
 * changing
 * location, and units.
 */
public class WeatherCLI {
  private Scanner input;
  private String location;
  private String locationName;
  private String unit;
  private DailyForecast forecast;
  private DailyForecast[] fiveDayForecast;
  private String defaultLocation = "omaha";
  private WeatherFormatter wFormatter = new WeatherFormatter();
  private static LocationReaderWriter lrw = new LocationReaderWriter();
  private WeatherResponseCache wrc;

  /**
   * Constructor for WeatherCLI. Initializes the application with user's location
   * preference
   * or a default location, sets the default unit to imperial, and initializes the
   * cache.
   */
  public WeatherCLI() {
    this.input = new Scanner(System.in);
    this.location = lrw.loadLocationPreference();
    if (this.location == null || this.location.isEmpty()) {
      this.location = defaultLocation;
      lrw.writeLocationPreference(this.location);
    }
    this.unit = "imperial";
    this.wrc = new WeatherResponseCache();
  }

  /**
   * Prints the application logo to the console.
   */
  private void printLogo() {
    String logo = """
           _      __            __   __                     _
          | | /| / /___  ___ _ / /_ / /  ___  ____ _    __ (_)___ ___
          | |/ |/ // -_)/ _ `// __// _ \\/ -_)/ __/| |/|/ // //_ // -_)
          |__/|__/ \\__/ \\_,_/ \\__//_//_/\\__//_/   |__,__//_/ /__/\\__/
        """;

    System.out.println(logo);
  }

  /**
   * Terminates the application.
   */
  private void terminate() {
    System.out.println("Thank you for using Weatherwise!");
    System.exit(0);
  }

  /**
   * Allows the user to change the current location for weather forecasts.
   */
  public void changeLocation() {
    String newLocation = WeatherCLI.lrw.getUserLocation();
    this.location = newLocation;
    WeatherCLI.lrw.writeLocationPreference(location);
  }

  /**
   * Returns the current unit of measurement for weather data.
   *
   * @return A string representing the current unit ("imperial" or "metric").
   */
  public String getUnit() {
    return this.unit;
  }

  /**
   * Allows the user to change the unit of measurement for weather data.
   */
  public void changeUnits() {
    boolean isValid = false;
    System.out.println("""
        Select a unit:
        (1) Imperial
        (2) Metric """);
    while (!isValid) {
      try {
        int userInput = Integer.parseInt(input.nextLine().trim());

        switch (userInput) {
          case 1:
            this.unit = "imperial";
            isValid = true;
            break;
          case 2:
            this.unit = "metric";
            isValid = true;
            break;
          default:
            System.out.println("Please select choice 1 or 2.");
            break;
        }
      } catch (NumberFormatException e) {
        System.out.println("Please select choice 1 or 2.");
      }
    }
  }

  /**
   * Fetches the weather forecast for a specified day index.
   *
   * @param dayIndex The index of the day for which the forecast is requested (0
   *                 for current day).
   * @return A {@link DailyForecast} object containing the forecast data.
   */
  private DailyForecast getWeatherForecast(int dayIndex) {
    WeatherResponse response = wrc.getResponse(this.location, this.unit);

    if (response == null) {
      try {
        WeatherApiClient wapi = new WeatherApiClient();
        response = wapi.weatherForecastApiCall(this.location, this.unit);
        wrc.cacheResponse(this.location, this.unit, response);
      } catch (IOException | InterruptedException e) {
        System.out.println(e);
        return null;
      }
    }
    this.locationName = response.getLocationName();
    return response.getDailyForecast(dayIndex);
  }

  /**
   * Fetches the five-day weather forecast.
   *
   * @return An array of {@link DailyForecast} objects containing the forecast
   *         data for five days.
   */
  private DailyForecast[] getFiveDayForecasts() {
    WeatherResponse response = wrc.getResponse(this.location, this.unit);
    if (response == null) {
      try {
        WeatherApiClient wapi = new WeatherApiClient();
        response = wapi.weatherForecastApiCall(this.location, this.unit);
        wrc.cacheResponse(this.location, this.unit, response);
      } catch (IOException | InterruptedException e) {
        System.out.println(e);
        return null;
      }
    }
    this.locationName = response.getLocationName();
    return response.getFiveDayForecast();
  }

  /**
   * Displays the main menu and handles user input to navigate through the
   * application's features.
   */
  private void menu() {
    while (true) {
      System.out.println("""
          Welcome to Weatherwise, your CLI Weather Companion!
          Please select an option by typing the corresponding number:
          1. Current Weather Forecast
          2. Tomorrow's Forecast
          3. 5 Day Forecast
          4. Change location (Current: %s)
          5. Change units (Current: %s)
          6. Exit""".formatted(this.location, this.unit));

      int userInput = 0;
      boolean validInput = false;

      while (!validInput) {
        try {
          userInput = Integer.parseInt(input.nextLine().trim());

          if (userInput < 1 || userInput > 6) {
            System.out.println("Please input a number between 1 and 6.");
          } else {
            validInput = true;
          }

        } catch (NumberFormatException e) {
          System.out.println("Failed to get correct input, try again.");
        }
      }

      handleMenuSelection(userInput);
    }
  }

  /**
   * Handles the user's menu selection.
   *
   * @param userInput The user's menu selection.
   */
  private void handleMenuSelection(int userInput) {
    int dailyIndex = 0;
    String userInputString = "";

    switch (userInput) {
      case 1:
        dailyIndex = 0;
        forecast = getWeatherForecast(dailyIndex);
        wFormatter.printDayForecast(forecast, dailyIndex, this.locationName);
        while (!userInputString.equals("q")) {
          userInputString = input.nextLine().trim();
        }
        break;
      case 2:
        dailyIndex = 1;
        forecast = getWeatherForecast(dailyIndex);
        wFormatter.printDayForecast(forecast, dailyIndex, this.locationName);
        while (!userInputString.equals("q")) {
          userInputString = input.nextLine().trim();
        }
        break;
      case 3:
        fiveDayForecast = getFiveDayForecasts();
        wFormatter.printFiveDayForecast(fiveDayForecast, this.locationName);
        break;
      case 4:
        changeLocation();
        break;
      case 5:
        changeUnits();
        break;
      case 6:
        terminate();
        break;
    }
  }

  /**
   * Main method to run the WeatherCLI application.
   *
   * @param args Command line arguments (not used).
   */
  public static void main(String[] args) {
    WeatherCLI wcli = new WeatherCLI();
    wcli.printLogo();
    wcli.menu();
  }
}
