#! /usr/bin/env bash
set -e

BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

CONTAINER_NAME=nbs-mssql
DATABASE_NAME=nbs_odse
BACKUP_PATH=${BASE}/.backups

while getopts ":c:d:p:f:" opt; do
  case $opt in
    c)
      CONTAINER_NAME=$OPTARG
      ;;
    d)
      DATABASE_NAME=$OPTARG
      ;;
    p)
      BACKUP_PATH=$OPTARG
      ;;
    f)
      FILE_NAME=$OPTARG
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      ;;
  esac
done

if [ -z $CONTAINER_NAME ]
then
  echo "Usage: $0 [container name] [database name]"
  exit 1
fi

if [ -z $DATABASE_NAME ]
then
  echo "Usage: $0 [container name] [database name]"
  exit 1
fi

# Set bash to exit if any further command fails
set -e
set -o pipefail

# Create a file name for the backup based on the current date and time
if [ -z $FILE_NAME ] 
then 
  FILE_NAME=$(date +%Y-%m-%d.$DATABASE_NAME.bak)
fi

FILE_LOCATION="$BACKUP_PATH/$FILE_NAME"

# Make sure the backups folder exists on the host file system
mkdir -p "$BACKUP_PATH"

echo "Backing up database [$DATABASE_NAME] from container $CONTAINER_NAME"

# Create a database backup with sqlcmd
docker exec -it "$CONTAINER_NAME" /opt/mssql-tools18/bin/sqlcmd -b -V16 -C -S localhost -U SA -Q "BACKUP DATABASE [$DATABASE_NAME] TO DISK = N'/var/opt/mssql/backups/$FILE_NAME' with COMPRESSION, NOFORMAT, NOINIT, NAME = '$DATABASE_NAME-full', SKIP, NOREWIND, NOUNLOAD, STATS = 10"

echo ""
echo "Exporting file from container"

# Copy the created file out of the container to the host filesystem
docker cp $CONTAINER_NAME:/var/opt/mssql/backups/$FILE_NAME $FILE_LOCATION

echo "Backed up database [$DATABASE_NAME] to $FILE_LOCATION"