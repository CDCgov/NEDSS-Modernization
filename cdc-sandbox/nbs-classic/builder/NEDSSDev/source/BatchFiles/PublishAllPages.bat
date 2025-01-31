@echo off

call setenvJBOSS.cmd

"%JAVA_HOME%"\jre\bin\java %JAVA_OPTIONS% -classpath "%CLASSPATH%" gov.cdc.nedss.pagemanagement.pagebuilderbatchprocess.PublishAllPages %1 %2