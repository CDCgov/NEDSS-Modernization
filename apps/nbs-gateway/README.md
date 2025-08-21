# National Electronic Disease Surveillance System (NEDSS) Base System Modernization Gateway

An entry point for an NBS 6.X Application that allows a Strangler Fig approach to piecemeal modernization to various
services.

## Running

The NBS Gateway is a Spring Cloud Gateway application that runs on port `8000` by default. It requires
the `OIDC_CLIENT_SECRET` environment variable be set. This can be done by
running `export OIDC_CLIENT_SECRET=<the value>` or by setting `nbs.security.oidc.client.secret` directly within
an `application-local.yml`. The application can be started from the root directory by executing;

```bash
./gradlew nbs-gateway:bootRun
```

### Configuring the Reverse Proxy to use local nbs-gateway

Start the reverse proxy configured to route to the local backend by running the following command from the `cdc-sandbox`
folder.

```shell
NBS_GATEWAY_SERVER=host.docker.internal docker compose up -d reverse-proxy
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

## Route Locators

Spring Cloud Gateway route locators are components that define how the gateway matches incoming requests to specific
routes. They determine which backend service should handle a given request based on various criteria.

### Routing to API services

With the `nbs-gateway` being the entry point for all client requests routes to API services should be defined by a
`RouteLocator`. By convention requests to a backend service are routed through the path `/nbs/{name}/api/**` and are
always enabled.

### Strangler Fig Routing

Any `RouteLocators` that route NBS6.x paths to a modernized service should be configured to be enabled and disabled via
deployment time configuration. This has been implemented using Spring's `@Configuration` and `@ConditionalOnExpression`
annotations to ensure that a `RouteLocator` is configured only when the value of a configuration property is `true`.

It is important to make sure that any NBS6.x paths are unique enough to only target the desired pages.

## Configuration

Spring Config allows configuration values to be overwritten at runtime. Values can be set through Java System Variables,
Environment
Variable,and [other useful means](https://docs.spring.io/spring-boot/reference/features/external-config.html).
The default profile contains the following properties configuration most likely to change.

| Name                                                   | Default                 | Description                                                                                                                             |
|--------------------------------------------------------|-------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| nbs.gateway.defaults.protocol                          | `http`                  | The default protocol used to connect to services. Intra-pod communication is `http`                                                     |
| nbs.gateway.classic                                    | `http://localhost:7001` | The URI location of the classic NBS Application                                                                                         |
| nbs.gateway.landing.uri                                | `no://op`               | The URI for the service that contains the desired landing page.  Defaults to `no://op` since this is a redirect.                        |
| nbs.gateway.landing.base                               | `/nbs/login`            | The path to redirect to when a user navigates to the base `/` path.  The landing redirect is not applied if this value is equal to `/`. |
| nbs.gateway.welcome.enabled                            | `false`                 | Enables routing to the `/welcome` page meant for non-production environments.                                                           |
| nbs.gateway.patient.search.enabled                     | `true`                  | Enables the Patient Search routing                                                                                                      |
| nbs.gateway.patient.search.service                     | `localhost:8080`        | The host name of the Patient Search service                                                                                             |
| nbs.gateway.patient.file.enabled                       | `false`                 | Enables the Patient File routing                                                                                                        |
| nbs.gateway.patient.file.service                       | `localhost:8080`        | The host name of the Patient File service                                                                                               |
| nbs.gateway.pagebuilder.enabled                        | `false`                 | Enables Page Builder routing                                                                                                            |
| nbs.gateway.pagebuilder.service                        | `localhost:8080`        | The host name of the service                                                                                                            |
| nbs.gateway.pagebuilder.page.library.enabled           | `false`                 | Enables the Page Builder Page Library routing                                                                                           |
| nbs.gateway.pagebuilder.page.management.create.enabled | `false`                 | Enables the Page Builder Page creation routing                                                                                          |
| nbs.gateway.pagebuilder.page.management.edit.enabled   | `false`                 | Enables the Page Builder Page preview/edit routing                                                                                      |
| nbs.gateway.deduplication.enabled                      | `false`                 | Enables routing to the Deduplication Api                                                                                                |
| nbs.gateway.deduplication.service                      | `localhost:8083`        | The host name of the Deduplication Api                                                                                                  |
| nbs.gateway.deduplication.uri                          | `http://localhost:8083` | The full URI of the Deduplication Api                                                                                                   |
| nbs.gateway.deduplication.merge.enabled                | `false`                 | Enables the routing for modernized System Identified merge list                                                                         |
| nbs.gateway.system.management.enabled                  | `false`                 | Enables the routing for modernized System Management screen                                                                             |

### Logo

By default, the NBS logo is served from the path `/images/nedssLogo.jpg` with the content coming from
the `nbs.gateway.classic` service. The logo can be changed by setting the `nbs.gateway.logo.file` value to the name of
the file to use as the logo.

| Name                  | Default                 | Description                                                 |
|-----------------------|-------------------------|-------------------------------------------------------------|
| nbs.gateway.logo.path | `/images/nedssLogo.jpg` | The path to serve the NBS logo on.                          |
| nbs.gateway.logo.file |                         | The file system path to the image to serve as the NBS logo. |

### Landing and Welcome page

There is a "Welcome to the NBS 7 demo site" landing page that can be enabled for non-production environment by setting
the `nbs.gateway.welcome.enabled` property to `true` and the `nbs.gateway.landing.base` property.

### Logging

Shortcut configurations have been created for logging of Spring resources. Each default to `INFO` with valid values
including `ERROR`, `WARN`, `INFO`, `DEBUG`, and `TRACE`.

| Name                           | Default | Description                                                                                                                                  |
|--------------------------------|---------|----------------------------------------------------------------------------------------------------------------------------------------------|
| nbs.gateway.log.level          | `INFO`  | A shortcut to the logging level for `org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping` which logs the routing handler. |
| nbs.gateway.security.log.level | `INFO`  | A shortcut to the logging level for `org.springframework.security` which logs Spring Security.                                               |

```shell
NBS_GATEWAY_LOG_LEVEL=DEBUG docker compose up -d nbs-gateway
```

### Authentication

Authentication is not enabled by default. The `nbs-gateway` has been preconfigured to work as an OIDC Client by
enabling the `oidc` profile. The profile requires a specific set of configuration values.

| Name                            | Default                                                                              | Description                                                                          |
|---------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| spring.profiles.include         |                                                                                      | The Spring property to include profiles. Set to `oidc` to enable OIDC authentication |
| nbs.security.oidc.protocol      | `http`                                                                               | The protocol used to communicate with the OIDC compatible Identity Provider          |
| nbs.security.oidc.host          |                                                                                      | The hostname of the OIDC compatible Identity Provider                                |
| nbs.security.oidc.base          | `/realms/nbs-users`                                                                  | The path to the OIDC endpoints                                                       |
| nbs.security.oidc.uri           | `${nbs.security.oidc.protocol}://${nbs.security.oidc.host}${nbs.security.oidc.base}` | The URI for the OIDC issuer                                                          |
| nbs.security.oidc.client.id     |                                                                                      | The client id used to initiate Authentication                                        |
| nbs.security.oidc.client.secret |                                                                                      | The client secret used to initiate Authentication                                    |
