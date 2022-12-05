#!/usr/bin/env bash

RESTORE_FILE=~/restore

if [[ -e ${RESTORE_FILE} ]] && [[ ${FORCE_RESTORE:0} -ne 1 ]] ; then
  echo "Restore file exists, skipping restore"
  exit 0
fi

restore_file_directory=/var/opt/db-restore/restore.d

# Massage code for hardcoded windows file paths
restoresql="${restore_file_directory}/NBS Databases Restore Scripts.sql"
userpermsql="${restore_file_directory}/NBS User Permission Scripts.sql"
sed -i 's/D:\\NBS DB Backups\\/\/var\/opt\/db-restore\/restore.d\//g' "$restoresql"
sed -i 's/D:\\MSSQL\\Data\\/\/var\/opt\/mssql\/data\//g' "$restoresql"
mv "$restoresql" "${restore_file_directory}/00-restore.sql"
mv "$userpermsql" "${restore_file_directory}/01-user-permissions.sql"

for restore_file in $(find "$restore_file_directory" -iname "*.sql" |sort) ; do
  echo "Restoring: $restore_file"
  for i in {1..50};
  do
      /usr/bin/sqlcmd -S localhost -U sa -i "$restore_file"
      if [ $? -eq 0 ]
      then
          echo "restore $restore_file completed"
          touch $RESTORE_FILE
          break
      else
          echo "database not ready yet..."
          sleep 1
      fi
  done
done
