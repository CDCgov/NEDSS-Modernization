# Database-Entities

Library that includes entity models, repositories for CRUD actions for both MS-SQL,
ElasticSearch and support Util classes for the NEDS-Modernization system.

## About

The database-entities library is currently being used by both  the mondernization-api and patient-listener projects Some packages to note include:

- gov.cdc.nbs.address - Location records for Country, County and City.
- gov.cdc.nbs.audit -   Contains classes related to auditing a record.
- gov.cdc.nbs.entity - Contains entity classes to interact with MS SQL DBMS and ElasticSearch. Also
contains entity related enums and converters. 
- gov.cdc.nbs.patient - Contains interface with commands related to patient record actions.
- gov.cdc.nbs.repository - Contains repository interfaces for MS SQL Dialect and ElasticSearch.
- gov.cdc.nbs.util - Contains helpful util classes and cosntants.

## Usage

In your project gradle build file **build.gradle** include:

```
implementation project(':database-entities')
```

Also in the bottom of your gradle build file include:

```
compileJava.mustRunAfter(":database-entities:build")
```

In addition ensure that the project is on the classpath of the importing project.