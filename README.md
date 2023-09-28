# NEDSS-Modernization

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=CDCgov_NEDSS-Modernization&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=CDCgov_NEDSS-Modernization)

## About

- [Modernization API](apps/modernization-api/README.md)
- [Modernization UI](apps/modernization-ui/README.md)
- [Patient Listener](apps/patient-listener/README.md)
- [Question Bank](apps/question-bank/README.md)
- [NBS Gateway](apps/nbs-gateway/README.md)
- [CDC Sandbox](cdc-sandbox/README.md)
- [Database-Entities](libs/database-entities/README.md)
- [Event-Schema](libs/event-schema/README.md)
- [Id-Generator](libs/id-generator/README.md)

## Guidelines

- [Package by Feature](documentation/Package-By-Feature.md) to make code easier to find.
- [Code Formatting](documentation/Code-Formatting.md)
- [Pull requests](documentation/Pull-Requests.md)

## Running everything inside docker

1. Install [Java 17](documentation/InstallJava.md)
1. Install Node / NPM
1. CD into the `cdc-sandbox/nbs-classic/builder` directory
   ```sh
   cd cdc-sandbox/nbs-classic/builder
   ```
1. Clone [NBS 6.0.15](https://github.com/cdcent/NEDSSDev/tree/NBS_6.0.15)
   ```sh
   git clone -b NBS_6.0.15 git@github.com:cdcent/NEDSSDev.git
   ```
1. CD into the `cdc-sandbox` directory
   ```sh
   cd ../..
   ```
1.  Start `nbs-mssql`, `wildfly` `Elasticsearch`, `Kibana`, and the [Traefik](https://traefik.io/) reverse proxy

    ```sh
    docker-compose up nbs-mssql wildfly elasticsearch kibana reverse-proxy -d
    ```
1. CD into the root project directory
   ```sh
   cd ..
   ```
1.  Start the `modernized` application containers
    ```sh
    docker-compose up -d
    ```
1.  CD into the `cdc-sandbox` directory and Start NiFi
    ```sh
    cd cdc-sandbox
    docker-compose up nifi -d
    ```
1.  Visit http://localhost:8080/nbs/login

    ```
    username: msa
    password:
    ```

1.  To create your own user account:
    - Navigate to System Management
    - Expand Security Management
    - Click Manage Users & click add
    - Enter userId, First Name and Last Name
    - Add a Role & click submit

## Code Formatting

## Print Artifact Version

```
./gradlew printVersion


> Task :printVersion
Version: 1.0.0-SNAPSHOT
```

## Running with local servers

By default, the reverse proxy will route to the containerized services. Routing to a local services can be achieved by altering the configuration to point to the local instances.

| Name                     | Default             | Description                                                     |
| ------------------------ | ------------------- | --------------------------------------------------------------- |
| MODERNIZATION_UI_SERVER  | `modernization-ui`  | The host name of the server that provides the frontend UI.      |
| MODERNIZATION_UI_PORT    | `80`                | The port the frontend UI is served from.                        |
| MODERNIZATION_API_SERVER | `modernization-api` | The host name of the server that provides the backend API.      |
| MODERNIZATION_API_PORT   | `8080`              | The port that modernization-api is served from.                 |
| PAGEBUILDER_API          | `pagebuilder-api`   | The host name of the server that provides the page-builder API. |
| PAGEBUILDER_API_PORT     | `8095`              | The port that page-builder is served from.                      |
| NBS_GATEWAY_SERVER       | `nbs-gateway`       | The host name of the server that provides the NBS Gateway.      |
| NBS_GATEWAY_PORT         | `8000`              | The port the NBS Gateway is served from.                        |

### Configuring the Reverse Proxy to use local modernization-ui

Start the frontend UI locally by running the following command from the `apps/modernization-ui` folder.

```shell
npm run start
```

Start the reverse proxy configured to route to the local frontend instance by running the following command from the `cdc-sandbox` folder

```shell
MODERNIZATION_UI_SERVER=host.docker.internal MODERNIZATION_UI_PORT=3000 docker compose up -d reverse-proxy
```

### Configuring the Reverse Proxy to use local modernization-api

Start the backend API locally listening on port 9080 from the root project folder. The `cdc-sandbox` exposes the reverse-proxy on port `8080`, which is the default port for Spring Boot. It must be changed in order for the backend to stat properly.

```shell
./gradlew :modernization-api:bootRun --args='--server.port=9080'
```

Start the reverse proxy configured to route to the local backend by running the following command from the `cdc-sandbox` folder

```shell
MODERNIZATION_API_SERVER=host.docker.internal MODERNIZATION_API_PORT=9080 docker compose up -d reverse-proxy
```

### Configuring the Reverse Proxy to use local pagebuilder-api

Start the question-bank applicaiton.

```shell
./gradlew :question-bank:bootRun'
```

Start the reverse proxy configured to route to the local backend by running the following command from the `cdc-sandbox` folder

```shell
PAGEBUILDER_API=host.docker.internal PAGEBUILDER_API_PORT=8095 docker compose up -d reverse-proxy
```

### Resetting to Docker only

Start the reverse proxy by running the following command from the `cdc-sandbox` folder

```shell
docker compose up -d reverse-proxy
```
