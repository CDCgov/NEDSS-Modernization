call setenvJBOSS.cmd
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\xmlbeans\xmlbeans-2.6.0.jar
"%JAVA_HOME%"\jre\bin\java %JAVA_OPTIONS% -classpath "%CLASSPATH%"  gov.cdc.nedss.systemservice.ejb.decisionsupportejb.util.UpdateDSMAlgorithms %1    