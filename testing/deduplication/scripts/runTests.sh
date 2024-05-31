#!/bin/bash
set -e

## Validate argument
if [ -z "$1" ] 
then
	echo "runTests is a helper script that runs the specified test data through the NBS 6 deduplication algorithm"
	echo "Usage: "
	echo "runTest <test_data_directory>"
	exit 1
fi

if  [ -d $1 ]
then  
  echo "Specified Test Data Directory: $(readlink -f ${1})"
  else 
  echo "Argument must be a valid directory"
  exit 1
fi

for filename in $(readlink -f ${1})/*; do
  if [[ ! -e $filename ]]; then continue; fi
  echo "Current file: $filename"
  ROW=$(echo $(basename $filename | awk -F '[_.]' '{print $2}'))
  echo "Current row: $ROW"

  # Update external_person_id to be row number to prevent database conflict
  cat $filename | jq ".external_person_id = $ROW" > tmp.json
  mv tmp.json $filename

  # Send file content to API
  curl -s -X POST http://localhost:8082/test -H "Accept:application/json" \
   -H "Content-Type:application/json" \
   --data "@$filename" | jq > "Results/${ROW}_results.json"

  ROW=$((ROW + 1))
done