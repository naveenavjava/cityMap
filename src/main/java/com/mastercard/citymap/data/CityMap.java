package com.mastercard.citymap.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Data object to hold and process cities and paths data
 */
public class CityMap {

    private final Map<String, Map<String, Double>> citiesPathInfo = new HashMap<>();

    /**
     * Adds given city to citiesPathInfo object
     * @param city
     * @return true if added, false if already exists and not added
     */
    public boolean addCity(String city) {
        if (citiesPathInfo.containsKey(city.toLowerCase())) return false;

        citiesPathInfo.put(city.toLowerCase(), new HashMap<String, Double>());
        return true;
    }

    /**
     * Checks if a city exists in the citiesPathInfo object
     * @param city
     * @return true if exists, false if does not exists
     */
    public boolean cityExists(String city) {
        return citiesPathInfo.get(city.toLowerCase()) != null;
    }
    
    /**
     * Adds path between origin and destination cities to citiesPathInfo object
     * @param origin origin city
     * @param destination destination city
     */
    public void addPath(String origin, String destination) {
        citiesPathInfo.get(origin.toLowerCase()).put(destination.toLowerCase(), 0.0);
        citiesPathInfo.get(destination.toLowerCase()).put(origin.toLowerCase(), 0.0);
    }

    /**
     * Removes path between origin and destination cities from citiesPathInfo object
     * @param origin origin city
     * @param destination destination city
     */
    public void removePath (String origin, String destination) {
        citiesPathInfo.get(origin.toLowerCase()).remove(destination.toLowerCase());
    }

    /**
     * Get available paths from given city
     * @param city
     * @return available paths
     */
    public Map<String, Double> getPathsFrom(String city) {
        return Collections.unmodifiableMap(citiesPathInfo.get(city.toLowerCase()));
    }
    
}