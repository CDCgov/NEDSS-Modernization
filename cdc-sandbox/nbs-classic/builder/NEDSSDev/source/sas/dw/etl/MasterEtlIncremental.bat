@echo off
set date2=%date:~10,4%%date:~4,2%%date:~7,2%
@rem delete files in rdbdata folder
del /Q C:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\dw\etl\rdbdata\*.*
@REM SET SAS REPORT DATABASE TYPE HERE
@set SAS_REPORT_DBTYPE=SQLSERVER
@IF %SAS_REPORT_DBTYPE%==NOTSET  (ECHO Cannot run ETL process. SAS_REPORT_DB_TYPE is not configured in MASTERETL.BAT.)
@IF %SAS_REPORT_DBTYPE%==NOTSET  GOTO cannotproceed
@rem Drop and Create tables prior to MasterEtl execution   
IF %SAS_REPORT_DBTYPE%==ORACLE  (GOTO true) ELSE GOTO false
:true
@rem ORACLE SETTINGS
sqlplus nbs_rdb/rdb@nbs_rdb @C:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\Incremental_Refresh_Oracle_Tables.sql > C:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\Drop_Create_Tables.log
@rem type C:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\Drop_Create_Tables.log
GOTO END
:false 
@rem SQL SERVER SETTINGS
osql -U nbs_rdb -P rdb -S CDCDEVDB2\rdb -d rdb -i C:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\Incremental_Refresh_SqlServer_Tables.sql> C:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\Drop_Create_Tables.log
@rem type C:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\Drop_Create_Tables.log 
GOTO END
:END	
@rem execute the SAS MasterEtl program 
%SAS_HOME%\sas.exe -sysin C:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\dw\etl\src\MasterETL.sas -nosyntaxcheck -print C:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\MasterETL.lst -log C:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\MasterETL.log -config  %SAS_HOME%\SASV9.CFG -autoexec C:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\autoexec.sas
@rem type C:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\MasterETL.log 
:cannotproceed

