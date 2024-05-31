#!/bin/bash

## Validate argument
set -e

if [ -z "$1" ] 
then
	echo "createMpi is a helper script that ingests FHIR data into NBS 6 for deduplication testing"
	echo "Usage: "
	echo "createMpi <mpi_directory> <append?>"
	exit 1
fi

if  [ -d $1 ]
then  
  echo "Specified MPI Directory: $(readlink -f ${1})"
  else 
  echo "Argument must be a valid directory"
  exit 1
fi


## Ask if this should be a new MPI or add additional data to existing
PS3="Do you wish to remove pre-existing data? "
	select OPTION in "Yes" "No"; do 
		CLEAN=$OPTION
    break;
	done

if [[ "$CLEAN" == "No" ]]
then
  echo "Adding data to existing MPI"
else
  echo "Deleting existing data..."
  curl -X PUT http://localhost:8082/reset
  echo ""
fi

# Combine all .txt files into single 'combined.json'
echo "Combining patient data..."
echo "[" > combined.json
FIRST=1
for filename in $(readlink -f ${1})/*; do
  if [[ ! -e "$filename" ]]; then continue; fi
  # If first entry, do not add comma
  if [[ "$FIRST" -eq "1" ]];then
    FIRST=0
    else
    echo "," >> combined.json
  fi

  cat "$filename" >> combined.json
done
echo "]" >> combined.json

## Load combined MPI json into database
echo "Loading patient data..."
curl --fail-with-body -X POST http://localhost:8082/load -H "Accept:application/json" -H "Content-Type:application/json" --data "@combined.json"
echo ""

echo "MPI successfully loaded"