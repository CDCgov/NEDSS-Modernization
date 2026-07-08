-- Migrate the NBSSR00020.SAS library to the nbs_sr_19 python library

USE [NBS_ODSE]

DECLARE @pyLib VARCHAR(50) = 'nbs_sr_19'
DECLARE @sasLib VARCHAR(50) = 'NBSSR00020.SAS'
DECLARE @desc VARCHAR(300) = 'TB Record Count - Summary Report by Count Date.'
DECLARE @libraryParams VARCHAR(300) = '{"count_column": "COUNT_DATE"}'

IF EXISTS (SELECT * FROM [dbo].[Report_Library] WHERE UPPER(library_name) = @sasLib)
BEGIN
UPDATE [dbo].[Report_Library]
SET
    library_name = @pyLib,
    runner = 'python',
    desc_txt = @desc,
    last_chg_time = CURRENT_TIMESTAMP,
    last_chg_user_id = 99999999,
    library_params = @libraryParams

WHERE
    UPPER(library_name) = @sasLib;
END
ELSE IF NOT EXISTS (SELECT * FROM [dbo].[Report_Library] WHERE library_name = @pyLib AND library_params = @libraryParams)
BEGIN
    -- Create a row for this library
INSERT INTO [dbo].[Report_Library] (
    library_name,
    desc_txt,
    runner,
    column_select_ind,
    is_builtin_ind,
    library_params,
    add_time,
    add_user_id,
    last_chg_time,
    last_chg_user_id
) VALUES (
    @pyLib,
    @desc,
    'python',
    'N',
    'Y',
    @libraryParams,
    CURRENT_TIMESTAMP,
    99999999,
    CURRENT_TIMESTAMP,
    99999999
    );
END
