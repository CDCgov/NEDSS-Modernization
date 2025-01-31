@echo off
set date2=%date:~10,4%%date:~4,2%%date:~7,2%
@rem delete files in rdbdata folder
del /Q D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\dw\etl\rdbdata\*.*

 
@rem SQL SERVER SETTINGS
sqlcmd -U nbs_rdb -P rdb -S localhost -d rdb -i D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\Refresh_SQL_RDB_tables.sql > D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\Drop_Create_Tables.log
@rem type D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\Drop_Create_Tables.log 

@rem execute the SAS MasterEtl program BEFRORE SSIS Package Execution
"%SAS_HOME%\sas.exe" -sysin D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\dw\etl\src\MasterETL1.sas -nosyntaxcheck -print D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\MasterETL1.lst -log D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\MasterETL1.log -config  "%SAS_HOME%\SASV9.CFG" -autoexec D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\autoexec.sas


call exec_NBS_SSIS.bat

@rem execute the SAS MasterEtl program AFTER SSIS Package Execution 
"%SAS_HOME%\sas.exe" -sysin D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\dw\etl\src\MasterETL2.sas -nosyntaxcheck -print D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\MasterETL2.lst -log D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\MasterETL2.log -config  "%SAS_HOME%\SASV9.CFG" -autoexec D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\autoexec.sas
@rem type D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\MasterETL.log 
sqlcmd -U nbs_rdb -P rdb -S localhost -d rdb -i D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\RedefineTablespace.sql


@rem Changes in MasterETL to include MasterETL changes
@rem execute Dynamic Datamart process
sqlcmd -S localhost -U nbs_rdb -P rdb -d RDB -Q "Exec RDB.dbo.DynDM_CLEAR_sp" > D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\DynamicDatamart.log
sqlcmd -S localhost -U nbs_rdb -P rdb -d RDB -Q "Exec RDB.dbo.DynDM_Parent_Main_sp"  >> D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\DynamicDatamart.log
sqlcmd -S localhost -U nbs_rdb -P rdb -d RDB -Q "Exec RDB.dbo.DynDM_CLEAR_sp" >> D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\DynamicDatamart.log



















