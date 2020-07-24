# CityMap Sample Application

This application is used to find if paths exists between given origin and destination city.

Run project using command : 
 - mvn spring-boot:run
 
API:
 - /connected?origin=:originCity&destination=:destinationCity
 - Example : curl -X GET http://localhost:8080/connected?origin=Boston&destination=Newark