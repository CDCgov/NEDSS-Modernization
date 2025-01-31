@echo off 

set dir=%~dp0:~0,-1%
set APPS_DIR=%dir:~0,2%

:checkforjboss
set JBOSS_HOME=%JBOSS_HOME%
set JAVA_HOME=%JAVA_HOME%
set path=%JAVA_HOME%\bin 

@rem THIS FILE SHOULD BE RUN FROM WITHIN NEDSS DEPLOYMENT DIRECTORY FOR RELATIVE PATHS TO WORK.

:setvariables

set JAVA_VM=-classic
set NEDSSDOMAIN=%JBOSS_HOME%\nedssdomain

if "%NEDSSDOMAIN%"  == "" goto error_nedssdomain

set NEDSSLIB=..\..\..\modules\system\layers\base\NBSLibraries\main

set JAVA_OPTIONS=-Dnbs.dir=.. -Djboss.server.home.dir=%NEDSSDOMAIN%

@rem This option should be set once the SSL Integration is done
@rem set JAVA_OPTIONS=-Dnbs.dir=..\ -Dweblogic.security.SSL.trustedCAKeyStore=%NEDSSDOMAIN%\lib

:setclasspath

set CLASSPATH=

set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\lib\tools.jar

set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\Log4J\log4j-1.2-api-2.19.0.jar  
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\Log4J\log4j-api-2.19.0.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\Log4J\log4j-core-2.19.0.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\webAF\webAF.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\webAF\webAFServerPages.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\SQLServer\mssql-jdbc-6.2.2.jre8.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\Castor\castor-0.9.3.9.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\Centrus\ABClient.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\Apache\commons-codec-1.4.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\Apache\poi-3.17.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\Apache\poi-ooxml-3.17.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\xmlbeans\xbean_xpath.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\xmlbeans\xmlbeans-qname.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\xmlbeans\xmlpublic.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\xmlbeans\nndintermediarymessage53.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\xmlbeans\PHDCMessage.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\xmlbeans\DSMAlgorithm.jar
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\OWASP\encoder-1.2.2.jar

@rem jboss client libraries
rem set CLASSPATH=%CLASSPATH%;%JBOSS_HOME%\client\jbossall-client.jar

@rem nbs libraries
set CLASSPATH=%CLASSPATH%;%NEDSSDOMAIN%\Nedss\classfiles\classfiles.zip
set CLASSPATH=%CLASSPATH%;%NEDSSLIB%\nndMasterMsgFrameWork\NedssMasterMessageFramework.jar
set CLASSPATH=%CLASSPATH%;..\Properties\LogConfig

set CLASSPATH=%CLASSPATH%;..\..\..\modules\system\layers\base\org\jboss\as\naming\main\wildfly-naming-10.0.0.Final.jar
set CLASSPATH=%CLASSPATH%;..\..\..\bin\client\jboss-client.jar
set CLASSPATH=%CLASSPATH%;..\..\..\modules\system\layers\base\org\jboss\log4j\logmanager\main\log4j-jboss-logmanager-1.1.0.Final.jar
set CLASSPATH=%CLASSPATH%;..\..\..\modules\system\layers\base\org\jboss\logmanager\main\jboss-logmanager-1.5.2.Final.jar

@echo off

:setpath
set PATH=%PATH%;%JAVA_HOME%\bin
set PATH=%PATH%;%JBOSS_HOME%\bin


goto finish


:error_nedssdomain
echo.
echo ERROR!
echo	The setenvJBOSS.cmd script could not locate %NEDSSDOMAIN%
echo	Edit the NEDSSDOMAIN = entry to point to your nedssdomain installation in jboss
echo.
goto finish


:finish


