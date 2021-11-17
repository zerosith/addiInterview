# Addi code Challenge

In this repo you will find some folders

The order of building this project is :

- serviceregistry   
- internalvalidator
- externalcriminalrecordsvalidator
- nationalregistryvalidator
- validatortesterservice

You need to enter the folders in the previous order and follow the instructions for that application

#Trouble shoot

### Problem:
Containers are not able to see each other
### Solution: 
Restart your docker local service


### Problem:
internalvalidator externalcriminalrecordsvalidator nationalregistryvalidator validatortesterservice
Don't connect to eureka server

### Solution
internalvalidator externalcriminalrecordsvalidator nationalregistryvalidator validatortesterservice
have their eureka server configuration path in src/main/resources/application.properties

You need to change the value of the property `eureka.client.service-url.defaultZone` to the url of the eureka server.

You can get this url when you open the eureka server:

![screenshot](/screenshot/screenshot.png)

Then change the value of the property to that url in all services (i.e. internalvalidator externalcriminalrecordsvalidator nationalregistryvalidator validatortesterservice)











