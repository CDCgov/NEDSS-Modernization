# About

NEDSS-Modernization

## Running

### VSCode

1. In the ui directory run `npm install`
1. In the root directory run `./gradlew build`
1. Press `Cmd+Shift+P` and run `Java: Clean Language Server Workspace`
1. VSCode should now recognize the QueryDSL generated Q classes and be able to launch the debugger

## GraphQL

The project utilizes GraphQL through the [spring-boot-starter-graphql](https://docs.spring.io/spring-graphql/docs/current/reference/html/) dependency. With the api running an interface is available at [/graphiql](http://localhost:8080/graphiql?path=/graphql#) for testing

## QueryDSL

[QueryDSL](https://github.com/querydsl/querydsl) allows the construction of type-safe SQL queries. The `QPatient` class is generated from the existing `Patient` JPA entity. When a new `@Entity` is added, run `./gradlew build` and the new Q classes will be created under `api/build/generated/sources/annotationProcessor/java/main`

```java
@PersistenceContext
private final EntityManager entityManager;

public List<Patient> findPatientsNamedJohn() {
    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
    var patient = QPatient.patient;
    return queryFactory.selectFrom(patient).where(patient.firstNm.like("John")).fetch();
}
```

## Auto Generating Endpoints

The UI code utilizes [gql-typescript-generator](https://github.com/TheBrainFamily/gql-typescript-generator) and [graphql-code-generator](https://github.com/dotansimha/graphql-code-generator) to auto generate GraphQL API requests.

Workflow:

1. Update the API `.graphqls` schema files
1. Run the following command in the `ui` folder while the **Java appliction is running**
    ```
    npm run generate
    ```
1. Use the newly generated methods to make API calls from typescript

## Swagger

A swagger page is available at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Packaging

The Gradle build is configured to use the [com.moowork.node](https://plugins.gradle.org/plugin/com.moowork.node) plugin to package the React application into the Jar file.
