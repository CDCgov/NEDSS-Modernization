use NBS_ODSE
GO

IF (object_id('pii_Act_id') is not null)
    DROP PROCEDURE [dbo].[pii_Act_id]
GO

CREATE PROCEDURE [dbo].[pii_Act_id]
@fromTime Datetime = null

AS
BEGIN
    SELECT act_uid, act_id_seq, root_extension_txt FROM dbo.Act_id
    WHERE @fromTime IS NULL OR (status_time IS NOT NULL AND status_time > @fromTime)
END
GO

IF (object_id('pii_Act_id_hist') is not null)
DROP PROCEDURE [dbo].[pii_Act_id_hist]
GO

CREATE PROCEDURE [dbo].[pii_Act_id_hist]
    @fromTime Datetime = null

AS
BEGIN
    SELECT act_uid, act_id_seq, version_ctrl_nbr, root_extension_txt
    FROM dbo.Act_id_hist
    WHERE @fromTime IS NULL OR (status_time IS NOT NULL AND status_time > @fromTime)
END
GO

IF (object_id('pii_Activity_log') is not null)
    DROP PROCEDURE [dbo].[pii_Activity_log]
GO

CREATE PROCEDURE [dbo].[pii_Activity_log]
@fromTime Datetime = null

AS
BEGIN
    SELECT activity_log_uid, doc_nm, message_txt
    FROM dbo.Activity_log
    WHERE @fromTime IS NULL OR add_time > @fromTime
END
GO

IF (object_id('pii_Auth_user') is not null)
    DROP PROCEDURE [dbo].[pii_Auth_user]
GO

CREATE PROCEDURE [dbo].[pii_Auth_user]
@fromTime Datetime = null

AS
BEGIN
    SELECT auth_user_uid, user_title, user_department, user_first_nm, user_last_nm,
           user_work_email, user_work_phone, user_mobile_phone, user_comments
    FROM dbo.Auth_user
    WHERE @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_case_management') is not null)
    DROP PROCEDURE [dbo].[pii_case_management]
GO

CREATE PROCEDURE [dbo].[pii_case_management]
@fromTime Datetime = null

AS
BEGIN
    SELECT case_management_uid, fld_foll_up_dispo_date, fld_foll_up_exam_date, fld_foll_up_expected_date, init_foll_up_closed_date, ooj_due_date,
           subj_complexion, subj_hair, subj_height, subj_oth_idntfyng_info, subj_size_build, surv_closed_date,
           ooj_initg_agncy_outc_due_date, ooj_initg_agncy_outc_snt_date, ooj_initg_agncy_recd_date, surv_assigned_date, foll_up_assigned_date,
           init_foll_up_assigned_date, interview_assigned_date, init_interview_assigned_date, case_closed_date, case_review_status_date
    FROM dbo.case_management
END
GO

IF (object_id('pii_case_management_hist') is not null)
    DROP PROCEDURE [dbo].[pii_case_management_hist]
GO

