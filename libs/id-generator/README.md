# Id-Generator

Library that includes entity model and repository to support entity Id generation.

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
