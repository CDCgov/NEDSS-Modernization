#!/bin/bash

# Ensure the GOLDEN_DB_HOME environment variable is set
if [ -z "$GOLDEN_DB_HOME" ]; then
  echo "The GOLDEN_DB_HOME environment variable is not set."
  exit 1
fi

# Check for the required argument
if [ $# -eq 0 ]; then
    echo "Usage: $0 <db_suffix>"
    echo "Example: $0 odse"
    exit 1
fi

# Database configuration - update these variables to match your environment
SERVER_NAME="localhost"
DATABASE_NAME="NBS_$1"
USERNAME="sa"
PASSWORD="fake.fake.fake.1234"

# Path to the file containing table names
TABLE_LIST_FILE="$GOLDEN_DB_HOME/tabs-$1.txt"

# Check if table list file exists
if [ ! -f "$TABLE_LIST_FILE" ]; then
  echo "Table list file $TABLE_LIST_FILE not found."
  exit 1
fi

GOLDEN_DB_DATA="$GOLDEN_DB_HOME/data"

# Read table names from the file and export each to a CSV file
while read -r table; do
  if [ ! -z "$table" ]; then  # Skip empty lines
    echo "Restoring $table data to CSV..."
    sqlcmd -S "$SERVER_NAME" -d "$DATABASE_NAME" -U "$USERNAME" -P "$PASSWORD" \
           -Q "EXEC dbo.pii_${table}_Restore '$GOLDEN_DB_DATA/out/${table}.deid.resynthesis.txt';"
  fi
done < "$TABLE_LIST_FILE"

echo "Import completed."