use NBS_ODSE
GO

IF (object_id('pii_Activity_log') is not null)
    DROP PROCEDURE [dbo].[pii_Activity_log]
GO

CREATE PROCEDURE [dbo].[pii_Activity_log]
AS
BEGIN
    SET NOCOUNT ON
    SELECT activity_log_uid, doc_nm, message_txt
    FROM dbo.Activity_log
    WHERE doc_nm IS NOT NULL OR message_txt IS NOT NULL
END
GO

IF (object_id('pii_Auth_user') is not null)
    DROP PROCEDURE [dbo].[pii_Auth_user]
GO

CREATE PROCEDURE [dbo].[pii_Auth_user]
AS
BEGIN
    SET NOCOUNT ON
    SELECT auth_user_uid, user_first_nm, user_last_nm
    FROM dbo.Auth_user
    WHERE user_first_nm IS NOT NULL OR user_last_nm IS NOT NULL
END
GO

IF (object_id('pii_case_management') is not null)
    DROP PROCEDURE [dbo].[pii_case_management]
GO

CREATE PROCEDURE [dbo].[pii_case_management]
AS
BEGIN
    SET NOCOUNT ON
    SELECT case_management_uid,
           fld_foll_up_dispo_date, fld_foll_up_exam_date, fld_foll_up_expected_date, init_foll_up_closed_date,
           ooj_agency, ooj_due_date, ooj_number, subj_oth_idntfyng_info, surv_closed_date,
           ooj_initg_agncy_outc_due_date, ooj_initg_agncy_outc_snt_date, ooj_initg_agncy_recd_date,
           surv_assigned_date, foll_up_assigned_date, init_foll_up_assigned_date, interview_assigned_date,
           init_interview_assigned_date, case_closed_date, case_review_status_date
    FROM dbo.case_management
END
GO

IF (object_id('pii_case_management_hist') is not null)
    DROP PROCEDURE [dbo].[pii_case_management_hist]
GO

