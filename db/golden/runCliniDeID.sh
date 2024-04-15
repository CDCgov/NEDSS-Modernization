#!/bin/bash

arguments="$*"

if [ -z "$CLINIDEID_HOME" ]; then
  echo "The CLINIDEID_HOME environment variable is not set."
  exit 1
fi

if [ -z "$GOLDEN_DB_HOME" ]; then
  echo "The GOLDEN_DB_HOME environment variable is not set."
  exit 1
fi

GOLDEN_DB_DATA="$GOLDEN_DB_HOME/data"

cd $CLINIDEID_HOME
java -Xmx28g -cp CliniDeIDComplete.jar com.clinacuity.deid.mains.DeidPipeline \
    -l beyond -pii OtherIDNumber-false,Date-2,Zip-2 -t resynthesis,detectedPII,map \
    -txt -if -id $GOLDEN_DB_DATA -of -od $GOLDEN_DB_DATA/out $arguments
