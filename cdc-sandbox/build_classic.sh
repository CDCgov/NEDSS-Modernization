#!/bin/sh
set -e

# Clone NEDSSDev
cd nbs-classic/builder
rm -rf NEDSSDEV
git clone -b NBS_6.0.15 git@github.com:cdcent/NEDSSDev.git
cd ../..

# Build and deploy database and wildfly containers
echo "Building SQL Server database and WildFly"
docker-compose up nbs-mssql wildfly --build -d

# Cleanup 
rm -rf nbs-classic/builder/NEDSSDEV

echo "**** Classic build complete ****"
echo "http://localhost:7001/nbs/login"
echo ""
echo "**** Available users ****"
echo "msa"
echo "superuser"
echo ""