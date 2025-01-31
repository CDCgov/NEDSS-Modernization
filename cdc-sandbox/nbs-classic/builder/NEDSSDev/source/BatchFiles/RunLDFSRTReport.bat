@echo off

@rem Usage: RunLDFSRTReport <arg>
@rem arg == "codeset" --- Report on available code set names 
@rem arg == "datatype" --- Report on available data types 
@rem arg == "validation" --- Report on available validation types 
@rem arg == "pageset" --- Report on available page set ids 
@rem arg == "classcode" --- Report on available class codes 
@rem arg == "all" --- All of the above
                  
if "%1" == "" goto Usage
if "%1" == "?" goto Usage
if "%1"=="help"  goto Usage
goto Start

:Usage
echo Please specify the report to run
echo     RunLDFSRTReport codeset --- Report on available code set names 
echo     RunLDFSRTReport datatype --- Report on available data types 
echo     RunLDFSRTReport validation --- Report on available validation types 
echo     RunLDFSRTReport pageset --- Report on available page set ids 
echo     RunLDFSRTReport classcode --- Report on available class codes 
echo     RunLDFSRTReport all --- All of the above 
goto END

:Start
call setenvJBOSS.cmd

@rem the NBS user that has LDF administration privilege
Set NEDSS_USER=admin

@rem where you want the output to be
Set OUTPUT_FILE=SRTReport.html

"%JAVA_HOME%\jre\bin\java" %JAVA_OPTIONS% -classpath "%CLASSPATH%" gov.cdc.nedss.ldf.helper.RunLDFSRTReport -username %NEDSS_USER% -output %OUTPUT_FILE% -reporttype %1

:END

