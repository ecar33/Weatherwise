# Weatherwise

### Project Overview
This project involves developing a command-line interface (CLI) application for consuming a weather API. The application aims to provide users with real-time weather data, including temperature, humidity, precipitation, and more, based on their location or a specified city.

### Chosen Project Idea
The core idea of this project is to create a simple yet effective weather information tool that can be used via the command line. This tool will fetch weather data from a public weather API and present it in an easy-to-read format on the terminal.

1. ### Simplifications and Enhancements
While the project will primarily focus on CLI functionality, the following enhancements and simplifications are planned:

2. ### Enhanced Data Presentation: 
Improve the way weather data is displayed in the command line, making it more user-friendly and easier to read.

3. ### Location-Based Weather Data: 
Allow users to input their location (or any location of interest) to fetch weather data specific to that area.

4. ### Weather Trends: 
Implement functionality to show short-term weather trends, like the forecast for today, tomorrow, or the next few days, based on user selection.

5. ### Caching Mechanism: 
Implement a caching mechanism that temporarily stores recent weather data, reducing the frequency of API calls.

6. ### Error Handling Improvements: 
Enhanced error handling to gracefully manage situations like API downtime, invalid user inputs, or network issues.

7. ### CLI-Based: 
The application will be purely command-line based, without a graphical user interface (GUI), to keep the focus on backend functionality and simplify the development process.

***
The application will limit its scope to basic weather parameters to keep the API consumption and data processing straightforward.
***


### To compile: 
git clone git@github.com:unocsci2830/csci2830sp24pa1-ecar33.git
cd csci2830sp24pa1-ecar33
git checkout compiled-version
git pull origin compiled-version
java -cp "Weatherwise.jar:lib/*" weather.WeatherCLI
