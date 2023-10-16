use NBS_MSGoute
GO
IF  EXISTS (SELECT * FROM sys.database_principals WHERE name = N'nbs_ods')
DROP USER [nbs_ods]
GO
use NBS_ODSE
GO
IF  EXISTS (SELECT * FROM sys.database_principals WHERE name = N'nbs_ods')
DROP USER [nbs_ods]
GO
USE [NBS_SRTE]
GO
IF  EXISTS (SELECT * FROM sys.database_principals WHERE name = N'nbs_ods')
DROP USER [nbs_ods]
GO
IF  EXISTS (SELECT * FROM sys.database_principals WHERE name = N'srte_admin')
DROP USER [srte_admin]
GO
use rdb
go
IF  EXISTS (SELECT * FROM sys.database_principals WHERE name = N'nbs_rdb')
DROP USER [nbs_rdb]
GO
use master
go
IF  EXISTS (SELECT * FROM sys.server_principals WHERE name = N'nbs_ods') DROP LOGIN [nbs_ods];
IF  EXISTS (SELECT * FROM sys.server_principals WHERE name = N'SRTE_ADMIN') DROP LOGIN [SRTE_ADMIN];
IF  EXISTS (SELECT * FROM sys.server_principals WHERE name = N'nbs_rdb') DROP LOGIN [nbs_rdb];
GO
CREATE LOGIN nbs_ods WITH PASSWORD = 'ods',DEFAULT_DATABASE = [master], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF  
CREATE LOGIN srte_admin WITH PASSWORD = 'admin',DEFAULT_DATABASE = [master], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF  
CREATE LOGIN nbs_rdb WITH PASSWORD = 'rdb',DEFAULT_DATABASE = [master], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF  
GO  
-- Add User to first database  
USE nbs_odse
CREATE USER nbs_ods FOR LOGIN nbs_ods;  
go
EXEC sp_addrolemember 'db_owner', 'nbs_ods'
GO  
USE nbs_srte
CREATE USER nbs_ods FOR LOGIN nbs_ods;  
CREATE USER srte_admin FOR LOGIN srte_admin;  
go
EXEC sp_addrolemember 'db_owner', 'nbs_ods'
EXEC sp_addrolemember 'db_owner', 'srte_admin'
GO  
USE nbs_msgoute
CREATE USER nbs_ods FOR LOGIN nbs_ods;  
go
EXEC sp_addrolemember 'db_owner', 'nbs_ods'
GO  
use rdb
go
Create user nbs_rdb for login nbs_rdb;
EXEC sp_addrolemember 'db_owner', 'nbs_rdb'
GO