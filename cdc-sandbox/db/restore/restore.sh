#!/usr/bin/env bash

restore_file_directory=/var/opt/db-restore/restore.d

for restore_file in $(find "$restore_file_directory" -iname "*.sql" |sort) ; do
  echo "Restoring: $restore_file"
  for i in {1..50};
  do
      /usr/bin/sqlcmd -S localhost -U sa -i "$restore_file"
      if [ $? -eq 0 ]
      then
          echo "restore $restore_file completed"
          break
      else
          echo "database not ready yet..."
          sleep 1
      fi
  done
done
