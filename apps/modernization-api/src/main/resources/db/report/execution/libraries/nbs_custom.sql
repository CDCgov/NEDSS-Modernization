-- Migrate the NBSCUSTOM.SAS library to the nbs_custom python library

USE [NBS_ODSE]

UPDATE [dbo].[Report_Library]
SET
    library_name = 'nbs_custom',
    runner = 'python',
    last_chg_time = CURRENT_TIMESTAMP,
    last_chg_user_id = 99999999
WHERE
    UPPER(library_name) = 'NBSCUSTOM.SAS';
