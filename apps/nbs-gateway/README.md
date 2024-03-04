# National Electronic Disease Surveillance System (NEDSS) Base System Modernization Gateway

An entry point for an NBS 6.X Application that allows a Strangler Fig approach to piecemeal modernization.

## Running

The NBS Gateway is a Spring Cloud Gateway application that runs on port `8000` by default. It can be started from the
root directory by executing;

```bash
./gradlew nbs-gateway:bootRun
```

### Debugging

The `bootRun` task is configured to allow remote debugging on port `5008` allowing any Java Debugger to attach without
having to restart the application. The debug port can be changed at runtime by setting the `debug.port` property.

For example, the debug port can be set to `8181` by executing.

```bash
./gradlew -Pdebug.port=8181 :nbs-gateway:bootRun
```

## Tests

To run all tests:

```bash
./gradlew nbs-gateway:test
```

## Building

### Docker

A container can be created by running the following command from the root folder;

```shell
docker compose build nbs-gateway
```

## Configuration

Spring Config allows configuration values to be overwritten at runtime. Values can be set through Java System Variables,
Environment
Variable,and [other useful means](https://docs.spring.io/spring-boot/docs/2.7.5/reference/html/features.html#features.external-config).
The default profile contains the following properties configuration most likely to change.

| Name                                                   | Default                 | Description                                                                                                                                                                                              |
|--------------------------------------------------------|-------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| nbs.gateway.defaults.protocol                          | `http`                  | The default protocol used to connect to services. Intra-pod communication is `http`                                                                                                                      |
| nbs.gateway.classic                                    | `http://localhost:7001` | The URI location of the classic NBS Application                                                                                                                                                          |
| nbs.gateway.log.level                                  | `INFO`                  | A shortcut to configuring the `RoutePredicateHandlerMapping` logging level.  Equivalent to specifying the property `logging.levelorg.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping` |
| nbs.gateway.patient.search.enabled                     | `true`                  | Enables the Patient Search routing                                                                                                                                                                       |
| nbs.gateway.patient.search.service                     | `localhost:8080`        | The host name of the Patient Search service                                                                                                                                                              |
| nbs.gateway.patient.profile.enabled                    | `true`                  | Enables the Patient Profile routing                                                                                                                                                                      |
| nbs.gateway.patient.profile.service                    | `localhost:8080`        | The host name of the Patient Profile service                                                                                                                                                             |
| nbs.gateway.pagebuilder.enabled                        | `false`                 | Enables Page Builder routing                                                                                                                                                                             |
| nbs.gateway.pagebuilder.service                        | `localhost:8080`        | The host name of the service                                                                                                                                                                             |
| nbs.gateway.pagebuilder.page.library.enabled           | `false`                 | Enables the Page Builder Page Library routing                                                                                                                                                            |
| nbs.gateway.pagebuilder.page.management.create.enabled | `false`                 | Enables the Page Builder Page creation routing                                                                                                                                                           |
| nbs.gateway.pagebuilder.page.management.edit.enabled   | `false`                 | Enables the Page Builder Page preview/edit routing                                                                                                                                                       |

### Configuring the Reverse Proxy to use local nbs-gateway

Start the reverse proxy configured to route to the local backend by running the following command from the `cdc-sandbox`
folder.

```shell
NBS_GATEWAY_SERVER=host.docker.internal docker compose up -d reverse-proxy
```

### Logging

The logging level for `RoutePredicateHandlerMapping` is set to `DEBUG` when running the `nbs-gateway` from the
command-line using gradle. When running the container the logging level can be changed using the `NBS_GATEWAY_LOG_LEVEL`
environment variable.

```shell
NBS_GATEWAY_LOG_LEVEL=DEBUG docker compose up -d nbs-gateway
```
