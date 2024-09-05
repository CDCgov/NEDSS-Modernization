# NEDSS-Modernization Question-Bank

Contains REST APIs for managing Question Bank entities.

## Running

### Prerequisites

1. Java 21
2. `nbs-mssql` docker containers. See [CDC Sandbox](../../cdc-sandbox/README.md)
3. `DATABASE_PASSWORD`, `TOKEN_SECRET` environment variables are set or relevant properties set in an `application-local.yml`

## Tests

Prior to running tests the `cdc-sandbox/nbs-mssql/` image must be built. To build this image run the following command
in
the `cdc-sandbox` directory.

```sh
docker compose build nbs-mssql
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

## Authentication

There are two modes of authentication; one to support the existing NBS 6 authentication and OpenID Connect (OIDC)
authentication. NBS 6 authentication is enabled by default.

### NBS 6 Authentication

Uses the JSESSIONID created by the NBS 6 instance to validate if the user has been authenticated by searching for an
entry in the `Security_Log` table. If a valid entry exists a token is generated.

Enabled when `nbs.security.oidc.enabled` is `false` or not present.

### OpenID Connect (OIDC)

The `question-bank` service can be configured to act as an OIDC Resource Server by setting `nbs.security.oidc.enabled`
to `true` and providing the `nbs.security.oidc.uri`. The JWT passed in the `Authentication` header must have a `perferred_username` value that matches an active user in
the NBS `Auth_User` table.

| Name                       | Default                                                                              | Description                                                                 |
|----------------------------|--------------------------------------------------------------------------------------|-----------------------------------------------------------------------------|
| nbs.security.oidc.enabled  |                                                                                      | Enables OIDC based authentication.                                          |
| nbs.security.oidc.protocol | `http`                                                                               | The protocol used to communicate with the OIDC compatible Identity Provider |
| nbs.security.oidc.host     |                                                                                      | The hostname of the OIDC compatible Identity Provider                       |
| nbs.security.oidc.base     | `/realms/nbs-users`                                                                  | The path to the OIDC endpoints                                              |
| nbs.security.oidc.uri      | `${nbs.security.oidc.protocol}://${nbs.security.oidc.host}${nbs.security.oidc.base}` | The URI for the OIDC issuer                                                 |
