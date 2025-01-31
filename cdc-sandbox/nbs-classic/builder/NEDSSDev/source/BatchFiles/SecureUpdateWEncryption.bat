@echo off

call setenvJBOSS.cmd

java %JAVA_OPTIONS% -classpath "%CLASSPATH%"  gov.cdc.nedss.systemservice.ejb.dbauthejb.util.SecureUpdateEncryption  %1