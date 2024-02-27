package src.api;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

import src.data.WeatherResponse;

/**
 * Client for making API calls to a weather forecasting service.
 */
public class WeatherApiClient {

    private static final String BASE_URL = "https://api.tomorrow.io/v4/weather/forecast"; // The base URL for the weather API.
    private static final HttpClient httpClient = HttpClient.newBuilder() // The HTTP client for making requests.
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
    private static final ObjectMapper mapper = new ObjectMapper() // JSON mapper for converting between JSON strings and Java objects.
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Makes an API call to get weather forecast data for a specified location and unit.
     * @param location The location for which to get the weather forecast.
     * @param unit The unit of measurement for the forecast (e.g., metric or imperial).
     * @return A WeatherResponse object containing the forecast data, or null if an error occurs.
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the operation is interrupted.
     */
    public WeatherResponse weatherForecastApiCall(String location, String unit) throws IOException, InterruptedException {
        String API_KEY = ApiKeyReader.get(); // Retrieve the API key.
        String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8); // Encode the location for URL compatibility.
        String encodedUnit = URLEncoder.encode(unit, StandardCharsets.UTF_8); // Encode the unit for URL compatibility.

        URI uri = URI.create(String.format("%s?apikey=%s&location=%s&units=%s", BASE_URL, API_KEY, encodedLocation, encodedUnit)); // Construct the URI for the API request.

        HttpRequest request = HttpRequest.newBuilder() // Build the HTTP request.
                        .GET()
                        .uri(uri)
                        .header("User-Agent", "Weatherwise") // Set a custom User-Agent header.
                        .header("content-type", "application/json") // Specify the content type as JSON.
                        .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString()); // Send the request and get the response.

        Integer httpStatusCode = httpResponse.statusCode(); // Get the HTTP status code from the response.

        if (httpStatusCode.equals(200)) { // Check if the response status code is 200 (OK).
            WeatherResponse weatherResponse = mapper.readValue(httpResponse.body(), WeatherResponse.class); // Parse the JSON response into a WeatherResponse object.
            weatherResponse.setFetchedAt(LocalDateTime.now()); // Set the time the data was fetched.
            return weatherResponse; // Return the parsed weather response.
        } else {
            System.out.println("Something went wrong, error code: " + httpStatusCode); // Log an error if the status code is not 200.
            return null; // Return null to indicate an error occurred.
        }
    } 
}
