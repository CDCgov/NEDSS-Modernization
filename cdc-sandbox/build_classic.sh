#!/bin/sh
set -e

BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

source $BASE/check_env.sh

if [ -z "$DATABASE_PASSWORD" ]
then
    echo "DATABASE_PASSWORD is required"
    exit 1
else
    echo "Building NBS classic with:"
    echo "DATABASE_PASSWORD=$DATABASE_PASSWORD"
fi


CLASSIC_PATH=$BASE/nbs-classic/builder/NEDSSDev
CLASSIC_VERSION=NBS_6.0.15

# Clone NEDSSDev
rm -rf $CLASSIC_PATH
git clone -b $CLASSIC_VERSION git@github.com:cdcent/NEDSSDev.git $CLASSIC_PATH

# Build and deploy database and wildfly containers
echo "Building SQL Server database and WildFly"
docker compose -f $BASE/docker-compose.yml up nbs-mssql wildfly --build -d

# Cleanup 
rm -rf $CLASSIC_PATH

echo "**** Classic build complete ****"
echo "http://localhost:7001/nbs/login"
echo ""
echo "**** Available users ****"
echo "*\tmsa"
echo "*\tsuperuser"
echo ""