-- Migrate the NBSCUSTOM.SAS library to the nbs_custom python library

USE [NBS_ODSE]

DECLARE @pyLib VARCHAR(50) = 'nbs_sr_05'
DECLARE @sasLib VARCHAR(50) = 'NBSSR00005.SAS'
DECLARE @desc VARCHAR(300) = 'SR5: Cases of Reportable Diseases by State. Report demonstrates, in table form, the total number of Investigation(s) [both Individual and Summary] by state irrespective of Case Status over various time periods.'

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
