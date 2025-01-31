@echo off
                  
if "%1" == "?" goto Usage
if "%1"=="help"  goto Usage
goto Start

:Usage
echo Usage:
echo     UndoCDFSubformImport --- Undo the last import 
echo     UndoCDFSubformImport versionNbr --- Undo import for specified version number 
goto END 

:Start
call setenvJBOSS.cmd

@rem Usage <username> <version>
"%JAVA_HOME%\jre\bin\java" %JAVA_OPTIONS% -classpath "%CLASSPATH%" gov.cdc.nedss.ldf.helper.LDFUndoImportEJBClient %1 %2


:END