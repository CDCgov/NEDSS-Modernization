@echo off

call setenvJBOSS.cmd

%JAVA_HOME%\jre\bin\java %JAVA_OPTIONS% -classpath "%CLASSPATH%" gov.cdc.nedss.nnd.nndbatchprocess.NNDBatchProcessor %1