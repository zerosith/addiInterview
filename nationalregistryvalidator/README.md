# National Registry Validator service

Stub service to simulate connection to National Registry database

This service is a springboot app enabled eureka client that connects to the
eureka server in the ip specified in the application.properties file

National 


## Installation

To run this application as a docker container and connect it to the eureka client do the following

In this root folder run:
```bash
mvn clean install
docker build -t national-validator-service .
docker run -p 8082:8082 national-validator-service
```
