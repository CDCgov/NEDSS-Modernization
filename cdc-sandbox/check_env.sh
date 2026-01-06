set -e
BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
ROOT="$BASE/.."


# Checks for the presence of a `.env` file in the project directory. 
# If one is found, load the values, otherwise create the .env with placeholders for expected values
if [ ! -f $ROOT/.env ]; then
  echo "No root .env found, copying sample.env"
  cp $ROOT/sample.env $ROOT/.env
fi

echo "Reading from "$ROOT/.env" file..."    
set -a
source "$ROOT/.env"
set +a
