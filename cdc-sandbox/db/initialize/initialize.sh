#!/usr/bin/env bash
set -e
BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

until /opt/mssql-tools18/bin/sqlcmd -C -S localhost -U sa -Q "select 1" &> /dev/null; do
    sleep 1s
done;

echo "*************************************************************************"
echo "  Initializing NBS databases"
echo "*************************************************************************"

for sql in $(find "$BASE/restore.d" -iname "*.sql" | sort) ; 
do
    echo "Executing: $sql"  
    /opt/mssql-tools18/bin/sqlcmd -C -S localhost -U sa -i "$sql"

    echo "Completed: $sql"
done

echo "*************************************************************************"
echo "  NBS databases ready"
echo "*************************************************************************"