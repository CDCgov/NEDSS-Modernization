#!/bin/sh
set -e

cd nbs-classic/builder
rm -rf NEDSSDEV
git clone -b NBS_6.0.15 git@github.com:cdcent/NEDSSDev.git
cd ../..

echo "Building SQL Server database and WildFly..."
docker-compose up nbs-mssql wildfly -d
rm -rf nbs-classic/builder/NEDSSDEV

echo "Classic build complete"
echo "http://localhost:7001/nbs/login"