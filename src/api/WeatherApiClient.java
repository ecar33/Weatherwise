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

    public WeatherResponse weatherApiCall(String location) throws IOException, InterruptedException {
        String API_KEY = ApiKeyReader.get();
        String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);

        URI uri = URI.create(String.format("%s?apikey=%s&location=%s", BASE_URL, API_KEY, encodedLocation));

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .header("User-Agent", "Weatherwise")
                .header("content-type", "application/json")
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("HTTP Status Code: " + httpResponse.statusCode());

        WeatherResponse weatherResponse = mapper.readValue(httpResponse.body(), WeatherResponse.class);
        weatherResponse.setFetchedAt(LocalDateTime.now()); // Assuming setFetchedAt takes a LocalDateTime argument

        return weatherResponse;
    }
}
