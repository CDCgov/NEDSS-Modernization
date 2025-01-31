set date2=%date:~10,4%%date:~4,2%%date:~7,2%
@REM SET SAS REPORT DATABASE TYPE HERE
@set SAS_REPORT_DBTYPE=SQL_SERVER

@IF %SAS_REPORT_DBTYPE%==NOTSET  (ECHO Cannot run ETL process. SAS_REPORT_DB_TYPE is not configured in MASTERETL.BAT.)


@IF %SAS_REPORT_DBTYPE%==NOTSET  GOTO cannotproceed

@rem Drop and Create tables prior to MasterEtl execution   
IF %SAS_REPORT_DBTYPE%==ORACLE  (GOTO true) ELSE GOTO false

:true
@rem ORACLE SETTINGS
sqlplus nbs_rdb/rdb@nbs_rdb @C:\report\Incremental Refresh_SQL_RDB_tables.sql > C:\report\log\Drop_Create_Tables.log
type C:\report\log\Drop_Create_Tables.log
GOTO END

:false 
@rem SQL SERVER SETTINGS
osql -U nbs_rdb -P rdb -S DEVSB1\RDB -d rdb -i C:\report\Incremental_Refresh_SQL_RDB_tables.sql > C:\report\log\Drop_Create_Tables.log
type C:\report\log\Drop_Create_Tables.log 
GOTO END

:END	

@rem execute the SAS MasterEtl program 
%SAS_HOME%\sas.exe -sysin C:\report\dw\etl\src\MasterETL.sas -nosyntaxcheck -print C:\report\log\MasterETL.lst -log C:\report\log\MasterETL.log -config  %SAS_HOME%\SASV9.CFG -autoexec C:\report\autoexec.sas
type C:\report\log\MasterETL.log 
type C:\report\log\MasterETL.log 


:cannotproceed