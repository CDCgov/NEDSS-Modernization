-- Migrate the QA05.SAS library to the qa_05 python library

USE [NBS_ODSE]

DECLARE @pyLib VARCHAR(50) = 'qa_05'
DECLARE @sasLib VARCHAR(50) = 'QA05.SAS'
DECLARE @desc VARCHAR(300) = 'QA05: Number of Records Entered by User ID. This report produces a table showing data entry user quick code by the number and type of records the user entered.'

IF EXISTS (SELECT * FROM [dbo].[Report_Library] WHERE UPPER(library_name) = @sasLib)
BEGIN
    UPDATE [dbo].[Report_Library]
    SET
        library_name = @pyLib,
        runner = 'python',
        desc_txt = @desc,
        last_chg_time = CURRENT_TIMESTAMP,
        last_chg_user_id = 99999999
    WHERE
        UPPER(library_name) = @sasLib;
END
ELSE
BEGIN
    -- Create a row for this library
    INSERT INTO [dbo].[Report_Library] (
        library_name,
        desc_txt,
        runner,
        is_builtin_ind,
        add_time,
        add_user_id,
        last_chg_time,
        last_chg_user_id
    ) VALUES (
        @pyLib,
        @desc,
        'python',
        'Y',
        CURRENT_TIMESTAMP,
        99999999,
        CURRENT_TIMESTAMP,
        99999999
    );
END
