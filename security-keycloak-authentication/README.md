# Using Keycloak Authentication Service to manage users and jwt

In this example, we use the Rest client to access keycloak, authenticate the user and return the Bearer token.

## Requirements

- JDK 11
- Maven
- Keycloak
- Docker
- Postman

## Stating and Configuring the Keycloak Server

To start the Keycloak Server on your running docker, just run the following command:

```shell
$ docker compose -f security-keycloak-authentication/keycloak_infrastructure/docker-compose.yaml up -d
```

You should be able to access the Keycloak Server at [http://localhost:9020](http://localhost:9020)

Log in as the `admin` using Administration Console. Credentials should be Username `admin` and Password `admin`. 

You can remove the container running the following command;

````shell
$ docker compose -f security-keycloak-authentication/keycloak_infrastructure/docker-compose.yaml down -v --remove-orphans
````