use NBS_ODSE
GO

IF (object_id('sp_bulk_insert') is not null)
    DROP PROCEDURE [dbo].[sp_bulk_insert]
GO

CREATE PROCEDURE [dbo].[sp_bulk_insert]
@filePath varchar(max)

AS
BEGIN
    DECLARE @SQL NVARCHAR(MAX) = ''
    SET @SQL = N'
    BULK INSERT #tmp FROM ''' + @filePath + '''
    WITH (FIELDTERMINATOR = ''|'', ROWTERMINATOR = ''\n'', FIRSTROW = 3)'

    EXEC sp_executesql @SQL
END
GO

IF (object_id('pii_Activity_log_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Activity_log_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Activity_log_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        name varchar(250),
        txt varchar(max)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Activity_log]
    SET doc_nm = name,
        message_txt = txt
    FROM [Activity_log]
    INNER JOIN #tmp ON activity_log_uid = uid
END
GO

IF (object_id('pii_Auth_user_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Auth_user_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Auth_user_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        first_nm varchar(100),
        last_nm varchar(100)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Auth_user]
    SET user_first_nm = first_nm,
        user_last_nm = last_nm
    FROM [dbo].[Auth_user]
    INNER JOIN #tmp ON auth_user_uid = uid
END
GO

IF (object_id('pii_case_management_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_case_management_Restore]
GO

CREATE PROCEDURE [dbo].[pii_case_management_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        ffu_disp_dt datetime, ffu_exam_dt datetime, ffu_exp_dt datetime, ifu_closed_dt datetime,
        ooj_agncy varchar(20), ooj_due_dt datetime, ooj_num varchar(20),
        oth_info varchar(2000), surv_closed_dt datetime,
        agncy_due_dt datetime, agncy_snt_dt datetime, agncy_recd_dt datetime,
        surv_assigned_dt datetime, fu_assigned_dt datetime,
        ifu_assigned_dt datetime, iv_assigned_dt datetime, init_iv_assigned_dt datetime,
        case_closed_dt datetime, case_review_status_dt datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[case_management]
    SET fld_foll_up_dispo_date = ffu_disp_dt, fld_foll_up_exam_date = ffu_exam_dt, fld_foll_up_expected_date = ffu_exp_dt,
        init_foll_up_closed_date = ifu_closed_dt, ooj_agency = ooj_agncy, ooj_due_date = ooj_due_dt, ooj_number = ooj_num,
        subj_oth_idntfyng_info = oth_info, surv_closed_date = surv_closed_dt,
        ooj_initg_agncy_outc_due_date = agncy_due_dt, ooj_initg_agncy_outc_snt_date = agncy_snt_dt, ooj_initg_agncy_recd_date = agncy_recd_dt,
        surv_assigned_date = surv_assigned_dt, foll_up_assigned_date = fu_assigned_dt,
        init_foll_up_assigned_date = ifu_assigned_dt, interview_assigned_date = iv_assigned_dt, init_interview_assigned_date = init_iv_assigned_dt,
        case_closed_date = case_closed_dt, case_review_status_date = case_review_status_dt
    FROM [dbo].[case_management]
    INNER JOIN #tmp ON case_management_uid = uid
END
GO

IF (object_id('pii_case_management_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_case_management_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_case_management_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        ffu_disp_dt datetime, ffu_exam_dt datetime, ffu_exp_dt datetime, ifu_closed_dt datetime,
        ooj_agncy varchar(20), ooj_due_dt datetime, ooj_num varchar(20),
        oth_info varchar(2000), surv_closed_dt datetime,
        agncy_due_dt datetime, agncy_snt_dt datetime, agncy_recd_dt datetime,
        surv_assigned_dt datetime, fu_assigned_dt datetime,
        ifu_assigned_dt datetime, iv_assigned_dt datetime, init_iv_assigned_dt datetime,
        case_closed_dt datetime, case_review_status_dt datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[case_management_hist]
    SET fld_foll_up_dispo_date = ffu_disp_dt, fld_foll_up_exam_date = ffu_exam_dt, fld_foll_up_expected_date = ffu_exp_dt,
        init_foll_up_closed_date = ifu_closed_dt, ooj_agency = ooj_agncy, ooj_due_date = ooj_due_dt, ooj_number = ooj_num,
        subj_oth_idntfyng_info = oth_info, surv_closed_date = surv_closed_dt,
        ooj_initg_agncy_outc_due_date = agncy_due_dt, ooj_initg_agncy_outc_snt_date = agncy_snt_dt, ooj_initg_agncy_recd_date = agncy_recd_dt,
        surv_assigned_date = surv_assigned_dt, foll_up_assigned_date = fu_assigned_dt,
        init_foll_up_assigned_date = ifu_assigned_dt, interview_assigned_date = iv_assigned_dt, init_interview_assigned_date = init_iv_assigned_dt,
        case_closed_date = case_closed_dt, case_review_status_date = case_review_status_dt
    FROM [dbo].[case_management_hist]
    INNER JOIN #tmp ON case_management_hist_uid = uid
END
GO

IF (object_id('pii_CDF_subform_import_log_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_CDF_subform_import_log_Restore]
GO

CREATE PROCEDURE [dbo].[pii_CDF_subform_import_log_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        comment varchar(300)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[CDF_subform_import_log]
    SET admin_comment = comment
    FROM [dbo].[CDF_subform_import_log]
    INNER JOIN #tmp ON import_log_uid = uid
END
GO

IF (object_id('pii_CN_transportq_out_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_CN_transportq_out_Restore]
GO

CREATE PROCEDURE [dbo].[pii_CN_transportq_out_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[CN_transportq_out]
    SET message_payload = NULL
    FROM [dbo].[CN_transportq_out]
    INNER JOIN #tmp ON cn_transportq_out_uid = uid
END
GO

IF (object_id('pii_Confirmation_method_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Confirmation_method_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Confirmation_method_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        cd varchar(20),
        dt datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Confirmation_method]
    SET confirmation_method_time = dt
    FROM [dbo].[Confirmation_method]
    INNER JOIN #tmp ON public_health_case_uid = uid AND confirmation_method_cd = cd
END
GO

IF (object_id('pii_Confirmation_method_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Confirmation_method_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Confirmation_method_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        cd varchar(20),
        ver smallint,
        dt datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Confirmation_method_hist]
    SET confirmation_method_time = dt
    FROM [dbo].[Confirmation_method_hist]
    INNER JOIN #tmp ON public_health_case_uid = uid AND confirmation_method_cd = cd AND
          version_ctrl_nbr = ver
END
GO

IF (object_id('pii_CT_contact_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_Restore]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        assigned_dt datetime,
        disp_dt datetime,
        named_on_dt datetime,
        ct_txt varchar(2000),
        smp_dt datetime, smp_txt varchar(2000),
        eval_dt datetime, eval_txt varchar(2000),
        treat_start_dt datetime, treat_end_dt datetime,
        treat_txt varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[CT_contact]
    SET investigator_assigned_date = assigned_dt,
        disposition_date = disp_dt,
        named_on_date = named_on_dt,
        txt = ct_txt,
        symptom_onset_date = smp_dt,
        symptom_txt = smp_txt,
        evaluation_date = eval_dt,
        evaluation_txt = eval_txt,
        treatment_start_date = treat_start_dt,
        treatment_end_date = treat_end_dt,
        treatment_txt = treat_txt
    FROM [dbo].[CT_contact]
    INNER JOIN #tmp ON ct_contact_uid = uid
END
GO

IF (object_id('pii_CT_contact_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        assigned_dt datetime,
        disp_dt datetime,
        named_on_dt datetime,
        ct_txt varchar(2000),
        smp_dt datetime, smp_txt varchar(2000),
        eval_dt datetime, eval_txt varchar(2000),
        treat_start_dt datetime, treat_end_dt datetime,
        treat_txt varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[CT_contact_hist]
    SET investigator_assigned_date = assigned_dt,
        disposition_date = disp_dt,
        named_on_date = named_on_dt,
        txt = ct_txt,
        symptom_onset_date = smp_dt,
        symptom_txt = smp_txt,
        evaluation_date = eval_dt,
        evaluation_txt = eval_txt,
        treatment_start_date = treat_start_dt,
        treatment_end_date = treat_end_dt,
        treatment_txt = treat_txt
    FROM [dbo].[CT_contact_hist]
    INNER JOIN #tmp ON ct_contact_hist_uid = uid
END
GO

IF (object_id('pii_CT_contact_answer_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_answer_Restore]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_answer_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ans_txt varchar(2000),
        large_txt varchar(max)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[CT_contact_answer]
    SET answer_txt = ans_txt,
        answer_large_txt = large_txt
    FROM [dbo].[CT_contact_answer]
    INNER JOIN #tmp ON ct_contact_answer_uid = uid
END
GO

IF (object_id('pii_CT_contact_answer_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_answer_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_answer_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ans_txt varchar(2000),
        large_txt varchar(max)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[CT_contact_answer_hist]
    SET answer_txt = ans_txt,
        answer_large_txt = large_txt
    FROM [dbo].[CT_contact_answer_hist]
    INNER JOIN #tmp ON ct_contact_answer_hist_uid = uid
END
GO

IF (object_id('pii_CT_contact_attachment_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_attachment_Restore]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_attachment_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        dsc_txt varchar(2000),
        name varchar(250)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[CT_contact_attachment]
    SET desc_txt = dsc_txt,
        attachment = NULL,
        file_nm_txt = name
    FROM [dbo].[CT_contact_attachment]
    INNER JOIN #tmp ON ct_contact_attachment_uid = uid
END
GO

IF (object_id('pii_CT_contact_note_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_note_Restore]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_note_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ct_note varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[CT_contact_note]
    SET note = ct_note
    FROM [dbo].[CT_contact_note]
    INNER JOIN #tmp ON ct_contact_note_uid = uid
END
GO

IF (object_id('pii_Custom_queues_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Custom_queues_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Custom_queues_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        name varchar(100),
        descr varchar(1000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Custom_queues]
    SET description = descr
    FROM [dbo].[Custom_queues]
    INNER JOIN #tmp ON queue_name = name
END
GO

IF (object_id('pii_Data_migration_record_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Data_migration_record_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Data_migration_record_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        batch_uid bigint,
        name varchar(100)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Data_migration_record]
    SET failed_record_txt = NULL,
        sub_nm = name
    FROM [dbo].[Data_migration_record]
    INNER JOIN #tmp ON data_migration_record_uid = uid AND data_migration_batch_uid = batch_uid
END
GO

IF (object_id('pii_dsm_algorithm_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_dsm_algorithm_Restore]
GO

CREATE PROCEDURE [dbo].[pii_dsm_algorithm_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        comment varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[dsm_algorithm]
    SET admin_comment = comment
    FROM [dbo].[dsm_algorithm]
    INNER JOIN #tmp ON dsm_algorithm_uid = uid
END
GO

IF (object_id('pii_dsm_algorithm_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_dsm_algorithm_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_dsm_algorithm_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        comment varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[dsm_algorithm_hist]
    SET admin_comment = comment
    FROM [dbo].[dsm_algorithm_hist]
    INNER JOIN #tmp ON dsm_algorithm_hist_uid = uid
END
GO

IF (object_id('pii_EDX_activity_detail_log_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_EDX_activity_detail_log_Restore]
GO

CREATE PROCEDURE [dbo].[pii_EDX_activity_detail_log_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        comment varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[EDX_activity_detail_log]
    SET log_comment = comment
    FROM [dbo].[EDX_activity_detail_log]
    INNER JOIN #tmp ON edx_activity_detail_log_uid = uid
END
GO

IF (object_id('pii_EDX_activity_log_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_EDX_activity_log_Restore]
GO

CREATE PROCEDURE [dbo].[pii_EDX_activity_log_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        name varchar(255)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[EDX_activity_log]
    SET Entity_nm = name
    FROM [dbo].[EDX_activity_log]
    INNER JOIN #tmp ON edx_activity_log_uid = uid
END
GO

IF (object_id('pii_EDX_Document_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_EDX_Document_Restore]
GO

CREATE PROCEDURE [dbo].[pii_EDX_Document_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        load varchar(max)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[EDX_Document]
    SET payload = '',
        original_payload = load
    FROM [dbo].[EDX_Document]
    INNER JOIN #tmp ON EDX_Document_uid = uid
END
GO

IF (object_id('pii_ELR_activity_log_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_ELR_activity_log_Restore]
GO

CREATE PROCEDURE [dbo].[pii_ELR_activity_log_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint,
        subj_nm varchar(100),
        fac_nm varchar(100),
        dtl_txt varchar(1000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[ELR_activity_log]
    SET subject_nm = subj_nm,
        report_fac_nm = fac_nm,
        detail_txt = dtl_txt
    FROM [dbo].[ELR_activity_log]
    INNER JOIN #tmp ON msg_observation_uid = uid AND elr_activity_log_seq = seq
END
GO

IF (object_id('pii_ELRWorkerQueue_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_ELRWorkerQueue_Restore]
GO

CREATE PROCEDURE [dbo].[pii_ELRWorkerQueue_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        name varchar(255),
        msg varchar(255)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[ELRWorkerQueue]
    SET payloadBinaryContent = NULL,
        payloadTextContent = NULL,
        payloadName = name,
        errorMessage = msg
    FROM [dbo].[ELRWorkerQueue]
    INNER JOIN #tmp ON recordId = uid
END
GO

IF (object_id('pii_Export_receiving_facility_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Export_receiving_facility_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Export_receiving_facility_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        comment varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Export_receiving_facility]
    SET admin_comment = comment
    FROM [dbo].[Export_receiving_facility]
    INNER JOIN #tmp ON export_receiving_facility_uid = uid
END
GO

IF (object_id('pii_Intervention_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Intervention_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Intervention_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        itxt varchar(1000),
        age smallint,
        lot_nm varchar(50),
        exp_time datetime
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Intervention]
    SET activity_from_time = CASE WHEN activity_from_time IS NOT NULL THEN DATEADD(day, @dw, activity_from_time) END,
        activity_to_time = CASE WHEN activity_to_time IS NOT NULL THEN DATEADD(day, @dw, activity_to_time) END,
        effective_from_time = CASE WHEN effective_from_time IS NOT NULL THEN DATEADD(day, @dw, effective_from_time) END,
        effective_to_time = CASE WHEN effective_to_time IS NOT NULL THEN DATEADD(day, @dw, effective_to_time) END,
        txt = itxt,
        age_at_vacc = age,
        material_lot_nm = lot_nm,
        material_expiration_time = exp_time
    FROM [dbo].[Intervention]
    INNER JOIN #tmp ON intervention_uid = uid
END
GO

IF (object_id('pii_Intervention_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Intervention_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Intervention_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ver smallint,
        itxt varchar(1000),
        age smallint,
        lot_nm varchar(50),
        exp_time datetime
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Intervention_hist]
    SET activity_from_time = CASE WHEN activity_from_time IS NOT NULL THEN DATEADD(day, @dw, activity_from_time) END,
        activity_to_time = CASE WHEN activity_to_time IS NOT NULL THEN DATEADD(day, @dw, activity_to_time) END,
        effective_from_time = CASE WHEN effective_from_time IS NOT NULL THEN DATEADD(day, @dw, effective_from_time) END,
        effective_to_time = CASE WHEN effective_to_time IS NOT NULL THEN DATEADD(day, @dw, effective_to_time) END,
        txt = itxt,
        age_at_vacc = age,
        material_lot_nm = lot_nm,
        material_expiration_time = exp_time
    FROM [dbo].[Intervention_hist]
    INNER JOIN #tmp ON intervention_uid = uid AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Interview_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Interview_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Interview_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        date1 datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Interview]
    SET interview_date = date1
    FROM [dbo].[Interview]
    INNER JOIN #tmp ON interview_uid = uid
END
GO

IF (object_id('pii_Interview_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Interview_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Interview_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        date1 datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Interview_hist]
    SET interview_date = date1
    FROM [dbo].[Interview_hist]
    INNER JOIN #tmp ON interview_hist_uid = uid
END
GO

IF (object_id('pii_lab_event_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_lab_event_Restore]
GO

CREATE PROCEDURE [dbo].[pii_lab_event_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        rpt_dt datetime,
        analyzed_dt datetime,
        collection_dt datetime,
        lab_comments varchar(2000),
        sus_lab_comments varchar(2000),
        sus_collection_dt datetime,
        sus_rpt_dt datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[lab_event]
    SET result_rpt_dt = rpt_dt,
        specimen_analyzed_dt = analyzed_dt,
        specimen_collection_dt = collection_dt,
        lab_result_comments = lab_comments,
        suscep_lab_result_comments = sus_lab_comments,
        suscep_specimen_collection_dt = sus_collection_dt,
        suscep_result_rpt_dt = sus_rpt_dt
    FROM [dbo].[lab_event]
    INNER JOIN #tmp ON lab_event_uid = uid
END
GO

IF (object_id('pii_lab_event_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_lab_event_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_lab_event_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        rpt_dt datetime,
        analyzed_dt datetime,
        collection_dt datetime,
        lab_comments varchar(2000),
        sus_lab_comments varchar(2000),
        sus_collection_dt datetime,
        sus_rpt_dt datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[lab_event_hist]
    SET result_rpt_dt = rpt_dt,
        specimen_analyzed_dt = analyzed_dt,
        specimen_collection_dt = collection_dt,
        lab_result_comments = lab_comments,
        suscep_lab_result_comments = sus_lab_comments,
        suscep_specimen_collection_dt = sus_collection_dt,
        suscep_result_rpt_dt = sus_rpt_dt
    FROM [dbo].[lab_event_hist]
    INNER JOIN #tmp ON lab_event_hist_uid = uid
END
GO

IF (object_id('pii_Manufactured_material_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Manufactured_material_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Manufactured_material_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint,
        exp_time datetime,
        name varchar(50)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Manufactured_material]
    SET expiration_time = exp_time,
        lot_nm = name
    FROM [dbo].[Manufactured_material]
    INNER JOIN #tmp ON material_uid = uid AND manufactured_material_seq = seq
END
GO

IF (object_id('pii_Manufactured_material_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Manufactured_material_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Manufactured_material_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint,
        ver smallint,
        exp_time datetime,
        name varchar(50)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Manufactured_material_hist]
    SET expiration_time = exp_time,
        lot_nm = name
    FROM [dbo].[Manufactured_material_hist]
    INNER JOIN #tmp ON material_uid = uid AND manufactured_material_seq = seq AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_nbs_answer_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_nbs_answer_Restore]
GO

CREATE PROCEDURE [dbo].[pii_nbs_answer_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        txt varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[nbs_answer]
    SET answer_txt = txt
    FROM [dbo].[nbs_answer]
    INNER JOIN #tmp ON nbs_answer_uid = uid
END
GO

IF (object_id('pii_nbs_answer_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_nbs_answer_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_nbs_answer_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        txt varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[nbs_answer_hist]
    SET answer_txt = txt
    FROM [dbo].[nbs_answer_hist]
    INNER JOIN #tmp ON nbs_answer_hist_uid = uid
END
GO

IF (object_id('pii_NBS_attachment_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_attachment_Restore]
GO

CREATE PROCEDURE [dbo].[pii_NBS_attachment_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        txt varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[NBS_attachment]
    SET desc_txt = txt,
        attachment = NULL
    FROM [dbo].[NBS_attachment]
    INNER JOIN #tmp ON nbs_attachment_uid = uid
END
GO

IF (object_id('pii_NBS_case_answer_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_case_answer_Restore]
GO

CREATE PROCEDURE [dbo].[pii_NBS_case_answer_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        txt varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[NBS_case_answer]
    SET answer_txt = txt
    FROM [dbo].[NBS_case_answer]
    INNER JOIN #tmp ON nbs_case_answer_uid = uid
END
GO

IF (object_id('pii_NBS_case_answer_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_case_answer_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_NBS_case_answer_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        txt varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[NBS_case_answer_hist]
    SET answer_txt = txt
    FROM [dbo].[NBS_case_answer_hist]
    INNER JOIN #tmp ON nbs_case_answer_hist_uid = uid
END
GO

IF (object_id('pii_NBS_document_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_document_Restore]
GO

CREATE PROCEDURE [dbo].[pii_NBS_document_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        doc_txt varchar(2000),
        name varchar(250)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[NBS_document]
    SET doc_payload = '',
        txt = doc_txt,
        sending_facility_nm = name,
        phdc_doc_derived = NULL
    FROM [dbo].[NBS_document]
    INNER JOIN #tmp ON nbs_document_uid = uid
END
GO

IF (object_id('pii_NBS_document_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_document_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_NBS_document_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        doc_txt varchar(2000),
        name varchar(250)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[NBS_document_hist]
    SET doc_payload = '',
        txt = doc_txt,
        sending_facility_nm = name,
        phdc_doc_derived = NULL
    FROM [dbo].[NBS_document_hist]
    INNER JOIN #tmp ON nbs_document_hist_uid = uid
END
GO

IF (object_id('pii_NBS_note_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_note_Restore]
GO

CREATE PROCEDURE [dbo].[pii_NBS_note_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        txt varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[NBS_note]
    SET note = txt
    FROM [dbo].[NBS_note]
    INNER JOIN #tmp ON nbs_note_uid = uid
END
GO

IF (object_id('pii_Notification_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Notification_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Notification_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ntxt varchar(1000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Notification]
    SET message_txt = NULL,
        txt = ntxt
    FROM [dbo].[Notification]
    INNER JOIN #tmp ON notification_uid = uid
END
GO

IF (object_id('pii_Notification_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Notification_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Notification_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ver smallint,
        ntxt varchar(1000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Notification_hist]
    SET message_txt = NULL,
        txt = ntxt
    FROM [dbo].[Notification_hist]
    INNER JOIN #tmp ON notification_uid = uid AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Obs_value_coded_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_coded_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_coded_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        cd varchar(20),
        name varchar(300),
        orig_txt varchar(300)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Obs_value_coded]
    SET display_name = name,
        original_txt = orig_txt
    FROM [dbo].[Obs_value_coded]
    INNER JOIN #tmp ON observation_uid = uid AND code = cd
END
GO

IF (object_id('pii_Obs_value_coded_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_coded_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_coded_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        cd varchar(20),
        ver smallint,
        name varchar(300),
        orig_txt varchar(300)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Obs_value_coded_hist]
    SET display_name = name,
        original_txt = orig_txt
    FROM [dbo].[Obs_value_coded_hist]
    INNER JOIN #tmp ON observation_uid = uid AND code = cd AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Obs_value_date_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_date_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_date_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Obs_value_date]
    SET from_time = CASE WHEN from_time IS NOT NULL THEN DATEADD(day, @dw, from_time) END,
        to_time = CASE WHEN to_time IS NOT NULL THEN DATEADD(day, @dw, to_time) END
    FROM [dbo].[Obs_value_date]
    INNER JOIN #tmp ON observation_uid = uid AND obs_value_date_seq = seq
END
GO

IF (object_id('pii_Obs_value_date_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_date_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_date_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint,
        ver smallint
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Obs_value_date_hist]
    SET from_time = CASE WHEN from_time IS NOT NULL THEN DATEADD(day, @dw, from_time) END,
        to_time = CASE WHEN to_time IS NOT NULL THEN DATEADD(day, @dw, to_time) END
    FROM [dbo].[Obs_value_date_hist]
    INNER JOIN #tmp ON observation_uid = uid AND obs_value_date_seq = seq AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Obs_value_txt_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_txt_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_txt_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint,
        txt varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Obs_value_txt]
    SET value_image_txt = NULL,
        value_txt = txt
    FROM [dbo].[Obs_value_txt]
    INNER JOIN #tmp ON observation_uid = uid AND obs_value_txt_seq = seq
END
GO

IF (object_id('pii_Obs_value_txt_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_txt_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_txt_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint,
        ver smallint,
        txt varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Obs_value_txt_hist]
    SET value_image_txt = NULL,
        value_txt = txt
    FROM [dbo].[Obs_value_txt_hist]
    INNER JOIN #tmp ON observation_uid = uid AND obs_value_txt_seq = seq AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Observation_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Observation_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Observation_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        otxt varchar(1000),
        rpt_time datetime
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Observation]
    SET activity_from_time = CASE WHEN activity_from_time IS NOT NULL THEN DATEADD(day, @dw, activity_from_time) END,
        activity_to_time = CASE WHEN activity_to_time IS NOT NULL THEN DATEADD(day, @dw, activity_to_time) END,
        effective_from_time = CASE WHEN effective_from_time IS NOT NULL THEN DATEADD(day, @dw, effective_from_time) END,
        effective_to_time = CASE WHEN effective_to_time IS NOT NULL THEN DATEADD(day, @dw, effective_to_time) END,
        txt = otxt,
        rpt_to_state_time = rpt_time
    FROM [dbo].[Observation]
    INNER JOIN #tmp ON observation_uid = uid
END
GO

IF (object_id('pii_Observation_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Observation_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Observation_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ver smallint,
        otxt varchar(1000),
        rpt_time datetime
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Observation_hist]
    SET activity_from_time = CASE WHEN activity_from_time IS NOT NULL THEN DATEADD(day, @dw, activity_from_time) END,
        activity_to_time = CASE WHEN activity_to_time IS NOT NULL THEN DATEADD(day, @dw, activity_to_time) END,
        effective_from_time = CASE WHEN effective_from_time IS NOT NULL THEN DATEADD(day, @dw, effective_from_time) END,
        effective_to_time = CASE WHEN effective_to_time IS NOT NULL THEN DATEADD(day, @dw, effective_to_time) END,
        txt = otxt,
        rpt_to_state_time = rpt_time
    FROM [dbo].[Observation_hist]
    INNER JOIN #tmp ON observation_uid = uid AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Organization_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Organization_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Organization_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        dsc varchar(1000),
        name varchar(100),
        city varchar(100),
        zip varchar(20)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Organization]
    SET description = dsc,
        display_nm = name,
        city_desc_txt = city,
        zip_cd = zip
    FROM [dbo].[Organization]
    INNER JOIN #tmp ON organization_uid = uid
END
GO

IF (object_id('pii_Organization_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Organization_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Organization_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ver smallint,
        dsc varchar(1000),
        name varchar(100),
        city varchar(100),
        zip varchar(20)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Organization_hist]
    SET description = dsc,
        display_nm = name,
        city_desc_txt = city,
        zip_cd = zip
    FROM [dbo].[Organization_hist]
    INNER JOIN #tmp ON organization_uid = uid AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Organization_name_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Organization_name_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Organization_name_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint,
        name varchar(100)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Organization_name]
    SET nm_txt = name
    FROM [dbo].[Organization_name]
    INNER JOIN #tmp ON organization_uid = uid AND organization_name_seq = seq
END
GO

IF (object_id('pii_Organization_name_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Organization_name_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Organization_name_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint,
        ver smallint,
        name varchar(100)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Organization_name_hist]
    SET nm_txt = name
    FROM [dbo].[Organization_name_hist]
    INNER JOIN #tmp ON organization_uid = uid AND organization_name_seq = seq AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Person_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Person_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Person_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        age_clc smallint,
        age_clc_time datetime,
        age_rep varchar(10),
        age_rep_time datetime,
        birt_time datetime,
        birt_time_clc datetime,
        dec_time datetime,
        descr varchar(2000),
        mothers_nm varchar(50),
        fst_nm varchar(50),
        lst_nm varchar(50),
        mid_nm varchar(50),
        pref_nm varchar(50),
        hm_st_addr1 varchar(100),
        hm_st_addr2 varchar(100),
        hm_cty_desc varchar(100),
        hm_zip varchar(20),
        hm_phone varchar(20),
        hm_email varchar(100),
        cell_phone varchar(20),
        wk_st_addr1 varchar(100),
        wk_st_addr2 varchar(100),
        wk_cty_desc varchar(100),
        wk_zip varchar(20),
        wk_phone varchar(20),
        wk_email varchar(100),
        ssn_cd varchar(100),
        med_num varchar(100),
        dr_lic varchar(100),
        birt_cty_desc varchar(100),
        dt_admin datetime,
        dt_ethnic datetime,
        dt_gen datetime,
        dt_morb datetime,
        dt_sex datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Person]
    SET age_calc = age_clc, age_calc_time = age_clc_time, age_reported = age_rep, age_reported_time = age_rep_time,
        birth_time = birt_time, birth_time_calc = birt_time_clc, deceased_time = dec_time, description = descr,
        mothers_maiden_nm = mothers_nm, first_nm = fst_nm, last_nm = lst_nm, middle_nm = mid_nm, preferred_nm = pref_nm,
        hm_street_addr1 = hm_st_addr1, hm_street_addr2 = hm_st_addr2, hm_city_desc_txt = hm_cty_desc, hm_zip_cd = hm_zip,
        hm_phone_nbr = hm_phone, hm_email_addr = hm_email, cell_phone_nbr = cell_phone,
        wk_street_addr1 = wk_st_addr1, wk_street_addr2 = wk_st_addr2, wk_city_desc_txt = wk_cty_desc,
        wk_zip_cd = wk_zip, wk_phone_nbr = wk_phone, wk_email_addr = wk_email,
        SSN = ssn_cd, medicaid_num = med_num, dl_num = dr_lic, birth_city_desc_txt = birt_cty_desc,
        as_of_date_admin = dt_admin, as_of_date_ethnicity = dt_ethnic, as_of_date_general = dt_gen,
        as_of_date_morbidity = dt_morb, as_of_date_sex = dt_sex
    FROM [dbo].[Person]
    INNER JOIN #tmp ON person_uid = uid
END
GO

IF (object_id('pii_Person_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Person_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Person_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ver smallint,
        age_clc smallint,
        age_clc_time datetime,
        age_rep varchar(10),
        age_rep_time datetime,
        birt_time datetime,
        birt_time_clc datetime,
        dec_time datetime,
        descr varchar(2000),
        mothers_nm varchar(50),
        fst_nm varchar(50),
        lst_nm varchar(50),
        mid_nm varchar(50),
        pref_nm varchar(50),
        hm_st_addr1 varchar(100),
        hm_st_addr2 varchar(100),
        hm_cty_desc varchar(100),
        hm_zip varchar(20),
        hm_phone varchar(20),
        hm_email varchar(100),
        cell_phone varchar(20),
        wk_st_addr1 varchar(100),
        wk_st_addr2 varchar(100),
        wk_cty_desc varchar(100),
        wk_zip varchar(20),
        wk_phone varchar(20),
        wk_email varchar(100),
        ssn_cd varchar(100),
        med_num varchar(100),
        dr_lic varchar(100),
        birt_cty_desc varchar(100),
        dt_admin datetime,
        dt_ethnic datetime,
        dt_gen datetime,
        dt_morb datetime,
        dt_sex datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Person_hist]
    SET age_calc = age_clc, age_calc_time = age_clc_time, age_reported = age_rep, age_reported_time = age_rep_time,
        birth_time = birt_time, birth_time_calc = birt_time_clc, deceased_time = dec_time, description = descr,
        mothers_maiden_nm = mothers_nm, first_nm = fst_nm, last_nm = lst_nm, middle_nm = mid_nm, preferred_nm = pref_nm,
        hm_street_addr1 = hm_st_addr1, hm_street_addr2 = hm_st_addr2, hm_city_desc_txt = hm_cty_desc, hm_zip_cd = hm_zip,
        hm_phone_nbr = hm_phone, hm_email_addr = hm_email, cell_phone_nbr = cell_phone,
        wk_street_addr1 = wk_st_addr1, wk_street_addr2 = wk_st_addr2, wk_city_desc_txt = wk_cty_desc,
        wk_zip_cd = wk_zip, wk_phone_nbr = wk_phone, wk_email_addr = wk_email,
        SSN = ssn_cd, medicaid_num = med_num, dl_num = dr_lic, birth_city_desc_txt = birt_cty_desc,
        as_of_date_admin = dt_admin, as_of_date_ethnicity = dt_ethnic, as_of_date_general = dt_gen,
        as_of_date_morbidity = dt_morb, as_of_date_sex = dt_sex
    FROM [dbo].[Person_hist]
    INNER JOIN #tmp ON person_uid = uid AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Person_name_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Person_name_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Person_name_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint,
        fst_nm varchar(50),
        fst_nm_sndx varchar(50),
        lst_nm varchar(50),
        lst_nm_sndx varchar(50),
        lst_nm2 varchar(50),
        lst_nm2_sndx varchar(50),
        mid_nm varchar(50),
        mid_nm2 varchar(50),
        as_of_dt datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Person_name]
    SET first_nm = fst_nm, first_nm_sndx = fst_nm_sndx,
        last_nm = lst_nm, last_nm_sndx = lst_nm_sndx,
        last_nm2 = lst_nm2, last_nm2_sndx = lst_nm2_sndx,
        middle_nm = mid_nm, middle_nm2 = mid_nm2,
        as_of_date = as_of_dt
    FROM [dbo].[Person_name]
    INNER JOIN #tmp ON person_uid = uid AND person_name_seq = seq
END
GO

IF (object_id('pii_Person_name_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Person_name_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Person_name_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint,
        ver smallint,
        fst_nm varchar(50),
        fst_nm_sndx varchar(50),
        lst_nm varchar(50),
        lst_nm_sndx varchar(50),
        lst_nm2 varchar(50),
        lst_nm2_sndx varchar(50),
        mid_nm varchar(50),
        mid_nm2 varchar(50),
        as_of_dt datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Person_name_hist]
    SET first_nm = fst_nm, first_nm_sndx = fst_nm_sndx,
        last_nm = lst_nm, last_nm_sndx = lst_nm_sndx,
        last_nm2 = lst_nm2, last_nm2_sndx = lst_nm2_sndx,
        middle_nm = mid_nm, middle_nm2 = mid_nm2,
        as_of_date = as_of_dt
    FROM [dbo].[Person_name_hist]
    INNER JOIN #tmp ON person_uid = uid AND person_name_seq = seq AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Postal_locator_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Postal_locator_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Postal_locator_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        cty_dsc_txt varchar(100),
        cnt_dsc_txt varchar(100),
        st_addr1 varchar(100),
        st_addr2 varchar(100),
        zip varchar(20)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Postal_locator]
    SET city_desc_txt = cty_dsc_txt, cnty_desc_txt = cnt_dsc_txt,
        street_addr1 = st_addr1, street_addr2 = st_addr2, zip_cd = zip
    FROM [dbo].[Postal_locator]
    INNER JOIN #tmp ON postal_locator_uid = uid
END
GO

IF (object_id('pii_Postal_locator_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Postal_locator_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Postal_locator_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ver smallint,
        cty_dsc_txt varchar(100),
        cnt_dsc_txt varchar(100),
        st_addr1 varchar(100),
        st_addr2 varchar(100),
        zip varchar(20)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Postal_locator_hist]
    SET city_desc_txt = cty_dsc_txt, cnty_desc_txt = cnt_dsc_txt,
        street_addr1 = st_addr1, street_addr2 = st_addr2, zip_cd = zip
    FROM [dbo].[Postal_locator_hist]
    INNER JOIN #tmp ON postal_locator_uid = uid AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Public_health_case_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Public_health_case_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Public_health_case_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        diag_time datetime,
        pat_age varchar(20),
        rpt_cmplt_time datetime,
        rpt_to_cnty_time datetime,
        rpt_to_ste_time datetime,
        txt1 varchar(2000),
        assigned_time datetime,
        import_cty_desc varchar(250),
        dec_time datetime,
        cont_inv_txt varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Public_health_case]
    SET activity_from_time = CASE WHEN activity_from_time IS NOT NULL THEN DATEADD(day, @dw, activity_from_time) END,
        activity_to_time = CASE WHEN activity_to_time IS NOT NULL THEN DATEADD(day, @dw, activity_to_time) END,
        diagnosis_time = diag_time,
        effective_from_time = CASE WHEN effective_from_time IS NOT NULL THEN DATEADD(day, @dw, effective_from_time) END,
        effective_to_time = CASE WHEN effective_to_time IS NOT NULL THEN DATEADD(day, @dw, effective_to_time) END,
        outbreak_from_time = CASE WHEN outbreak_from_time IS NOT NULL THEN DATEADD(day, @dw, outbreak_from_time) END,
        outbreak_to_time = CASE WHEN outbreak_to_time IS NOT NULL THEN DATEADD(day, @dw, outbreak_to_time) END,
        pat_age_at_onset = pat_age,
        rpt_form_cmplt_time = rpt_cmplt_time,
        rpt_to_county_time = rpt_cnty_cd,
        rpt_to_state_time = rpt_to_ste_time,
        txt = txt1,
        investigator_assigned_time = assigned_time,
        hospitalized_admin_time = CASE WHEN hospitalized_admin_time IS NOT NULL THEN DATEADD(day, @dw, hospitalized_admin_time) END,
        hospitalized_discharge_time = CASE WHEN hospitalized_discharge_time IS NOT NULL THEN DATEADD(day, @dw, hospitalized_discharge_time) END,
        imported_city_desc_txt = import_cty_desc,
        deceased_time = dec_time,
        contact_inv_txt = cont_inv_txt,
        infectious_from_date = CASE WHEN infectious_from_date IS NOT NULL THEN DATEADD(day, @dw, infectious_from_date) END,
        infectious_to_date = CASE WHEN infectious_to_date IS NOT NULL THEN DATEADD(day, @dw, infectious_to_date) END
    FROM [dbo].[Public_health_case]
    INNER JOIN #tmp ON public_health_case_uid = uid
END
GO

IF (object_id('pii_Public_health_case_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Public_health_case_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Public_health_case_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ver smallint,
        diag_time datetime,
        pat_age varchar(20),
        rpt_cmplt_time datetime,
        rpt_to_cnty_time datetime,
        rpt_to_ste_time datetime,
        txt1 varchar(2000),
        assigned_time datetime,
        import_cty_desc varchar(250),
        dec_time datetime,
        cont_inv_txt varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Public_health_case_hist]
    SET activity_from_time = CASE WHEN activity_from_time IS NOT NULL THEN DATEADD(day, @dw, activity_from_time) END,
        activity_to_time = CASE WHEN activity_to_time IS NOT NULL THEN DATEADD(day, @dw, activity_to_time) END,
        diagnosis_time = diag_time,
        effective_from_time = CASE WHEN effective_from_time IS NOT NULL THEN DATEADD(day, @dw, effective_from_time) END,
        effective_to_time = CASE WHEN effective_to_time IS NOT NULL THEN DATEADD(day, @dw, effective_to_time) END,
        outbreak_from_time = CASE WHEN outbreak_from_time IS NOT NULL THEN DATEADD(day, @dw, outbreak_from_time) END,
        outbreak_to_time = CASE WHEN outbreak_to_time IS NOT NULL THEN DATEADD(day, @dw, outbreak_to_time) END,
        pat_age_at_onset = pat_age,
        rpt_form_cmplt_time = rpt_cmplt_time,
        rpt_to_county_time = rpt_cnty_cd,
        rpt_to_state_time = rpt_to_ste_time,
        txt = txt1,
        investigator_assigned_time = assigned_time,
        hospitalized_admin_time = CASE WHEN hospitalized_admin_time IS NOT NULL THEN DATEADD(day, @dw, hospitalized_admin_time) END,
        hospitalized_discharge_time = CASE WHEN hospitalized_discharge_time IS NOT NULL THEN DATEADD(day, @dw, hospitalized_discharge_time) END,
        imported_city_desc_txt = import_cty_desc,
        deceased_time = dec_time,
        contact_inv_txt = cont_inv_txt,
        infectious_from_date = CASE WHEN infectious_from_date IS NOT NULL THEN DATEADD(day, @dw, infectious_from_date) END,
        infectious_to_date = CASE WHEN infectious_to_date IS NOT NULL THEN DATEADD(day, @dw, infectious_to_date) END
    FROM [dbo].[Public_health_case_hist]
    INNER JOIN #tmp ON public_health_case_uid = uid AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_PublicHealthCaseFact_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_PublicHealthCaseFact_Restore]
GO

CREATE PROCEDURE [dbo].[pii_PublicHealthCaseFact_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ageInM smallint,
        ageInY smallint,
        age_rpt_time datetime,
        age_rpt numeric(8),
        birt_time datetime,
        birt_time_calc datetime,
        cnty_code_dsc varchar(200),
        city_dsc varchar(100),
        confirm_method_time datetime,
        cnty varchar(255),
        decs_time datetime,
        diag_dt datetime,
        event_dt datetime,
        first_notif_send_dt datetime,
        first_notif_dt datetime,
        geoLat real,
        geoLong real,
        inv_assign_dt datetime,
        inv_nm varchar(102),
        last_notif_dt datetime,
        last_notif_send_dt datetime,
        mart_rec_crt_dt datetime,
        mart_rec_crt_time datetime,
        notif_dt datetime,
        onset_dt datetime,
        org_nm varchar(100),
        pat_age_onset numeric(8),
        provider_nm varchar(102),
        reporter_nm varchar(102),
        rpt_form_time datetime,
        rpt_to_cnty_time datetime,
        rpt_to_ste_time datetime,
        zip varchar(20),
        pat_nm varchar(102),
        jur varchar(50),
        inv_start_dt datetime,
        rpt_dt datetime,
        sub_addr_as_of_dt datetime,
        rpt_cnty_desc varchar(300)
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[PublicHealthCaseFact]
    SET ageInMonths = ageInM, ageInYears = ageInY,
        age_reported_time = age_rpt_time, age_reported = age_rpt,
        birth_time = birt_time, birth_time_calc = birt_time_calc,
        cnty_code_desc_txt = cnty_code_dsc, city_desc_txt = cnty_code_dsc,
        confirmation_method_time = confirm_method_time, county = cnty,
        deceased_time = decs_time, diagnosis_date = diag_dt, event_date = event_dt,
        firstNotificationSenddate = first_notif_send_dt, firstNotificationdate = first_notif_dt,
        geoLatitude = geoLat, geoLongitude = geoLong,
        investigatorAssigneddate = inv_assign_dt, investigatorName = inv_nm,
        lastNotificationdate = last_notif_dt, lastNotificationSenddate = last_notif_send_dt,
        mart_record_creation_date = mart_rec_crt_dt, mart_record_creation_time = mart_rec_crt_time,
        notificationdate = notif_dt, onSetDate = onset_dt, organizationName = org_nm,
        outbreak_from_time = CASE WHEN outbreak_from_time IS NOT NULL THEN DATEADD(day, @dw, outbreak_from_time) END,
        outbreak_to_time = CASE WHEN outbreak_to_time IS NOT NULL THEN DATEADD(day, @dw, outbreak_to_time) END,
        pat_age_at_onset = pat_age_onset,
        providerName = provider_nm, reporterName = reporter_nm,
        rpt_form_cmplt_time = rpt_form_time, rpt_to_county_time = rpt_to_cnty_time, rpt_to_state_time = rpt_to_ste_time,
        zip_cd = zip, patientName = pat_nm, jurisdiction = jur,
        investigationstartdate = inv_start_dt, report_date = rpt_dt,
        sub_addr_as_of_date = sub_addr_as_of_dt, rpt_cnty_desc_txt = rpt_cnty_desc,
        HSPTL_ADMISSION_DT = CASE WHEN HSPTL_ADMISSION_DT IS NOT NULL THEN DATEADD(day, @dw, HSPTL_ADMISSION_DT) END,
        HSPTL_DISCHARGE_DT = CASE WHEN HSPTL_DISCHARGE_DT IS NOT NULL THEN DATEADD(day, @dw, HSPTL_DISCHARGE_DT) END
    FROM [dbo].[PublicHealthCaseFact]
    INNER JOIN #tmp ON public_health_case_uid = uid
END
GO

IF (object_id('pii_Referral_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Referral_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Referral_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        rsn_txt varchar(100),
        ref_desc varchar(100),
        txt1  varchar(1000)
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Referral]
    SET activity_from_time = CASE WHEN activity_from_time IS NOT NULL THEN DATEADD(day, @dw, activity_from_time) END,
        activity_to_time = CASE WHEN activity_to_time IS NOT NULL THEN DATEADD(day, @dw, activity_to_time) END,
        effective_from_time = CASE WHEN effective_from_time IS NOT NULL THEN DATEADD(day, @dw, effective_from_time) END,
        effective_to_time = CASE WHEN effective_to_time IS NOT NULL THEN DATEADD(day, @dw, effective_to_time) END,
        reason_txt = rsn_txt,
        referral_desc_txt = ref_desc,
        txt = txt1
    FROM [dbo].[Referral]
    INNER JOIN #tmp ON referral_uid = uid
END
GO

IF (object_id('pii_Referral_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Referral_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Referral_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ver smallint,
        rsn_txt varchar(100),
        ref_desc varchar(100),
        txt1  varchar(1000)
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Referral_hist]
    SET activity_from_time = CASE WHEN activity_from_time IS NOT NULL THEN DATEADD(day, @dw, activity_from_time) END,
        activity_to_time = CASE WHEN activity_to_time IS NOT NULL THEN DATEADD(day, @dw, activity_to_time) END,
        effective_from_time = CASE WHEN effective_from_time IS NOT NULL THEN DATEADD(day, @dw, effective_from_time) END,
        effective_to_time = CASE WHEN effective_to_time IS NOT NULL THEN DATEADD(day, @dw, effective_to_time) END,
        reason_txt = rsn_txt,
        referral_desc_txt = ref_desc,
        txt = txt1
    FROM [dbo].[Referral_hist]
    INNER JOIN #tmp ON referral_uid = uid AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_SUSPCT_MEAT_OBTND_DATA_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_SUSPCT_MEAT_OBTND_DATA_Restore]
GO

CREATE PROCEDURE [dbo].[pii_SUSPCT_MEAT_OBTND_DATA_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid1 bigint,
        uid2 bigint,
        ver smallint,
        ldf_val varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[SUSPCT_MEAT_OBTND_DATA]
    SET ldf_value = ldf_val
    FROM [dbo].[SUSPCT_MEAT_OBTND_DATA]
    INNER JOIN #tmp ON business_object_uid = uid1 AND ldf_uid = uid2 AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Tele_locator_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Tele_locator_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Tele_locator_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        email varchar(100),
        phone_nbr varchar(20)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Tele_locator]
    SET email_address = email,
        phone_nbr_txt = phone_nbr
    FROM [dbo].[Tele_locator]
    INNER JOIN #tmp ON tele_locator_uid = uid
END
GO

IF (object_id('pii_Tele_locator_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Tele_locator_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Tele_locator_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ver smallint,
        email varchar(100),
        phone_nbr varchar(20)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[Tele_locator_hist]
    SET email_address = email,
        phone_nbr_txt = phone_nbr
    FROM [dbo].[Tele_locator_hist]
    INNER JOIN #tmp ON tele_locator_uid = uid AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Treatment_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Treatment_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Treatment_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ttxt varchar(1000)
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Treatment]
    SET activity_from_time = CASE WHEN activity_from_time IS NOT NULL THEN DATEADD(day, @dw, activity_from_time) END,
        activity_to_time = CASE WHEN activity_to_time IS NOT NULL THEN DATEADD(day, @dw, activity_to_time) END,
        txt = ttxt
    FROM [dbo].[Treatment]
    INNER JOIN #tmp ON treatment_uid = uid
END
GO

IF (object_id('pii_Treatment_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Treatment_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Treatment_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        ver smallint,
        ttxt varchar(1000)
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Treatment_hist]
    SET activity_from_time = CASE WHEN activity_from_time IS NOT NULL THEN DATEADD(day, @dw, activity_from_time) END,
        activity_to_time = CASE WHEN activity_to_time IS NOT NULL THEN DATEADD(day, @dw, activity_to_time) END,
        txt = ttxt
    FROM [dbo].[Treatment_hist]
    INNER JOIN #tmp ON treatment_uid = uid AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Treatment_administered_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Treatment_administered_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Treatment_administered_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Treatment_administered]
    SET effective_from_time = CASE WHEN effective_from_time IS NOT NULL THEN DATEADD(day, @dw, effective_from_time) END,
        effective_to_time = CASE WHEN effective_to_time IS NOT NULL THEN DATEADD(day, @dw, effective_to_time) END
    FROM [dbo].[Treatment_administered]
    INNER JOIN #tmp ON treatment_uid = uid and treatment_administered_seq = seq
END
GO

IF (object_id('pii_Treatment_administered_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Treatment_administered_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Treatment_administered_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint,
        ver smallint
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Treatment_administered_hist]
    SET effective_from_time = CASE WHEN effective_from_time IS NOT NULL THEN DATEADD(day, @dw, effective_from_time) END,
        effective_to_time = CASE WHEN effective_to_time IS NOT NULL THEN DATEADD(day, @dw, effective_to_time) END
    FROM [dbo].[Treatment_administered_hist]
    INNER JOIN #tmp ON treatment_uid = uid and treatment_administered_seq = seq AND version_ctrl_nbr = ver
END
GO

IF (object_id('pii_Treatment_procedure_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Treatment_procedure_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Treatment_procedure_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Treatment_procedure]
    SET effective_from_time = CASE WHEN effective_from_time IS NOT NULL THEN DATEADD(day, @dw, effective_from_time) END,
        effective_to_time = CASE WHEN effective_to_time IS NOT NULL THEN DATEADD(day, @dw, effective_to_time) END
    FROM [dbo].[Treatment_procedure]
    INNER JOIN #tmp ON treatment_uid = uid and treatment_procedure_seq = seq
END
GO

IF (object_id('pii_Treatment_procedure_hist_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_Treatment_procedure_hist_Restore]
GO

CREATE PROCEDURE [dbo].[pii_Treatment_procedure_hist_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        seq smallint,
        ver smallint
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[Treatment_procedure_hist]
    SET effective_from_time = CASE WHEN effective_from_time IS NOT NULL THEN DATEADD(day, @dw, effective_from_time) END,
        effective_to_time = CASE WHEN effective_to_time IS NOT NULL THEN DATEADD(day, @dw, effective_to_time) END
    FROM [dbo].[Treatment_procedure_hist]
    INNER JOIN #tmp ON treatment_uid = uid and treatment_procedure_seq = seq AND version_ctlr_nbr = ver
END
GO

IF (object_id('pii_USER_PROFILE_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_USER_PROFILE_Restore]
GO

CREATE PROCEDURE [dbo].[pii_USER_PROFILE_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON
    CREATE TABLE #tmp (
        uid bigint,
        fst_nm varchar(100),
        lst_nm varchar(100)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[USER_PROFILE]
    SET FIRST_NM = fst_nm, LAST_NM = lst_nm
    FROM [dbo].[USER_PROFILE]
    INNER JOIN #tmp ON NEDSS_ENTRY_ID = uid
END
GO