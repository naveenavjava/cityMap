package com.mastercard.citymap.config;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import com.mastercard.citymap.data.CityMap;

/**
 * Configuration class to load cities from text file. This class is loaded only once during server startup.
 * If there is an error loading the cities data, the application will not start
 */
@Configuration
public class MapConfig{
    private static final Logger log = LoggerFactory.getLogger(MapConfig.class);
    
    private static final String CITY_FILE = "city.txt";
    private static final String DELIMITER = ",";

    /**
     * Loads comma separated cities data from city.txt file and creates a bean with available cities and paths between them
     * @return CityMap
     * @throws IOException
     * @throws URISyntaxException
     */
    @Bean
    @NonNull
    public CityMap citiesMap() throws IOException, URISyntaxException {
        CityMap cityMap = new CityMap();
        try (Stream<String> lines = Files.lines(Paths.get(ClassLoader.getSystemResource(CITY_FILE).toURI()))) {
            lines.filter(line -> line.contains(DELIMITER))
                 .forEach(line -> {
                     String[] cities = line.split(DELIMITER);
                     cityMap.addCity(cities[0].trim().toLowerCase()); // add city
                     cityMap.addCity(cities[1].trim().toLowerCase()); // add second city
                     cityMap.addPath(cities[0].trim().toLowerCase(),cities[1].trim().toLowerCase()); // add path between cities
                 });
        }
        log.info("CityMap loaded successfully from text file");
        return cityMap;
    }
}
