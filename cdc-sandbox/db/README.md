# Local development db

A near empty NBS database for use in local development and test execution.

## Building

The `build.sh` script automates the setup of the `nbs-mssql` container by creating a SQL Server Docker container initialized with an NBS6 database. This is a development database that is only meant for local development and to support integration tests.

**Usage:**

```sh
./build.sh -p <DATABASE_PASSWORD>
```

| Option | Default | Description                                                                       |
| ------ | ------- | --------------------------------------------------------------------------------- |
| `-p`   |         | Password for the "SA" user (can also be set via `DATABASE_PASSWORD` env variable) |
| `-c`   | `false` | Removes the existing container and data volume before building.                   |

## Backup

Creates a backup of the database from the running container and copies it to the host filesystem.

**Usage:**

```sh
./backup.sh  -c <CONTAINER_NAME> -d <DATABASE_NAME> -b <BACKUP_PATH>
```

| Option | Default                     | Description                                        |
| ------ | --------------------------- | -------------------------------------------------- |
| `-c`   | `mssql`                     | Name of the running container.                     |
| `-d`   | `nbs_odse`                  | Name of the database to backup.                    |
| `-p`   | `.backups`                  | The local path to store the resulting backup file. |
| `-f`   | `Y-M-D.{database-name}.bak` | Name of the backup file.                           |

## Restore

Restores the database of the running container using a backup file from the host file system.

**Usage:**

```sh
./restore.sh -c <CONTAINER_NAME> -d <DATABASE_NAME> -f <BACKUP_FILE>
```

| Option | Default    | Description                                   |
| ------ | ---------- | --------------------------------------------- |
| `-c`   | `mssql`    | Name of the running container.                |
| `-d`   | `nbs_odse` | Name of the database to backup.               |
| `-p`   | `.backups` | The local path that contains the backup file. |
| `-f`   |            | Name of the backup file to restore.           |

## Upgrade

Applies the [NBS6 database release upgrade scripts](https://github.com/cdcent/NEDSSDB) for a specific version.

**Usage:**

```sh
./upgrade.sh -c <CONTAINER_NAME> -v <NEDSSDB_VERSION>
```

| Option | Default | Description                                                                                                            |
| ------ | ------- | ---------------------------------------------------------------------------------------------------------------------- |
| `-c`   | `mssql` | Name of the running container.                                                                                         |
| `-v`   |         | The name of the folder that contains the release update scripts for a specific version of the NBS Database. (required) |

## Pushing a new test db image

### Prerequisites

- Install [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
- Configure [SSO](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-sso.html)
- Build the test-db image

1. Log in to AWS CLI
   ```
   aws sso login --sso-session <session-name>
   ```
2. Connect docker to AWS ECR (See push commands for more info within the AWS ECR UI)
   ```
   aws ecr get-login-password --region us-east-1 --profile shared | docker login --username AWS --password-stdin <ecr url: ...dkr.ecr.us-east-1.amazonaws.com>
   ```
3. Tag the image
   ```
   docker tag cdc-sandbox-nbs-mssql:latest <...dkr.ecr.us-east-1.amazonaws.com/cdc-nbs-modernization/modernization-test-db:latest>
   ```
4. Push the image to ECR
   ```
   docker push <image to push: ...dkr.ecr.us-east-1.amazonaws.com/cdc-nbs-modernization/modernization-test-db:latest>
   ```
