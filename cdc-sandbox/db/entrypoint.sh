#!/bin/bash

# Start the script to restore the databse
$INITIALIZE_SCRIPTS/restore.sh &

/opt/mssql/bin/sqlservr