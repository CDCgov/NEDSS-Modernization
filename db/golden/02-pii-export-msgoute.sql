use NBS_MSGOUTE
GO

IF (object_id('pii_MSG_ANSWER') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_ANSWER]
GO

CREATE PROCEDURE [dbo].[pii_MSG_ANSWER]
@lastUid bigint = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID,
           ANS_DISPLAY_TXT, ANSWER_TXT
    FROM dbo.MSG_ANSWER
    WHERE @lastUid IS NULL OR (MSG_CONTAINER_UID > @lastUid)
END
GO

IF (object_id('pii_MSG_CASE_INVESTIGATION') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_CASE_INVESTIGATION]
GO

CREATE PROCEDURE [dbo].[pii_MSG_CASE_INVESTIGATION]
@lastUid bigint = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID,
           INV_CLOSE_DT, INV_DIAGNOSIS_DT, INV_EFFECTIVE_TIME,
           INV_HOSPITALIZED_ADMIT_DT, INV_HOSPITALIZED_DISCHARGE_DT,
           INV_ILLNESS_ONSET_AGE, INV_INVESTIGATOR_ASSIGNED_DT, INV_PATIENT_DEATH_DT,
           INV_REPORT_DT, INV_REPORT_TO_COUNTY_DT, INV_REPORT_TO_STATE_DT,
           INV_START_DT
    FROM dbo.MSG_CASE_INVESTIGATION
    WHERE @lastUid IS NULL OR (MSG_CONTAINER_UID > @lastUid)
END
GO

IF (object_id('pii_MSG_CONTAINER') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_CONTAINER]
GO

CREATE PROCEDURE [dbo].[pii_MSG_CONTAINER]
@lastUid bigint = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID,
           EFFECTIVE_TIME, RECORD_STATUS_TIME
    FROM dbo.MSG_CONTAINER
    WHERE @lastUid IS NULL OR (MSG_CONTAINER_UID > @lastUid)
END
GO

IF (object_id('pii_MSG_INTERVIEW') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_INTERVIEW]
GO

CREATE PROCEDURE [dbo].[pii_MSG_INTERVIEW]
@lastUid bigint = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID,
           IXS_EFFECTIVE_TIME, IXS_INTERVIEW_DT
    FROM dbo.MSG_INTERVIEW
    WHERE @lastUid IS NULL OR (MSG_CONTAINER_UID > @lastUid)
END
GO

IF (object_id('pii_MSG_ORGANIZATION') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_ORGANIZATION]
GO

CREATE PROCEDURE [dbo].[pii_MSG_ORGANIZATION]
@lastUid bigint = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID,
           ORG_EFFECTIVE_TIME, ORG_NAME_TXT, ORG_ADDR_CITY_TXT,
           ORG_ADDR_STREET_ADDR1_TXT, ORG_ADDR_STREET_ADDR2_TXT, ORG_ADDR_ZIP_CODE_TXT,
           ORG_EMAIL_ADDRESS_TXT, ORG_ID_CLIA_NBR_TXT, ORG_ID_FACILITY_IDENTIFIER_TXT,
           ORG_PHONE_NBR_TXT, ORG_URL_ADDRESS_TXT
    FROM dbo.MSG_ORGANIZATION
    WHERE @lastUid IS NULL OR (MSG_CONTAINER_UID > @lastUid)
END
GO

IF (object_id('pii_MSG_PATIENT') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_PATIENT]
GO

CREATE PROCEDURE [dbo].[pii_MSG_PATIENT]
@lastUid bigint = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID,
           PAT_ADDR_CITY_TXT, PAT_ADDR_CENSUS_TRACT_TXT,
           PAT_ADDR_STREET_ADDR1_TXT, PAT_ADDR_STREET_ADDR2_TXT, PAT_ADDR_ZIP_CODE_TXT,
           PAT_BIRTH_DT, PAT_CELL_PHONE_NBR_TXT, PAT_DECEASED_DT, PAT_EFFECTIVE_TIME,
           PAT_ID_MEDICAL_RECORD_NBR_TXT, PAT_ID_STATE_HIV_CASE_NBR_TXT, PAT_ID_SSN_TXT,
           PAT_EMAIL_ADDRESS_TXT, PAT_HOME_PHONE_NBR_TXT, PAT_NAME_ALIAS_TXT,
           PAT_NAME_FIRST_TXT, PAT_NAME_LAST_TXT, PAT_NAME_MIDDLE_TXT, PAT_PHONE_COMMENT_TXT,
           PAT_REPORTED_AGE, PAT_URL_ADDRESS_TXT, PAT_WORK_PHONE_NBR_TXT, PAT_WORK_PHONE_EXTENSION_TXT
    FROM dbo.MSG_PATIENT
    WHERE @lastUid IS NULL OR (MSG_CONTAINER_UID > @lastUid)
