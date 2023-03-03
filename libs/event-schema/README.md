# Event-Schema

Library that contains message based event classes usseful for Kafka event based communication between the
modernization-api and patient-listener projects. Also contains useful classes for communication between modernization-api and modernization-ui 

## About

The database-entities library is currently being used by both  the modernization-api and patient-listener projects Some packages to note include:

- gov.cdc.nbs.message - Contains classes useful for event based kafka communicaiton. 
- gov.cdc.nbs.message.enums - Contains enums related to patient biography information.


## Usage

In your project gradle build file **build.gradle** include:

```
implementation project(':event-schema')
```

Also in the bottom of your gradle build file include:

```
compileJava.mustRunAfter(":event-schema:build")
```
 
 In addition ensure that the project is on the classpath of the importing project.
