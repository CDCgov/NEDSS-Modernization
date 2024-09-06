#!/bin/sh
set -e
BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

source $BASE/check_env.sh

if [ -z "$DATABASE_PASSWORD" ] || [ -z "$NIFI_PASSWORD" ] || [ -z "$KEYCLOAK_ADMIN_PASSWORD" ] || [ -z "$TOKEN_SECRET" ] || [ -z "$PARAMETER_SECRET" ]
then
    echo "DATABASE_PASSWORD, NIFI_PASSWORD, KEYCLOAK_ADMIN_PASSWORD, TOKEN_SECRET are required"
    echo "DATABASE_PASSWORD: $DATABASE_PASSWORD"
    echo "NIFI_PASSWORD: $NIFI_PASSWORD"
    echo "KEYCLOAK_ADMIN_PASSWORD: $KEYCLOAK_ADMIN_PASSWORD"
    echo "PARAMETER_SECRET: $PARAMETER_SECRET"
    echo "TOKEN_SECRET: $TOKEN_SECRET"
    exit 1
else
    echo "DATABASE_PASSWORD: $DATABASE_PASSWORD"
    echo "NIFI_PASSWORD: $NIFI_PASSWORD"
    echo "KEYCLOAK_ADMIN_PASSWORD: $KEYCLOAK_ADMIN_PASSWORD"
    echo "PARAMETER_SECRET: $PARAMETER_SECRET"
    echo "TOKEN_SECRET: $TOKEN_SECRET"
fi

# Builds and starts all containers
./build_classic.sh
./build_modernized.sh