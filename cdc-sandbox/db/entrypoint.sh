#!/usr/bin/env bash

set -m
/opt/mssql/bin/sqlservr & /var/opt/db-restore/restore.sh
fg
