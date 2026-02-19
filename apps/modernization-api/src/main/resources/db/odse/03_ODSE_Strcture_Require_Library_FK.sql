/*
NOTE: The NOT NULL constraints are added in this migration rather
than in 01_ODSE_Strucutre_Report_Library_Create.sql because the initial
`library_uid` is not populated until the
02_ODSE_Data_Report_Library_Init.sql migration is applied.
*/

USE [NBS_ODSE]

GO

ALTER TABLE [dbo].[Report]
    ALTER COLUMN library_uid bigint NOT NULL;
GO
