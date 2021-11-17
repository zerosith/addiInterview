# Validator Tester service

This is an eureka client sprinboot application that connects to the service discovery server.

This service has the uses cases to test

To run the Validator tester service:

in the root folder run

```sh
mvn clean install
docker build -t validator-tester-service .
docker run -p 8083:8083 validator-tester-service
```
