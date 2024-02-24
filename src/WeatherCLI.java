package src;

import java.io.IOException;
import java.util.Scanner;

import src.api.*;
import src.data.*;
import src.view.WeatherFormatter;

public class WeatherCLI {
  private Scanner input;
  private String location;
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

  private DailyForecast getCurrentWeatherConditions() {
    int dayIndex = 0;

    try {
      WeatherApiClient wapi = new WeatherApiClient();
      WeatherResponse response = wapi.weatherApiCall(location);

      DailyForecast forecast = response.getDailyForecast(dayIndex);
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
          2. Short-term Forecast (Next 24 hours)
          3. Weekly Forecast (Next 7 days)
          4. Weather Alerts and Warnings
          5. Change location (Current location = %s)
          6. Exit""".formatted(this.location));

      validInput = false;
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

      switch (userInput) {
        case 1:
          DailyForecast todaysForecast = getCurrentWeatherConditions();
          wFormatter.printCurrentForecast(todaysForecast);
          break;
        case 2:
          break;
        case 3:
          break;
        case 4:
          break;
        case 5:
          changeLocation();
          break;
        case 6:
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
