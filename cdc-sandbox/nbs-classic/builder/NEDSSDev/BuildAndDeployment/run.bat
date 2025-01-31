@echo off
rem -------------------------------------------------------------------------
rem JBoss Bootstrap Script for Win32
rem -------------------------------------------------------------------------

rem $Id: run.bat,v 1.13.4.2 2005/05/04 23:05:42 starksm Exp $

@if not "%ECHO%" == ""  echo %ECHO%
@if "%OS%" == "Windows_NT"  setlocal

set DIRNAME=.\
if "%OS%" == "Windows_NT" set DIRNAME=%~dp0%
set PROGNAME=run.bat
if "%OS%" == "Windows_NT" set PROGNAME=%~nx0%

rem Read all command line arguments

REM
REM The %ARGS% env variable commented out in favor of using %* to include
REM all args in java command line. See bug #840239. [jpl]
REM
REM set ARGS=
REM :loop
REM if [%1] == [] goto endloop
REM         set ARGS=%ARGS% %1
REM         shift
REM         goto loop
REM :endloop

rem Find run.jar, or we can't continue

set RUNJAR=%DIRNAME%\run.jar
if exist "%RUNJAR%" goto FOUND_RUN_JAR
echo Could not locate %RUNJAR%. Please check that you are in the
echo bin directory when running this script.
goto END

:FOUND_RUN_JAR
set JAVA_HOME=%DIRNAME%\..\jrrt-3.1.2-1.6.0
set JBOSS_HOME=%DIRNAME%\..
set JAVA=java

set JAVA=%JAVA_HOME%\bin\java
set nbs.dir=%JBOSS_HOME%\nedssdomain\Nedss
ECHO NBSDIRECTORY= %nbs.dir%
if exist "%JAVA_HOME%\lib\tools.jar" goto SKIP_TOOLS
echo Could not locate %JAVA_HOME%\lib\tools.jar. Unexpected results may occur.
echo Make sure that JAVA_HOME points to a JDK and not a JRE.

:SKIP_TOOLS

rem Include the JDK javac compiler for JSP pages. The default is for a Sun JDK
rem compatible distribution to which JAVA_HOME points

set JAVAC_JAR=%JAVA_HOME%\lib\tools.jar
set NEDSS_DIR=%JBOSS_HOME%\nedssdomain\Nedss
rem If JBOSS_CLASSPATH is empty, don't include it, as this will 
rem result in including the local directory, which makes error tracking
rem harder.

set JBOSS_CLASSPATH=
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\castor\castor-0.9.3.9.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\Log4J\log4j.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\servlet\servlet-2_3.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\webAF\webafutil.jar;
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\webAF\sas.core.jar;
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\webAF\sas.ads.core.jar;
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\webAF\sas.ads.misc.jar;
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\webAF\sas.ads.servlet.jar;
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\webAF\sas.servlet.jar;
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\webAF\sas.text.jar;
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\webAF\sas.webEIS.jar;
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\webAF\sas.intrnet.javatools.jar;
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\tidy\Tidy.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\Apache\commons-pool-1.2.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\Apache\commons-collections-3.2.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\nndMasterMsgFrameWork\NedssMasterMessageFramework.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\customSubformJaxbLibrary\cdf-subform.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\jaxb\jaxb-api.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\jaxb\jaxb-impl.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\jaxb\jaxb-libs.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\jaxb\jaxb-xjc.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\jaxb\jax-qname.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\jaxb\namespace.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\jaxb\relaxngDatatype.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\jaxb\xsdlib.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\SQLServer\sqljdbc4.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\commons-logging-1.0.4.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\struts-core-1.3.8.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\struts-el-1.3.8.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\struts-extras-1.3.8.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\struts-faces-1.3.8.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\struts-taglib-1.3.8.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\jstl-1.0.2.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\commons-beanutils-1.7.0.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\commons-digester-1.8.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\commons-chain-1.1.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\jsp-api-2.3.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\commons-validator-1.3.1.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\Struts-Layout-1.2.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\oro-2.0.8.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\ditchnet-tabs-taglib.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\JustFormsPDF\JustFormsPDF.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\xmlbeans\xbean-2.3.0.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\xmlbeans\jsr173_1.0_api.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\xmlbeans\resolver.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\xmlbeans\xbean_xpath.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\xmlbeans\xmlbeans-qname.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\xmlbeans\xmlpublic.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\xmlbeans\nndintermediarymessage.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\mail\mail.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\mail\activation.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\xmlbeans\PHDCMessage.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\xmlbeans\CDAMessage.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\charts\jcommon-1.0.16.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\commons-fileupload-1.1.1.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\Apache\commons-codec-1.4.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\Apache\poi-3.17.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\Apache\poi-ooxml-3.17.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\struts\commons-io-1.1.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\charts\jfreechart-1.0.13.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\charts\nbs-charts.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\vads\hessian-3.1.3.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\vads\vocabServiceClient.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\DMBPageSchema\dmbPageSchema.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\DMBPageSchema\dmbTemplateExportSchema.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\xmlbeans\DSMAlgorithm.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\xmlbeans\PHDCMessage.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\pdfbox\pdfbox-app-1.8.2.jar
set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%NEDSS_DIR%\libraries\PartnerServices\PartnerServices_v3.jar


if "%JBOSS_CLASSPATH%" == "" (
	set JBOSS_CLASSPATH=%JAVAC_JAR%;%RUNJAR%
) ELSE (
	set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%JAVAC_JAR%;%RUNJAR%
)

rem Setup JBoss specific properties
set JAVA_OPTS=%JAVA_OPTS% -Dprogram.name=%PROGNAME%

rem Sun JVM memory allocation pool parameters. Modify as appropriate.
set JAVA_OPTS=%JAVA_OPTS% -Xms128m -Xmx512m

rem JPDA options. Uncomment and modify as appropriate to enable remote debugging.
set JAVA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n %JAVA_OPTS%

rem Setup the java endorsed dirs
set JBOSS_ENDORSED_DIRS=%JAVA_HOME%\jre\lib\endorsed

echo ===============================================================================
echo .
echo   JBoss Bootstrap Environment
echo .
echo   JBOSS_HOME: %JBOSS_HOME%
echo .
echo   JAVA: %JAVA%
echo .
echo   JAVA_OPTS: %JAVA_OPTS%
echo .
echo   CLASSPATH: %JBOSS_CLASSPATH%
echo .
echo ===============================================================================
echo .

:RESTART
"%JAVA%" %JAVA_OPTS% "-Djava.endorsed.dirs=%JBOSS_ENDORSED_DIRS%" -Djavax.xml.transform.TransformerFactory=org.apache.xalan.processor.TransformerFactoryImpl -Dorg.xml.sax.parser=org.apache.xerces.parsers.SAXParser -Dorg.xml.sax.driver=org.apache.xerces.parsers.SAXParser -Dnbs.dir="%nbs.dir%" -classpath "%JBOSS_CLASSPATH%" org.jboss.Main %*
IF ERRORLEVEL 10 GOTO RESTART

:END
if "%NOPAUSE%" == "" pause

:END_NO_PAUSE
