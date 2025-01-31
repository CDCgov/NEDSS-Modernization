@echo off

call setenvJBOSS.cmd

"%JAVA_HOME%\jre\bin\java" %JAVA_OPTIONS% -classpath "%CLASSPATH%"   gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxEntityMatchHashcodeBatchUtil %1  %2    