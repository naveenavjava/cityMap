package com.mastercard.citymap;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.CollectionUtils;

import com.mastercard.citymap.data.CityMap;

@SpringBootTest
@AutoConfigureMockMvc
class CitymapApplicationTests {

    private static final String PATH_EXISTS = "YES";
    private static final String PATH_DOES_NOT_EXISTS = "NO";
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CityMap cityMap;
    
    // These tests cover both controller and service class
    @Test
    public void findPath_Success() throws Exception {
        String origin = "Newark";
        String destination = "Boston";
        String url = String.format("/connected?origin=%s&destination=%s", origin, destination);
        this.mockMvc.perform(get(url)).andExpect(status().isOk())
                .andExpect(content().string(containsString(PATH_EXISTS)));
    }
	
    @Test
    public void findPath_Fail() throws Exception {
        String origin = "Newark";
        String destination = "Albany";
        String url = String.format("/connected?origin=%s&destination=%s", origin, destination);
        
        this.mockMvc.perform(get(url)).andExpect(status().isOk())
                .andExpect(content().string(containsString(PATH_DOES_NOT_EXISTS)));
    }

    // These test cover the data model functionality
    @Test
    public void testCityMap() {
        assertNotNull(cityMap);
        
        // Check citis exists
        assertTrue(cityMap.cityExists("Boston"));
        assertTrue(cityMap.cityExists("boSTon"));
        assertTrue(cityMap.cityExists("Orlando"));
        assertFalse(cityMap.cityExists("ashburn"));
        
        // Do not add duplicates
        assertTrue(cityMap.addCity("Fairfax"));
        assertFalse(cityMap.addCity("Fairfax"));
        
        //Add path between cities
        assertTrue(cityMap.addCity("ashburn"));
        assertTrue(cityMap.cityExists("ashburn"));
        cityMap.addPath("Fairfax", "Ashburn");
        assertFalse(CollectionUtils.isEmpty(cityMap.getPathsFrom("Fairfax")));
        
        // Remove path between cities
        cityMap.removePath("Fairfax", "Ashburn");
        assertTrue(CollectionUtils.isEmpty(cityMap.getPathsFrom("Fairfax")));
    }
}
