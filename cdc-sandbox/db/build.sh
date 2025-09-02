#!/bin/sh
set -e

BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

clean=false

##

while getopts ":c:p:" opt; do
  case $opt in
    c)
      clean=true
      ;;
    p)
      DATABASE_PASSWORD="$OPTARG"
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      ;;
  esac
done

##

if [ -z "$DATABASE_PASSWORD" ]
then
    echo "A password for the database is required, please provide one using the -p option or by setting the DATABASE_PASSWORD environment variable."
    exit 1
fi

##

if $clean; then
  docker compose stop nbs-mssql && docker compose rm nbs-mssql
  docker volume rm --force cdc-sandbox-nbs-mssql-data
fi

echo "Building SQL Server"

DOCKER_DEFAULT_PLATFORM=linux/amd64 docker compose -f $BASE/../docker-compose.yml up nbs-mssql --build -d
