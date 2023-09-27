# Run

This solution makes use of database backups that were created by updating the "Development Package Rel6.0.11.zip" database to `6.0.15`. 
It allows for the deployment of the NBS application with connectivity to a local database.
To access the NBS application locally, once deployed, visit **localhost:7001/nbs/login**.

This package functions by deploying the databases and NBS application with Docker containers.
For information on building the docker containers required by this README,
refer to the [Build README](doc/build/README.md).

Various filechanges are maybe once the NBS Application container is up. 
These changes are found in the root level DockerFile under the "FILE Modifications" section.

## Contents

- Database Container
- Wildfly Container
- Navigate the Application
- Remote Debugging

## Database Container

```shell
$ docker-compose up nbs-mssql
```

After the database has started and the data has been restored (about a minute),
the Wildfly container can be started.

### Logs
You can view the logs of the database container with the following command.

```shell
$ docker-compose logs nbs-mssql
```

### Log in to Interactive Shell

To log in to a terminal session on the running container use

```shell
$ docker-compose exec nbs-mssql /bin/bash
```

### How to SQLCMD

With a shell open (see above), you can use the sqlcmd interactively inside the running
container instance.

To view the databases, you can use the following in the sqlcmd shell.

```shell

$ sqlcmd -S localhost -U SA

1> select DB_NAME()
2> GO
```

## Wildfly Container

With the database container running and initialized, start the NBS application
with the following command.

```shell
$ docker-compose up wildfly
```

### Logs
You can view the logs in the running container with the following command.

```shell
$ docker-compose logs wildfly
```

### Log in to Interactive Shell

To open a terminal session in the running container use

```shell
$ docker-compose exec wildfly /bin/bash
```

## Navigate the Application

With both containers running, the application can be accessed in a web browser with 
the following URL

[**http://localhost:7001/nbs/login**](http://localhost:7001/nbs/login)

The administrator username is "msa" and has a blank password.

### Known bugs
1. Unzipping of database files into wildfly-10.0.0.Final/nedssdomain/pagemanagement does not work properly. The zipped files show up, but they do not get properly unzipped. Unzipping manually, from within the container, results in a directory structure resembling D:/wildfly-10.0.0.Final.
2. After logging in with a newly created user and navigating to Data Entry -> Lab Report
   - User is created with the following minimum permissions set STD, with SUPERUSER
   - This error is likely related to Known Bug #1

## Debugging
WildFly remote debugging is enabled by default in docker. Connect to port 8787 for remote debugging.

Example VS Code launch.json entry:
```json
{
    "type": "java",
    "name": "Debug (Attach)",
    "projectName": "NEDSS",
    "request": "attach",
    "hostName": "localhost",
    "port": 8787
}
```
