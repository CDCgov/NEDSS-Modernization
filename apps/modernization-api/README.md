# National Electronic Disease Surveillance System (NEDSS) Modernization API

## Running

### Prerequisites

1. Java 21
2. Node / NPM
3. nbs-mssql, elasticsearch, and nifi docker containers are running. See [CDC Sandbox](../../cdc-sandbox/README.md)
4. `DATABASE_PASSWORD`, `TOKEN_SECRET` and `PARAMETER_SECRET` environment variables are set or relevant properties set
   in an `application-local.yml`

### VSCode

1. In the ui directory run `npm install`
2. In the modernization-api directory run `./gradlew build`
    - Alternatively, from the root directory run `./gradlew :modernization-api:buildDependents`
3. Press `Cmd+Shift+P` and run `Java: Clean Language Server Workspace`
4. VSCode should now recognize the QueryDSL generated Q classes and be able to launch the debugger

## Tests

Prior to running tests the `cdc-sandbox/test-db/` image must be built. To build this image run the following command in
the `cdc-sandbox` directory.

```sh
docker compose up test-db -d
```

To run all tests:

```bash
./gradlew :modernization-api:test
```

To execute specific tagged feature tests:

```shell
./gradlew -Dtest.single="RunCucumber" -Dcucumber.filter.tags="@patient_create" :modernization-api:test
```

## Running

The Modernization API can be started from the root directory by executing:

```bash
./gradlew :modernization-api:bootRun
```

It assumes that Elasticsearch and MSSQL Server are running on `localhost`. Preconfigured containers are available in
the [CDC Sandbox](../../cdc-sandbox/README.md), `cdc-sandbox/elasticsearch` and `cdc-sandbox/db`.

### Connecting to Kafka

The Modernization API can connect to the Kafka instance defined in `cdc-sandbox/kafka` by starting the service
with `kafka.enabled` set to true.

```shell
./gradlew :modernization-api:bootRun --args='--kafka.enabled'
```

### Debugging

The `bootRun` task is configured to allow remote debugging on port `5005` allowing any Java Debugger to attach without
having to restart the application. The debug port can be changed at runtime by setting the `debug.port` property.

For example, the debug port can be set to `8181` by executing.

```bash
./gradlew -Pdebug.port=8181 :modernization-api:bootRun
```

## Authentication

There are two modes of authentication; one to support the existing NBS 6 authentication and OpenID Connect (OIDC)
authentication. NBS 6 authentication is enabled by default.

### NBS 6 Authentication

Uses the JSESSIONID created by the NBS 6 instance to validate if the user has been authenticated by searching for an
entry in the `Security_Log` table. If a valid entry exists a token is generated.

Enabled when `nbs.security.oidc.enabled` is `false` or not present.

### OpenID Connect (OIDC)

The `modernization-api` service can be configured to act as an OIDC Resource Server by
setting `nbs.security.oidc.enabled` to `true` and providing the `nbs.security.oidc.uri`. The JWT passed in
the `Authentication` header must have a `perferred_username` value that matches an active user in the NBS `Auth_User`
table.

| Name                       | Default                                                                              | Description                                                                 |
|----------------------------|--------------------------------------------------------------------------------------|-----------------------------------------------------------------------------|
| nbs.security.oidc.enabled  |                                                                                      | Enables OIDC based authentication.                                          |
| nbs.security.oidc.protocol | `http`                                                                               | The protocol used to communicate with the OIDC compatible Identity Provider |
| nbs.security.oidc.host     |                                                                                      | The hostname of the OIDC compatible Identity Provider                       |
| nbs.security.oidc.base     | `/realms/nbs-users`                                                                  | The path to the OIDC endpoints                                              |
| nbs.security.oidc.uri      | `${nbs.security.oidc.protocol}://${nbs.security.oidc.host}${nbs.security.oidc.base}` | The URI for the OIDC issuer                                                 |

## GraphQL

The project utilizes GraphQL through
the [spring-boot-starter-graphql](https://docs.spring.io/spring-graphql/docs/current/reference/html/) dependency. With
the api running an interface is available at [/graphiql](http://localhost:8080/graphiql?path=/graphql#) for testing

## Swagger

A swagger page is available
at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Configuration

Spring Config allows configuration values to be overwritten at runtime. Values can be set through Java System Variables,
Environment Variable,
and [other useful means](https://docs.spring.io/spring-boot/docs/2.7.5/reference/html/features.html#features.external-config)
. The default profile contains the following properties configuration most likely to change.

| Name                          | Default   | Description                                         |
|-------------------------------|-----------|-----------------------------------------------------|
| nbs.elasticsearch.server      | localhost | The host name of the server running ElasticSearch   |
| nbs.elasticsearch.port        | 9200      | The port in which ElasticSearch is listening        |
| nbs.wildfly.server            | localhost | The host name of the server running NBS Classic     |
| nbs.wildfly.port              | 7001      | The port in which NBS Classic is listening          |
| nbs.datasource.server         | localhost | The host name of the server running MS SQL Server   |
| nbs.identifier.person.initial | 10000000  | The initial seed value for Person local identifiers |

### UI Features

The modernization-api contains a `/nbs/api/configuration` endpoint that returns a set of configurations for the UI. The
following configurations are present

| Name                                                | Default | Description                                         |
|-----------------------------------------------------|---------|-----------------------------------------------------|
| nbs.ui.features.address.autocomplete                | false   | Enables the address autocomplete feature            |
| nbs.ui.features.address.verification                | false   | Enables the address verification feature            |
| nbs.ui.features.pageBuilder.enabled                 | false   | Enables the PageBuilder feature                     |
| nbs.ui.features.pageBuilder.page.library.enabled    | false   | Enables the PageBuilder Page Library feature        |
| nbs.ui.features.pageBuilder.page.management.enabled | false   | Enables the PageBuilder Page Management feature     |
| nbs.ui.features.search.events.enabled               | true    | Enables access to NBS6 Event Search                 |
| nbs.ui.features.search.investigations.enabled       | false   | Enables access to modernized Investigation search   |
| nbs.ui.features.search.laboratoryReports.enabled    | false   | Enables access to modernized Laboratory search      |
| nbs.ui.features.patient.add.enabled                 | false   | Enables access to modernized Patient short form add |
| nbs.ui.features.patient.add.extended.enabled        | false   | Enables access to modernized Patient Extended add   |
| nbs.ui.features.patient.profile.enabled             | false   | Enables access to modernized Patient Profile        |

Configuration properties can be overwritten at runtime using the `--args` Gradle option to pass arguments to Spring
Boot.

For example, running the following will cause the modernization-api to connect to a database on a server named `other`
by executing.

```bash
./gradlew :modernization-api:bootRun --args'--nbs-datasource.server=other '
```

### Kafka

| Name                                        | Default        | Description                                                               |
|---------------------------------------------|----------------|---------------------------------------------------------------------------|
| kafka.enabled                               | false          | Weather or not the Modernization API connects to Kafka                    |
| spring.kafka.bootstrap-servers              | localhost:9092 | URL of one or more Kafka brokers to obtain information about the cluster. |
| spring.kafka.properties.schema.registry.url | localhost:9091 | URL of the server providing the centralized message schema repository.    |
