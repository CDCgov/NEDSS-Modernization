set -e
BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
ROOT="$BASE/.."


# Checks for the presence of a `.env` file in the scripts directory. 
# If one is found, load the values, otherwise create the .env with placeholders for expected values
if [ ! -f $ROOT/.env ]; then
  echo "DATABASE_PASSWORD=$DATABASE_PASSWORD" > $ROOT/.env
  echo "TOKEN_SECRET=$TOKEN_SECRET" >> $ROOT/.env
  echo "PARAMETER_SECRET=$PARAMETER_SECRET" >> $ROOT/.env

  # Supplmental values  
  echo "DATABASE_USER=${DATABASE_USER:-sa}" >> $ROOT/.env
  echo 'NBS_DATASOURCE_SERVER=nbs-mssql' >> $ROOT/.env
  echo 'CLASSIC_SERVICE=wildfly:7001' >> $ROOT/.env
  echo 'MODERNIZATION_SERVICE=${MODERNIZATION_API_SERVER:-modernization-api}:${MODERNIZATION_API_PORT:-8080}' >> $ROOT/.env
  echo 'UI_SERVICE=${MODERNIZATION_UI_SERVER:-modernization-api}:${MODERNIZATION_UI_PORT:-8080}' >> $ROOT/.env
  echo 'PAGEBUILDER_SERVICE=${PAGEBUILDER_API_SERVER:-pagebuilder-api}:${PAGEBUILDER_API_PORT:-8095}' >> $ROOT/.env
  echo 'NBS_SECURITY_OIDC_ENABLED=${OIDC_ENABLED:-false}' >> $ROOT/.env
  echo 'NBS_SECURITY_OIDC_URI=${ISSUER_URI:-http://keycloak:8080/realms/nbs-users}' >> $ROOT/.env
  echo 'PAGEBUILDER_ENABLED=true' >> $ROOT/.env
fi

echo "Reading from "$ROOT/.env" file..."    
set -a
source "$ROOT/.env"
set +a

if [ ! -f $BASE/.env ]; then
  echo "DATABASE_PASSWORD=$DATABASE_PASSWORD" > $BASE/.env
  echo "NIFI_PASSWORD=$NIFI_PASSWORD" >> $BASE/.env
  echo "KEYCLOAK_ADMIN_PASSWORD=$KEYCLOAK_ADMIN_PASSWORD" >> $BASE/.env
fi

echo "Reading from "$BASE/.env" file..."    
set -a
source "$BASE/.env"
set +a