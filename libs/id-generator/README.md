# Id-Generator

Library that includes the models and repository necessary to support the NBS identifier generation defined by the
`getNextUid_sp` stored procedure. Many database entities in NBS utilize the `Local_UID_generator` table to track their
Ids. This library simplifies retrieving valid Ids from this table.

## Usage

In your project gradle build file **build.gradle** include:

```
implementation project(':id-generator')
```

In addition, ensure that the project is on the classpath of the importing project.

There is a convenience annotation to make an `IdGeneratorService` available for injection in a Spring based project.

```java

@EnableNBSIdGenerator
public class Application {
    ...
}
```
