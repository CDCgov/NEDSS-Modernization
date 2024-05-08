# Keycloak

A preconfigured Keycloak container has been created to support authentication and user maintenance of
the `nbs-gateway`, `modernization-api`, and `pagebuilder-api` services.

## Default Realm

An [export](imports/nbs-users.json) for the `nbs-users` realm has been provided to be used by NBS7. The export includes
a pre-configured client as well as users matching the base users of the nbs-mssql database.

An [extra set of users](users/nbs.qa.users.json) has been provided to create users commonly used by the QA team.

## Running

The Keycloak container can be started by running the following from the `cdc-sandbox` folder

```shell
docker compose up -d keycloak
```
