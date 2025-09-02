#!/usr/bin/env bash
set -e

CURRENT="$(pwd)"
BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

MIGRATION_PATH="/var/opt/database/upgrade"

while getopts ":p:v:" opt; do
  case $opt in
    p)
      MIGRATION_PATH=$OPTARG
      ;;
    v)
      NEDSSDB_VERSION=$OPTARG
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


SCRIPT_PATH="$MIGRATION_PATH/$NEDSSDB_VERSION"
cd "$SCRIPT_PATH"

echo "Applying database updates for [$NEDSSDB_VERSION]"

for database in */; 
do
    if [ -d "$database" ]; then
        echo "\n\tFound updates for $database"

        for found in $(find "$database" -maxdepth 1 -iname "*.sql" | sort) ;
        do
            echo "\t\tApplying $found\n"

            /opt/mssql-tools18/bin/sqlcmd -C -S localhost -U sa -i  "$SCRIPT_PATH/$found"
            
        done

    fi
done

echo "Database migrations for [$NEDSSDB_VERSION] completed"

cd "$CURRENT"