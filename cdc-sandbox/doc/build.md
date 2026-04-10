# Build

## Contents

- [Elasicsearch with Kibana](#elasicsearch-with-kibana)
- [Traefik Reverse Proxy](#traefik-reverse-proxy)
- [NiFi](#nifi)

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
