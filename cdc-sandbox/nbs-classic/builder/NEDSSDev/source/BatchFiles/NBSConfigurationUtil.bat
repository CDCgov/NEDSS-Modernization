@echo off

call setenvJBOSS.cmd

"%JAVA_HOME%\jre\bin\java" %JAVA_OPTIONS% -classpath "%CLASSPATH%"  gov.cdc.nedss.systemservice.ejb.dbauthejb.util.NBSConfigurationUtil %1  %2  %3  