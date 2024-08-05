#!/bin/sh
set -e

BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

source $BASE/check_env.sh

if [ -z "$DATABASE_PASSWORD" ]
then
    echo "DATABASE_PASSWORD is required"
    exit 1
fi


CLASSIC_PATH=$BASE/nbs-classic/builder/NEDSSDev
CLASSIC_VERSION=NBS_6.0.16

clean=false

while getopts ":c" opt; do
  case $opt in
    c)
      clean=true
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      ;;
  esac
done

if $clean; then
  echo "Removing SQL Server and NBS6 containers"
  docker compose down --volumes wildfly nbs-mssql
fi

echo "Building SQL Server"

DOCKER_DEFAULT_PLATFORM=linux/amd64 docker compose -f $BASE/docker-compose.yml up nbs-mssql --build -d


echo "Building NBS6 Application"

rm -rf $CLASSIC_PATH && \
  git clone -b $CLASSIC_VERSION git@github.com:cdcent/NEDSSDev.git $CLASSIC_PATH && \
  docker compose -f $BASE/docker-compose.yml up wildfly --build -d && \
  rm -rf $CLASSIC_PATH


echo "**** Classic build complete ****"
echo "http://localhost:7001/nbs/login"
echo ""
echo "**** Available users ****"
echo "*\tmsa"
echo "*\tsuperuser"
echo ""