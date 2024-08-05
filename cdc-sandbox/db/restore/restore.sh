#!/usr/bin/env bash
until /opt/mssql-tools18/bin/sqlcmd -C -S localhost -U sa -Q "select 1" &> /dev/null; do
    sleep 1s
done;

echo "*************************************************************************"
echo "Initializing NBS databases"
echo "*************************************************************************"

for restore_file in $(find "$INITIALIZE_SCRIPTS/restore.d" -iname "*.sql" | sort) ; 
do
    echo "Executing: $restore_file"  
    /opt/mssql-tools18/bin/sqlcmd -C -S localhost -U sa -i "$restore_file"

    echo "Completed: $restore_file"
done
