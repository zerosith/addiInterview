To run the Validator tester service:

in the root folder run

```sh
mvn clean install
docker build -t validator-tester-service .
docker run -p 8083:8083 validator-tester-service
```
