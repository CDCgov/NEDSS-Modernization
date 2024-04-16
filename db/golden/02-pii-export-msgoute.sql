use NBS_MSGOUTE
GO

IF (object_id('pii_MSG_ANSWER') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_ANSWER]
GO

CREATE PROCEDURE [dbo].[pii_MSG_ANSWER]
AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID,
           ANS_DISPLAY_TXT, ANSWER_TXT
    FROM dbo.MSG_ANSWER
END
GO

IF (object_id('pii_MSG_CASE_INVESTIGATION') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_CASE_INVESTIGATION]
GO

CREATE PROCEDURE [dbo].[pii_MSG_CASE_INVESTIGATION]
AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID, INV_LOCAL_ID, PAT_LOCAL_ID,
           INV_CLOSE_DT, INV_DIAGNOSIS_DT, INV_EFFECTIVE_TIME,
           INV_ILLNESS_ONSET_AGE, INV_INVESTIGATOR_ASSIGNED_DT,
           INV_IMPORT_CITY_TXT, INV_PATIENT_DEATH_DT,
           INV_REPORT_DT, INV_REPORT_TO_COUNTY_DT, INV_REPORT_TO_STATE_DT,
           INV_START_DT
    FROM dbo.MSG_CASE_INVESTIGATION
END
GO

IF (object_id('pii_MSG_INTERVIEW') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_INTERVIEW]
GO

CREATE PROCEDURE [dbo].[pii_MSG_INTERVIEW]
AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID, IXS_LOCAL_ID, IXS_INTERVIEWEE_ID,
           IXS_EFFECTIVE_TIME, IXS_INTERVIEW_DT
    FROM dbo.MSG_INTERVIEW
    WHERE IXS_EFFECTIVE_TIME IS NOT NULL OR IXS_INTERVIEW_DT IS NOT NULL
END
GO

IF (object_id('pii_MSG_ORGANIZATION') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_ORGANIZATION]
GO

CREATE PROCEDURE [dbo].[pii_MSG_ORGANIZATION]
AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID, ORG_LOCAL_ID,
           ORG_NAME_TXT, ORG_ADDR_CITY_TXT,
           ORG_ADDR_STREET_ADDR1_TXT, ORG_ADDR_STREET_ADDR2_TXT, ORG_ADDR_ZIP_CODE_TXT,
           ORG_EMAIL_ADDRESS_TXT, ORG_ID_CLIA_NBR_TXT, ORG_ID_FACILITY_IDENTIFIER_TXT,
           ORG_PHONE_NBR_TXT
    FROM dbo.MSG_ORGANIZATION
END
GO

IF (object_id('pii_MSG_PATIENT') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_PATIENT]
GO

CREATE PROCEDURE [dbo].[pii_MSG_PATIENT]
AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID, PAT_LOCAL_ID,
           PAT_ADDR_CITY_TXT, PAT_ADDR_STREET_ADDR1_TXT, PAT_ADDR_STREET_ADDR2_TXT, PAT_ADDR_ZIP_CODE_TXT,
           PAT_BIRTH_DT, PAT_CELL_PHONE_NBR_TXT, PAT_DECEASED_DT, PAT_EFFECTIVE_TIME,
           PAT_ID_MEDICAL_RECORD_NBR_TXT, PAT_ID_STATE_HIV_CASE_NBR_TXT, PAT_ID_SSN_TXT,
           PAT_EMAIL_ADDRESS_TXT, PAT_HOME_PHONE_NBR_TXT, PAT_NAME_ALIAS_TXT,
           PAT_NAME_FIRST_TXT, PAT_NAME_LAST_TXT, PAT_NAME_MIDDLE_TXT, PAT_PHONE_COMMENT_TXT,
           PAT_REPORTED_AGE, PAT_WORK_PHONE_NBR_TXT
    FROM dbo.MSG_PATIENT
END
GO

IF (object_id('pii_MSG_PLACE') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_PLACE]
GO

CREATE PROCEDURE [dbo].[pii_MSG_PLACE]
AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID, PLA_LOCAL_ID,
           PLA_ADDR_CITY_TXT, PLA_ADDR_STREET_ADDR1_TXT, PLA_ADDR_STREET_ADDR2_TXT,
           PLA_ADDR_ZIP_CODE_TXT, PLA_EMAIL_ADDRESS_TXT, PLA_NAME_TXT, PLA_PHONE_NBR_TXT
    FROM dbo.MSG_PLACE
END
GO

IF (object_id('pii_MSG_PROVIDER') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_PROVIDER]
GO

CREATE PROCEDURE [dbo].[pii_MSG_PROVIDER]
AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID, PRV_LOCAL_ID,
           PRV_ADDR_CITY_TXT, PRV_ADDR_STREET_ADDR1_TXT, PRV_ADDR_STREET_ADDR2_TXT, PRV_ADDR_ZIP_CODE_TXT,
           PRV_ID_ALT_ID_NBR_TXT, PRV_ID_QUICK_CODE_TXT, PRV_ID_NBR_TXT, PRV_ID_NPI_TXT,
           PRV_EMAIL_ADDRESS_TXT, PRV_NAME_FIRST_TXT, PRV_NAME_LAST_TXT, PRV_NAME_MIDDLE_TXT,
           PRV_PHONE_NBR_TXT
    FROM dbo.MSG_PROVIDER
END
GO

IF (object_id('pii_MSG_TREATMENT') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_TREATMENT]
GO

