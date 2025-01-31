@echo on

call setenvJBoss.cmd

 %JAVA_HOME%\jre\bin\java %JAVA_OPTIONS% -classpath  %CLASSPATH% gov.cdc.nedss.systemservice.nbscontext.contextgenerator.SimpleCSVProcessor %1
