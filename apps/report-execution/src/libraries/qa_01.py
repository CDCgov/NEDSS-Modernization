from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """QA01 STD Program Report: Interview Record Listing
    Conversion notes:
    * Data format is YYYY-MM-DD.
    """
    sql_query = f"""
    WITH Shd_Filtered AS (
    SELECT *
    FROM ({subset_query}) AS base
    WHERE ca_patient_intv_status = 'I - Interviewed'
      AND diagnosis_cd IS NOT NULL
      AND ix_date_oi IS NOT NULL
    )
    SELECT
        shd.investigation_key AS [INVESTIGATION_KEY],
        em.add_user_id AS [ADD_USER_ID],
        up.provider_quick_code AS [PROVIDER_QUICK_CODE],
        shd.patient_name AS [PATIENT_NAME],
        shd.patient_age_reported AS [PATIENT_AGE_REPORTED],
        shd.patient_sex AS [PATIENT_SEX],
        shd.patient_race AS [PATIENT_RACE],
        shd.diagnosis_cd AS [DIAGNOSIS_CD],
        shd.field_record_number AS [FIELD_RECORD_NUMBER],
        shd.investigator_interview_qc AS [INVESTIGATOR_INTERVIEW_QC],
        IIF(shd.cc_closed_dt IS NULL, 'Y', 'N') AS [Open_Status],
        FORMAT(CAST(shd.ca_interviewer_assign_dt AS DATE), 'MM/dd/yyyy') 
            AS [ASSIGNED_DT],
        FORMAT(CAST(shd.cc_closed_dt AS DATE), 'MM/dd/yyyy') AS [CLOSED_DT],
        LOWER(shd.patient_name) AS [name_l],
        IIF(
            shd.patient_race IS NOT NULL AND shd.patient_race <> '', 'XXX', ''
            ) AS [race],
        '' AS [sex],
        '13' AS [i],
        shd.patient_age_reported AS [age]
    FROM Shd_Filtered shd
        LEFT JOIN rdb.dbo.investigation i 
            ON shd.investigation_key = i.investigation_key
        LEFT JOIN rdb.dbo.event_metric em 
            ON i.case_uid = em.event_uid
        LEFT JOIN rdb.dbo.user_profile up 
            ON em.add_user_id = up.nedss_entry_id
    ORDER BY [PATIENT_NAME], [DIAGNOSIS_CD], [INVESTIGATION_KEY];
    """

    content = trx.query(sql_query)

    description = 'NEDSS STD Program Activity Report - QA01'

    return ReportResult(
        content_type='table',
        content=content,
        subheader=None,
        description=description,
    )