CREATE PROCEDURE [dbo].[pii_case_management_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT case_management_hist_uid, fld_foll_up_dispo_date, fld_foll_up_exam_date, fld_foll_up_expected_date, init_foll_up_closed_date, ooj_due_date,
           subj_complexion, subj_hair, subj_height, subj_oth_idntfyng_info, subj_size_build, surv_closed_date,
           ooj_initg_agncy_outc_due_date, ooj_initg_agncy_outc_snt_date, ooj_initg_agncy_recd_date, surv_assigned_date, foll_up_assigned_date,
           init_foll_up_assigned_date, interview_assigned_date, init_interview_assigned_date, case_closed_date, case_review_status_date
    FROM dbo.case_management_hist
END
GO

IF (object_id('pii_CDF_subform_import_log') is not null)
    DROP PROCEDURE [dbo].[pii_CDF_subform_import_log]
GO

CREATE PROCEDURE [dbo].[pii_CDF_subform_import_log]
@fromTime Datetime = null

AS
BEGIN
    SELECT import_log_uid, admin_comment
    FROM dbo.CDF_subform_import_log
    WHERE @fromTime IS NULL OR (import_time IS NOT NULL AND import_time > @fromTime)
END
GO

IF (object_id('pii_Chart_report_metadata') is not null)
    DROP PROCEDURE [dbo].[pii_Chart_report_metadata]
GO

CREATE PROCEDURE [dbo].[pii_Chart_report_metadata]
@fromTime Datetime = null

AS
BEGIN
    SELECT chart_report_metadata_uid, chart_report_desc_txt, chart_report_short_desc_txt
    FROM dbo.Chart_report_metadata
    WHERE @fromTime IS NULL OR (add_time IS NOT NULL AND add_time > @fromTime)
END
GO

IF (object_id('pii_Confirmation_method') is not null)
    DROP PROCEDURE [dbo].[pii_Confirmation_method]
GO

CREATE PROCEDURE [dbo].[pii_Confirmation_method]
@fromTime Datetime = null

AS
BEGIN
    SELECT public_health_case_uid, confirmation_method_cd, confirmation_method_time
    FROM dbo.Confirmation_method
END
GO

IF (object_id('pii_Confirmation_method_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Confirmation_method_hist]
GO

CREATE PROCEDURE [dbo].[pii_Confirmation_method_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT public_health_case_uid, confirmation_method_cd, confirmation_method_time
    FROM dbo.Confirmation_method_hist
END
GO

IF (object_id('pii_CT_contact') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact]
@fromTime Datetime = null

AS
BEGIN
    SELECT ct_contact_uid, investigator_assigned_date, disposition_date, named_on_date,
           txt, symptom_onset_date, symptom_txt, evaluation_date, evaluation_txt,
           treatment_start_date, treatment_end_date, treatment_txt
    FROM dbo.CT_contact
    WHERE @fromTime IS NULL OR add_time > @fromTime
END
GO

IF (object_id('pii_CT_contact_hist') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_hist]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT ct_contact_hist_uid, investigator_assigned_date, disposition_date, named_on_date,
           txt, symptom_onset_date, symptom_txt, evaluation_date, evaluation_txt,
           treatment_start_date, treatment_end_date, treatment_txt
    FROM dbo.CT_contact_hist
    WHERE @fromTime IS NULL OR add_time > @fromTime
END
GO

IF (object_id('pii_CT_contact_answer') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_answer]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_answer]
@fromTime Datetime = null

AS
BEGIN
    SELECT ct_contact_answer_uid, answer_txt, answer_large_txt
    FROM dbo.CT_contact_answer
    WHERE @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_CT_contact_answer_hist') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_answer_hist]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_answer_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT ct_contact_answer_hist_uid, answer_txt, answer_large_txt
    FROM dbo.CT_contact_answer_hist
    WHERE @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_CT_contact_attachment') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_attachment]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_attachment]
@fromTime Datetime = null

AS
BEGIN
    SELECT ct_contact_attachment_uid, desc_txt, file_nm_txt
    FROM dbo.CT_contact_attachment
    WHERE @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_CT_contact_note') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_note]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_note]
@fromTime Datetime = null

AS
BEGIN
    SELECT ct_contact_note_uid, note
    FROM dbo.CT_contact_note
    WHERE @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_Custom_queues') is not null)
    DROP PROCEDURE [dbo].[pii_Custom_queues]
GO

CREATE PROCEDURE [dbo].[pii_Custom_queues]
@fromTime Datetime = null

AS
BEGIN
    SELECT queue_name, description, search_criteria_desc
    FROM dbo.Custom_queues
    WHERE @fromTime IS NULL OR (last_chg_time IS NOT NULL AND last_chg_time > @fromTime)
END
GO

IF (object_id('pii_Custom_subform_metadata') is not null)
    DROP PROCEDURE [dbo].[pii_Custom_subform_metadata]
GO

CREATE PROCEDURE [dbo].[pii_Custom_subform_metadata]
@fromTime Datetime = null

AS
BEGIN
    SELECT custom_subform_metadata_uid, admin_comment, html_data
    FROM dbo.Custom_subform_metadata
    WHERE @fromTime IS NULL OR add_time > @fromTime
END
GO

IF (object_id('pii_Data_migration_record') is not null)
    DROP PROCEDURE [dbo].[pii_Data_migration_record]
GO

CREATE PROCEDURE [dbo].[pii_Data_migration_record]
@fromTime Datetime = null

AS
BEGIN
    SELECT data_migration_record_uid, data_migration_batch_uid, sub_nm
    FROM dbo.Data_migration_record
END
GO

IF (object_id('pii_dsm_algorithm') is not null)
    DROP PROCEDURE [dbo].[pii_dsm_algorithm]
GO

CREATE PROCEDURE [dbo].[pii_dsm_algorithm]
@fromTime Datetime = null

AS
BEGIN
    SELECT dsm_algorithm_uid, algorithm_nm, admin_comment
    FROM dbo.dsm_algorithm
    WHERE @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_dsm_algorithm_hist') is not null)
    DROP PROCEDURE [dbo].[pii_dsm_algorithm_hist]
GO

CREATE PROCEDURE [dbo].[pii_dsm_algorithm_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT dsm_algorithm_hist_uid, algorithm_nm, admin_comment
    FROM dbo.dsm_algorithm_hist
    WHERE @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_EDX_activity_detail_log') is not null)
    DROP PROCEDURE [dbo].[pii_EDX_activity_detail_log]
GO

CREATE PROCEDURE [dbo].[pii_EDX_activity_detail_log]
@fromTime Datetime = null

AS
BEGIN
    SELECT edx_activity_detail_log_uid, log_comment
    FROM dbo.EDX_activity_detail_log Eadl
    JOIN dbo.EDX_activity_log Eal ON Eal.edx_activity_log_uid = Eadl.edx_activity_log_uid
    WHERE  @fromTime IS NULL OR (record_status_time IS NOT NULL AND record_status_time > @fromTime)
END
GO

IF (object_id('pii_EDX_activity_log') is not null)
    DROP PROCEDURE [dbo].[pii_EDX_activity_log]
GO

CREATE PROCEDURE [dbo].[pii_EDX_activity_log]
@fromTime Datetime = null

AS
BEGIN
    SELECT edx_activity_log_uid, Entity_nm
    FROM dbo.EDX_activity_log
    WHERE  @fromTime IS NULL OR (record_status_time IS NOT NULL AND record_status_time > @fromTime)
END
GO

IF (object_id('pii_EDX_Document') is not null)
    DROP PROCEDURE [dbo].[pii_EDX_Document]
GO

CREATE PROCEDURE [dbo].[pii_EDX_Document]
@fromTime Datetime = null

AS
BEGIN
    SELECT EDX_Document_uid, original_payload
    FROM dbo.EDX_Document
    WHERE  @fromTime IS NULL OR add_time > @fromTime
END
GO

IF (object_id('pii_ELR_activity_log') is not null)
    DROP PROCEDURE [dbo].[pii_ELR_activity_log]
GO

CREATE PROCEDURE [dbo].[pii_ELR_activity_log]
@fromTime Datetime = null

AS
BEGIN
    SELECT msg_observation_uid, elr_activity_log_seq,
           process_time, subject_nm, report_fac_nm, detail_txt
    FROM dbo.ELR_activity_log
    WHERE  @fromTime IS NULL OR process_time > @fromTime
END
GO

IF (object_id('pii_ELRWorkerQueue') is not null)
    DROP PROCEDURE [dbo].[pii_ELRWorkerQueue]
GO

CREATE PROCEDURE [dbo].[pii_ELRWorkerQueue]
@fromTime Datetime = null

AS
BEGIN
    SELECT recordId, payloadName, errorMessage
    FROM dbo.ELRWorkerQueue
    WHERE  @fromTime IS NULL OR (receivedTime IS NOT NULL AND receivedTime > @fromTime)
END
GO

IF (object_id('pii_Entity_id') is not null)
    DROP PROCEDURE [dbo].[pii_Entity_id]
GO

CREATE PROCEDURE [dbo].[pii_Entity_id]
@fromTime Datetime = null

AS
BEGIN
    SELECT entity_uid, entity_id_seq, root_extension_txt
    FROM dbo.Entity_id
    WHERE  @fromTime IS NULL OR
        (last_chg_time IS NOT NULL AND last_chg_time > @fromTime) OR
        (add_time IS NOT NULL AND add_time > @fromTime)
END
GO

IF (object_id('pii_Entity_locator_participation') is not null)
    DROP PROCEDURE [dbo].[pii_Entity_locator_participation]
GO

CREATE PROCEDURE [dbo].[pii_Entity_locator_participation]
@fromTime Datetime = null

AS
BEGIN
    SELECT entity_uid, locator_uid, as_of_date
    FROM dbo.Entity_locator_participation
    WHERE  @fromTime IS NULL OR (last_chg_time IS NOT NULL AND last_chg_time > @fromTime)
END
GO

IF (object_id('pii_Entity_loca_participation_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Entity_loca_participation_hist]
GO

CREATE PROCEDURE [dbo].[pii_Entity_loca_participation_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT entity_uid, locator_uid, version_ctrl_nbr, as_of_date
    FROM dbo.Entity_loc_participation_hist
    WHERE  @fromTime IS NULL OR (last_chg_time IS NOT NULL AND last_chg_time > @fromTime)
END
GO

IF (object_id('pii_Export_receiving_facility') is not null)
    DROP PROCEDURE [dbo].[pii_Export_receiving_facility]
GO

CREATE PROCEDURE [dbo].[pii_Export_receiving_facility]
@fromTime Datetime = null

AS
BEGIN
    SELECT export_receiving_facility_uid, admin_comment
    FROM dbo.Export_receiving_facility
    WHERE  @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_Intervention') is not null)
    DROP PROCEDURE [dbo].[pii_Intervention]
GO

CREATE PROCEDURE [dbo].[pii_Intervention]
@fromTime Datetime = null

AS
BEGIN
    SELECT intervention_uid,
           activity_from_time, activity_to_time, effective_from_time, effective_to_time,
           txt, age_at_vacc, material_lot_nm, material_expiration_time
    FROM dbo.Intervention
    WHERE  @fromTime IS NULL OR
           (last_chg_time IS NOT NULL AND last_chg_time > @fromTime) OR
           (add_time IS NOT NULL AND add_time > @fromTime)
END
GO

IF (object_id('pii_Intervention_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Intervention_hist]
GO

CREATE PROCEDURE [dbo].[pii_Intervention_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT intervention_uid, version_ctrl_nbr,
           activity_from_time, activity_to_time, effective_from_time, effective_to_time,
           txt, age_at_vacc, material_lot_nm, material_expiration_time
    FROM dbo.Intervention_hist
    WHERE  @fromTime IS NULL OR
           (last_chg_time IS NOT NULL AND last_chg_time > @fromTime) OR
           (add_time IS NOT NULL AND add_time > @fromTime)
END
GO

IF (object_id('pii_Interview') is not null)
    DROP PROCEDURE [dbo].[pii_Interview]
GO

CREATE PROCEDURE [dbo].[pii_Interview]
@fromTime Datetime = null

AS
BEGIN
    SELECT interview_uid, interview_date
    FROM dbo.Interview
    WHERE  @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_Interview_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Interview_hist]
GO

CREATE PROCEDURE [dbo].[pii_Interview_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT interview_hist_uid, interview_date
    FROM dbo.Interview_hist
    WHERE  @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_lab_event') is not null)
    DROP PROCEDURE [dbo].[pii_lab_event]
GO

CREATE PROCEDURE [dbo].[pii_lab_event]
@fromTime Datetime = null

AS
BEGIN
    SELECT lab_event_uid,
           result_rpt_dt, specimen_analyzed_dt, specimen_collection_dt,
           suscep_specimen_collection_dt, suscep_result_rpt_dt,
           lab_result_comments, suscep_lab_result_comments
    FROM dbo.lab_event
END
GO

IF (object_id('pii_lab_event_hist') is not null)
    DROP PROCEDURE [dbo].[pii_lab_event_hist]
GO

CREATE PROCEDURE [dbo].[pii_lab_event_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT lab_event_hist_uid,
           result_rpt_dt, specimen_analyzed_dt, specimen_collection_dt,
           suscep_specimen_collection_dt, suscep_result_rpt_dt,
           lab_result_comments, suscep_lab_result_comments
    FROM dbo.lab_event_hist
END
GO

IF (object_id('pii_Manufactured_material') is not null)
    DROP PROCEDURE [dbo].[pii_Manufactured_material]
GO

CREATE PROCEDURE [dbo].[pii_Manufactured_material]
@fromTime Datetime = null

AS
BEGIN
    SELECT material_uid, manufactured_material_seq, expiration_time,
           lot_nm, stability_from_time, stability_to_time
    FROM dbo.Manufactured_material
    WHERE @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_Manufactured_material_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Manufactured_material_hist]
GO

CREATE PROCEDURE [dbo].[pii_Manufactured_material_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT material_uid, manufactured_material_seq, version_ctrl_nbr,
           expiration_time, lot_nm, stability_from_time, stability_to_time
    FROM dbo.Manufactured_material_hist
    WHERE @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_Material') is not null)
    DROP PROCEDURE [dbo].[pii_Material]
GO

CREATE PROCEDURE [dbo].[pii_Material]
@fromTime Datetime = null

AS
BEGIN
    SELECT material_uid, effective_from_time, effective_to_time
    FROM dbo.Material
    WHERE @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_Material_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Material_hist]
GO

CREATE PROCEDURE [dbo].[pii_Material_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT material_uid, version_ctrl_nbr,
           effective_from_time, effective_to_time
    FROM dbo.Material_hist
    WHERE @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_nbs_answer') is not null)
    DROP PROCEDURE [dbo].[pii_nbs_answer]
GO

CREATE PROCEDURE [dbo].[pii_nbs_answer]
@fromTime Datetime = null

AS
BEGIN
    SELECT nbs_answer_uid, answer_txt
    FROM dbo.nbs_answer
    WHERE nbs_question_uid IN (
        SELECT nbs_question_uid FROM dbo.NBS_ui_metadata
        WHERE nbs_ui_component_uid IN (1009, 1014, 1019, 1026, 1029))
            AND @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_nbs_answer_hist') is not null)
    DROP PROCEDURE [dbo].[pii_nbs_answer_hist]
GO

CREATE PROCEDURE [dbo].[pii_nbs_answer_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT nbs_answer_hist_uid, answer_txt
    FROM dbo.nbs_answer_hist ans
    WHERE nbs_question_uid IN (
        SELECT nbs_question_uid FROM dbo.NBS_ui_metadata
        WHERE nbs_ui_component_uid IN (1009, 1014, 1019, 1026, 1029))
            AND @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_NBS_case_answer') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_case_answer]
GO

CREATE PROCEDURE [dbo].[pii_NBS_case_answer]
@fromTime Datetime = null

AS
BEGIN
    SELECT nbs_case_answer_uid, answer_txt
    FROM dbo.NBS_case_answer ans
    WHERE nbs_question_uid IN (
        SELECT nbs_question_uid FROM dbo.NBS_ui_metadata
        WHERE nbs_ui_component_uid IN (1009, 1014, 1019, 1026, 1029))
            AND @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_NBS_case_answer_hist') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_case_answer_hist]
GO

CREATE PROCEDURE [dbo].[pii_NBS_case_answer_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT nbs_case_answer_hist_uid, answer_txt
    FROM dbo.NBS_case_answer_hist ans
    WHERE nbs_question_uid IN (
        SELECT nbs_question_uid FROM dbo.NBS_ui_metadata
        WHERE nbs_ui_component_uid IN (1009, 1014, 1019, 1026, 1029))
            AND @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_NBS_document') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_document]
GO

CREATE PROCEDURE [dbo].[pii_NBS_document]
@fromTime Datetime = null

AS
BEGIN
    SELECT nbs_document_uid, txt, sending_facility_nm
    FROM dbo.NBS_document
    WHERE  @fromTime IS NULL OR
           (last_chg_time IS NOT NULL AND last_chg_time > @fromTime) OR
           (add_time > @fromTime)
END
GO

IF (object_id('pii_NBS_document_hist') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_document_hist]
GO

CREATE PROCEDURE [dbo].[pii_NBS_document_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT nbs_document_hist_uid, txt, sending_facility_nm
    FROM dbo.NBS_document_hist
    WHERE  @fromTime IS NULL OR
           (last_chg_time IS NOT NULL AND last_chg_time > @fromTime) OR
           (add_time > @fromTime)
END
GO

IF (object_id('pii_NBS_note') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_note]
GO

CREATE PROCEDURE [dbo].[pii_NBS_note]
@fromTime Datetime = null

AS
BEGIN
    SELECT nbs_note_uid, note
    FROM dbo.NBS_note
    WHERE  @fromTime IS NULL OR last_chg_time > @fromTime
END
GO

IF (object_id('pii_Notification') is not null)
    DROP PROCEDURE [dbo].[pii_Notification]
GO

CREATE PROCEDURE [dbo].[pii_Notification]
@fromTime Datetime = null

AS
BEGIN
    SELECT notification_uid, txt
    FROM dbo.Notification
    WHERE  @fromTime IS NULL OR (last_chg_time IS NOT NULL AND last_chg_time > @fromTime)
END
GO

IF (object_id('pii_Notification_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Notification_hist]
GO

CREATE PROCEDURE [dbo].[pii_Notification_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT notification_uid, version_ctrl_nbr, txt
    FROM dbo.Notification_hist
    WHERE  @fromTime IS NULL OR (last_chg_time IS NOT NULL AND last_chg_time > @fromTime)
END
GO

IF (object_id('pii_Obs_value_coded') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_coded]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_coded]
@fromTime Datetime = null

AS
BEGIN
    SELECT observation_uid, code,
           display_name, original_txt
    FROM dbo.Obs_value_coded
END
GO

IF (object_id('pii_Obs_value_coded_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_coded_hist]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_coded_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT observation_uid, code,
           display_name, original_txt
    FROM dbo.Obs_value_coded_hist
END
GO

IF (object_id('pii_Obs_value_numeric') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_numeric]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_numeric]
@fromTime Datetime = null

AS
BEGIN
    SELECT observation_uid, obs_value_numeric_seq,
           numeric_value_1, numeric_value_2
    FROM dbo.Obs_value_numeric
END
GO

IF (object_id('pii_Obs_value_numeric_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_numeric_hist]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_numeric_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT observation_uid, obs_value_numeric_seq, version_ctrl_nbr,
           numeric_value_1, numeric_value_2
    FROM dbo.Obs_value_numeric_hist
END
GO

IF (object_id('pii_Obs_value_txt') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_txt]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_txt]
@fromTime Datetime = null

AS
BEGIN
    SELECT observation_uid, obs_value_txt_seq,
           value_txt
    FROM dbo.Obs_value_txt
END
GO

IF (object_id('pii_Obs_value_txt_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_txt_hist]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_txt_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT observation_uid, obs_value_txt_seq, version_ctrl_nbr,
           value_txt
    FROM dbo.Obs_value_txt_hist
END
GO

IF (object_id('pii_Observation') is not null)
    DROP PROCEDURE [dbo].[pii_Observation]
GO

CREATE PROCEDURE [dbo].[pii_Observation]
@fromTime Datetime = null

AS
BEGIN
    SELECT observation_uid,
           txt, rpt_to_state_time
    FROM dbo.Observation
    WHERE  @fromTime IS NULL OR
           (last_chg_time IS NOT NULL AND last_chg_time > @fromTime) OR
           (add_time IS NOT NULL AND add_time > @fromTime)
END
GO

IF (object_id('pii_Observation_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Observation_hist]
GO

CREATE PROCEDURE [dbo].[pii_Observation_hist]
@fromTime Datetime = null

AS
BEGIN
    SELECT observation_uid, version_ctrl_nbr,
           txt, rpt_to_state_time
    FROM dbo.Observation_hist
    WHERE  @fromTime IS NULL OR
           (last_chg_time IS NOT NULL AND last_chg_time > @fromTime) OR
           (add_time IS NOT NULL AND add_time > @fromTime)
END
GO

