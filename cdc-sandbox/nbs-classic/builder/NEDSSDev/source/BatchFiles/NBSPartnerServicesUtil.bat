@echo off
REM Usage :  NBSPartnerServices.bat <USER_ID> [March or September] [Year YYYY] [Contact First Name] [optional Contact Last Name]
call setenvJBOSS.cmd

"%JAVA_HOME%\jre\bin\java" %JAVA_OPTIONS% -classpath "%CLASSPATH%"  gov.cdc.nedss.nnd.util.PartnerServicesFileExtractUtil %1 %2 %3 %4 %5