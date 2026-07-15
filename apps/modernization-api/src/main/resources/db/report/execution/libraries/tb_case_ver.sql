-- Migrate the Case_Verification_Report.sas library to the tb_case_ver python library

USE [NBS_ODSE]

DECLARE @pyLib VARCHAR(50) = 'tb_case_ver'
DECLARE @sasLib VARCHAR(50) = 'Case_Verification_Report.sas'
DECLARE @desc VARCHAR(300) = 'TB Case Verification Report - 2020 RVCT'


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
    INSERT INTO [dbo].[Report_Library] (
        library_name,
        desc_txt,
        runner,
        column_select_ind,
        is_builtin_ind,
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
        CURRENT_TIMESTAMP,
        99999999,
        CURRENT_TIMESTAMP,
        99999999
    );
END
