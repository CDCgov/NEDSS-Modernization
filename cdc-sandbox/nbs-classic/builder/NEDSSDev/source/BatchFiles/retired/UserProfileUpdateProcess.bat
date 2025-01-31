call setenvJBOSS.cmd

set ADMIN_USER_ID=msa


@rem The batch process supports the following options
@rem ODS -- Uploads the latest user profile information into ODS
@rem HTML - Creates a html using the latest user profile information
@rem ODS_AND_HTML -- Uploads data into ODS and creates a HTML file

"%JAVA_HOME%"\jre\bin\java %JAVA_OPTIONS% -classpath "%CLASSPATH%" gov.cdc.nedss.systemservice.ejb.nbssecurityejb.updateprofilebatchprocess.PopulateProfileProcess %ADMIN_USER_ID% ODS_AND_HTML







