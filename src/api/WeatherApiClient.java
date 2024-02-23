package src.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;

import src.data.*;

import com.fasterxml.jackson.databind.DeserializationFeature;

public class WeatherApiClient {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;

    public WeatherResponse weatherApiCall(String location) throws IOException, InterruptedException {

        String API_KEY = ApiKeyReader.get();

        String query_parameters = String.format("apikey=%s&location=%s", API_KEY, location);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format("https://api.tomorrow.io/v4/weather/forecast?%s", query_parameters)))
                .setHeader("User-Agent", "Weatherwise") // add request header
                .setHeader("content-type", "application/json")
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // // print response headers
        // HttpHeaders headers = response.headers();
        // headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

        // print status code

        System.out.println(httpResponse.statusCode());

        WeatherResponse weatherResponse = mapper.readValue(httpResponse.body(), WeatherResponse.class);
        weatherResponse.setFetchedAt();

        // Get 'values'
        // Map<String, String> values =
        // weatherResponse.getTimelines().getDaily()[1].getValues();

        return weatherResponse;

    }
}