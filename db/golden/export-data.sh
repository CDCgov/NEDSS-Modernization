#!/bin/bash

# Ensure the SQLCMD_OUTPUT_DIR environment variable is set
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
TABLE_LIST_FILE="$GOLDEN_DB_HOME/tabs_$1.txt"

# Check if table list file exists
if [ ! -f "$TABLE_LIST_FILE" ]; then
  echo "Table list file $TABLE_LIST_FILE not found."
  exit 1
fi

# Read table names from the file and export each to a CSV file
while read -r table; do
  if [ ! -z "$table" ]; then  # Skip empty lines
    echo "Exporting $table to CSV..."
    sqlcmd -S "$SERVER_NAME" -d "$DATABASE_NAME" -U "$USERNAME" -P "$PASSWORD" \
           -Q "EXEC dbo.pii_${table};" \
           -o "$GOLDEN_DB_HOME/data/${table}.txt" -s","
  fi
done < "$TABLE_LIST_FILE"

echo "Export completed."