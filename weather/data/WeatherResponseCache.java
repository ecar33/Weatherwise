package weather.data;

import java.util.HashMap;
import java.util.Map;

/**
 * This class serves as a cache for weather responses to minimize redundant
 * network requests.
 * It uses a combination of location and unit (e.g., metric or imperial) as the
 * cache key.
 */
public class WeatherResponseCache {
    /**
     * A private static class used as the key for the cache.
     * It combines location and unit to uniquely identify a cache entry.
     */
    private static class CacheKey {
        String location;
        String unit;

        /**
         * Constructs a CacheKey with specified location and unit.
         * 
         * @param location The geographical location.
         * @param unit     The unit of measurement (e.g., metric or imperial).
         */
        CacheKey(String location, String unit) {
            this.location = location;
            this.unit = unit;
        }

        /**
         * Checks if this CacheKey is equal to another object.
         * 
         * @param o The object to compare with.
         * @return true if both objects are CacheKey with identical location and unit,
         *         false otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            CacheKey cacheKey = (CacheKey) o;
            return location.equals(cacheKey.location) && unit.equals(cacheKey.unit);
        }

        /**
         * Generates a hash code for this CacheKey.
         * 
         * @return The hash code.
         */
        @Override
        public int hashCode() {
            return 31 * location.hashCode() + unit.hashCode();
        }
    }

    // The cache storage, mapping CacheKeys to WeatherResponses.
    private final Map<CacheKey, WeatherResponse> cache = new HashMap<>();

    /**
     * Caches a WeatherResponse using location and unit as the key.
     * 
     * @param location The geographical location of the weather data.
     * @param unit     The unit of measurement for the weather data (e.g., metric or
     *                 imperial).
     * @param response The WeatherResponse to cache.
     */
    public void cacheResponse(String location, String unit, WeatherResponse response) {
        CacheKey key = new CacheKey(location, unit);
        cache.put(key, response);
    }

    /**
     * Retrieves a WeatherResponse from the cache, if available and not expired.
     * 
     * @param location The geographical location of the weather data.
     * @param unit     The unit of measurement for the weather data (e.g., metric or
     *                 imperial).
     * @return The cached WeatherResponse, or null if not found or expired.
     */
    public WeatherResponse getResponse(String location, String unit) {
        CacheKey key = new CacheKey(location, unit);
        if (cache.containsKey(key)) {
            WeatherResponse response = cache.get(key);
            // Check if the cached response is expired
            if (!response.isExpired()) {
                return response; // Return the cached response if it's still valid
            } else {
                // Handle expired response by removing it from the cache
                cache.remove(key);
                return null; // Indicate no valid cached response is available
            }
        } else {
            return null; // Indicate no cached response is available
        }
    }
}