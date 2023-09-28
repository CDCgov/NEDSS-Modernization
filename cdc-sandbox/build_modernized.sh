#!/bin/sh
set -e

echo "Starting elasticsearch kibana reverse-proxy..."
docker-compose up elasticsearch kibana reverse-proxy -d
cd ..
echo "Starting modernized application"
docker-compose up -d
cd cdc-sandbox
echo "Starting NiFi"
docker-compose up nifi -d
echo "Modernized application startup complete"