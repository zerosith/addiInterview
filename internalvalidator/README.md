# Internal Validator Service

To run internalValidator service do the following

## Pre requisites
- Maven 3.8.3
- JDK 17.0.1
- Docker

## Features
- Uses hibernate validators to check data types and constrainst of the lead domain object

## Run the code
Go to root folder internalvalidator and run:
```sh
mvn clean install
```
After that the jar `internalvalidator-0.0.1-SNAPSHOT.jar` is generated in the **internalvalidator\target** folder
Go to the folder **internalvalidator\target** and run:
```sh
java -jar internalvalidator-0.0.1-SNAPSHOT.jar --server.port=8081
```
This will start a springboot microservice in the port `8081`

## Postman Support
Calls to this api are supported in the provided postan collection under the folder **internalValidator** in postman

## Logging

This service uses **LOG4j2** with asynchronous logging and low latency
This service  also logs in a file `app.log` in the `target/logs` folder and to standard output

## Docker Support (current use)
This service has a `Dockerfile`.
To build the docker image install docker in your local machine in this folder run:
```sh
mvn clean install
docker build -t internal-validator-service .
docker run -p 8081:8081 internal-validator-service
```
This will start the container internal-validator-service in your local machine with port 8081

Then use postman collection to interact with this service