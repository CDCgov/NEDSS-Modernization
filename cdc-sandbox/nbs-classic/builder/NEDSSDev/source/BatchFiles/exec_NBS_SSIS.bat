REM The following two lines are to execute the SSIS package (commented out for SQL2016, uncomment for SQL2017 )
REM Where "-S SQL_server\instance", replace with your SQL Server name\instance name.
REM Where "-U nbs_rdb", replace with the SQL user name for your RDB database.
REM Where "-P rdb", replace with the related SQL user password.
REM Change the file path of the *.sql and *.log files as required for your configuration.

REM sqlcmd -S SQL_server\instance -d RDB -U nbs_rdb -P rdb -Q "execute msdb.dbo.sp_start_job @job_name='ExecuteSSIS'" > D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\SSIS.log
REM sqlcmd -S SQL_server\instance -d RDB -U nbs_rdb -P rdb -i D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\SSIS_SP_BATCH_WAIT_COMPLETE.sql

REM The following line is to run the stored procedures directly, by-passing the SSIS call (comment this line out for SQL2017)
REM Where "-S SQL_server\instance", replace with your SQL Server name\instance name.
REM Where "-U nbs_rdb", replace with the SQL user name for your RDB database.
REM Where "-P rdb", replace with the related SQL user password.
REM Change the file path of the SSIS.log file as required for your configuration.

sqlcmd -S localhost -U nbs_rdb -P rdb -d RDB -Q "Exec RDB.dbo.sp_SLD_ALL" > D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\SSIS.log
