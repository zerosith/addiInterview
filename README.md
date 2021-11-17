# Addi code Challenge

In this repo you will find some folders

The order of building this project is :

- serviceregistry   
- internalvalidator
- externalcriminalrecordsvalidator
- nationalregistryvalidator
- validatortesterservice

You need to enter the folders in the previous order and follow the instructions for that application


## Pre requisites
- Maven 3.8.3
- JDK 17.0.1
- Docker
- Postman


## Postamn support
Import the file `addiInterview.postman_collection.json` to postman to have the calls to test this code challende


## Troubleshoot:

### Problem:
Containers are not able to see each other
### Solution: 
Restart your docker local service


### Problem:
`internalvalidator` `externalcriminalrecordsvalidator` `nationalregistryvalidator` `validatortesterservice`
Don't connect to eureka server

### Solution
`internalvalidator` `externalcriminalrecordsvalidator` `nationalregistryvalidator` `validatortesterservice`
have their eureka server configuration path in src/main/resources/application.properties

You need to change the value of the property `eureka.client.service-url.defaultZone` to the url of the eureka server.

You can get this url when you open the eureka server:

![screenshot](/screenshot/screenshot.png)

Then change the value of the property to that url in all services (i.e. `internalvalidator` `externalcriminalrecordsvalidator` `nationalregistryvalidator` `validatortesterservice`)











