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

## TLDR: Running everything inside docker

### Mac ARM system (M1/M2)

1. Install [Java 17](documentation/InstallJava.md)
2. Install Node / NPM
3. Clone [NBS 6.0.12](https://github.com/cdcent/NEDSSDev/tree/NBS_6.0.12)
4. Set `NEDSS_HOME` environment variable to the Classic NBS directory created by step 3

   ```sh
   export NEDSS_HOME="/Users/michaelpeels/Projects/NBS/NEDSSDev"

   # can verify that the variable is set
   echo $NEDSS_HOME

   ```

5. CD into the `cdc-sandbox` directory
   ```sh
   cd cdc-sandbox
   ```
6. Run the NBS [build script](cdc-sandbox/build.sh) to build the image
   ```sh
   ./build.sh
   ```
7. Download the [database restore file](https://enquizit.sharepoint.com/:u:/s/CDCNBSProject/EQtb-5WSO9xGrocNofv_eMgBH1WX30TNV0wTlZ84E5coYg?e=uNtem1)
8. Unzip the restore file contents to `cdc-sandbox/db/restore/restore.d/`

   ```sh
   unzip -j db-restore.zip -d <path-to-cdc-sandbox/db/restore/restore.d/>
   ```

9. Run the NBS [run script](cdc-sandbox/run.sh) to start the `nbs-mssql` database and `nbs`. NBS runs inside [WildFly 10.0.0](https://www.wildfly.org/news/2016/01/30/WildFly10-Released/), so the container is named `wildfly`
   ```sh
   ./run.sh
   ```
10. Start `Elasticsearch`, `Kibana`, and the [Traefik](https://traefik.io/) reverse proxy

    ```sh
    docker-compose up elasticsearch kibana reverse-proxy -d
    ```

11. Start the `modernization` containers `modernization-api` and `nbs-gateway`
    ```sh
    docker-compose up -d
    ```
12. CD into the `cdc-sandbox` directory and Start NiFi
    ```sh
    cd ../cdc-sandbox
    docker-compose up nifi -d
    ```
13. Visit http://localhost:8080/nbs/login

    ```
    username: msa
    password:
    ```

14. To create your own user account visit site (line 15):

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