CREATE PROCEDURE [dbo].[pii_MSG_TREATMENT]
AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID, TRT_LOCAL_ID,
           TRT_CUSTOM_TREATMENT_TXT, TRT_EFFECTIVE_TIME, TRT_TREATMENT_DT
    FROM dbo.MSG_TREATMENT
END
GO

IF (object_id('pii_MsgOut_activity_log') is not null)
    DROP PROCEDURE [dbo].[pii_MsgOut_activity_log]
GO

CREATE PROCEDURE [dbo].[pii_MsgOut_activity_log]
AS
BEGIN
    SET NOCOUNT ON
    SELECT activity_log_uid, doc_nm
    FROM dbo.MsgOut_activity_log
    WHERE doc_nm IS NOT NULL OR message_txt IS NOT NULL
END
GO

IF (object_id('pii_MsgOut_activity_log_detail') is not null)
    DROP PROCEDURE [dbo].[pii_MsgOut_activity_log_detail]
GO

CREATE PROCEDURE [dbo].[pii_MsgOut_activity_log_detail]
AS
BEGIN
    SET NOCOUNT ON
    SELECT msgout_activity_detail_log_uid, log_comment
    FROM dbo.MsgOut_activity_log_detail
    WHERE log_comment IS NOT NULL
END
GO

IF (object_id('pii_MsgOut_Message') is not null)
    DROP PROCEDURE [dbo].[pii_MsgOut_Message]
GO

CREATE PROCEDURE [dbo].[pii_MsgOut_Message]
AS
BEGIN
    SET NOCOUNT ON
    SELECT message_uid
    FROM dbo.MsgOut_Message
END
GO

IF (object_id('pii_MsgOut_Receiving_facility') is not null)
    DROP PROCEDURE [dbo].[pii_MsgOut_Receiving_facility]
GO

CREATE PROCEDURE [dbo].[pii_MsgOut_Receiving_facility]
AS
BEGIN
    SET NOCOUNT ON
    SELECT receiving_facility_entity_uid, receiving_facility_nm
    FROM dbo.MsgOut_Receiving_facility
    WHERE receiving_facility_nm IS NOT NULL
END
GO

IF (object_id('pii_MsgOut_Sending_facility') is not null)
    DROP PROCEDURE [dbo].[pii_MsgOut_Sending_facility]
GO

CREATE PROCEDURE [dbo].[pii_MsgOut_Sending_facility]
AS
BEGIN
    SET NOCOUNT ON
    SELECT sending_facility_entity_uid, sending_facility_nm
    FROM dbo.MsgOut_Sending_facility
    WHERE sending_facility_nm IS NOT NULL
END
GO

IF (object_id('pii_NBS_interface') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_interface]
GO

CREATE PROCEDURE [dbo].[pii_NBS_interface]
AS
BEGIN
    SET NOCOUNT ON
    SELECT nbs_interface_uid, lab_clia, specimen_coll_date
    FROM dbo.NBS_interface
END
GO

IF (object_id('pii_NETSS_TransportQ_out') is not null)
    DROP PROCEDURE [dbo].[pii_NETSS_TransportQ_out]
GO

CREATE PROCEDURE [dbo].[pii_NETSS_TransportQ_out]
AS
BEGIN
    SET NOCOUNT ON
    SELECT NETSS_TransportQ_out_uid, payload
    FROM dbo.NETSS_TransportQ_out
END
GO

IF (object_id('pii_PSF_CLIENT') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_CLIENT]
GO

CREATE PROCEDURE [dbo].[pii_PSF_CLIENT]
AS
BEGIN
    SET NOCOUNT ON
    SELECT psfClientUid,
           clientFirstName, clientLastName, clientDOB, birthYear
    FROM dbo.PSF_CLIENT
END
GO

IF (object_id('pii_PSF_INDEX') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_INDEX]
GO

CREATE PROCEDURE [dbo].[pii_PSF_INDEX]
AS
BEGIN
    SET NOCOUNT ON
    SELECT psfIndexUid,
           clientFirstName, clientLastName, clientDOB,
           indexDateDemographicsCollected
    FROM dbo.PSF_INDEX
END
GO

IF (object_id('pii_PSF_PARTNER') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_PARTNER]
GO

CREATE PROCEDURE [dbo].[pii_PSF_PARTNER]
AS
BEGIN
    SET NOCOUNT ON
    SELECT psfPartnerUid,
           clientFirstName, clientLastName, clientDOB,
           sampleDate, firstMedicalCareAppointmentDate
    FROM dbo.PSF_PARTNER
END
GO

IF (object_id('pii_PSF_RISK') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_RISK]
GO

CREATE PROCEDURE [dbo].[pii_PSF_RISK]
AS
BEGIN
    SET NOCOUNT ON
    SELECT psfRiskUid,
           clientFirstName, clientLastName, clientDOB,
           dateCollectedForRiskProfile
    FROM dbo.PSF_RISK
END
GO

IF (object_id('pii_PSF_SESSION') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_SESSION]
GO

CREATE PROCEDURE [dbo].[pii_PSF_SESSION]
AS
BEGIN
    SET NOCOUNT ON
    SELECT psfSessionUid,
           clientFirstName, clientLastName, clientDOB,
           sessionDate
    FROM dbo.PSF_SESSION
END
GO

IF (object_id('pii_TransportQ_out') is not null)
    DROP PROCEDURE [dbo].[pii_TransportQ_out]
GO

CREATE PROCEDURE [dbo].[pii_TransportQ_out]
AS
BEGIN
    SET NOCOUNT ON
    SELECT recordId
    FROM dbo.TransportQ_out
    WHERE payloadContent IS NOT NULL
END
GO