from src.config import get_cached_config_value
from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """QA01 STD Program Report: Interview Record Listing.

    Conversion notes:
    * Did not include logging of run time
    * Hardcode i to "13" instead of the count of the columns
    """
    nbs_rdb = get_cached_config_value('REPORT_DB_NBS_RDB')

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
        CAST(shd.ca_interviewer_assign_dt AS DATE) AS [ASSIGNED_DT],
        CAST(shd.cc_closed_dt AS DATE) AS [CLOSED_DT],
        LOWER(shd.patient_name) AS [name_l],
        IIF(
            shd.patient_race IS NOT NULL AND shd.patient_race <> '', 'XXX', ''
            ) AS [race],
        '' AS [sex],
        '13' AS [i], -- the sas library included a column with the count of the columns
        shd.patient_age_reported AS [age]
    FROM Shd_Filtered shd
        LEFT JOIN {nbs_rdb}.dbo.investigation i 
            ON shd.investigation_key = i.investigation_key
        LEFT JOIN {nbs_rdb}.dbo.event_metric em 
            ON i.case_uid = em.event_uid
        LEFT JOIN {nbs_rdb}.dbo.user_profile up 
            ON em.add_user_id = up.nedss_entry_id
    ORDER BY [PATIENT_NAME], [DIAGNOSIS_CD], [INVESTIGATION_KEY];
    """

    content = trx.query(sql_query)

    return ReportResult(content_type='table', content=content)
