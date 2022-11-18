# Build

NBS is a Java EE application which is distributed as a .ear (enterprise application archive) file.
It is currently built using ANT and Java 8.
Because it has typically been built using the tools on Windows OS, in order to gain the convenience
of using Docker, it is necessary to massage a few changes into the code before it can
be built in the linux environment.

- The project code files are currently encoded with the ISO-8859-1 character set.
  This causes problems if the java compiler is expecting UTF-8 which is it's default
- One of the files referenced in the build.xml ANT build script has incorrect matching
  case with the actual file on the filesystem

The Ant Builder docker in this project addresses these concerns automatically.
The section in this README titled "Build NBS without Docker" addresses these issues directly.

## Contents

- Build NBS with Docker
- Build NBS without Docker
- Build MSSQL Database Container Image
- Build Wildfly Application Server Container Image

## Build NBS with Docker

Building NBS with Docker is as easy as running the build script.
For more information here are the steps in the build script outlined for manual execution if desired.

### Build the Maven/Ant Builder

```shell
docker build -t nedssdev --force-rm -f Dockerfile-antbuild .
```

### Build NBS Using the Maven/Ant Builder Docker

```shell
$ cd /Users/scottmefferd/_dev/NEDSSDev
$ docker run --rm -v "$(pwd)":/mnt/src nedssdev
```

The build artifacts are placed in the dist directory in this project folder.

#### Using the build container terminal interactively

```shell
$ cd $NEDSS_HOME
$ docker run -ti --rm -v $(pwd):/mnt/src -v m2:/root/.m2 nedssdev /bin/bash
$ $MAVEN_HOME/bin/mvn package
```

Build artifacts are placed in the dist directory.

## Build NBS without Docker

### Requirements

1. [Apache Ant](https://ant.apache.org/)
1. [Java 8](https://openjdk.org/install/)

### Running Maven

```shell
# the project contains some non utf-8 chars
$ export JAVA_TOOL_OPTIONS=-Dfile.encoding=ISO-8859-1

# make sure ant is using correct java install
$ export JAVA_HOME=<path-to-your-java8>

# run the build process
$ MAVEN_HOME/bin/mvn package
```

The build artifacts are placed in the dist directory.

### Running Ant

If the branch your building doesn't have a maven project file (pom.xml) yet, then it still requires the ant build.

Execute the full build with:

```sh
$ export JAVA_TOOL_OPTIONS=-Dfile.encoding=ISO-8859-1
$ export JAVA_HOME=<path-to-your-java8>
$ cd BuildAndDeployment
$ $ANT_HOME/bin/ant
```

or execute a single stage:

```sh
$ $ANT_HOME/bin/ant "nbs.clean"
```

The build artifacts will be located in the `BuildAndDeployment/build` directory

## Build MSSQL Database Container Image

```shell
docker-compose build nbs-mssql
```

## Build Wildfly Application Server Container Image

```shell
docker-compose build wildfly
```

The docker build will copy the output of `build.sh` (located in the dist folder) as well as the [pagemanagement](../../pagemanagement/) files into the container. In a normal windows installation the pagemanagement files are generated as follows:

1. Database is restored from `NBS_ODSE.bak`
1. NBS queries database for zip files from `NBS_ODSE.NBS_page.jsp_payload`
1. NBS unzips pagemanagement files

In docker however, the unzip step fails. For this reason we manually copy the files into the container

## Clean Docker Environment

To start over and create all the Docker images from scratch again, run the
following command in this project directory.

```shell
docker-compose down
```

The Docker volume named "nbs-mssql-data" will not be removed automatically.
If you wish to delete the database data volume and reset the database to an
initial state, list the volumes and then delete it.

```shell
cdc-sandbox $ docker volume list
DRIVER    VOLUME NAME
local     cdc-sandbox_nbs-mssql-data

cdc-sandbox $ docker volume rm cdc-sandbox_nbs-mssql-data
```
