# Service registry service



## Pre requisites
- Maven 3.8.3
- JDK 17.0.1


This is an eureka server that will register the addiInternalValidator Service and the external validator service

To run this serviceRegistry server:
```sh
mvn clean install
```
go to target folder generated from this previous command and run:

```sh
java -jar serviceregistry-0.0.1-SNAPSHOT.jar
```

This will start a local java app listening to port `8761`


## Docker support

To compile latest changes:
```sh
mvn clean install
```

To build the image:
```sh
docker build -t service-registry-service .
```

To run the image
```sh
docker run -p 8761:8761 service-registry-service
```

after this service will be available under 
http://localhost:8761


From there you can 











