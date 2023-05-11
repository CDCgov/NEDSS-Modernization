# Routing

The routing to and from NBS Modernization and Classic NBS to support Strangler Fig migration.

## NBS Classic

| Criteria                                                                                                                                                             | Path                          | Description                                                                                                                                                        |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Path starts with `/nbs/`                                                                                                                                             | `/nbs`                        | The Classic NBS system served by a WildFly Application Server                                                                                                      |
| A POST request where the Path is exactly `/nbs/AddObservationMorb2.do` containing the Query Parameter `ContextAction` with a value of `SubmitAndCreateInvestigation` | `/nbs/AddObservationMorb2.do` | Submitting a Morbidity Report and Creating an Investigation after completing the Classic form displayed when a Morbidity Report is added from the Patient Profile. |

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

| Criteria                                                                                                                                                                                   | API Path                       | Description                                                                      |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------|----------------------------------------------------------------------------------|
| Path is exactly `/nbs/HomePage.do` containing the Query Parameter `method` with a value of `mergeSubmit` and the Query Parameter `ContextAction` with a value of `Submit`                  | `/nbs/redirect/simpleSearch`   | Transforms a classic simple search request into a modernized search request URL. |
| Path is exactly `/nbs/MyTaskList.do` containing the Query Parameter `ContextAction` with a value of `GlobalPatient`                                                                        | `/nbs/redirect/advancedSearch` | Routes the Classic Advanced search to the Modernized UI                          |
| Path is exactly `/nbs/MyTaskList.do` containing the Query Parameter `ContextAction` with a value of `GlobalMP_ManualSearch` and the Query Parameter `Model1` with a value of `ManualMerge` | `/nbs/redirect/advancedSearch` | Routes the Classic Advanced search to the Modernized UI                          |

### Patient Profile Routes

Transforms requests to a Classic Patient Profile into an authenticated request that redirects to the Modernized Patient
Profile of the Patient identified by the `MPRUid` or `uid` request parameter.

| Criteria                                                                              | API Path                       | Description                                                                                              |
|---------------------------------------------------------------------------------------|--------------------------------|----------------------------------------------------------------------------------------------------------|
| Contains the Query Parameter `ContextAction` with a value of `ViewFile`               | `/nbs/redirect/patientProfile` | View a Patient Profile link within Classic NBS.                                                          |
| Contains the Query Parameter `ContextAction` with a value of `FileSummary`            | `/nbs/redirect/patientProfile` | View a Patient Profile link within Classic NBS                                                           |
| A GET request where the Query Parameter `ContextAction` with a value of `FileSummary` | `/nbs/redirect/patientProfile` | View a Patient Profile link within Classic NBS after Morbidity Report `Submit and Create Investigation`  |

Transforms requests to return to a Classic Profile into an authenticated request that redirects to the Modernized
Patient Profile of the Patient identified by the value of the `Return-Patient` cookie. This cookie added to requests via
an API call prior to redirecting to Classic NBS.

| Criteria                                                                                                                                                                    | API Path                                        | Description                                                                                                                         |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------|
| Contains the Query Parameter `ContextAction` with a value of `ReturnToFileEvents`                                                                                           | `/nbs/redirect/patientProfile/events/return`    | Return to the Classic Patient Profile with the Events tab active                                                                    |
| Path is exactly `/nbs/SelectCondition1.do` containing the Query Parameter `ContextAction` with a value of `Cancel`                                                          | `/nbs/redirect/patientProfile/events/return`    | Return to the Classic Patient Profile after cancelling an `Add investigation`                                                       |
| Path is exactly `/nbs/PageAction.do` containing the Query Parameter `method` with a value of `mergeSubmit` and the Query Parameter `ContextAction` with a value of `Submit` | `/nbs/redirect/patientProfile/events/return`    | Return to the Classic Patient Profile after completing a `Compare investigation`                                                    |
| Path is exactly `/nbs/AddObservationMorb2.do` containing the Query Parameter `ContextAction` with a value of `Cancel`                                                       | `/nbs/redirect/patientProfile/events/return`    | Return to the Classic Patient Profile after cancelling an `Add morbidity report`                                                    |
| A POST request where the Path is exactly `/nbs/AddObservationMorb2.do` without any Query Parameters.                                                                        | `/nbs/redirect/patient/report/morbidity/submit` | Submitting a Morbidity Report after completing the Classic form displayed when a Morbidity Report is added from the Patient Profile |
