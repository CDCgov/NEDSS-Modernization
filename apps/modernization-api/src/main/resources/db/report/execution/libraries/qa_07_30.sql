-- Migrate the QA06.SAS library to the qa_06 python library

USE [NBS_ODSE]

DECLARE @pyLib VARCHAR(50) = 'qa_07'
DECLARE @sasLib VARCHAR(50) = 'QA07_30.SAS'
DECLARE @desc VARCHAR(300) = 'QA07: Duplicate Cases - 30 Days'
DECLARE @libraryParams VARCHAR(300) = '{"days_value": 30}'

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
        'N',
        'Y',
        @libraryParams,
        CURRENT_TIMESTAMP,
        99999999,
        CURRENT_TIMESTAMP,
        99999999
    );
END
