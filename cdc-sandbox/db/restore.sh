#! /usr/bin/env bash
CONTAINER_NAME=nbs-mssql
DATABASE_NAME=nbs_odse

while getopts ":c:d:f:" opt; do
  case $opt in
    c)
      CONTAINER_NAME=$OPTARG
      ;;
    d)
      DATABASE_NAME=$OPTARG
      ;;
    f)
      FILE_PATH=$OPTARG
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      ;;
  esac
done

if [ -z $CONTAINER_NAME ]
then
  echo "Usage: $0 [container name] [database name] [backup file name]"
  exit 1
fi

if [ -z $DATABASE_NAME ]
then
  echo "Usage: $0 [container name] [database name] [backup file name]"
  exit 1
fi

if [ -z $FILE_PATH ]
then
  echo "Usage: $0 [container name] [database name] [backup file name]"
  exit 1
fi

# Check if the backup file can be found
if [ ! -f $FILE_PATH ]
then
  echo "Backup file $FILE_PATH does not exist."
  exit 1
fi

# Set bash to exit if any command fails
set -e
set -o pipefail

FILE_NAME=$(basename $FILE_PATH)

echo "Copying backup file $FILE_PATH to container '$CONTAINER_NAME'"
echo "Note: the container should already be running!"

# Copy the file over to a special restore folder in the container, where the sqlcmd binary can access it
docker exec $CONTAINER_NAME mkdir -p /var/opt/mssql/restores
docker cp $FILE_PATH "$CONTAINER_NAME:/var/opt/mssql/restores/$FILE_NAME"
docker exec -u root $CONTAINER_NAME chown mssql "/var/opt/mssql/restores/$FILE_NAME"

echo "Restoring database '$DATABASE_NAME' in container '$CONTAINER_NAME'..."

# Restore the database with sqlcmd
docker exec -it "$CONTAINER_NAME" /opt/mssql-tools18/bin/sqlcmd -C -S localhost -U SA -Q "RESTORE DATABASE [$DATABASE_NAME] FROM DISK = N'/var/opt/mssql/restores/$FILE_NAME' WITH FILE = 1, NOUNLOAD, REPLACE, RECOVERY, STATS = 5"

# Recreate the database user permissions
docker exec -it "$CONTAINER_NAME" /opt/mssql-tools18/bin/sqlcmd -C -S localhost -U SA -i "/var/opt/database/initialize/restore.d/01-database-user-permissions.sql"

echo ""
echo "Restored database '$DATABASE_NAME' in container '$CONTAINER_NAME' from file $FILE_PATH"
echo "Done!"