END
GO

IF (object_id('pii_MSG_PLACE') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_PLACE]
GO

CREATE PROCEDURE [dbo].[pii_MSG_PLACE]
@lastUid bigint = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID,
           PLA_EFFECTIVE_TIME, PLA_ADDR_CITY_TXT, PLA_ADDR_STREET_ADDR1_TXT, PLA_ADDR_STREET_ADDR2_TXT,
           PLA_ADDR_ZIP_CODE_TXT, PLA_CENSUS_TRACT_TXT, PLA_EMAIL_ADDRESS_TXT, PLA_NAME_TXT,
           PLA_PHONE_EXTENSION_TXT, PLA_PHONE_NBR_TXT, PLA_URL_ADDRESS_TXT
    FROM dbo.MSG_PLACE
    WHERE @lastUid IS NULL OR (MSG_CONTAINER_UID > @lastUid)
END
GO

IF (object_id('pii_MSG_PROVIDER') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_PROVIDER]
GO

CREATE PROCEDURE [dbo].[pii_MSG_PROVIDER]
@lastUid bigint = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID,
           PRV_ADDR_CITY_TXT, PRV_ADDR_STREET_ADDR1_TXT, PRV_ADDR_STREET_ADDR2_TXT, PRV_ADDR_ZIP_CODE_TXT,
           PRV_ID_ALT_ID_NBR_TXT, PRV_ID_QUICK_CODE_TXT, PRV_ID_NBR_TXT, PRV_ID_NPI_TXT, PRV_EFFECTIVE_TIME,
           PRV_EMAIL_ADDRESS_TXT, PRV_NAME_FIRST_TXT, PRV_NAME_LAST_TXT, PRV_NAME_MIDDLE_TXT,
           PRV_PHONE_EXTENSION_TXT, PRV_PHONE_NBR_TXT, PRV_URL_ADDRESS_TXT
    FROM dbo.MSG_PROVIDER
    WHERE @lastUid IS NULL OR (MSG_CONTAINER_UID > @lastUid)
END
GO

IF (object_id('pii_MSG_TREATMENT') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_TREATMENT]
GO

CREATE PROCEDURE [dbo].[pii_MSG_TREATMENT]
@lastUid bigint = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT MSG_CONTAINER_UID,
           TRT_CUSTOM_TREATMENT_TXT, TRT_EFFECTIVE_TIME, TRT_TREATMENT_DT
    FROM dbo.MSG_TREATMENT
    WHERE @lastUid IS NULL OR (MSG_CONTAINER_UID > @lastUid)
END
GO

IF (object_id('pii_MsgOut_activity_log') is not null)
    DROP PROCEDURE [dbo].[pii_MsgOut_activity_log]
GO

CREATE PROCEDURE [dbo].[pii_MsgOut_activity_log]
@fromTime Datetime = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT activity_log_uid, doc_nm, action_txt
    FROM dbo.MsgOut_activity_log
    WHERE @fromTime IS NULL OR
        (add_time IS NOT NULL AND add_time > @fromTime) OR
        (start_time IS NOT NULL AND start_time > @fromTime)
END
GO

IF (object_id('pii_MsgOut_activity_log_detail') is not null)
    DROP PROCEDURE [dbo].[pii_MsgOut_activity_log_detail]
GO

CREATE PROCEDURE [dbo].[pii_MsgOut_activity_log_detail]
@fromTime Datetime = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT msgout_activity_detail_log_uid, log_comment
    FROM dbo.MsgOut_activity_log_detail
    WHERE @fromTime IS NULL OR (start_time IS NOT NULL AND start_time > @fromTime)
END
GO

