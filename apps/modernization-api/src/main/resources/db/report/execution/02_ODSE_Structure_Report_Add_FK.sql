/*
Create the `NBS_ODSE..Report_Library` table and add FK to it from `NBS_ODSE..Report`

The Report_Library table contains metadata on all of the libraries available to NBS for running reports in application.
The `location` column of the `Report` table currently tracks this as a SAS file name. The metadata enables better UX and checks
that the reports can exist. It also enables running of NBS 6 and NBS 7 reporting modules in parallel.
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
