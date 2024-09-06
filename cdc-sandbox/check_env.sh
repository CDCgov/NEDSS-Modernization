set -e
BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
ROOT="$BASE/.."

# Checks for the presence of a `.env` file in the scripts directory. 
# If one is found, load the values, otherwise create the .env with placeholders for expected values
if [ -f $BASE/.env ] && [ -f $ROOT/.env ]; then
  echo "Reading from .env files..."
  source "$BASE/.env"
  source "$ROOT/.env"
else
  echo "No .env file found, creating..."
  echo "DATABASE_PASSWORD=$DATABASE_PASSWORD" > $BASE/.env
  echo "NIFI_PASSWORD=$NIFI_PASSWORD" >> $BASE/.env
  echo "KEYCLOAK_ADMIN_PASSWORD=$KEYCLOAK_ADMIN_PASSWORD" >> $BASE/.env

  echo "DATABASE_PASSWORD=$DATABASE_PASSWORD" > $ROOT/.env
  echo "TOKEN_SECRET=$TOKEN_SECRET" >> $ROOT/.env
  echo "PARAMETER_SECRET=$PARAMETER_SECRET" >> $ROOT/.env
fi