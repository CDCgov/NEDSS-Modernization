REM The following two lines are to execute the SSIS package (commented out for SQL2016, uncomment for SQL2017 )
REM Where "-S SQL_server\instance", replace with your SQL Server name\instance name.
REM Where "-U NBS_ODSE", replace with the SQL user name for your NBS_ODSE database.
REM Where "-P NBS_ODSE", replace with the related SQL user password.
REM Change the file path of the *.sql and *.log files as required for your configuration.

REM sqlcmd -S SQL_server\instance -d NBS_ODSE -U NBS_ODSE -P NBS_ODSE -Q "execute msdb.dbo.sp_start_job @job_name='ExecuteSSIS'" > D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\SSIS.log
REM sqlcmd -S SQL_server\instance -d NBS_ODSE -U NBS_ODSE -P NBS_ODSE -i D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\SSIS_SP_BATCH_WAIT_COMPLETE.sql

REM The following line is to run the stored procedures directly, by-passing the SSIS call (comment this line out for SQL2017)
REM Where "-S SQL_server\instance", replace with your SQL Server name\instance name.
REM Where "-U NBS_ODSE", replace with the SQL user name for your NBS_ODSE database.
REM Where "-P NBS_ODSE", replace with the related SQL user password.
REM Change the file path of the SSIS.log file as required for your configuration.

sqlcmd -S localhost -U nbs_phcmart -P P@ssword123! -d NBS_ODSE -Q "Exec NBS_ODSE.dbo.sp_PublicHealthCaseFact_DATAMART" > D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\PHCMartETL.log
