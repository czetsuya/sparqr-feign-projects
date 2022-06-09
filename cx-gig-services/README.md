# GIG Services

## Building the Docker Container

```
mvn clean install
docker build -t sparqr/cx-gig-services:latest  -f ./docker/Dockerfile .
```