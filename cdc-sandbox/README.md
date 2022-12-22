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

- ANT / Amazon Corretto Java 8
- MSSQL Server / Ubuntu 18.04
- Wildfly Server / Amazon Corretto Java 8

The ANT container is only used when bulding NBSSDev artifacts for deployment into the Wildfly server.
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
3. Database restore files are taken from a point-in-time backup from provided the dev server on date 9-20-2022 and zipped in [db-restore.zip](db/restore/restore.d/db-restore.zip). They contain updates from 6.0.12-GA release and
   should only be used until a proper solution is implemented. The build scripts automatically unzip contents into the correct directory.

## Build

A convenience script has been provided to automate the build of the container images.
There are however a few prerequisites to build the images.

- Download or clone the NBS_6.0.12 branch from the [NEDSSDev github](https://github.com/cdcent/NEDSSDev/tree/NBS_6.0.12)
- Set NEDSS_HOME environment variable to the directory path of the NEDSSDev source tree
- Run the provided build script

(Linux/MacOS)

```shell
$ export NEDSS_HOME="/home/Users/scottmefferd/_dev/NEDSSDev"
$ ./build.sh
```

(Windows cmd only not powershell)

```bat
> set NEDSS_HOME="C:\Users\ScottMefferd\Documents\NBS_6.0.12"
> build.bat
```

(WINDOWS) To prevent excessive memory usage under `C:\Users\<yourUserName>`
create a file called `.wslconfig` and paste the following snippet (set to 6 GB).

```
[wsl2]
memory=6GB
```

For more information on the build process click here for the [Build README](doc/build/README.md)

## Run

After docker images have been created we can use the run script to start the containers.
First you must download and place the database restore scripts in this project (cdc-sandbox)
directory root.

- Database restore files can be found at [db-restore.zip](db/restore/restore.d/db-restore.zip).

- Contents should automatically be unzipped into the proper folder. If this **fails** unzip the contents of db-restore.zip into db/restore/restore.d found in this project (cdc-sandbox).
- Run the provided run script

**Note** that the typically database restore scripts are provided in the [Development Package Rel6.0.11.zip](https://cdcnbscentral.com/attachments/24435), a REL6.0.12 does not currently exist.  
under db/SQLSERVER. Due to missing pages this is currently unused within this build.

(Linux/MacOS)

```shell
$ ./run.sh
```

(Windows cmd only not powershell)

```bat
> run.bat
```

To access the NBS application visit **http://localhost:7001/nbs/login**.
The administrator username is "msa" and has a blank password.

For more information on the run process click here for the [Run README](doc/run/README.md)

## Elasicsearch with Kibana

This feature has **not yet been automated** due to a relaxed security feature within the elasticsearch setup. Currently implementation of elasticsearch with kibana is provided as a part of [docker-compose.yaml](docker-compose.yml). A sample configuration file for Kibana, while not used, is provided [here](kibana/config/kibana.yml). To enable use of this configuration file uncomment the volumes block under the kibana block in [docker-compose.yaml](docker-compose.yml).

### Prerequisites

For elasticsearch to run with Docker you will need to set _vm.max_map_count=262144_ on your machine. Instructions below are taken from this [link](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#_set_vm_max_map_count_to_at_least_262144).

#### LINUX and macOS with Docker for Mac

```
sysctl -w vm.max_map_count=262144
```

#### macOS with Docker Desktop

```
docker-machine ssh
sudo sysctl -w vm.max_map_count=262144
```

#### Windows with Docker Desktop WSL 2 backend

```
docker-machine ssh
sudo sysctl -w vm.max_map_count=262144
```

### Build and Run Elasticsearch and Kibana

To build Elasticsearch and Kibana open your command prompt, navigate to the directory containing this repository, and follow the steps below.
Configuration settings for **Elasticsearch** can be found at the external link [here](https://www.elastic.co/guide/en/elasticsearch/reference/current/settings.html). Configuration settings for **Kibana** can be found at the external link [here](https://www.elastic.co/guide/en/kibana/8.4/settings.html).

```
docker-compose up -d elasticsearch
docker-compose up -d kibana
```

Once the containers are up you can access the Kibana home page as follows:

```
http://localhost:5601
```

## Traefik Reverse Proxy

### Running Traefik

```bash
docker-compose up -d reverse-proxy
```

### Requirements

[Modernization](https://github.com/cdcent/NEDSS-Modernization),
[WildFly, and NBS-MSSQL](docker-compose.yml) docker containers are running

### About Traefik

[Traefik proxy](https://traefik.io/) is configured to proxy requests from the NBS application to the Modernization app. Traefik configuration is located in the [traefik.yml](proxy/traefik.yml) file and has a dashboard visible at `localhost:8081`.

Traefik provider configuration is handled by file [here](proxy/providers.yml). This provider file sets the routes and what service to forward requests too.
Example:

```yml
http:
  routers:
    searchForward:
      service: modernization
      rule: "Path(`/nbs/MyTaskList1.do`) || (Path(`/nbs/HomePage.do`) && Query(`method=patientSearchSubmit`))"
  services:
    modernization:
      loadBalancer:
        servers:
          - url: "http://modernization:8080/"
```

This configuration snippet defines a `router` that will catch requests matching the path `/nbs/MyTaskList1.do` OR `/nbs/HomePage.do?method=patientSearchSubmit` and forward them to the modernization service at the specified url: `http://modernization:8080/`.

## NiFi

### About

[NiFi](https://nifi.apache.org/) is an ETL tool that we are using to extract data from the NBS Microsoft SQL Server database (nbs-mssql) and insert into the Elasticsearch database.

### Prerequisites

1. Running `nbs-mssql` container
1. Running `elasticsearch` container
1. If you are on Mac using the M1 or M2 chip, run the following command to build a compatible docker image for NiFi

```sh
./cdc-sandbox/nifi/buildImage.sh
```

1. Starting NiFi (run in the cdc-sandbox directory):

```sh
docker-compose up nifi -d
```

## Contribute

Contributions to this code repository follow some general guidelines.

- The default protected branch is "master"
- Feature branches are named like contributor/jira-description or just jira-description
- Push daily work to feature branches
- Pull requests and review are required to merge branches into master

Developers are expected to be able to force push only on their own individual feature branches..
Team feature branches and master branch are protected and should never be rewritten.
