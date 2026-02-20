# National Electronic Disease Surveillance System (NEDSS) Modernization API

## Table of Contents

- [Prerequisites](#-prerequisites)
- [Initial Setup](#-initial-setup)
- [Development Workflow](#-development-workflow)
- [Running the Application](#-running-the-application)
- [Tests](#-tests)
- [API Documentation](#-api-documentation-and-tools)

---

## Prerequisites

Ensure you have the following installed:

* **Java 21**
* **Node.js & NPM**
* **Docker** (for mssql, elasticsearch, and nifi)

---

## 🛠 Initial Setup

### 1. Environment & Secrets

This project uses a `.env` file for local configuration. The provided script initializes this file and exports
variables to your current session.

```shell
# Initialize and load environment variables
source ./check_env.sh
```

> [!IMPORTANT] You must use the source command. Running `./check_env.sh` directly will fail to persist variables in your
> active terminal.
>
> * Windows users should use Git shell or WSL.

### 2. Infrastructure (Containers)

Build the necessary database and support images within the cdc-sandbox directory:

```shell
cd cdc-sandbox
./build_all.sh
```

Note: Containers do not need to be running to execute tests, but the images must be built.

### 3. Application Dependencies

```shell
# Install UI dependencies
cd ui && npm install

# Build the API (from root)
./gradlew :modernization-api:buildDependents
```

## 💻 Development Workflow

### VSCode Integration

1. In the ui directory run npm install
2. In the modernization-api directory run ./gradlew build
    1. Alternatively, from the root directory run ./gradlew :modernization-api:buildDependents
3. Press Cmd+Shift+P and run Java: Clean Language Server Workspace
4. VSCode should now recognize the QueryDSL generated Q classes and be able to launch the debugger

### Debugging

The `bootRun` task supports remote debugging on port `5005` allowing any Java Debugger to attach without having to
restart the application.

The debug port can be changed at runtime by setting the `debug.port` property. For example, the debug port can be set
to 8181 by executing.

```shell
./gradlew -Pdebug.port=8181 :modernization-api:bootRun
```

## 🚀 Running the Application

### Standard Start

```shell
./gradlew :modernization-api:bootRun
```

It assumes that Elasticsearch and MSSQL Server are running on localhost. Preconfigured containers are available in the
CDC Sandbox, cdc-sandbox/elasticsearch and cdc-sandbox/db.

### With Kafka Enabled

The Modernization API can connect to the Kafka instance defined in cdc-sandbox/kafka by starting the service with
kafka.enabled set to true.

```shell
./gradlew :modernization-api:bootRun --args='--kafka.enabled'
```

## 🧪 Tests

### Run all tests:

```shell
./gradlew :modernization-api:test
```

### Run specific Cucumber features:

```shell
./gradlew -Dtest.single="RunCucumber" -Dcucumber.filter.tags="@patient_create" :modernization-api:test
```

## 📖 API Documentation and Tools

### Authentication

There are two modes of authentication; one to support the existing NBS 6 authentication and OpenID Connect (OIDC)
authentication. NBS 6 authentication is enabled by default.

#### NBS 6 Authentication

Uses the JSESSIONID created by the NBS 6 instance to validate if the user has been authenticated by searching for an
entry in the `Security_Log` table. If a valid entry exists a token is generated.

Enabled when `nbs.security.oidc.enabled` is `false` or not present.

#### OpenID Connect (OIDC)

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

### GraphQL

The project utilizes GraphQL through
the [spring-boot-starter-graphql](https://docs.spring.io/spring-graphql/docs/current/reference/html/) dependency. With
the api running an interface is available at [/graphiql](http://localhost:8080/graphiql?path=/graphql#) for testing

### Swagger

A swagger page is available
at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## ⚙️ Configuration

Spring Config allows configuration values to be overwritten at deployment. Values can be set through Java System
Variables,
Environment Variable,
and [other useful means](https://docs.spring.io/spring-boot/docs/2.7.5/reference/html/features.html#features.external-config).

Configuration properties can be overwritten at runtime using the `--args` Gradle option to pass arguments to Spring
Boot.

For example, running the following will cause the modernization-api to connect to a database on a server named `other`
by executing.

```bash
./gradlew :modernization-api:bootRun --args'--nbs-datasource.server=other '
```

The `default` profile contains the following properties configuration most likely to change.

| Name                          | Default   | Description                                         |
|-------------------------------|-----------|-----------------------------------------------------|
| nbs.elasticsearch.server      | localhost | The host name of the server running ElasticSearch   |
| nbs.elasticsearch.port        | 9200      | The port in which ElasticSearch is listening        |
| nbs.wildfly.server            | localhost | The host name of the server running NBS Classic     |
| nbs.wildfly.port              | 7001      | The port in which NBS Classic is listening          |
| nbs.datasource.server         | localhost | The host name of the server running MS SQL Server   |
| nbs.identifier.person.initial | 10000000  | The initial seed value for Person local identifiers |

### UI Configuration

The modernization-api includes the [configuration-api](/libs/configuration-api/README.md) to expose the configuration
endpoint.

### Interactions with NBS6.X WildFly instance

There are a handful of endpoints that intercept requests from the NBS6.X WildFly instance in order to route to the
modernized Patient file. Some of the intercepted POST requests contain a large amount of form fields represented as
multipart form-data that easily hit the default `max-part-count` of Tomcat. A threshold of `550` has been set to
accommodate Page Builder templates with large number of questions. Due to the customizability of Page Builder templates
this limit may not be sufficient in some deployments. The `max-part-count` can be overridden using the
`SERVER_TOMCAT_MAX_PART_COUNT` environment variable.

