-- Migrate the NBSCUSTOM.SAS library to the nbs_custom python library

USE [NBS_ODSE]

DECLARE @pyLib VARCHAR(50) = 'pa_02'
DECLARE @sasLib VARCHAR(50) = 'PA02_STD.SAS'
DECLARE @desc VARCHAR(300) = 'PA02: Field Investigation Outcomes - STD.'
DECLARE @libraryParams VARCHAR(300) = '{"report_type": "STD"}'

IF EXISTS (SELECT * FROM [dbo].[Report_Library] WHERE UPPER(library_name) = @sasLib)
BEGIN
    UPDATE [dbo].[Report_Library]
    SET
        library_params = @libraryParams,
        library_name = @pyLib,
        runner = 'python',
        desc_txt = @desc,
        last_chg_time = CURRENT_TIMESTAMP,
        last_chg_user_id = 99999999
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
        'Y',
        'Y',
        @libraryParams,
        CURRENT_TIMESTAMP,
        99999999,
        CURRENT_TIMESTAMP,
        99999999
    );
END
