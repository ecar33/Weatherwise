package src;

import java.io.IOException;
import java.util.Scanner;

import src.api.*;
import src.data.*;
import src.view.WeatherFormatter;

public class WeatherCLI {
  private Scanner input;
  private String location;
  private String locationName;
  private String unit;
  private DailyForecast forecast;
  private String defaultLocation = "omaha";
  private WeatherFormatter wFormatter = new WeatherFormatter();
  private static LocationReaderWriter lrw = new LocationReaderWriter();

  public WeatherCLI() {
    this.input = new Scanner(System.in);
    this.location = lrw.loadLocationPreference();
    if (this.location == null || this.location.isEmpty()) {
      this.location = defaultLocation;
      lrw.writeLocationPreference(this.location);
    }
    this.unit = "imperial";
  }

  private void printLogo() {

    String logo = """
           _      __            __   __                     _
          | | /| / /___  ___ _ / /_ / /  ___  ____ _    __ (_)___ ___
          | |/ |/ // -_)/ _ `// __// _ \\/ -_)/ __/| |/|/ // //_ // -_)
          |__/|__/ \\__/ \\_,_/ \\__//_//_/\\__//_/   |__,__//_/ /__/\\__/
        """;

    System.out.println(logo);
  }

  private void terminate() {
    System.out.println("Thank you for using Weatherwise!");
    System.exit(0);
  }

  public void changeLocation() {
    String newLocation = WeatherCLI.lrw.getUserLocation();
    this.location = newLocation;
    WeatherCLI.lrw.writeLocationPreference(location);
  }

  public String getUnit() {
    return this.unit;
  }

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

  private DailyForecast getWeatherForecast(int dayIndex) {
    try {
      WeatherApiClient wapi = new WeatherApiClient();
      WeatherResponse response = wapi.weatherApiCall(this.location, this.unit);

      this.locationName = response.getLocationName();
      forecast = response.getDailyForecast(dayIndex);
      return forecast;

    } catch (IOException e) {
      System.out.println(e);
    } catch (InterruptedException e) {
      System.out.println(e);
    }
    return null;

  }

  private void menu() {
    int userInput = 0;
    boolean validInput;

    while (true) {
      System.out.println("""
          Welcome to Weatherwise, your CLI Weather Companion!
          Please select an option by typing the corresponding number:
          1. Current Weather Forecast
          2. Tomorrow's Forecast
          3. 5 Day Forecast
          4. Weather Alerts and Warnings
          5. Change location (Current: %s)
          6. Change units (Current: %s)
          7. Exit""".formatted(this.location, this.unit));

      validInput = false;
      while (!validInput) {
        try {
          userInput = Integer.parseInt(input.nextLine().trim());

          if (userInput < 1 || userInput > 7) {
            System.out.println("Please input a number between 1 and 7.");
          } else {
            validInput = true;
          }

        } catch (NumberFormatException e) {
          System.out.println("Failed to get correct input, try again.");
        }
      }

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
          break;
        case 4:
          break;
        case 5:
          changeLocation();
          break;
        case 6:
          changeUnits();
          System.out.println(getUnit());
          break;
        case 7:
          terminate();
          break;
      }

    }
  }

  public static void main(String[] args) {
    WeatherCLI wcli = new WeatherCLI();
    wcli.printLogo();
    wcli.menu();
  }
}
