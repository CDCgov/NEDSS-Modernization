# NEDSS-Modernization API

## Running

### Prerequisites

1. Java 17
1. Node / NPM
1. nbs-mssql, elasticsearch, and nifi docker containers are running. See [CDC Sandbox](../../cdc-sandbox/README.md)

### VSCode

1. In the ui directory run `npm install`
1. In the patient-search directory run `./gradlew build`
1. Press `Cmd+Shift+P` and run `Java: Clean Language Server Workspace`
1. VSCode should now recognize the QueryDSL generated Q classes and be able to launch the debugger

## Tests

To run all tests:

```bash
./gradlew test
```

To execute specific test tags:

```bash
./gradlew -Dcucumber.filter.tags="@patient_create" test
```

## GraphQL

The project utilizes GraphQL through the [spring-boot-starter-graphql](https://docs.spring.io/spring-graphql/docs/current/reference/html/) dependency. With the api running an interface is available at [/graphiql](http://localhost:8080/graphiql?path=/graphql#) for testing

## QueryDSL

[QueryDSL](https://github.com/querydsl/querydsl) allows the construction of type-safe SQL queries. The `QPatient` class is generated from the existing `Patient` JPA entity. When a new `@Entity` is added, run `./gradlew build` and the new Q classes will be created under `api/build/generated/sources/annotationProcessor/java/main`

### Example query:

```java
@PersistenceContext
private final EntityManager entityManager;

public List<Patient> findPatientsNamedJohn() {
    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
    var patient = QPatient.patient;
    return queryFactory.selectFrom(patient).where(patient.firstNm.like("John")).fetch();
}
```

## Swagger

A swagger page is available at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
