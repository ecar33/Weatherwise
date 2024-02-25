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

public class WeatherApiClient {

        private static final String BASE_URL = "https://api.tomorrow.io/v4/weather/forecast";
        private static final HttpClient httpClient = HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_2)
                        .connectTimeout(Duration.ofSeconds(10))
                        .build();
        private static final ObjectMapper mapper = new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        public WeatherResponse weatherForecastApiCall(String location, String unit) throws IOException, InterruptedException {
                String API_KEY = ApiKeyReader.get();
                String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);
                String encodedUnit = URLEncoder.encode(unit, StandardCharsets.UTF_8);

                URI uri = URI.create(String.format("%s?apikey=%s&location=%s&units=%s", BASE_URL, API_KEY,
                                encodedLocation, encodedUnit));

                HttpRequest request = HttpRequest.newBuilder()
                                .GET()
                                .uri(uri)
                                .header("User-Agent", "Weatherwise")
                                .header("content-type", "application/json")
                                .build();

                HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                Integer httpStatusCode = httpResponse.statusCode();

                if (httpStatusCode.equals(200)) {
                        WeatherResponse weatherResponse = mapper.readValue(httpResponse.body(), WeatherResponse.class);
                        weatherResponse.setFetchedAt(LocalDateTime.now());
                        return weatherResponse;

                } else {
                        System.out.println("Something went wrong, error code: " + httpStatusCode);
                        return null;
                }

        } 
}
