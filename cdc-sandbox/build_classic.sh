#!/bin/bash
set -e

BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

source $BASE/check_env.sh

if [ -z "$DATABASE_PASSWORD" ]
then
    echo "DATABASE_PASSWORD is required"
    exit 1
fi

# Start NBS 6 and DB
echo "Starting NBS 6 DB and wildfly server"
docker compose -f $BASE/docker-compose.yml up nbs-mssql wildfly --pull always -d

if [[ "${ENABLE_SAS}" = "true" ]]; then
    echo "Starting SAS server"
    docker compose -f $BASE/docker-compose.yml up sas -d
fi


echo "**** Classic build complete ****"
echo "http://localhost:7001/nbs/login"
echo ""
echo "**** Available users ****"
echo "*\tmsa"
echo "*\tsuperuser"
echo ""
