/*
Add foreign key from `NBS_ODSE..Report` to `NBS_ODSE..Report_library`
*/

USE [NBS_ODSE]

IF NOT EXISTS
( SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'library_uid' AND TABLE_NAME = 'Report'
)
BEGIN
    -- Nullable for now until populated
    ALTER TABLE [dbo].[Report]
    ADD library_uid bigint FOREIGN KEY REFERENCES [dbo].[Report_Library] (library_uid);
END
