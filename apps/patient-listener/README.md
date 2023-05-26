# NEDSS-Modernization Patient-Listener

Responds to Patient Requests posted to the `patient` topic, the result of the request completion is then posted to the `patient-status` topic.

## Running

### Prerequisites

1. Java 17
2. nbs-mssql, elasticsearch, and kafka docker containers are running. See [CDC Sandbox](../../cdc-sandbox/README.md)

## Tests

Prior to running tests the `cdc-sandbox/test-db/` image must be built. To build this image run the following command in
the `cdc-sandbox` directory.

```sh
docker-compose build test-db
```

To run all tests:

```bash
./gradlew patient-listener:test
```

## Running

The Patient-Listener can be started from the root directory by runninng:

```bash
./gradlew :patient-listener:bootRun
```

It assumes that Elasticsearch, MSSQL Server,and Kafka are running on `localhost`. Preconfigured containers are available in
the [CDC Sandbox](../../cdc-sandbox/README.md), `cdc-sandbox/elasticsearch`, `cdc-sandbox/db`, and `cdc-sandbox/kafka`.

The application will listen on port 8281 by default however it's only function is to connect and respond to messages in the `patient` topic.

### Debugging

The `bootRun` task is configured to allow remote debugging on port `18281` allowing any Java Debugger to attach without
having to restart the application. The debug port can be changed at runtime by setting the `debug.port` property.

For example, the debug port can be set to `8181` by executing.

```bash
./gradlew -Pdebug.port=8181 :patient-listener:bootRun
```
