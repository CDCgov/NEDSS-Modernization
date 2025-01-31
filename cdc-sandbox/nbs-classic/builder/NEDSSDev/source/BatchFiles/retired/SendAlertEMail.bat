call setenvJBOSS.cmd

set ALERT_ADMIN_USER_ID=msa

"%JAVA_HOME%"\jre\bin\java %JAVA_OPTIONS% -classpath "%CLASSPATH%" gov.cdc.nedss.alert.admin.AlertMailler %ALERT_ADMIN_USER_ID% ALERT_ADMIN_USER_ID








 