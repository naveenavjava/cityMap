package com.mastercard.citymap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Application to find if path exist between given cities. Cities and path info is loaded from city.txt file.
 */
@SpringBootApplication
public class CitymapApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitymapApplication.class, args);
	}

}
