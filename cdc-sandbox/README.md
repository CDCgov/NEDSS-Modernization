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

To build and run the project use the instructions in the following sections of this README.
Each section has a its own detailed documentation as well.
Follow the links for more insight into development best practices and other options for build and run procedures.
The steps outlined in this README should get the system up and running quickly for the first time.

### Caveats

1. This solution **does not** have any connection to _Rhapsody_.

## Build
Secrets must be provided at build time for the various containers, the `build_all.sh` script will instantiate them if a `.env` file is not present.

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
