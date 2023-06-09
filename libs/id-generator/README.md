# Id-Generator

Library that includes the models and repository necessary to support entity Id generation. Many database entities in NBS utilize the `Local_UID_generator` table to track their Ids. This library simplifies retrieving valid Ids from this table.

## About

The id-generator library is currently being used by the mondernization-api, patient-listener, and question-bank projects.

## Usage

In your project gradle build file **build.gradle** include:

```
implementation project(':id-generator')
```

Also in the bottom of your gradle build file include:

```
compileJava.mustRunAfter(":id-generator:build")
```

In addition ensure that the project is on the classpath of the importing project.

There is a convienience annotation can help with this.

```java
@EnableNBSIdGenerator
public class Application {
    ...
}
```
