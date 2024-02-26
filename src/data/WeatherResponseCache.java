package src.data;

import java.util.HashMap;
import java.util.Map;

public class WeatherResponseCache {
    private static class CacheKey {
        String location;
        String unit;

        CacheKey(String location, String unit) {
            this.location = location;
            this.unit = unit;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            CacheKey cacheKey = (CacheKey) o;
            return location.equals(cacheKey.location) && unit.equals(cacheKey.unit);
        }

        @Override
        public int hashCode() {
            return 31 * location.hashCode() + unit.hashCode();
        }
    }

    private final Map<CacheKey, WeatherResponse> cache = new HashMap<>();

    public void cacheResponse(String location, String unit, WeatherResponse response) {
        CacheKey key = new CacheKey(location, unit);
        cache.put(key, response);
    }

    public WeatherResponse getResponse(String location, String unit) {
        CacheKey key = new CacheKey(location, unit);
        if (cache.containsKey(key)) {
            WeatherResponse response = cache.get(key);
            // Check if the cached response is expired
            if (!response.isExpired()) {
                return response;
            } else {
                // Handle expired response, e.g., by removing it from the cache
                cache.remove(key);
                return null;
            }
        } else {
            return null;
        }
    }
}