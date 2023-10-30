# NEDSS-Modernization Question-Bank

Contains REST APIs for managing Question Bank entities.

## Running

### Prerequisites

1. Java 17
2. `nbs-mssql` docker containers. See [CDC Sandbox](../../cdc-sandbox/README.md)

## Tests

Prior to running tests the `cdc-sandbox/nbs-mssql/` image must be built. To build this image run the following command in
the `cdc-sandbox` directory.

```sh
docker-compose build nbs-mssql
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

It assumes that MSSQL Server is running on `localhost`. Preconfigured containers are available in
the [CDC Sandbox](../../cdc-sandbox/README.md),`cdc-sandbox/db`.

The application will listen on port `8095`.


### Debugging

The `bootRun` task is configured to allow remote debugging on port `18095` allowing any Java Debugger to attach without
having to restart the application. The debug port can be changed at runtime by setting the `debug.port` property.

For example, the debug port can be set to `8181` by executing.

```bash
./gradlew -Pdebug.port=8181 :question-bank:bootRun
```
