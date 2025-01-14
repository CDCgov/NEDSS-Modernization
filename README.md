# NEDSS-Modernization

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=CDCgov_NEDSS-Modernization&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=CDCgov_NEDSS-Modernization)

## About

- [Modernization API](apps/modernization-api/README.md)
- [Modernization UI](apps/modernization-ui/README.md)
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

## Configuring local development secrets

Some containers within the `cdc-sandbox` directory require sensitive values be set prior to building.

| Container | Required environment variable    |
|-----------|----------------------------------|
| nbs-mssql | DATABASE_PASSWORD                |
| nifi      | NIFI_PASSWORD, DATABASE_PASSWORD |
| keycloak  | KEYCLOAK_ADMIN_PASSWORD          |

## Running everything inside docker

1. Gain access to the [NBS source code repository](https://github.com/cdcent/NEDSSDev) _this is required to build the
   wildfly container_
2. Navigate to the `cdc-sandbox` directory

   ```sh
   cd cdc-sandbox
   ```
3. Run the `build_all.sh` script

   ```sh
   ./build_all.sh
   ```
4. Visit the [NBS Login page](http://localhost:8080/nbs/login)

   ```
   username: msa
   password:
   ```

5. To create your own user account:
    - Navigate to System Management
    - Expand Security Management
    - Click Manage Users & click Add
    - Enter userId, First Name and Last Name
    - Add a Role(s) & click submit

To learn more about the build process view the cdc-sandbox [README](cdc-sandbox/README.md)

## Running the Modernization API and UI in development mode

1. Navigate to the `cdc-sandbox` directory

   ```sh
   cd cdc-sandbox
   ```
2. Start the database and Elasticsearch containers

    ```sh
    docker compose up -d nbs-mssql elasticsearch
    ```
3. Navigate to the root directory

   ```sh
   cd ..
   ``` 

4. Start the `modernized-api` _Port `5005` will be open for debugger attachment._

    ```sh
    ./gradlew :modernization-api:bootRun
    ```
5. Navigate to the `modernization-ui` folder

    ```sh
    cd apps/modernization-ui/    
    ```
6. Launch the `modernization-ui`

    ```sh
    npm run start
    ```

7. Access the [UI](http://localhost:3000)

## Code Formatting

## Print Artifact Version

```
./gradlew printVersion


> Task :printVersion
Version: 1.0.0-SNAPSHOT
```

## Running with local servers

The `nbs-gateway` container is configured to route to the containerized services. Routing to a local service can be
achieved by altering the configuration to point to the local instances.

| Name                     | Default             | Description                                                     |
|--------------------------|---------------------|-----------------------------------------------------------------|
| MODERNIZATION_UI_SERVER  | `modernization-ui`  | The host name of the server that provides the frontend UI.      |
| MODERNIZATION_UI_PORT    | `80`                | The port the frontend UI is served from.                        |
| MODERNIZATION_API_SERVER | `modernization-api` | The host name of the server that provides the backend API.      |
| MODERNIZATION_API_PORT   | `8080`              | The port that modernization-api is served from.                 |
| PAGEBUILDER_API          | `pagebuilder-api`   | The host name of the server that provides the page-builder API. |
| PAGEBUILDER_API_PORT     | `8095`              | The port that page-builder is served from.                      |
| NBS_GATEWAY_SERVER       | `nbs-gateway`       | The host name of the server that provides the NBS Gateway.      |
| NBS_GATEWAY_PORT         | `8000`              | The port the NBS Gateway is served from.                        |

### Configuring the NBS-Gateway to use local modernization-ui

1. Start the frontend UI locally by running the following command from the `apps/modernization-ui` folder.

   ```shell
   npm run start
   ```

2. Start the `nbs-gateway` container configured to route to the local `modernization-ui` by executing the
   following command from the root folder

   ```shell
   MODERNIZATION_UI_SERVER=host.docker.internal MODERNIZATION_UI_PORT=3000 docker compose up -d nbs-gateway
   ```

### Configuring the NBS-Gateway to use a local modernization-api

From the root folder.

1. Start the backend API locally listening on port 9080 from the root project folder. The `nbs-gateway` container is
   accessible from port `8080`, which is the default port for the `modernization-api`. It must be changed in order for
   the
   backend to start properly.

   ```shell
   ./gradlew :modernization-api:bootRun --args='--server.port=9080'
   ```

2. Start the `nbs-gateway` container configured to route to the local `modernization-api` by executing the
   following command.

   ```shell
   MODERNIZATION_API_SERVER=host.docker.internal MODERNIZATION_API_PORT=9080 docker compose up -d nbs-gateway
   ```

### Configuring the NBS-Gateway to use a local pagebuilder-api

From the root folder.

1. Start the local `pagebuilder-api` service.

   ```shell
   ./gradlew :question-bank:bootRun'
   ```

2. Start the `nbs-gateway` container configured to route to the local `pagebuilder-api` by executing the
   following command.

   ```shell
   PAGEBUILDER_API=host.docker.internal PAGEBUILDER_API_PORT=8095 docker compose up -d nbs-gateway
   ```

### Resetting to Docker only

Start the `nbs-gateway` container by running the following command from the root folder

```shell
docker compose up -d nbs-gateway
```
