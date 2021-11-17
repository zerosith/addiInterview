# External Criminal Records service

Stub service to simulate connection to Criminal Registry database


This service is a springboot app enabled eureka client that connects to the
eureka server in the ip specified in the application.properties file


The CriminalRecordsValidatorController class is the responsible for the microservice response
In this case this response is true or false generated randomly

## Pre requisites



## Installation

To run this application as a [docker](https://www.docker.com/) container and connect it to the eureka client do the following:

In this root folder run:
```bash
mvn clean install
docker build -t criminal-validator-service .
docker run -p 8084:8084 criminal-validator-service
```