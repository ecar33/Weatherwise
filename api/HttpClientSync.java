package api;

//usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.fasterxml.jackson.core:jackson-databind:2.13.0
//DEPS com.fasterxml.jackson.core:jackson-core:2.13.0
//DEPS com.fasterxml.jackson.core:jackson-annotations:2.13.0
//SOURCES ApiKeyReader.java


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

public class HttpClientSync {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void main(String[] args) throws IOException, InterruptedException {

        String API_KEY = ApiKeyReader.get();
        String location = "omaha";

        String query_parameters = String.format("apikey=%s&location=%s", API_KEY, location);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format("https://api.tomorrow.io/v4/weather/forecast?%s", query_parameters)))
                .setHeader("User-Agent", "Weatherwise") // add request header
                .setHeader("content-type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // // print response headers
        // HttpHeaders headers = response.headers();
        // headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

        // print status code
        System.out.println(response.statusCode());

        // print response body
        HttpClientSync.toMap(response.body());

    }

    public static void toMap(String jsonString) throws JsonProcessingException {
        // Create an ObjectMapper instance
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = mapper.readValue(jsonString, Map.class);

        // // Print the resulting map to the console
        // System.out.println(result.get("location"));
    }
}