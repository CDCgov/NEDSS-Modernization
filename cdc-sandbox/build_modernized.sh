#!/bin/sh
set -e
BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

source $BASE/check_env.sh

if [ -z "$DATABASE_PASSWORD" ] || [ -z "$TOKEN_SECRET" ] || [ -z "$PARAMETER_SECRET" ] 
then
    echo "DATABASE_PASSWORD,TOKEN_SECRET, PARAMETER_SECRET are required"
    exit 1
else
    echo "Building modernized services with:"
    echo "DATABASE_PASSWORD: $DATABASE_PASSWORD"
    echo "PARAMETER_SECRET: $PARAMETER_SECRET"
    echo "TOKEN_SECRET: $TOKEN_SECRET"
fi


# Start ES and the proxy
echo "Starting elasticsearch reverse-proxy"
docker compose -f $BASE/docker-compose.yml up elasticsearch --build -d
attempts=0
until curl -sf http://localhost:9200 > /dev/null 2>&1; do
  attempts=$((attempts + 1))
  if [ $attempts -ge 24 ]; then
    echo "Timed out waiting for Elasticsearch"
    exit 1
  fi
  sleep 5
done
echo "Elasticsearch is ready"

# Start application services (without NiFi) to initialize Elasticsearch indices
echo "Starting modernized application services"
docker compose -f $BASE/docker-compose.yml up modernization-api pagebuilder-api nbs-gateway report-execution keycloak --build -d

echo "Waiting for Elasticsearch indices to be created..."
attempts=0
until curl -sf http://localhost:9200/person/_mapping > /dev/null 2>&1; do
  attempts=$((attempts + 1))
  if [ $attempts -ge 24 ]; then
    echo "Timed out waiting for Elasticsearch indices (is modernization-api running?)"
    exit 1
  fi
  sleep 5
done
echo "Elasticsearch indices are ready"

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
