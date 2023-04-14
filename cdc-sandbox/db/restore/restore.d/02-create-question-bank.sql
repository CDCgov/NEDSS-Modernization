CREATE DATABASE question_bank;
GO
use master
GO
CREATE LOGIN qb_admin WITH PASSWORD = 'qb_admin',DEFAULT_DATABASE = [question_bank], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF  
GO
USE question_bank;
GO
CREATE SCHEMA MODERNIZATION;
GO
CREATE USER qb_admin FOR LOGIN qb_admin WITH DEFAULT_SCHEMA = [MODERNIZATION]; 
GO
EXEC sp_addrolemember 'db_owner', 'qb_admin'
GO