CREATE PROCEDURE [dbo].[pii_case_management_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT case_management_hist_uid,
           fld_foll_up_dispo_date, fld_foll_up_exam_date, fld_foll_up_expected_date, init_foll_up_closed_date,
           ooj_agency, ooj_due_date, ooj_number, subj_oth_idntfyng_info, surv_closed_date,
           ooj_initg_agncy_outc_due_date, ooj_initg_agncy_outc_snt_date, ooj_initg_agncy_recd_date,
           surv_assigned_date, foll_up_assigned_date, init_foll_up_assigned_date, interview_assigned_date,
           init_interview_assigned_date, case_closed_date, case_review_status_date
    FROM dbo.case_management_hist
END
GO

IF (object_id('pii_CDF_subform_import_log') is not null)
    DROP PROCEDURE [dbo].[pii_CDF_subform_import_log]
GO

CREATE PROCEDURE [dbo].[pii_CDF_subform_import_log]
AS
BEGIN
    SET NOCOUNT ON
    SELECT import_log_uid, admin_comment
    FROM dbo.CDF_subform_import_log
    WHERE admin_comment IS NOT NULL
END
GO

IF (object_id('pii_CN_transportq_out') is not null)
    DROP PROCEDURE [dbo].[pii_CN_transportq_out]
GO

CREATE PROCEDURE [dbo].[pii_CN_transportq_out]
AS
BEGIN
    SET NOCOUNT ON
    SELECT cn_transportq_out_uid
    FROM dbo.CN_transportq_out
    WHERE message_payload IS NOT NULL
END
GO

IF (object_id('pii_Confirmation_method') is not null)
    DROP PROCEDURE [dbo].[pii_Confirmation_method]
GO

CREATE PROCEDURE [dbo].[pii_Confirmation_method]
AS
BEGIN
    SET NOCOUNT ON
    SELECT public_health_case_uid, confirmation_method_cd,
           confirmation_method_time
    FROM dbo.Confirmation_method
    WHERE confirmation_method_time IS NOT NULL
END
GO

IF (object_id('pii_Confirmation_method_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Confirmation_method_hist]
GO

CREATE PROCEDURE [dbo].[pii_Confirmation_method_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT public_health_case_uid, confirmation_method_cd, version_ctrl_nbr,
           confirmation_method_time
    FROM dbo.Confirmation_method_hist
    WHERE confirmation_method_time IS NOT NULL
END
GO

IF (object_id('pii_CT_contact') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact]
AS
BEGIN
    SET NOCOUNT ON
    SELECT ct_contact_uid, investigator_assigned_date, disposition_date, named_on_date,
           txt, symptom_onset_date, symptom_txt, evaluation_date, evaluation_txt,
           treatment_start_date, treatment_end_date, treatment_txt
    FROM dbo.CT_contact
END
GO

IF (object_id('pii_CT_contact_hist') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_hist]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT ct_contact_hist_uid, investigator_assigned_date, disposition_date, named_on_date,
           txt, symptom_onset_date, symptom_txt, evaluation_date, evaluation_txt,
           treatment_start_date, treatment_end_date, treatment_txt
    FROM dbo.CT_contact_hist
END
GO

IF (object_id('pii_CT_contact_answer') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_answer]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_answer]
AS
BEGIN
    SET NOCOUNT ON
    SELECT ct_contact_answer_uid, answer_txt, answer_large_txt
    FROM dbo.CT_contact_answer
    WHERE answer_txt IS NOT NULL OR answer_large_txt IS NOT NULL
END
GO

IF (object_id('pii_CT_contact_answer_hist') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_answer_hist]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_answer_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT ct_contact_answer_hist_uid, answer_txt, answer_large_txt
    FROM dbo.CT_contact_answer_hist
    WHERE answer_txt IS NOT NULL OR answer_large_txt IS NOT NULL
END
GO

IF (object_id('pii_CT_contact_attachment') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_attachment]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_attachment]
AS
BEGIN
    SET NOCOUNT ON
    SELECT ct_contact_attachment_uid, desc_txt, file_nm_txt
    FROM dbo.CT_contact_attachment
END
GO

IF (object_id('pii_CT_contact_note') is not null)
    DROP PROCEDURE [dbo].[pii_CT_contact_note]
GO

CREATE PROCEDURE [dbo].[pii_CT_contact_note]
AS
BEGIN
    SET NOCOUNT ON
    SELECT ct_contact_note_uid, note
    FROM dbo.CT_contact_note
END
GO

IF (object_id('pii_Custom_queues') is not null)
    DROP PROCEDURE [dbo].[pii_Custom_queues]
GO

CREATE PROCEDURE [dbo].[pii_Custom_queues]
AS
BEGIN
    SET NOCOUNT ON
    SELECT queue_name, description
    FROM dbo.Custom_queues
END
GO

IF (object_id('pii_Data_migration_record') is not null)
    DROP PROCEDURE [dbo].[pii_Data_migration_record]
GO

CREATE PROCEDURE [dbo].[pii_Data_migration_record]
AS
BEGIN
    SET NOCOUNT ON
    SELECT data_migration_record_uid, data_migration_batch_uid, sub_nm
    FROM dbo.Data_migration_record
    WHERE sub_nm IS NOT NULL OR failed_record_txt IS NOT NULL
END
GO

IF (object_id('pii_dsm_algorithm') is not null)
    DROP PROCEDURE [dbo].[pii_dsm_algorithm]
GO

CREATE PROCEDURE [dbo].[pii_dsm_algorithm]
AS
BEGIN
    SET NOCOUNT ON
    SELECT dsm_algorithm_uid, admin_comment
    FROM dbo.dsm_algorithm
    WHERE admin_comment IS NOT NULL
END
GO

IF (object_id('pii_dsm_algorithm_hist') is not null)
    DROP PROCEDURE [dbo].[pii_dsm_algorithm_hist]
GO

CREATE PROCEDURE [dbo].[pii_dsm_algorithm_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT dsm_algorithm_hist_uid, admin_comment
    FROM dbo.dsm_algorithm_hist
    WHERE admin_comment IS NOT NULL
END
GO

IF (object_id('pii_EDX_activity_detail_log') is not null)
    DROP PROCEDURE [dbo].[pii_EDX_activity_detail_log]
GO

CREATE PROCEDURE [dbo].[pii_EDX_activity_detail_log]
AS
BEGIN
    SET NOCOUNT ON
    SELECT edx_activity_detail_log_uid, log_comment
    FROM dbo.EDX_activity_detail_log Eadl
    JOIN dbo.EDX_activity_log Eal ON Eal.edx_activity_log_uid = Eadl.edx_activity_log_uid
    WHERE log_comment IS NOT NULL
END
GO

IF (object_id('pii_EDX_activity_log') is not null)
    DROP PROCEDURE [dbo].[pii_EDX_activity_log]
GO

CREATE PROCEDURE [dbo].[pii_EDX_activity_log]
AS
BEGIN
    SET NOCOUNT ON
    SELECT edx_activity_log_uid, Entity_nm
    FROM dbo.EDX_activity_log
    WHERE Entity_nm IS NOT NULL
END
GO

IF (object_id('pii_EDX_Document') is not null)
    DROP PROCEDURE [dbo].[pii_EDX_Document]
GO

CREATE PROCEDURE [dbo].[pii_EDX_Document]
AS
BEGIN
    SET NOCOUNT ON
    SELECT EDX_Document_uid, original_payload
    FROM dbo.EDX_Document
END
GO

IF (object_id('pii_ELR_activity_log') is not null)
    DROP PROCEDURE [dbo].[pii_ELR_activity_log]
GO

CREATE PROCEDURE [dbo].[pii_ELR_activity_log]
AS
BEGIN
    SET NOCOUNT ON
    SELECT msg_observation_uid, elr_activity_log_seq,
           subject_nm, report_fac_nm, detail_txt
    FROM dbo.ELR_activity_log
END
GO

IF (object_id('pii_ELRWorkerQueue') is not null)
    DROP PROCEDURE [dbo].[pii_ELRWorkerQueue]
GO

CREATE PROCEDURE [dbo].[pii_ELRWorkerQueue]
AS
BEGIN
    SET NOCOUNT ON
    SELECT recordId, payloadName, errorMessage
    FROM dbo.ELRWorkerQueue
END
GO

IF (object_id('pii_Export_receiving_facility') is not null)
    DROP PROCEDURE [dbo].[pii_Export_receiving_facility]
GO

CREATE PROCEDURE [dbo].[pii_Export_receiving_facility]
AS
BEGIN
    SET NOCOUNT ON
    SELECT export_receiving_facility_uid, admin_comment
    FROM dbo.Export_receiving_facility
    WHERE admin_comment IS NOT NULL
END
GO

IF (object_id('pii_Intervention') is not null)
    DROP PROCEDURE [dbo].[pii_Intervention]
GO

CREATE PROCEDURE [dbo].[pii_Intervention]
AS
BEGIN
    SET NOCOUNT ON
    SELECT intervention_uid,
           txt, age_at_vacc, material_lot_nm, material_expiration_time
    FROM dbo.Intervention
END
GO

IF (object_id('pii_Intervention_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Intervention_hist]
GO

CREATE PROCEDURE [dbo].[pii_Intervention_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT intervention_uid, version_ctrl_nbr,
           txt, age_at_vacc, material_lot_nm, material_expiration_time
    FROM dbo.Intervention_hist
END
GO

IF (object_id('pii_Interview') is not null)
    DROP PROCEDURE [dbo].[pii_Interview]
GO

CREATE PROCEDURE [dbo].[pii_Interview]
AS
BEGIN
    SET NOCOUNT ON
    SELECT interview_uid, interview_date
    FROM dbo.Interview
    WHERE interview_date IS NOT NULL
END
GO

IF (object_id('pii_Interview_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Interview_hist]
GO

CREATE PROCEDURE [dbo].[pii_Interview_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT interview_hist_uid, interview_date
    FROM dbo.Interview_hist
    WHERE interview_date IS NOT NULL
END
GO

IF (object_id('pii_lab_event') is not null)
    DROP PROCEDURE [dbo].[pii_lab_event]
GO

CREATE PROCEDURE [dbo].[pii_lab_event]
AS
BEGIN
    SET NOCOUNT ON
    SELECT lab_event_uid,
           result_rpt_dt, specimen_analyzed_dt, specimen_collection_dt,
           lab_result_comments, suscep_lab_result_comments,
           suscep_specimen_collection_dt, suscep_result_rpt_dt

    FROM dbo.lab_event
END
GO

IF (object_id('pii_lab_event_hist') is not null)
    DROP PROCEDURE [dbo].[pii_lab_event_hist]
GO

CREATE PROCEDURE [dbo].[pii_lab_event_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT lab_event_hist_uid,
           result_rpt_dt, specimen_analyzed_dt, specimen_collection_dt,
           lab_result_comments, suscep_lab_result_comments,
           suscep_specimen_collection_dt, suscep_result_rpt_dt
    FROM dbo.lab_event_hist
END
GO

IF (object_id('pii_Manufactured_material') is not null)
    DROP PROCEDURE [dbo].[pii_Manufactured_material]
GO

CREATE PROCEDURE [dbo].[pii_Manufactured_material]
AS
BEGIN
    SET NOCOUNT ON
    SELECT material_uid, manufactured_material_seq,
           expiration_time, lot_nm
    FROM dbo.Manufactured_material
    WHERE expiration_time IS NOT NULL OR lot_nm IS NOT NULL
END
GO

IF (object_id('pii_Manufactured_material_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Manufactured_material_hist]
GO

CREATE PROCEDURE [dbo].[pii_Manufactured_material_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT material_uid, manufactured_material_seq, version_ctrl_nbr,
           expiration_time, lot_nm
    FROM dbo.Manufactured_material_hist
    WHERE expiration_time IS NOT NULL OR lot_nm IS NOT NULL
END
GO

IF (object_id('pii_nbs_answer') is not null)
    DROP PROCEDURE [dbo].[pii_nbs_answer]
GO

CREATE PROCEDURE [dbo].[pii_nbs_answer]
AS
BEGIN
    SET NOCOUNT ON
    SELECT nbs_answer_uid, answer_txt
    FROM dbo.nbs_answer
    WHERE answer_txt IS NOT NULL AND nbs_question_uid IN (
        SELECT nbs_question_uid FROM dbo.NBS_ui_metadata
        WHERE nbs_ui_component_uid IN (1009, 1014, 1019, 1026, 1029))
END
GO

IF (object_id('pii_nbs_answer_hist') is not null)
    DROP PROCEDURE [dbo].[pii_nbs_answer_hist]
GO

CREATE PROCEDURE [dbo].[pii_nbs_answer_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT nbs_answer_hist_uid, answer_txt
    FROM dbo.nbs_answer_hist ans
    WHERE answer_txt IS NOT NULL AND nbs_question_uid IN (
        SELECT nbs_question_uid FROM dbo.NBS_ui_metadata
        WHERE nbs_ui_component_uid IN (1009, 1014, 1019, 1026, 1029))
END
GO

IF (object_id('pii_NBS_attachment') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_attachment]
GO

CREATE PROCEDURE [dbo].[pii_NBS_attachment]
AS
BEGIN
    SET NOCOUNT ON
    SELECT nbs_attachment_uid, desc_txt
    FROM dbo.NBS_attachment
    WHERE attachment IS NOT NULL OR desc_txt IS NOT NULL
END
GO

IF (object_id('pii_NBS_case_answer') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_case_answer]
GO

CREATE PROCEDURE [dbo].[pii_NBS_case_answer]
AS
BEGIN
    SET NOCOUNT ON
    SELECT nbs_case_answer_uid, answer_txt
    FROM dbo.NBS_case_answer ans
    WHERE answer_txt IS NOT NULL AND nbs_question_uid IN (
        SELECT nbs_question_uid FROM dbo.NBS_ui_metadata
        WHERE nbs_ui_component_uid IN (1009, 1014, 1019, 1026, 1029))
END
GO

IF (object_id('pii_NBS_case_answer_hist') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_case_answer_hist]
GO

CREATE PROCEDURE [dbo].[pii_NBS_case_answer_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT nbs_case_answer_hist_uid, answer_txt
    FROM dbo.NBS_case_answer_hist ans
    WHERE answer_txt IS NOT NULL AND nbs_question_uid IN (
        SELECT nbs_question_uid FROM dbo.NBS_ui_metadata
        WHERE nbs_ui_component_uid IN (1009, 1014, 1019, 1026, 1029))
END
GO

IF (object_id('pii_NBS_document') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_document]
GO

CREATE PROCEDURE [dbo].[pii_NBS_document]
AS
BEGIN
    SET NOCOUNT ON
    SELECT nbs_document_uid, txt, sending_facility_nm
    FROM dbo.NBS_document
END
GO

IF (object_id('pii_NBS_document_hist') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_document_hist]
GO

CREATE PROCEDURE [dbo].[pii_NBS_document_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT nbs_document_hist_uid, txt, sending_facility_nm
    FROM dbo.NBS_document_hist
END
GO

IF (object_id('pii_NBS_note') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_note]
GO

CREATE PROCEDURE [dbo].[pii_NBS_note]
AS
BEGIN
    SET NOCOUNT ON
    SELECT nbs_note_uid, note
    FROM dbo.NBS_note
END
GO

IF (object_id('pii_Notification') is not null)
    DROP PROCEDURE [dbo].[pii_Notification]
GO

CREATE PROCEDURE [dbo].[pii_Notification]
AS
BEGIN
    SET NOCOUNT ON
    SELECT notification_uid, txt
    FROM dbo.Notification
    WHERE message_txt IS NOT NULL OR txt IS NOT NULL
END
GO

IF (object_id('pii_Notification_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Notification_hist]
GO

CREATE PROCEDURE [dbo].[pii_Notification_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT notification_uid, version_ctrl_nbr, txt
    FROM dbo.Notification_hist
    WHERE message_txt IS NOT NULL OR txt IS NOT NULL
END
GO

IF (object_id('pii_Obs_value_coded') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_coded]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_coded]
AS
BEGIN
    SET NOCOUNT ON
    SELECT observation_uid, code,
           display_name, original_txt
    FROM dbo.Obs_value_coded
END
GO

IF (object_id('pii_Obs_value_coded_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_coded_hist]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_coded_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT observation_uid, code, version_ctrl_nbr,
           display_name, original_txt
    FROM dbo.Obs_value_coded_hist
END
GO

IF (object_id('pii_Obs_value_date') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_date]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_date]
AS
BEGIN
    SET NOCOUNT ON
    SELECT observation_uid, obs_value_date_seq
    FROM dbo.Obs_value_date
    WHERE from_time IS NOT NULL OR to_time IS NOT NULL
END
GO

IF (object_id('pii_Obs_value_date_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_date_hist]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_date_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT observation_uid, obs_value_date_seq, version_ctrl_nbr
    FROM dbo.Obs_value_date_hist
    WHERE from_time IS NOT NULL OR to_time IS NOT NULL
END
GO

IF (object_id('pii_Obs_value_txt') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_txt]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_txt]
AS
BEGIN
    SET NOCOUNT ON
    SELECT observation_uid, obs_value_txt_seq,
           value_txt
    FROM dbo.Obs_value_txt
END
GO

IF (object_id('pii_Obs_value_txt_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Obs_value_txt_hist]
GO

CREATE PROCEDURE [dbo].[pii_Obs_value_txt_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT observation_uid, obs_value_txt_seq, version_ctrl_nbr,
           value_txt
    FROM dbo.Obs_value_txt_hist
END
GO

IF (object_id('pii_Observation') is not null)
    DROP PROCEDURE [dbo].[pii_Observation]
GO

CREATE PROCEDURE [dbo].[pii_Observation]
AS
BEGIN
    SET NOCOUNT ON
    SELECT observation_uid,
           txt, rpt_to_state_time
    FROM dbo.Observation
END
GO

IF (object_id('pii_Observation_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Observation_hist]
GO

CREATE PROCEDURE [dbo].[pii_Observation_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT observation_uid, version_ctrl_nbr,
           txt, rpt_to_state_time
    FROM dbo.Observation_hist
END
GO

IF (object_id('pii_Organization') is not null)
    DROP PROCEDURE [dbo].[pii_Organization]
GO

CREATE PROCEDURE [dbo].[pii_Organization]
AS
BEGIN
    SET NOCOUNT ON
    SELECT organization_uid,
           description, display_nm, city_desc_txt, zip_cd
    FROM dbo.Organization
END
GO

IF (object_id('pii_Organization_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Organization_hist]
GO

CREATE PROCEDURE [dbo].[pii_Organization_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT organization_uid, version_ctrl_nbr,
           description, display_nm, city_desc_txt, zip_cd
    FROM dbo.Organization_hist
END
GO

IF (object_id('pii_Organization_name') is not null)
    DROP PROCEDURE [dbo].[pii_Organization_name]
GO

CREATE PROCEDURE [dbo].[pii_Organization_name]
AS
BEGIN
    SET NOCOUNT ON
    SELECT nm.organization_uid, organization_name_seq, nm_txt
    FROM dbo.Organization_name nm
    INNER JOIN dbo.Organization org 
        ON org.organization_uid = nm.organization_uid
    WHERE nm_txt IS NOT NULL
END
GO

IF (object_id('pii_Organization_name_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Organization_name_hist]
GO

CREATE PROCEDURE [dbo].[pii_Organization_name_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT nm.organization_uid, organization_name_seq, nm.version_ctrl_nbr,
           nm_txt
    FROM dbo.Organization_name_hist nm
    INNER JOIN dbo.Organization_hist org
        ON org.organization_uid = nm.organization_uid
    WHERE nm_txt IS NOT NULL
END
GO

IF (object_id('pii_Person') is not null)
    DROP PROCEDURE [dbo].[pii_Person]
GO

CREATE PROCEDURE [dbo].[pii_Person]
AS
BEGIN
    SET NOCOUNT ON
    SELECT person_uid,
           age_calc, age_calc_time, age_reported, age_reported_time,
           birth_time, birth_time_calc, deceased_time, description,
           mothers_maiden_nm, first_nm, last_nm, middle_nm, preferred_nm,
           hm_street_addr1, hm_street_addr2, hm_city_desc_txt,
           hm_zip_cd, hm_phone_nbr, hm_email_addr, cell_phone_nbr,
           wk_street_addr1, wk_street_addr2, wk_city_desc_txt,
           wk_zip_cd, wk_phone_nbr, wk_email_addr,
           SSN, medicaid_num, dl_num, birth_city_desc_txt,
           as_of_date_admin, as_of_date_ethnicity, as_of_date_general, as_of_date_morbidity, as_of_date_sex
    FROM dbo.Person
END
GO

IF (object_id('pii_Person_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Person_hist]
GO

CREATE PROCEDURE [dbo].[pii_Person_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT person_uid, version_ctrl_nbr,
           age_calc, age_calc_time, age_reported, age_reported_time,
           birth_time, birth_time_calc, deceased_time, description,
           mothers_maiden_nm, first_nm, last_nm, middle_nm, preferred_nm,
           hm_street_addr1, hm_street_addr2, hm_city_desc_txt,
           hm_zip_cd, hm_phone_nbr, hm_email_addr, cell_phone_nbr,
           wk_street_addr1, wk_street_addr2, wk_city_desc_txt,
           wk_zip_cd, wk_phone_nbr, wk_email_addr,
           SSN, medicaid_num, dl_num, birth_city_desc_txt,
           as_of_date_admin, as_of_date_ethnicity, as_of_date_general, as_of_date_morbidity, as_of_date_sex
    FROM dbo.Person_hist
END
GO

IF (object_id('pii_Person_name') is not null)
    DROP PROCEDURE [dbo].[pii_Person_name]
GO

CREATE PROCEDURE [dbo].[pii_Person_name]
AS
BEGIN
    SET NOCOUNT ON
    SELECT person_uid, person_name_seq,
           first_nm, first_nm_sndx, last_nm, last_nm_sndx,
           last_nm2, last_nm2_sndx, middle_nm, middle_nm2,
           as_of_date
    FROM dbo.Person_name
END
GO

IF (object_id('pii_Person_name_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Person_name_hist]
GO

CREATE PROCEDURE [dbo].[pii_Person_name_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT person_uid, person_name_seq, version_ctrl_nbr,
           first_nm, first_nm_sndx, last_nm, last_nm_sndx,
           last_nm2, last_nm2_sndx, middle_nm, middle_nm2,
           as_of_date
    FROM dbo.Person_name_hist
END
GO

IF (object_id('pii_Postal_locator') is not null)
    DROP PROCEDURE [dbo].[pii_Postal_locator]
GO

CREATE PROCEDURE [dbo].[pii_Postal_locator]
AS
BEGIN
    SET NOCOUNT ON
    SELECT postal_locator_uid,
           city_desc_txt, cnty_desc_txt, street_addr1, street_addr2, zip_cd
    FROM dbo.Postal_locator
END
GO

IF (object_id('pii_Postal_locator_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Postal_locator_hist]
GO

CREATE PROCEDURE [dbo].[pii_Postal_locator_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT postal_locator_uid, version_ctrl_nbr,
           city_desc_txt, cnty_desc_txt, street_addr1, street_addr2, zip_cd
    FROM dbo.Postal_locator_hist
END
GO

IF (object_id('pii_Public_health_case') is not null)
    DROP PROCEDURE [dbo].[pii_Public_health_case]
GO

CREATE PROCEDURE [dbo].[pii_Public_health_case]
AS
BEGIN
    SET NOCOUNT ON
    SELECT public_health_case_uid,
           diagnosis_time, pat_age_at_onset,
           rpt_form_cmplt_time, rpt_to_county_time, rpt_to_state_time,
           txt, investigator_assigned_time, imported_city_desc_txt,
           deceased_time, contact_inv_txt
    FROM dbo.Public_health_case
END
GO

IF (object_id('pii_Public_health_case_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Public_health_case_hist]
GO

CREATE PROCEDURE [dbo].[pii_Public_health_case_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT public_health_case_uid, version_ctrl_nbr,
           diagnosis_time, pat_age_at_onset,
           rpt_form_cmplt_time, rpt_to_county_time, rpt_to_state_time,
           txt, investigator_assigned_time, imported_city_desc_txt,
           deceased_time, contact_inv_txt
    FROM dbo.Public_health_case_hist
END
GO

IF (object_id('pii_PublicHealthCaseFact') is not null)
    DROP PROCEDURE [dbo].[pii_PublicHealthCaseFact]
GO

CREATE PROCEDURE [dbo].[pii_PublicHealthCaseFact]
AS
BEGIN
    SET NOCOUNT ON
    SELECT public_health_case_uid,
           ageInMonths, ageInYears, age_reported_time, age_reported, birth_time, birth_time_calc,
           cnty_code_desc_txt, city_desc_txt, confirmation_method_time, county,
           deceased_time, diagnosis_date, event_date,
           firstNotificationSenddate, firstNotificationdate, geoLatitude, geoLongitude,
           investigatorAssigneddate, investigatorName, lastNotificationdate, lastNotificationSenddate,
           mart_record_creation_date, mart_record_creation_time, notificationdate, onSetDate,
           organizationName, pat_age_at_onset, providerName, reporterName,
           rpt_form_cmplt_time, rpt_to_county_time, rpt_to_state_time, zip_cd,
           patientName, jurisdiction, investigationstartdate, report_date, sub_addr_as_of_date,
           rpt_cnty_desc_txt
    FROM dbo.PublicHealthCaseFact
END
GO

IF (object_id('pii_Referral') is not null)
    DROP PROCEDURE [dbo].[pii_Referral]
GO

CREATE PROCEDURE [dbo].[pii_Referral]
AS
BEGIN
    SET NOCOUNT ON
    SELECT referral_uid,
           reason_txt, referral_desc_txt, txt
    FROM dbo.Referral
END
GO

IF (object_id('pii_Referral_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Referral_hist]
GO

CREATE PROCEDURE [dbo].[pii_Referral_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT referral_uid, version_ctrl_nbr,
           reason_txt, referral_desc_txt, txt
    FROM dbo.Referral_hist
END
GO

IF (object_id('pii_SUSPCT_MEAT_OBTND_DATA') is not null)
    DROP PROCEDURE [dbo].[pii_SUSPCT_MEAT_OBTND_DATA]
GO

CREATE PROCEDURE [dbo].[pii_SUSPCT_MEAT_OBTND_DATA]
AS
BEGIN
    SET NOCOUNT ON
    SELECT business_object_uid, ldf_uid, version_ctrl_nbr, ldf_value
    FROM dbo.SUSPCT_MEAT_OBTND_DATA
    WHERE ldf_value IS NOT NULL
END
GO

IF (object_id('pii_Tele_locator') is not null)
    DROP PROCEDURE [dbo].[pii_Tele_locator]
GO

CREATE PROCEDURE [dbo].[pii_Tele_locator]
AS
BEGIN
    SET NOCOUNT ON
    SELECT tele_locator_uid, email_address, phone_nbr_txt
    FROM dbo.Tele_locator
    WHERE email_address IS NOT NULL OR phone_nbr_txt IS NOT NULL
END
GO

IF (object_id('pii_Tele_locator_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Tele_locator_hist]
GO

CREATE PROCEDURE [dbo].[pii_Tele_locator_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT tele_locator_uid, version_ctrl_nbr,
           email_address, phone_nbr_txt
    FROM dbo.Tele_locator_hist
    WHERE email_address IS NOT NULL OR phone_nbr_txt IS NOT NULL
END
GO

IF (object_id('pii_Treatment') is not null)
    DROP PROCEDURE [dbo].[pii_Treatment]
GO

CREATE PROCEDURE [dbo].[pii_Treatment]
AS
BEGIN
    SET NOCOUNT ON
    SELECT treatment_uid, txt
    FROM dbo.Treatment
    WHERE txt IS NOT NULL OR
          activity_from_time IS NOT NULL OR activity_to_time IS NOT NULL
END
GO

IF (object_id('pii_Treatment_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Treatment_hist]
GO

CREATE PROCEDURE [dbo].[pii_Treatment_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT treatment_uid, version_ctrl_nbr, txt
    FROM dbo.Treatment_hist
    WHERE txt IS NOT NULL OR
        activity_from_time IS NOT NULL OR activity_to_time IS NOT NULL
END
GO

IF (object_id('pii_Treatment_administered') is not null)
    DROP PROCEDURE [dbo].[pii_Treatment_administered]
GO

CREATE PROCEDURE [dbo].[pii_Treatment_administered]
AS
BEGIN
    SET NOCOUNT ON
    SELECT treatment_uid, treatment_administered_seq
    FROM dbo.Treatment_administered
    WHERE effective_from_time IS NOT NULL OR effective_to_time IS NOT NULL
END
GO

IF (object_id('pii_Treatment_administered_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Treatment_administered_hist]
GO

CREATE PROCEDURE [dbo].[pii_Treatment_administered_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT treatment_uid, treatment_administered_seq, version_ctrl_nbr
    FROM dbo.Treatment_administered_hist
    WHERE effective_from_time IS NOT NULL OR effective_to_time IS NOT NULL
END
GO

IF (object_id('pii_Treatment_procedure') is not null)
    DROP PROCEDURE [dbo].[pii_Treatment_procedure]
GO

CREATE PROCEDURE [dbo].[pii_Treatment_procedure]
AS
BEGIN
    SET NOCOUNT ON
    SELECT treatment_uid, treatment_procedure_seq
    FROM dbo.Treatment_procedure
    WHERE effective_from_time IS NOT NULL OR effective_to_time IS NOT NULL
END
GO

IF (object_id('pii_Treatment_procedure_hist') is not null)
    DROP PROCEDURE [dbo].[pii_Treatment_procedure_hist]
GO

CREATE PROCEDURE [dbo].[pii_Treatment_procedure_hist]
AS
BEGIN
    SET NOCOUNT ON
    SELECT treatment_uid, treatment_procedure_seq
    FROM dbo.Treatment_procedure_hist
    WHERE effective_from_time IS NOT NULL OR effective_to_time IS NOT NULL
END
GO

IF (object_id('pii_USER_PROFILE') is not null)
    DROP PROCEDURE [dbo].[pii_USER_PROFILE]
GO

CREATE PROCEDURE [dbo].[pii_USER_PROFILE]
AS
BEGIN
    SET NOCOUNT ON
    SELECT NEDSS_ENTRY_ID, FIRST_NM, LAST_NM
    FROM dbo.USER_PROFILE
END
GO

