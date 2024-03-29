arguments="$*"

if [ -z "$CLINIDEID_HOME" ]; then
  echo "The $CLINIDEID_HOME environment variable is not set."
  exit 1
fi

if [ -z "$GOLDEN_DB_HOME" ]; then
  echo "The GOLDEN_DB_HOME environment variable is not set."
  exit 1
fi


cd $CLINIDEID_HOME
java -Xmx28g -cp CliniDeIDComplete.jar com.clinacuity.deid.mains.DeidPipeline \
    -l beyond -pii OtherIDNumber-false,Zip-2 -t resynthesis,detectedPII,map \
    -txt -if -id $GOLDEN_DB_HOME -of -od $GOLDEN_DB_HOME/out $arguments
