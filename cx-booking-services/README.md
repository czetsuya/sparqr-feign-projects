# CX Booking Services

## Building the Docker Container

```
mvn clean install
docker build -t sparqr/cx-booking-services:latest  -f ./docker/Dockerfile .
```