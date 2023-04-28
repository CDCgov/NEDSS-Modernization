# Routing

The routing to and from NBS Modernization and Classic NBS to support Strangler Fig migration.

## NBS Classic

| Criteria                  | Path   | Description                                                   |
|---------------------------|--------|---------------------------------------------------------------|
| Path starts with `/nbs/`  | `/nbs` | The Classic NBS system served by a WildFly Application Server |


## Modernization API

THe following routes should be proxied to the `modernization-api` service.

| Criteria                      | API Path      | Description                                            |
|-------------------------------|---------------|--------------------------------------------------------|
| Path starts with `/login`     | `/login`      | User Authorization                                     |
| Path is exactly `/graphql`    | `/graphql`    | Handles all GraphQL requests                           |
| Path is exactly `/encryption` | `/encryption` | Encryption and Decryption of Patient Search parameters |
| Path starts with `/`          | `/`           | Routes to the packaged frontend application            |
| Path starts with `/nbs/api`   | `/nbs/api`    |                                                        |

### Patient Search Routes

| Criteria                                                                                                                                                                                   | API Path                        | Description                                                                      |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------|----------------------------------------------------------------------------------|
| Path is exactly `/nbs/HomePage.do` containing the Query Parameter `method` with a value of `mergeSubmit` and the Query Parameter `ContextAction` with a value of `Submit`                  | `/nbs/redirect/simpleSearch`    | Transforms a classic simple search request into a modernized search request URL. |
| Path is exactly `/nbs/MyTaskList.do` containing the Query Parameter `ContextAction` with a value of `GlobalPatient`                                                                        | `/nbs/redirect/advancedSearch`  | Routes the Classic Advanced search to the Modernized UI                          |
| Path is exactly `/nbs/MyTaskList.do` containing the Query Parameter `ContextAction` with a value of `GlobalMP_ManualSearch` and the Query Parameter `Model1` with a value of `ManualMerge` | `/nbs/redirect/advancedSearch`  | Routes the Classic Advanced search to the Modernized UI                          |


### Patient Profile Routes

| Criteria                                                                                                                                                                    | API Path                                      | Description                                                                                                                                                                                            |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Contains the Query Parameter `ContextAction` with a value of `ViewFile`                                                                                                     | `/nbs/redirect/patientProfile`                | Transforms a request for the Classic Patient Profile into an authenticated request for the Modernized Patient Profile.                                                                                 |
| Contains the Query Parameter `ContextAction` with a value of `FileSummary`                                                                                                  | `/nbs/redirect/patientProfile`                | Transforms a request for the Classic Patient Profile into an authenticated request for the Modernized Patient Profile.                                                                                 |
| Contains the Query Parameter `ContextAction` with a value of `ReturnToFileSummary`                                                                                          | `/nbs/redirect/patientProfile/summary/return` | Transforms a request to return to the Classic Patient Profile into an authenticated request that redirects to the Modernized Patient Profile of the Patient                                            |
| Contains the Query Parameter `ContextAction` with a value of `ReturnToFileEvents`                                                                                           | `/nbs/redirect/patientProfile/events/return`  | Transforms a request to return to the Classic Patient Profile into an authenticated request that redirects to the Modernized Patient Profile of the Patient                                            |
| Path is exactly `/nbs/SelectCondition1.do` containing the Query Parameter `ContextAction` with a value of `Cancel`                                                          | `/nbs/redirect/patientProfile/events/return`  | Transforms a request to return to the Classic Patient Profile after cancelling an `Add Investigation` into an authenticated request that redirects to the Modernized Patient Profile of the Patient    |
| Path is exactly `/nbs/PageAction.do` containing the Query Parameter `method` with a value of `mergeSubmit` and the Query Parameter `ContextAction` with a value of `Submit` | `/nbs/redirect/patientProfile/events/return`  | Transforms a request to return to the Classic Patient Profile after completing a `Compare Investigation` into an authenticated request that redirects to the Modernized Patient Profile of the Patient |

