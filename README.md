# About

NEDSS-Modernization

# Potentially useful information

## GraphQL

The project utilizes GraphQL through the [spring-boot-starter-graphql](https://docs.spring.io/spring-graphql/docs/current/reference/html/) dependency. With the api running an interface is available at [/graphiql](http://localhost:8080/graphiql?path=/graphql#) for testing

## Auto Generating Endpoints

The UI code utilizes [openapi-typescript-codegen](https://github.com/ferdikoomen/openapi-typescript-codegen) to auto generate models and method for the existing Java API endpoints.

Workflow:

1. Update the Java code with new controller endpoints
1. Run the following command in the `ui` folder while the **Java appliction is running**
    ```
    npm run generate
    ```
1. Use the newly generated methods to make API calls from typescript

## Swagger

A swagger page is available at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Packaging

The Gradle build is configured to use the [com.moowork.node](https://plugins.gradle.org/plugin/com.moowork.node) plugin to package the React application into the Jar file.
