import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


public class WeatherCLI {
    public static void main(String[] args) {

        String logo = """
               _      __            __   __                     _         
              | | /| / /___  ___ _ / /_ / /  ___  ____ _    __ (_)___ ___ 
              | |/ |/ // -_)/ _ `// __// _ \\/ -_)/ __/| |/|/ // //_ // -_)
              |__/|__/ \\__/ \\_,_/ \\__//_//_/\\__//_/   |__,__//_/ /__/\\__/ 
            """;

        System.out.println(logo);
}
}

