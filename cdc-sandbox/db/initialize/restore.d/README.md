# Want to automatically restore data?

Any `.sql` placed here will be restored during startup of the docker container in lexicographic order using `sqlcmd`.

## Important paths

The path to this directory as mounted by the docker container:

```
/var/opt/db-restore/restore.d
```

The path to mssql data within the docker container:

```
/var/opt/mssql/data
```