/*
Create the `NBS_ODSE..Report_Library` table and add FK to it from `NBS_ODSE..Report`

The Report_Library table contains metadata on all of the libraries available to NBS for running reports in application.
The `location` column of the `Report` table currently tracks this as a SAS file name. The metadata enables better UX and checks
that the reports can exist. It also enables running of NBS 6 and NBS 7 reporting modules in parallel.
*/

USE [NBS_ODSE];
GO

IF NOT EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[Report_Library]', 'U'))
BEGIN

    CREATE TABLE [dbo].[Report_Library] (
            library_uid             bigint          IDENTITY(10000001,1)    NOT NULL,
            library_name            varchar(50)     NOT NULL,
            desc_txt                varchar(300)    NOT NULL,
            runner                  varchar(10)     NOT NULL,
            is_builtin_ind          char(1)         NOT NULL,              
            add_time                datetime        NOT NULL,
            add_user_id             bigint          NOT NULL,
            last_chg_time           datetime        NOT NULL,
            last_chg_user_id        bigint          NOT NULL,

            CONSTRAINT PK_Report_Library PRIMARY KEY (library_uid)
        )
    GO

    IF NOT EXISTS
    ( SELECT *
        FROM INFORMATION_SCHEMA.COLUMNS
        WHERE COLUMN_NAME = 'library_uid' AND TABLE_NAME = 'Report'
    )
    BEGIN
        -- Nullable for now until populated
        ALTER TABLE [dbo].[Report]
        ADD library_uid bigint FOREIGN KEY REFERENCES [dbo].[Report_Library].[library_uid]
    END
    GO

END
GO
