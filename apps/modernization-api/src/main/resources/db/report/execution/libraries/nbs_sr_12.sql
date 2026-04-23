-- Migrate the NBSSR00011.SAS library to the nbs_sr_11 python library

USE [NBS_ODSE]

-- SR12 is the same as SR11 once pivoting is removed. Re-pointing SR12 to SR11 via migration

DECLARE @pyLib VARCHAR(50) = 'nbs_sr_11'
DECLARE @sasLib VARCHAR(50) = 'NBSSR00012.SAS'

DECLARE @sr11Id BIGINT = (SELECT library_uid FROM [dbo].[Report_Library] WHERE library_name = @pyLib)
DECLARE @sr12Id BIGINT = (SELECT library_uid FROM [dbo].[Report_Library] WHERE library_name = @sasLib)

UPDATE [dbo].[Report]
SET library_uid = @sr11Id
WHERE library_uid = @sr12Id;

DELETE FROM [dbo].[Report_Library]
WHERE library_uid = @sr12Id;
