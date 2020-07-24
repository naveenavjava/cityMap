package com.mastercard.citymap.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mastercard.citymap.data.CityMap;

/**
 * {@inheritDoc}
 */
@Service
public class MapServiceImpl implements MapService{

    private static final Logger log = LoggerFactory.getLogger(MapServiceImpl.class);
    private static final String PATH_EXISTS = "YES";
    private static final String PATH_DOES_NOT_EXISTS = "NO";
    
    @Autowired CityMap cityMap;
    
    @Override
    @NonNull
    /**
     * {@inheritDoc}
     */
    public String connectedCities(final String origin, final String destination) {
        log.info("find if cities {} and {} are connected", origin, destination);
        
        //Validate cities
        if(!cityMap.cityExists(origin) || !cityMap.cityExists(destination)) {
            return PATH_DOES_NOT_EXISTS;
        }
        
        // get all paths available between given cities
        List<List<String>> paths = getAllPaths(origin, destination);
        String response = CollectionUtils.isEmpty(paths) ?  PATH_DOES_NOT_EXISTS : PATH_EXISTS;
        
        log.info("Response : Cities {} and {} are connected : {}", origin, destination, response); 
        return response;
    }
 
    /**
     * Finds all paths between given origin and destination cities
     * @param origin origin city
     * @param destination destination city
     * @return list of available paths
     */
    private List<List<String>> getAllPaths(final String origin, final String destination) {
        List<List<String>> paths = new ArrayList<>();
        findPath(origin, destination, paths, new LinkedHashSet<String>());
        return paths;
    }

    /**
     * Finds path between given origin and destination cities. It is called recursively to iterate through all available paths
     * @param origin origin city
     * @param destination destination city
     * @param paths list of paths that will be populated based on availability
     * @param path path between cities
     */
    private void findPath(final String origin, final String destination, List<List<String>> paths, LinkedHashSet<String> path) {
        path.add(origin);
        
        if (origin.equals(destination)) {
            paths.add(new ArrayList<String>(path));
            path.remove(origin);
            return;
        }

        final Set<String> cityPaths  = cityMap.getPathsFrom(origin).keySet();
        cityPaths.stream().forEach(city -> {
                if(!path.contains(city)) {
                    findPath(city, destination, paths, path);
                }
            });
        
        path.remove(origin);
    }    
}
