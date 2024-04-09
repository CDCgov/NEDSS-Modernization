use NBS_MSGOUTE
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

IF (object_id('pii_MSG_ANSWER_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_ANSWER_Restore]
GO

CREATE PROCEDURE [dbo].[pii_MSG_ANSWER_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        disp_txt varchar(250),
        txt varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[MSG_ANSWER]
    SET ANS_DISPLAY_TXT = disp_txt,
        ANSWER_TXT = txt,
        ANSWER_LARGE_TXT = NULL,
        ANSWER_XML_TXT = NULL
    FROM [dbo].[MSG_ANSWER]
    INNER JOIN #tmp ON MSG_CONTAINER_UID = uid
END
GO

IF (object_id('pii_MSG_CASE_INVESTIGATION_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_CASE_INVESTIGATION_Restore]
GO

CREATE PROCEDURE [dbo].[pii_MSG_CASE_INVESTIGATION_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        iid varchar(100),
        pid varchar(100),
        close_dt datetime,
        diag_dt datetime,
        effect_time datetime,
        age int,
        inv_assign_dt datetime,
        import_cty varchar(100),
        pat_death_dt datetime,
        report_dt datetime,
        rpt_cnty_dt datetime,
        rpt_ste_dt datetime,
        start_dt datetime
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[MSG_CASE_INVESTIGATION]
    SET INV_CLOSE_DT = close_dt,
        INV_COMMENT_TXT = NULL,
        INV_CONTACT_INV_COMMENT_TXT = NULL,
        INV_DIAGNOSIS_DT = diag_dt,
        INV_EFFECTIVE_TIME = effect_time,
        INV_HOSPITALIZED_ADMIT_DT = CASE WHEN INV_HOSPITALIZED_ADMIT_DT IS NOT NULL THEN DATEADD(day, @dw, INV_HOSPITALIZED_ADMIT_DT) END,
        INV_HOSPITALIZED_DISCHARGE_DT = CASE WHEN INV_HOSPITALIZED_DISCHARGE_DT IS NOT NULL THEN DATEADD(day, @dw, INV_HOSPITALIZED_DISCHARGE_DT) END,
        INV_ILLNESS_START_DT = CASE WHEN INV_ILLNESS_START_DT IS NOT NULL THEN DATEADD(day, @dw, INV_ILLNESS_START_DT) END,
        INV_ILLNESS_END_DT = CASE WHEN INV_ILLNESS_END_DT IS NOT NULL THEN DATEADD(day, @dw, INV_ILLNESS_END_DT) END,
        INV_ILLNESS_ONSET_AGE = age,
        INV_INVESTIGATOR_ASSIGNED_DT = inv_assign_dt,
        INV_IMPORT_CITY_TXT = import_cty,
        INV_INFECTIOUS_FROM_DT = CASE WHEN INV_INFECTIOUS_FROM_DT IS NOT NULL THEN DATEADD(day, @dw, INV_INFECTIOUS_FROM_DT) END,
        INV_INFECTIOUS_TO_DT = CASE WHEN INV_INFECTIOUS_TO_DT IS NOT NULL THEN DATEADD(day, @dw, INV_INFECTIOUS_TO_DT) END,
        INV_PATIENT_DEATH_DT = pat_death_dt,
        INV_REPORT_DT = report_dt,
        INV_REPORT_TO_COUNTY_DT = rpt_cnty_dt,
        INV_REPORT_TO_STATE_DT = rpt_ste_dt,
        INV_START_DT = start_dt
    FROM [dbo].[MSG_CASE_INVESTIGATION]
    INNER JOIN #tmp ON MSG_CONTAINER_UID = uid AND INV_LOCAL_ID = iid AND PAT_LOCAL_ID = pid
END
GO

IF (object_id('pii_MSG_INTERVIEW_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_INTERVIEW_Restore]
GO

CREATE PROCEDURE [dbo].[pii_MSG_INTERVIEW_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        lid varchar(100),
        iid varchar(100),
        effect_time datetime,
        int_dt datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[MSG_INTERVIEW]
    SET IXS_EFFECTIVE_TIME = effect_time,
        IXS_INTERVIEW_DT = int_dt
    FROM [dbo].[MSG_INTERVIEW]
    INNER JOIN #tmp ON MSG_CONTAINER_UID = uid AND IXS_LOCAL_ID = lid AND IXS_INTERVIEWEE_ID = iid
END
GO

IF (object_id('pii_MSG_ORGANIZATION_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_ORGANIZATION_Restore]
GO

CREATE PROCEDURE [dbo].[pii_MSG_ORGANIZATION_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        lid varchar(100),
        name varchar(100),
        addr_city varchar(100),
        addr_st1 varchar(100),
        addr_st2 varchar(100),
        addr_zip varchar(10),
        email varchar(100),
        clia_nbr varchar(100),
        id_txt varchar(100),
        phone varchar(50)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[MSG_ORGANIZATION]
    SET ORG_NAME_TXT = name,
        ORG_ADDR_CITY_TXT = addr_city,
        ORG_ADDR_COMMENT_TXT = NULL,
        ORG_COMMENT_TXT = NULL,
        ORG_ADDR_STREET_ADDR1_TXT = addr_st1,
        ORG_ADDR_STREET_ADDR2_TXT = addr_st2,
        ORG_ADDR_ZIP_CODE_TXT = addr_zip,
        ORG_EMAIL_ADDRESS_TXT = email,
        ORG_ID_CLIA_NBR_TXT = clia_nbr,
        ORG_ID_FACILITY_IDENTIFIER_TXT = id_txt,
        ORG_PHONE_COMMENT_TXT = NULL,
        ORG_PHONE_NBR_TXT = phone
    FROM [dbo].[MSG_ORGANIZATION]
    INNER JOIN #tmp ON MSG_CONTAINER_UID = uid AND ORG_LOCAL_ID = lid
END
GO

IF (object_id('pii_MSG_PATIENT_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_PATIENT_Restore]
GO

CREATE PROCEDURE [dbo].[pii_MSG_PATIENT_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        lid varchar(100),
        addr_city varchar(100),
        addr_st1 varchar(100),
        addr_st2 varchar(100),
        addr_zip varchar(10),
        birth_dt datetime,
        cell_phone varchar(50),
        dec_dt datetime,
        effect_time datetime,
        med_rec varchar(100),
        hiv_case_nbr varchar(100),
        id_ssn varchar(11),
        email varchar(100),
        home_phone varchar(50),
        name_alias varchar(100),
        name_first varchar(100),
        name_last varchar(100),
        name_mid varchar(100),
        phone_comment varchar(100),
        rpt_age int,
        work_phone varchar(100)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[MSG_PATIENT]
    SET PAT_ADDR_CITY_TXT = addr_city,
        PAT_ADDR_COMMENT_TXT = NULL,
        PAT_ADDR_STREET_ADDR1_TXT = addr_st1,
        PAT_ADDR_STREET_ADDR2_TXT = addr_st2,
        PAT_ADDR_ZIP_CODE_TXT = addr_zip,
        PAT_BIRTH_DT = birth_dt,
        PAT_CELL_PHONE_NBR_TXT = cell_phone,
        PAT_COMMENT_TXT = NULL,
        PAT_DECEASED_DT = dec_dt,
        PAT_EFFECTIVE_TIME = effect_time,
        PAT_ID_MEDICAL_RECORD_NBR_TXT = med_rec,
        PAT_ID_STATE_HIV_CASE_NBR_TXT = hiv_case_nbr,
        PAT_ID_SSN_TXT = id_ssn,
        PAT_EMAIL_ADDRESS_TXT = email,
        PAT_HOME_PHONE_NBR_TXT = home_phone,
        PAT_NAME_ALIAS_TXT = name_alias,
        PAT_NAME_FIRST_TXT = name_first,
        PAT_NAME_LAST_TXT = name_last,
        PAT_NAME_MIDDLE_TXT = name_mid,
        PAT_PHONE_COMMENT_TXT = phone_comment,
        PAT_REPORTED_AGE = rpt_age,
        PAT_WORK_PHONE_NBR_TXT = work_phone
    FROM [dbo].[MSG_PATIENT]
    INNER JOIN #tmp ON MSG_CONTAINER_UID = uid AND PAT_LOCAL_ID = lid
END
GO

IF (object_id('pii_MSG_PLACE_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_PLACE_Restore]
GO

CREATE PROCEDURE [dbo].[pii_MSG_PLACE_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        lid varchar(100),
        addr_city varchar(100),
        addr_st1 varchar(100),
        addr_st2 varchar(100),
        addr_zip varchar(10),
        email varchar(100),
        name varchar(100),
        phone varchar(50)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[MSG_PLACE]
    SET PLA_ADDR_CITY_TXT = addr_city,
        PLA_ADDR_STREET_ADDR1_TXT = addr_st1,
        PLA_ADDR_STREET_ADDR2_TXT = addr_st2,
        PLA_ADDR_ZIP_CODE_TXT = addr_zip,
        PLA_ADDR_COMMENT_TXT = NULL,
        PLA_COMMENT_TXT = NULL,
        PLA_EMAIL_ADDRESS_TXT = email,
        PLA_NAME_TXT = name,
        PLA_PHONE_NBR_TXT = phone,
        PLA_PHONE_COMMENT_TXT = NULL
    FROM [dbo].[MSG_PLACE]
    INNER JOIN #tmp ON MSG_CONTAINER_UID = uid AND PLA_LOCAL_ID = lid
END
GO

IF (object_id('pii_MSG_PROVIDER_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_PROVIDER_Restore]
GO

CREATE PROCEDURE [dbo].[pii_MSG_PROVIDER_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        lid varchar(100),
        addr_city varchar(100),
        addr_st1 varchar(100),
        addr_st2 varchar(100),
        addr_zip varchar(10),
        alt_id_nbr varchar(100),
        quick_code varchar(100),
        id_nbr varchar(100),
        id_npi varchar(100),
        email varchar(100),
        name_first varchar(100),
        name_last varchar(100),
        name_mid varchar(100),
        phone varchar(50)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[MSG_PROVIDER]
    SET PRV_ADDR_CITY_TXT = addr_city,
        PRV_ADDR_COMMENT_TXT = NULL,
        PRV_ADDR_STREET_ADDR1_TXT = addr_st1,
        PRV_ADDR_STREET_ADDR2_TXT = addr_st2,
        PRV_ADDR_ZIP_CODE_TXT = addr_zip,
        PRV_COMMENT_TXT = NULL,
        PRV_ID_ALT_ID_NBR_TXT = alt_id_nbr,
        PRV_ID_QUICK_CODE_TXT = quick_code,
        PRV_ID_NBR_TXT = id_nbr,
        PRV_ID_NPI_TXT = id_npi,
        PRV_EMAIL_ADDRESS_TXT = email,
        PRV_NAME_FIRST_TXT = name_first,
        PRV_NAME_LAST_TXT = name_last,
        PRV_NAME_MIDDLE_TXT = name_mid,
        PRV_PHONE_COMMENT_TXT = NULL,
        PRV_PHONE_NBR_TXT = phone
    FROM [dbo].[MSG_PROVIDER]
    INNER JOIN #tmp ON MSG_CONTAINER_UID = uid AND PRV_LOCAL_ID = lid
END
GO

IF (object_id('pii_MSG_TREATMENT_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_MSG_TREATMENT_Restore]
GO

CREATE PROCEDURE [dbo].[pii_MSG_TREATMENT_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        lid varchar(100),
        custom_txt varchar(255),
        effect_time datetime,
        treat_dt datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[MSG_TREATMENT]
    SET TRT_COMMENT_TXT = NULL,
        TRT_CUSTOM_TREATMENT_TXT = custom_txt,
        TRT_EFFECTIVE_TIME = effect_time,
        TRT_TREATMENT_DT = treat_dt
    FROM [dbo].[MSG_TREATMENT]
    INNER JOIN #tmp ON MSG_CONTAINER_UID = uid AND TRT_LOCAL_ID = lid
END
GO

IF (object_id('pii_MsgOut_activity_log_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_MsgOut_activity_log_Restore]
GO

CREATE PROCEDURE [dbo].[pii_MsgOut_activity_log_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        name varchar(250)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[MsgOut_activity_log]
    SET doc_nm = name,
        message_txt = NULL
    FROM [dbo].[MsgOut_activity_log]
    INNER JOIN #tmp ON activity_log_uid = uid
END
GO

IF (object_id('pii_MsgOut_activity_log_detail_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_MsgOut_activity_log_detail_Restore]
GO

CREATE PROCEDURE [dbo].[pii_MsgOut_activity_log_detail_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        comment varchar(2000)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[MsgOut_activity_log_detail]
    SET log_comment = comment
    FROM [dbo].[MsgOut_activity_log_detail]
    INNER JOIN #tmp ON msgout_activity_detail_log_uid = uid
END
GO

IF (object_id('pii_MsgOut_Message_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_MsgOut_Message_Restore]
GO

CREATE PROCEDURE [dbo].[pii_MsgOut_Message_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[MsgOut_Message]
    SET attachment_txt = ''
    FROM [dbo].[MsgOut_Message]
    INNER JOIN #tmp ON message_uid = uid
END
GO

IF (object_id('pii_MsgOut_Receiving_facility_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_MsgOut_Receiving_facility_Restore]
GO

CREATE PROCEDURE [dbo].[pii_MsgOut_Receiving_facility_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        name varchar(100)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[MsgOut_Receiving_facility]
    SET receiving_facility_nm = name
    FROM [dbo].[MsgOut_Receiving_facility]
    INNER JOIN #tmp ON receiving_facility_entity_uid = uid
END
GO

IF (object_id('pii_MsgOut_Sending_facility_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_MsgOut_Sending_facility_Restore]
GO

CREATE PROCEDURE [dbo].[pii_MsgOut_Sending_facility_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        name varchar(100)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[MsgOut_Sending_facility]
    SET sending_facility_nm = name
    FROM [dbo].[MsgOut_Sending_facility]
    INNER JOIN #tmp ON sending_facility_entity_uid = uid
END
GO

IF (object_id('pii_NBS_interface_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_NBS_interface_Restore]
GO

CREATE PROCEDURE [dbo].[pii_NBS_interface_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        info varchar(250),
        coll_dt datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[NBS_interface]
    SET payload = NULL,
        original_payload = NULL,
        lab_clia = info,
        specimen_coll_date = coll_dt,
        original_payload_RR = NULL
    FROM [dbo].[NBS_interface]
    INNER JOIN #tmp ON nbs_interface_uid = uid
END
GO

IF (object_id('pii_NETSS_TransportQ_out_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_NETSS_TransportQ_out_Restore]
GO

CREATE PROCEDURE [dbo].[pii_NETSS_TransportQ_out_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        txt varchar(250)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[NETSS_TransportQ_out]
    SET payload = txt
    FROM [dbo].[NETSS_TransportQ_out]
    INNER JOIN #tmp ON NETSS_TransportQ_out_uid = uid
END
GO

IF (object_id('pii_PSF_CLIENT_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_CLIENT_Restore]
GO

CREATE PROCEDURE [dbo].[pii_PSF_CLIENT_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        fst_name varchar(50),
        lst_name varchar(50),
        dob_dt datetime,
        birth_year varchar(4)
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[PSF_CLIENT]
    SET clientFirstName = fst_name,
        clientLastName = lst_name,
        clientDOB = dob_dt,
        birthYear = birth_year
    FROM [dbo].[PSF_CLIENT]
    INNER JOIN #tmp ON psfClientUid = uid
END
GO

IF (object_id('pii_PSF_INDEX_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_INDEX_Restore]
GO

CREATE PROCEDURE [dbo].[pii_PSF_INDEX_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        fst_name varchar(50),
        lst_name varchar(50),
        dob_dt datetime,
        index_dt datetime
    )

    EXEC sp_bulk_insert @filePath

    DECLARE @dw smallint
    SET @dw = DATEPART(dw, GETDATE())

    UPDATE [dbo].[PSF_INDEX]
    SET clientFirstName = fst_name,
        clientLastName = lst_name,
        clientDOB = dob_dt,
        indexDateDemographicsCollected = index_dt,
        caseOpenDate = CASE WHEN caseOpenDate IS NOT NULL THEN DATEADD(day, @dw, caseOpenDate) END,
        caseCloseDate = CASE WHEN caseCloseDate IS NOT NULL THEN DATEADD(day, @dw, caseCloseDate) END
    FROM [dbo].[PSF_INDEX]
    INNER JOIN #tmp ON psfIndexUid = uid
END
GO

IF (object_id('pii_PSF_PARTNER_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_PARTNER_Restore]
GO

CREATE PROCEDURE [dbo].[pii_PSF_PARTNER_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        fst_name varchar(50),
        lst_name varchar(50),
        dob_dt datetime,
        smp_dt datetime,
        fst_app_dt datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[PSF_PARTNER]
    SET clientFirstName = fst_name,
        clientLastName = lst_name,
        clientDOB = dob_dt,
        sampleDate = smp_dt,
        firstMedicalCareAppointmentDate = fst_app_dt
    FROM [dbo].[PSF_PARTNER]
    INNER JOIN #tmp ON psfPartnerUid = uid
END
GO

IF (object_id('pii_PSF_RISK_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_RISK_Restore]
GO

CREATE PROCEDURE [dbo].[pii_PSF_RISK_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        fst_name varchar(50),
        lst_name varchar(50),
        dob_dt datetime,
        coll_dt datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[PSF_RISK]
    SET clientFirstName = fst_name,
        clientLastName = lst_name,
        clientDOB = dob_dt,
        dateCollectedForRiskProfile = coll_dt
    FROM [dbo].[PSF_RISK]
    INNER JOIN #tmp ON psfRiskUid = uid
END
GO

IF (object_id('pii_PSF_SESSION_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_PSF_SESSION_Restore]
GO

CREATE PROCEDURE [dbo].[pii_PSF_SESSION_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint,
        fst_name varchar(50),
        lst_name varchar(50),
        dob_dt datetime,
        sess_dt datetime
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[PSF_SESSION]
    SET clientFirstName = fst_name,
        clientLastName = lst_name,
        clientDOB = dob_dt,
        sessionDate = sess_dt
    FROM [dbo].[PSF_SESSION]
    INNER JOIN #tmp ON psfSessionUid = uid
END
GO

IF (object_id('pii_TransportQ_out_Restore') is not null)
    DROP PROCEDURE [dbo].[pii_TransportQ_out_Restore]
GO

CREATE PROCEDURE [dbo].[pii_TransportQ_out_Restore]
@filePath varchar(max)

AS
BEGIN
    SET NOCOUNT ON

    CREATE TABLE #tmp (
        uid bigint
    )

    EXEC sp_bulk_insert @filePath

    UPDATE [dbo].[TransportQ_out]
    SET payloadContent = NULL
    FROM [dbo].[TransportQ_out]
    INNER JOIN #tmp ON recordId = uid
END
GO