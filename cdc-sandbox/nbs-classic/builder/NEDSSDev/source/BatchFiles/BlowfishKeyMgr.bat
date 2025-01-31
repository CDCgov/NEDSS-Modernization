@echo off

@rem you have to set NEDSS_USER to a user name with MSA privilege
Set NEDSS_USER=msa
                  
if "%1" == "?" goto Usage
if "%1"=="help"  goto Usage
if "%1"=="nbs_user"  goto NBSUser
goto Start


:Usage
echo     BlowfishKeyMgr ? --- Show the usage instruction 
echo     BlowfishKeyMgr help --- Show the usage instruction 
echo     BlowfishKeyMgr nbs_user --- Show the NBS user defined to run the utility
echo     BlowfishKeyMgr --- Show the current encryption key 
echo     BlowfishKeyMgr phrase --- 7 character phrase provided by state site that will be used to generate new encryption key
goto END 

:NBSUser
echo   You have set NEDSS_USER to %NEDSS_USER%. If %NEDSS_USER% does not 
echo   have MSA privilege, please edit the batch file and set NEDSS_USER 
echo   to an NBS user name with MSA privilege.
goto END

:Start
call setenvJBOSS.cmd

if "" == "%1" goto KEY_GEN
"%JAVA_HOME%\jre\bin\java" %JAVA_OPTIONS% -classpath "%CLASSPATH%" gov.cdc.nedss.systemservice.nbssecurity.encryption.blowfish.BlowFishKeyManager -username %NEDSS_USER% -newkeyphrase %1
GOTO END
:KEY_GEN
"%JAVA_HOME%\jre\bin\java" %JAVA_OPTIONS% -classpath "%CLASSPATH%" gov.cdc.nedss.systemservice.nbssecurity.encryption.blowfish.BlowFishKeyManager -username %NEDSS_USER%
:END