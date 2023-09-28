@echo off

rem =====================================================================
rem NEDSS_HOME env variable must be set to the full path of the
rem NEDSSDev source tree downloaded from https://github.com/cdcent/NEDSSDev
rem =====================================================================

@if not "%ECHO%" == ""  echo %ECHO%

setlocal
set "%OS%" == "Windows_NT" setlocal

docker build -t nedssdev --force-rm -f Dockerfile-antbuild .

set NEDSS_DIST=%cd%\dist\nedss
mkdir "%NEDSS_DIST%"
docker run --rm -v "%NEDSS_HOME%":/mnt/src:ro -v "%NEDSS_DIST%":/mnt/dist -v m2:/root/.m2 nedssdev

docker-compose build nbs-mssql
docker-compose build wildfly
