set -e
BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

# Checks for the presence of a `.env` file in the scripts directory. 
# If one is found, load the values, otherwise create the .env with placeholders for expected values
if [ -f $BASE/.env ] && [ -f $BASE/../.env ]; then
  echo "Reading values from .env file..."
  cat $BASE/.env
  source "$BASE/.env"
  source "$BASE/../.env"
else
  echo "No .env file found, creating..."
  echo "DATABASE_PASSWORD=$DATABASE_PASSWORD" > $BASE/.env
  echo "NIFI_PASSWORD=$NIFI_PASSWORD" >> $BASE/.env
  echo "KEYCLOAK_ADMIN_PASSWORD=$KEYCLOAK_ADMIN_PASSWORD" >> $BASE/.env

  echo "DATABASE_PASSWORD=$DATABASE_PASSWORD" > $BASE/../.env
  echo "TOKEN_SECRET=$TOKEN_SECRET" >> $BASE/../.env
  echo "PARAMETER_SECRET=$PARAMETER_SECRET" >> $BASE/../.env
fi