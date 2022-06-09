# Sparqr BFF

## Building the Docker Container

```
mvn clean install
docker build -t sparqr/cx-sparqr-be:latest  -f ./docker/Dockerfile .
``` 