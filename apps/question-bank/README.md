# NEDSS-Modernization Question-Bank

Contains a GraphQL API for managing Question Bank entities. All non read requests are posted to the Kafka `questionbank` topic and then consumed for processing. After processing a status update will be posted to the `questionbank-status` topic.

## Running

### Prerequisites

1. Java 17
2. `nbs-mssql`, `kafka` docker containers. See [CDC Sandbox](../../cdc-sandbox/README.md)

## Tests

Prior to running tests the `cdc-sandbox/test-db/` image must be built. To build this image run the following command in
the `cdc-sandbox` directory.

```sh
docker-compose build test-db
```

To run all tests:

```bash
./gradlew question-bank:test
```

## Running

The Question-Bank can be started from the root directory by runninng:

```bash
./gradlew :question-bank:bootRun
```

It assumes that MSSQL Server and Kafka are running on `localhost`. Preconfigured containers are available in
the [CDC Sandbox](../../cdc-sandbox/README.md),`cdc-sandbox/db`, and `cdc-sandbox/kafka`.

The application will listen on port `8095` and expose a graphql API at `/graphql`. There is also an integrated test environment for running GraphQL queries located at `/graphiql`.

To access the `/graphql` API, an authorization header must be supplied in the `"Authorization" : "Bearer <Token>"` format. A valid token can be retrieved by sending a request to the `/login` API of the [modernization-api](../modernization-api/README.md) project.

### Debugging

The `bootRun` task is configured to allow remote debugging on port `18095` allowing any Java Debugger to attach without
having to restart the application. The debug port can be changed at runtime by setting the `debug.port` property.

For example, the debug port can be set to `8181` by executing.

```bash
./gradlew -Pdebug.port=8181 :question-bank:bootRun
```
