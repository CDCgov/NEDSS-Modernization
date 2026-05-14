-- Migrate the QA05.SAS library to the qa_05 python library

USE [NBS_ODSE]

DECLARE @pyLib VARCHAR(50) = 'qa_04'
DECLARE @sasLib VARCHAR(50) = 'QA04.SAS'
DECLARE @desc VARCHAR(300) = 'QA04: Cases Missing Lab and/or Treatment. This report generates a list, by name, of individuals with cases that are not linked to a positive lab test record (for this reported case) or to a treatment record.'

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
ELSE IF NOT EXISTS (SELECT * FROM [dbo].[Report_Library] WHERE library_name = @pyLib)
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
