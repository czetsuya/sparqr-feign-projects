# IAM Services

## Required Environment Variables

| Key                   | Value      |
|-----------------------|------------|
| AWS_ACCESS_KEY_ID     | Access key |
| AWS_SECRET_ACCESS_KEY | Secret key |

## Building the Docker Container

```
mvn clean install
docker build -t sparqr/cx-iam-services:latest  -f ./docker/Dockerfile .
```