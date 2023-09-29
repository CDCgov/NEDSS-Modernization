#!/bin/sh
set -e

# Start ES and the proxy
echo "Starting elasticsearch kibana reverse-proxy"
docker-compose up elasticsearch kibana reverse-proxy --build -d
cd ..

# Start of modernization-api initializes the elasticserach indices 
echo "Starting modernized application services"
docker-compose up --build -d
cd cdc-sandbox

# NiFi handles synchronizing data between nbs-mssql and elasticsearch
echo "Starting NiFi"
docker-compose up nifi --build -d

# Process complete
echo "**** Modernized application startup complete ****"
echo "http://localhost:8080/nbs/login"
echo ""
echo "**** Available users ****"
echo "msa"
echo "superuser"
echo ""