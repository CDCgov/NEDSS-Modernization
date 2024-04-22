## NBS 6 Deduplication 

This application allows manual execution of the NBS 6 deduplication algorithms and provides a convienient method in which to reset the database to allow repeated execution.

### Starting the application
The application will start on port `8082` by default and will have a swagger page available: [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html).

Within the `/testing/deduplication/` directory execute the following to start the service.
```sh
./gradlew bootRun
```

### Prerequisites
1. Java 21
1. Running `nbs-mssql` database - [/cdc-sandbox/docker-compose.yml](../../cdc-sandbox/docker-compose.yml)

