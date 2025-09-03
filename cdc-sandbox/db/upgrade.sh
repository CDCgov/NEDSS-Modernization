#!/usr/bin/env bash
set -e

CURRENT="$(pwd)"
BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"


NEDSSDB_PATH=$BASE/.working/NEDSSDB/
CONTAINER_NAME=nbs-mssql
clean=false

while getopts ":p:v:n:" opt; do
  case $opt in
    p)
      NEDSSDB_PATH=$OPTARG
      ;;
    v)
      NEDSSDB_VERSION=$OPTARG
      ;;    
    n)
      CONTAINER_NAME=$OPTARG
      ;;  
    \?)
      echo "Invalid option: -$OPTARG" >&2
      ;;
  esac
done

if [ -z "$NEDSSDB_VERSION" ]
then
    echo "The NEDSS DB version is required, use -v to specify the version.  i.e. -v R6.0.18"
    exit 1
fi

DATABASE_UPDATE_SCRIPT_PATH="NBS_DB/db/Disk_Database/WINDOWS/DB Setup Scripts-Release Upgrade/MS SQL/$NEDSSDB_VERSION"

if [ ! -d $NEDSSDB_PATH ]; then
    echo "Downloading NEDSSDB"
    git clone --no-checkout git@github.com:cdcent/NEDSSDB.git $NEDSSDB_PATH
    
    cd $NEDSSDB_PATH
    git sparse-checkout init --cone
    git sparse-checkout set "$DATABASE_UPDATE_SCRIPT_PATH"
    git checkout master

    cd "$CURRENT"
fi

echo "Preparing database updates for [$NEDSSDB_VERSION]"

CONTAINER_UPGRADE_PATH=/var/opt/database/upgrade

docker exec $CONTAINER_NAME mkdir -p "$CONTAINER_UPGRADE_PATH/$NEDSSDB_VERSION"
docker cp "$BASE/migrate.sh" "$CONTAINER_NAME:$CONTAINER_UPGRADE_PATH/migrate.sh"
docker cp "$NEDSSDB_PATH/$DATABASE_UPDATE_SCRIPT_PATH" "$CONTAINER_NAME:$CONTAINER_UPGRADE_PATH"

docker exec $CONTAINER_NAME sh "$CONTAINER_UPGRADE_PATH/migrate.sh" -v $NEDSSDB_VERSION

echo "Database updated to [$NEDSSDB_VERSION]"


if $clean; then
  rm -rf $NEDSSDB_PATH
fi