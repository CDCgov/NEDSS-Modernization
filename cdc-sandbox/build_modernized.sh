#!/bin/sh
set -e

if [ -z "$DATABASE_PASSWORD" ] || [ -z "$NIFI_PASSWORD" ] || [ -z "$KEYCLOAK_ADMIN_PASSWORD" ] || [ -z "$TOKEN_SECRET" ] || [ -z "$PARAMETER_SECRET" ] 
then
    echo "DATABASE_PASSWORD, NIFI_PASSWORD, KEYCLOAK_ADMIN_PASSWORD, TOKEN_SECRET are required"
    exit 1
fi

BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

# Start ES and the proxy
echo "Starting elasticsearch reverse-proxy"
docker-compose -f $BASE/docker-compose.yml up elasticsearch --build -d

MODERNIZED_PATH=$BASE/../

# Start of modernization-api initializes the elasticserach indices 
echo "Starting modernized application services"
docker-compose -f $MODERNIZED_PATH/docker-compose.yml  up --build -d

# NiFi handles synchronizing data between nbs-mssql and elasticsearch
echo "Starting NiFi"
docker-compose -f $BASE/docker-compose.yml up nifi --build -d

# Process complete
echo "**** Modernized application startup complete ****"
echo "http://localhost:8080/nbs/login"
echo ""
echo "**** Available users ****"
echo "*\tmsa"
echo "*\tsuperuser"
echo ""
