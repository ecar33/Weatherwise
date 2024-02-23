package src;

import java.io.IOException;
import java.util.Scanner;

import src.api.*;
import src.data.*;
import src.view.WeatherFormatter;

public class WeatherCLI {
  private Scanner input;
  private String location = "omaha";
  private WeatherFormatter wFormatter = new WeatherFormatter();

  public WeatherCLI() {
    this.input = new Scanner(System.in);
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

    System.out.println("""
        Welcome to Weatherwise, your CLI Weather Companion!
        Please select an option by typing the corresponding number:
        1. Current Weather Forecast
        2. Short-term Forecast (Next 24 hours)
        3. Weekly Forecast (Next 7 days)
        4. Weather Alerts and Warnings
        5. Change location (Default: omaha)
        6. Exit""");

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
        break;
      case 6:
        terminate();
        break;
    }

  }

  public static void main(String[] args) {
    WeatherCLI wcli = new WeatherCLI();
    LocationReaderWriter locationReaderWriter = new LocationReaderWriter();
    locationReaderWriter.createLocationPreferenceFile();

    wcli.printLogo();
    wcli.menu();
  }
}
