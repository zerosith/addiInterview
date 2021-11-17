
Pre requisites

Maven 3.8.3
JDK 17.0.1

This is an eureka server that will register the addiInternalValidator Service and the external validator service

To run this serviceRegistry server:

mvn clean install

go to target folder generated from this previous command and run:

java -jar serviceregistry-0.0.1-SNAPSHOT.jar

This will start a local java app listening to port 8761


To run with docker:

To build the image
docker build -t service-registry-service .

To run the image on port 8761
docker run -p 8761:8761 service-registry-service


after this service will be available under http://localhost:8761












