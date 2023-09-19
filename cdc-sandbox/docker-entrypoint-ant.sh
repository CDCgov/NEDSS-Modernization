#!/bin/bash

NEDSS_HOME=/home/NEDSSDev

rm -rf $NEDSS_HOME
mkdir $NEDSS_HOME

echo Copying NEDSSDev src directory to build container ...

cp -r /mnt/src/* $NEDSS_HOME

# Massage NEDSS source code
sed -i 's/PhcMartEtl.bat/PHCMartETL.bat/g' $NEDSS_HOME/BuildAndDeployment/build.report.xml
sed -i 's/PhcMartEtl.bat/PHCMartETL.bat/g' $NEDSS_HOME/pom.xml
cp $NEDSS_HOME/source/gov/cdc/nedss/webapp/nbs/resource/javascript/Globals.js $NEDSS_HOME/source/gov/cdc/nedss/webapp/nbs/resource/javascript/globals.js

cd $NEDSS_HOME

# Clean container dist directory
rm -rf build

if [ -f "/mnt/src/pom.xml" ]; then
    echo Starting maven build
    $MAVEN_HOME/bin/mvn package -DskipTests
else
    echo Starting ant build
    cd BuildAndDeployment
    $ANT_HOME/bin/ant
fi

# TODO: check build return code and send failure status to docker context

# Clean output dist directory and copy artifacts after a successful build
rm -rf /mnt/dist/*
cp -r build/* /mnt/dist/