IF (object_id('pii_MsgOut_Receiving_facility') is not null)
    DROP PROCEDURE [dbo].[pii_MsgOut_Receiving_facility]
GO

CREATE PROCEDURE [dbo].[pii_MsgOut_Receiving_facility]
@fromTime Datetime = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT receiving_facility_entity_uid, receiving_facility_nm
    FROM dbo.MsgOut_Receiving_facility
    WHERE @fromTime IS NULL OR (status_time IS NOT NULL AND status_time > @fromTime)
END
GO

IF (object_id('pii_MsgOut_Sending_facility') is not null)
    DROP PROCEDURE [dbo].[pii_MsgOut_Sending_facility]
GO

CREATE PROCEDURE [dbo].[pii_MsgOut_Sending_facility]
@fromTime Datetime = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT sending_facility_entity_uid, sending_facility_nm
    FROM dbo.MsgOut_Sending_facility
    WHERE @fromTime IS NULL OR (status_time IS NOT NULL AND status_time > @fromTime)
END
GO

IF (object_id('pii_NBS_interface') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_interface]
GO

CREATE PROCEDURE [dbo].[pii_NBS_interface]
@fromTime Datetime = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT nbs_interface_uid, lab_clia, specimen_coll_date
    FROM dbo.NBS_interface
    WHERE @fromTime IS NULL OR add_time > @fromTime
END
GO

IF (object_id('pii_NETSS_TransportQ_out') is not null)
    DROP PROCEDURE [dbo].[pii_NETSS_TransportQ_out]
GO

CREATE PROCEDURE [dbo].[pii_NETSS_TransportQ_out]
@fromTime Datetime = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT NETSS_TransportQ_out_uid, payload
    FROM dbo.NETSS_TransportQ_out
    WHERE @fromTime IS NULL OR (add_time IS NOT NULL AND add_time > @fromTime)
END
GO

IF (object_id('pii_PSF_CLIENT') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_CLIENT]
GO

CREATE PROCEDURE [dbo].[pii_PSF_CLIENT]
@fromTime Datetime = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT psfClientUid, clientFirstName, clientLastName, clientDOB, birthYear
    FROM dbo.PSF_CLIENT
    WHERE @fromTime IS NULL OR (lastModifiedDate IS NOT NULL AND lastModifiedDate > @fromTime)
END
GO

IF (object_id('pii_PSF_INDEX') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_INDEX]
GO

CREATE PROCEDURE [dbo].[pii_PSF_INDEX]
@fromTime Datetime = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT psfIndexUid, clientFirstName, clientLastName, clientDOB,
           caseOpenDate, caseCloseDate
    FROM dbo.PSF_INDEX
    WHERE @fromTime IS NULL OR (indexLastChgDt IS NOT NULL AND indexLastChgDt > @fromTime)
END
GO

IF (object_id('pii_PSF_PARTNER') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_PARTNER]
GO

CREATE PROCEDURE [dbo].[pii_PSF_PARTNER]
@fromTime Datetime = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT psfPartnerUid, clientFirstName, clientLastName, clientDOB,
           sampleDate, firstMedicalCareAppointmentDate
    FROM dbo.PSF_PARTNER
    WHERE @fromTime IS NULL OR
        (crAddTime IS NOT NULL AND crAddTime > @fromTime) OR
        (crLastChgTime IS NOT NULL AND crLastChgTime > @fromTime)
END
GO

IF (object_id('pii_PSF_RISK') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_RISK]
GO

CREATE PROCEDURE [dbo].[pii_PSF_RISK]
@fromTime Datetime = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT psfRiskUid, clientFirstName, clientLastName, clientDOB
    FROM dbo.PSF_RISK
    WHERE @fromTime IS NULL OR
        (dateCollectedForRiskProfile IS NOT NULL AND dateCollectedForRiskProfile > @fromTime)
END
GO

IF (object_id('pii_PSF_SESSION') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_SESSION]
GO

CREATE PROCEDURE [dbo].[pii_PSF_SESSION]
@fromTime Datetime = null

AS
BEGIN
    SET NOCOUNT ON
    SELECT psfSessionUid, clientFirstName, clientLastName, clientDOB
    FROM dbo.PSF_SESSION
    WHERE @fromTime IS NULL OR (sessionDate IS NOT NULL AND sessionDate > @fromTime)
END
GO
