#!/bin/sh
set -e
BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

source $BASE/check_env.sh

if [ -z "$DATABASE_PASSWORD" ] || [ -z "$NIFI_PASSWORD" ] || [ -z "$KEYCLOAK_ADMIN_PASSWORD" ] || [ -z "$TOKEN_SECRET" ] || [ -z "$PARAMETER_SECRET" ] 
then
    echo "DATABASE_PASSWORD, NIFI_PASSWORD, KEYCLOAK_ADMIN_PASSWORD, TOKEN_SECRET, PARAMETER_SECRET are required"
    exit 1
else
    echo "Building modernized services with:"
    echo "DATABASE_PASSWORD: $DATABASE_PASSWORD"
    echo "NIFI_PASSWORD: $NIFI_PASSWORD"
    echo "KEYCLOAK_ADMIN_PASSWORD: $KEYCLOAK_ADMIN_PASSWORD"
    echo "PARAMETER_SECRET: $PARAMETER_SECRET"
    echo "TOKEN_SECRET: $TOKEN_SECRET"
fi


# Start ES and the proxy
echo "Starting elasticsearch reverse-proxy"
docker compose -f $BASE/docker-compose.yml up elasticsearch --build -d

# Start of modernization-api initializes the elasticserach indices 
echo "Starting modernized application services"
docker compose -f $BASE/docker-compose.yml  up --build -d

# NiFi handles synchronizing data between nbs-mssql and elasticsearch
echo "Starting NiFi"
docker compose -f $BASE/docker-compose.yml up nifi --build -d

# Process complete
echo "**** Modernized application startup complete ****"
echo "http://localhost:8000/nbs/login"
echo ""
echo "**** Available users ****"
echo "*\tmsa"
echo "*\tsuperuser"
echo ""
