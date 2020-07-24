package com.mastercard.citymap.service;

/**
 * Service to handle Map related services
 */
public interface MapService{

    /**
     * Checks if paths are available between given origin and destination cities
     * @param origin origin city
     * @param destination destination city
     * @return YES (if paths exists), NO (if paths do not exist)
     */
    String connectedCities(final String origin, final String destination);
    
}
