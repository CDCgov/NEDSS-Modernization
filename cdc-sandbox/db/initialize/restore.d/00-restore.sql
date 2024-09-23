use [master];
go

-- Following Script will restore the DATABASE FROM "/var/opt/database/restore" Backups folder
-- All database Data files/log files go to "/var/opt/mssql/data". User can Modify the folder where they need to....

---------------------------------------------------------------------------------------------------
--  NBS_ODSE
---------------------------------------------------------------------------------------------------

RESTORE DATABASE [NBS_ODSE] FROM
DISK = N'/var/opt/database/initialize/restore.d/NBS_ODSE.bak'
WITH  FILE = 1,
MOVE N'ODS_PRIMARY_DATA01' TO N'/var/opt/mssql/data/Nbs_odse.mdf',
MOVE N'ODS_PRIMARY_DAT02' TO N'/var/opt/mssql/data/nbs_odse1.mdf',
MOVE N'ODS_ACTS01' TO N'/var/opt/mssql/data/nbs_odse2.mdf',
MOVE N'ODS_ACTS02' TO N'/var/opt/mssql/data/nbs_odse3.mdf',
MOVE N'ODS_ENTITIES01' TO N'/var/opt/mssql/data/nbs_odse4.mdf',
MOVE N'ODS_ENTITIES02' TO N'/var/opt/mssql/data/nbs_odse5.mdf',
MOVE N'ODS_ENTITIES_LOCATOR01' TO N'/var/opt/mssql/data/nbs_odse6.mdf',
MOVE N'ODS_ENTITIES_LOCATOR02' TO N'/var/opt/mssql/data/nbs_odse7.mdf',
MOVE N'ODS_HIST01' TO N'/var/opt/mssql/data/nbs_odse8.mdf',
MOVE N'ODS_HIST02' TO N'/var/opt/mssql/data/nbs_odse9.mdf',
MOVE N'ODS_INDEX01' TO N'/var/opt/mssql/data/nbs_odse10.mdf',
MOVE N'ODS_INDEX02' TO N'/var/opt/mssql/data/nbs_odse11.mdf',
MOVE N'ODS_REPORT01' TO N'/var/opt/mssql/data/nbs_odse12.mdf',
MOVE N'ODS_REPORT02' TO N'/var/opt/mssql/data/nbs_odse13.mdf',
MOVE N'ODS_TEXTFILE01' TO N'/var/opt/mssql/data/nbs_odse14.mdf',
MOVE N'ODS_TEXTFILE02' TO N'/var/opt/mssql/data/nbs_odse15.mdf',
MOVE N'ODS_PARTICIPATION01' TO N'/var/opt/mssql/data/nbs_odse16.mdf',
MOVE N'ODS_PARTICIPATION02' TO N'/var/opt/mssql/data/nbs_odse17.mdf',
MOVE N'PRIMARY_LOG1' TO N'/var/opt/mssql/data/nbs_odse_1.ldf',  NOUNLOAD,  STATS = 10
GO

EXEC dbo.sp_dbcmptlevel @dbname=N'NBS_ODSE', @new_cmptlevel=120
GO

---------------------------------------------------------------------------------------------------
--  NBS_SRTE
---------------------------------------------------------------------------------------------------

RESTORE DATABASE [NBS_SRTE] FROM
DISK = N'/var/opt/database/initialize/restore.d/NBS_SRTE.bak'
WITH  FILE = 1,
MOVE N'nbs_srte_generic113_Data' TO N'/var/opt/mssql/data/nbs_srte.mdf',
MOVE N'nbs_srte_generic113_Log' TO N'/var/opt/mssql/data/nbs_srte.ldf',  NOUNLOAD,  STATS = 10
GO

EXEC dbo.sp_dbcmptlevel @dbname=N'NBS_SRTE', @new_cmptlevel=120
GO

---------------------------------------------------------------------------------------------------
--  NBS_MSGOUTE
---------------------------------------------------------------------------------------------------

RESTORE DATABASE [NBS_MSGOUTE] FROM  DISK = N'/var/opt/database/initialize/restore.d/NBS_MSGOUTE.bak' WITH  FILE = 1,
MOVE N'MSGOUT_PRIMARY_DATA01' TO N'/var/opt/mssql/data/nbs_msgoute.mdf',
MOVE N'MSGOUT_PRIMARY_DATA02' TO N'/var/opt/mssql/data/nbs_msgoute_1.mdf',
MOVE N'MSGOUT_TEXTFILE01' TO N'/var/opt/mssql/data/nbs_msgoute_2.mdf',
MOVE N'MSGOUT_TEXTFILE02' TO N'/var/opt/mssql/data/nbs_msgoute_3.mdf',
MOVE N'MSGOUT_APP01' TO N'/var/opt/mssql/data/nbs_msgoute_4.mdf',
MOVE N'MSGOUT_APP02' TO N'/var/opt/mssql/data/nbs_msgoute_5.mdf',
MOVE N'PRIMARY_LOG1' TO N'/var/opt/mssql/data/nbs_msgoute_log.ldf',
NOUNLOAD,  STATS = 10
GO

EXEC dbo.sp_dbcmptlevel @dbname=N'NBS_MSGOUTE', @new_cmptlevel=120
GO

---------------------------------------------------------------------------------------------------
--  RDB
---------------------------------------------------------------------------------------------------

RESTORE DATABASE [RDB] FROM  DISK = N'/var/opt/database/initialize/restore.d/RDB.bak' WITH  FILE = 1,
MOVE N'RDB' TO N'/var/opt/mssql/data/rdb_data.mdf',
MOVE N'RDB_log' TO N'/var/opt/mssql/data/rdb_log.ldf',  NOUNLOAD,  STATS = 10
GO


EXEC dbo.sp_dbcmptlevel @dbname=N'RDB', @new_cmptlevel=120
GO
