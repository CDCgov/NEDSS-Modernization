## Summary

This project aims to provide a convenient environment for developers to build and wrap the NBS application in a
containerized execution environment for the purposes of experimenting with and developing technology solutions for
the modernization of the NEDSS Base System.

The NEDSS Base System is a specific implementation of the NEDSS standards.
It provides a system for the secure, accurate, and efficient collection, transmission, and analysis of public health data.
The NBS also provides a platform upon which program-specific modules can be built to meet state and program area requirements.
Development on the NEDSS system takes place by authorized developers in a private github repository.
Access to the private repository is required to build with these instructions and is provided to authorized contractors
by the CDC.

Click here to view the [NEDSSDev Github Repository](https://github.com/cdcent/NEDSSDev).

The containerized environment here consists of three Docker images.

- Maven / Amazon Corretto Java 8
- MSSQL Server / Ubuntu 18.04
- Wildfly Server / Amazon Corretto Java 8

The Maven container is only used when bulding NBSSDev artifacts for deployment into the Wildfly server.
The MSSQL and Wildfly containers are the only containers instantiated to actually run the application.

To build and run the project use the instructions in the following sections of this README.
Each section has a it's own detailed documentation as well.
Follow the links for more insight into development best practices and other options for build and run procedures.
The steps outlined in this README should get the system up and running quickly for the first time.

### Caveats

1. This solution **does not** have any connection to _Rhapsody_.
2. The pagemanagement directory is unzipped **incorrectly** within the Linux container. This is temporarily remedied
   by manually unzipping the files and adding them to this repository. The directory called pagemanagement should be
   placed within the wildfly server directory with the relative path wildfly-10.0.0.Final/nedssdomain/Nedss/pagemanagement.
3. Database restore files from a point-in-time backup that was provided. The corresponding upgrade scripts found [here](https://github.com/cdcent/NEDSSDB/tree/master/NBS_DB/db/Disk_Database/WINDOWS/DB%20Setup%20Scripts-Release%20Upgrade/MS%20SQL) have been applied to migrate the database to version `6.0.15`.

## Build

A convenience script has been provided to automate the build of the container images.
(Linux/MacOS)

```shell
./build_all.sh
```

For more information on the build process click here for the [Build README](doc/build.md)


To access the NBS application visit **http://localhost:7001/nbs/login**.

|Available Users|
|-|
|msa|
|superuser|

The password field should be left blank

