@echo off

rem =====================================================================
rem One Stop Run Script for NBS Application. Ends by run standalone.bat
rem found in wildfly.x/bin/standalone.bat
rem =====================================================================

@if not "%ECHO%" == ""  echo %ECHO%

setlocal
set "%OS%" == "Windows_NT" setlocal
set NEDSS_DEVPKG=".\Development Package Rel6.0.11\NBS_6.0.11"
set NEDSS_SQLDIR="%NEDSS_DEVPKG%\db\SQLSERVER"

docker-compose up -d nbs-mssql

rem THIS LINE IS BAD PRACTICE AND IS ONLY USED FOR TESTING
rem ENSURE DATABASE CONNECTION ARE PROPERLY SET UP INSTEAD OF WAITING 60s
timeout 60

docker-compose up -d wildfly
