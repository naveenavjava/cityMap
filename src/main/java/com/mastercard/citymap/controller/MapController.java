package com.mastercard.citymap.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.citymap.service.MapService;

/**
 * Controller class to handle Map services
 */
@RestController()
public class MapController {
    
    private static final Logger log = LoggerFactory.getLogger(MapController.class);
    
    @Autowired MapService mapService;
    
    /**
     * Service end point to find if the given cities are connected.
     * 
     * @api {get} /connected?origin=:originCity&destination=:destinationCity
     * @apiExample {curl} Example usage: 
     *      curl -X GET http://localhost:8080//connected?origin=Boston&destination=Newark
     * 
     * @param origin origin city
     * @param destination destination city
     * @return YES (If cities are connected)/ NO (If cities are not connected)
     */
    @GetMapping(path = "/connected", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> connectedCities(@RequestParam(name = "origin") final String origin, @RequestParam(name = "destination") final String destination) {
        log.info("Received request for connected cities. Origin {}, Destination {}", origin, destination);
        return ResponseEntity.ok(mapService.connectedCities(origin.toLowerCase(), destination.toLowerCase()));
    }
}
