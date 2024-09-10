# Build

NBS is a Java EE application which is distributed as a .ear (enterprise application archive) file.
It is currently built using Maven and Java 8.
Because it has typically been built using the tools on Windows OS, in order to gain the convenience
of using Docker, it is necessary to make a few changes before it can
be built in a linux environment.

- The project code files are currently encoded with the ISO-8859-1 character set.
  This causes problems if the java compiler is expecting UTF-8 which is it's default
- One of the files referenced in the build.xml build script has incorrect matching
  case with the actual file on the filesystem
- The pom-jar.xml is missing an entry for a couple of `provided` dependencies. `Xalan` and `xerces`. 

The builder docker in this project addresses these concerns automatically.

## Contents

- [Build NBS with Docker](#build-nbs-with-docker)
- [Build MSSQL Database Container Image](#build-mssql-database-container-image)
- [Elasicsearch with Kibana](#elasicsearch-with-kibana)
- [Traefik Reverse Proxy](#traefik-reverse-proxy)
- [NiFi](#nifi)
- [Kafka](#kafka)

## Build NBS with Docker

Building NBS with Docker is as easy as running the build script.
For more information here are the steps in the build script outlined for manual execution if desired.

#### Pulling the NBS source code

The NBS source code must first be pulled and placed within the `nbs-classic/builder` directory.

```shell
cd nbs-classic/builder
git clone -b NBS_6.0.15 git@github.com:cdcent/NEDSSDev.git
```

#### Building the NBS source and deploying it into a WildFly docker container

From the `cdc-sanbox` directory, execute
```shell
docker compose up wildfly
```

The docker build will copy the output of `build.sh` (located in the dist folder) as well as the [pagemanagement](../../pagemanagement/) files into the container. In a normal windows installation the pagemanagement files are generated as follows:

1. Database is restored from `NBS_ODSE.bak`
1. NBS queries database for zip files from `NBS_ODSE.NBS_page.jsp_payload`
1. NBS unzips pagemanagement files

In docker however, the unzip step fails. For this reason we manually copy the files into the container


## Build MSSQL Database Container Image

```shell
docker compose build nbs-mssql
```

## Clean Docker Environment

To start over and create all the Docker images from scratch again, run the
following command in this project directory.

```shell
docker compose down
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
docker compose up -d elasticsearch
docker compose up -d kibana
```

Once the containers are up you can access the Kibana home page as follows:

```
http://localhost:5601
```

## Traefik Reverse Proxy

### Running Traefik

```bash
docker compose up -d reverse-proxy
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
docker compose up nifi -d
```

## Kafka

Please follow the steps below to setup a Kafka  docker container environment that includes Kafka, Zookeeper, Schema-Registry and Kafka-UI

Kafka

[Zookeeper](https://zookeeper.apache.org/) ZooKeeper is a centralized service for maintaining configuration information, naming, providing distributed synchronization, and providing group services.

[Schema-Registry](https://docs.confluent.io/platform/current/schema-registry/index.html#sr-overview) Confluent Schema Registry provides a serving layer for your metadata. It provides a RESTful interface for storing and retrieving your Avro®, JSON Schema, and Protobuf schemas. It stores a versioned history of all schemas based on a specified subject name strategy, provides multiple compatibility settings and allows evolution of schemas according to the configured compatibility settings and expanded support for these schema types.

[Kafka-UI](https://github.com/provectus/kafka-ui) UI for Apache Kafka is a open source tool that makes data flows observable, helps find and troubleshoot issues , create/manage topics, and deliver optimal performance.  A lightweight dashboard makes it easy to track key metrics of your Kafka clusters - Brokers, Topics, Partitions, Production, and Consumption.

1. Go to Kafka directory:
```
cd cdc-sandbox/kafka
```

2. Launch kafka Docker container environment. If you are on an ARM Environment you may use (docker-compose-arm64v8.yml) if you experience any performance issues but regular script should work fine.
```
docker compose  -f docker-compose.yml up -d
```

* Note: To create a topic you can open Zookeeper CLI and find the commands online. You can also open Kafka-UI in
the browser and add a topic using the dashboard.
