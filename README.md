# NEDSS-Modernization

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=CDCgov_NEDSS-Modernization&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=CDCgov_NEDSS-Modernization)

## About

- [Modernization API](apps/modernization-api/README.md)
- [Modernization UI](apps/modernization-ui/README.md)
- [Patient Listener](apps/patient-listener/README.md)
- [CDC Sandbox](cdc-sandbox/README.md)
- [Database-Entities](libs/database-entities/README.md)
- [Event-Schema](libs/event-schema/README.md)

## Guidelines

- [Package by Feature](docs/Package-By-Feature.md) to make code easier to find.

## TLDR: Running everything inside docker

### Mac ARM system (M1/M2)

1. Install [Java 17](docs/InstallJava.md)
1. Install Node / NPM
1. Clone [NBS](https://github.com/cdcent/NEDSSDev)
1. Set `NEDSS_HOME` environment variable, this should point to the directory `NBS` was cloned into. Example:

   ```sh
   export NEDSS_HOME="/Users/michaelpeels/Projects/NBS/NEDSSDev"

   # can verify that the variable is set
   echo $NEDSS_HOME

   ```

1. CD into the `cdc-sandbox` directory
   ```sh
   cd cdc-sandbox
   ```
1. Run the NBS [build script](cdc-sandbox/build.sh) to build the image
   ```sh
   ./build.sh
   ```
1. Download the [database restore file](https://enquizit.sharepoint.com/:u:/s/CDCNBSProject/EQtb-5WSO9xGrocNofv_eMgBH1WX30TNV0wTlZ84E5coYg?e=uNtem1) and place it in `cdc-sandbox/db/restore/restore.d/`
1. Run the NBS [run script](cdc-sandbox/run.sh) to start the `nbs-mssql` database and `nbs`. NBS runs inside [WildFly 10.0.0](https://www.wildfly.org/news/2016/01/30/WildFly10-Released/), so the container is named `wildfly`
   ```sh
   ./run.sh
   ```
1. Start `Elasticsearch`, `Kibana`, and the [Traefik](https://traefik.io/) reverse proxy
   ```sh
   docker-compose up elasticsearch kibana reverse-proxy -d
   ```
1. CD into the `apps/modernization-ui` directory
   ```sh
   cd ../apps/modernization-ui
   ```
1. Run `npm install`
   ```sh
   npm i
   ```
1. CD to the `apps/modernization-api` directory
   ```sh
   cd ../modernization-api
   ```
1. Start the `modernization-api` container
   ```sh
   docker-compose up modernization-api -d
   ```
1. CD into the `cdc-sandbox` directory and Start NiFi
   ```sh
   cd ../cdc-sandbox
   docker-compose up nifi -d
   ```
1. Visit http://localhost:8080/nbs/login

   ```
   username: msa
   password:
   ```

1. To create your own user account visit site (line 15):

- Navigate to System Management
- Expand Security Management
- Click Manage Users & click add
- Enter userId, First Name and Last Name
- Add a Role & click submit

## Code Formatting

### Java

Java Code is formatted using the default IntelliJ code formatter. If using VS-Code, add the following to your `settings.json`.

```json
"java.format.settings.url": "eclipse-formatter.xml",
"java.format.settings.profile": "IntelliJ"
```

### Typescript

Typescript formatting is handled by the [Prettier](https://prettier.io/) plugin/package.
