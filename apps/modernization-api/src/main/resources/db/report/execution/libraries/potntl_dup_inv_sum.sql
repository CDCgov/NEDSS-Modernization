-- Migrate the NBSCUSTOM.SAS library to the nbs_sr_05 python library

USE [NBS_ODSE]

DECLARE @pyLib VARCHAR(50) = 'potntl_dup_inv_sum'
DECLARE @sasLib VARCHAR(50) = 'POTNTL_DUP_INV_SUM.SAS'
DECLARE @desc VARCHAR(300) = 'Potential Duplicate Investigations - Identifies potential duplicate investigations for the same patient with the
    same disease within a user-specified number of days.'

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
