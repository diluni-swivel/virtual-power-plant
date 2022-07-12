# virtual-power-plant
## Virtual Power Plant System - Backend Code Challenge 

***************************************************************************************************************************************************************

This solution can be further developed into a multiple power plant management system. 

Solution contains below technologies, considerations, features and documents;

1. Technologies - Java 11, SpringBoot 2.7.1, Maven 3.8.6, Postgresql 14, Lombok package.

2. You have to configure application.properties file with your database details (add your username and password), create database power_plant_db. 

3. Postman collection with response examples is under the "Virtual Power Plant.postman_collection".

4. One postal area can have multiple batteries.

5. Columns powerPlantId and isDecommissioned are there in the Battery as an initiation point to the multiple power plant management system.

6. Add Postal Area POST REST API - This allows user to add master data which are prerequired for run this solution.

7. Add Batteries POST REST API - This allows user to add batteries and response shows suceeded and failed battery creations.

8. Get Batteries GET REST API - This allows user to get alphabetically ordered battery name list for the given post code range with the relevant statistics.

******************************************************************************************************************************************************************