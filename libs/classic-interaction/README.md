# Classic Interaction

A library that facilitates communication with an NBS wildfly server as currently logged-in user. The `JSESSIONID` cookie
used to identify sessions within WildFly is resolved from an incoming request and attached to an outgoing request to
simulate a user interacting with NBS6.

## Usage

In your project's **build.gradle** file include:

```
implementation project(':classic-interaction')
```

There is a convenience annotation to make a `RestTemplate` named `classicTemplate` available for injection in a Spring
based project.

```java

@EnableClassicInteraction
public class Application {
    ...
}
```
