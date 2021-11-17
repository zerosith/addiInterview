FROM openjdk:17
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/usr/src/myapp/app.jar"]
