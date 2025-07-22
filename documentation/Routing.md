# Routing

The routing to and from NBS Modernization and Classic NBS to support Strangler Fig migration.

## NBS Classic

| Criteria                 | Path   | Description                                                   |
|--------------------------|--------|---------------------------------------------------------------|
| Path starts with `/nbs/` | `/nbs` | The Classic NBS system served by a WildFly Application Server |

## Modernization API

The following routes should be proxied to the `modernization-api` service.

| Criteria                      | API Path      | Description                                            |
|-------------------------------|---------------|--------------------------------------------------------|
| Path starts with `/login`     | `/login`      | User Authorization                                     |
| Path is exactly `/graphql`    | `/graphql`    | Handles all GraphQL requests                           |
| Path is exactly `/encryption` | `/encryption` | Encryption and Decryption of Patient Search parameters |
| Path starts with `/`          | `/`           | Routes to the packaged frontend application            |
| Path starts with `/nbs/api`   | `/nbs/api`    | Routes to endpoints on the `modernization-api`         |

## Strangler Fig Routing

The `nbs-gateway` intercepts traffic for NBS Classic to allow redirects to the `modernization-api` based on specific
criteria of the request URL.

### Patient Search Routes

| Criteria                                                                                                                                                                  | API Path                       | Description                                                                      |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------|----------------------------------------------------------------------------------|
| Path is exactly `/nbs/HomePage.do` containing the Query Parameter `method` with a value of `mergeSubmit` and the Query Parameter `ContextAction` with a value of `Submit` | `/nbs/redirect/simpleSearch`   | Transforms a classic simple search request into a modernized search request URL. |
| Path is exactly `/nbs/MyTaskList.do` containing the Query Parameter `ContextAction` with a value of `GlobalPatient`                                                       | `/nbs/redirect/advancedSearch` | Routes the Classic Advanced search to the Modernized UI                          |

- [Patient file](routing/Patient-File-Routing.md)
