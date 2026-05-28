import time

from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """QA10 STD Program Report: Interviews - Pregnant/Recent Birth Report.

    Conversion notes:
    * Did not include logging of run time
    * Hardcode i to "14" instead of the count of the columns
    """

    sql_query = f"""
    WITH shd AS (
    SELECT *
    FROM ({subset_query}) AS base
    )
    SELECT
        a.investigation_key as [INVESTIGATION_KEY],
        NULLIF(a.patient_name, 'NULL') AS [PATIENT_NAME],
        a.patient_local_id as [PATIENT_LOCAL_ID],
        NULLIF(a.patient_age_reported, 'NULL') AS [PATIENT_AGE_REPORTED],
        SUBSTRING(patient_sex, 1, 1) AS [PATIENT_SEX],
        a.DIAGNOSIS_CD,
        a.JURISDICTION_NM,
        NULLIF(INVESTIGATOR_INTERVIEW_QC, 'NULL') AS [INVESTIGATOR_INTERVIEW_QC],
        FORMAT(CAST(a.cc_closed_dt AS DATE), 'MM/dd/yyyy') as CLOSED_DT,
        CASE WHEN a.CC_CLOSED_DT IS NULL THEN 'Y' ELSE 'N' END as Open_Status, 
        SUBSTRING(NULLIF(a.PATIENT_PREGNANT_IND, 'NULL'), 1, 1) AS [PATIENT_PREGNANT_IND],
        NULLIF(PBI_PREG_IN_LAST_12MO_IND, 'NULL') AS [PBI_PREG_IN_LAST_12MO_IND],
        NULLIF(a.PBI_PREG_AT_EXAM_IND, 'NULL') AS [PBI_PREG_AT_EXAM_IND],
        NULLIF(PBI_PREG_AT_IX_IND, 'NULL') AS [PBI_PREG_AT_IX_IND],
        NULLIF(PBI_PREG_IN_LAST_12MO_IND, 'NULL') AS [PBI_PREG_IN_LAST_12MO_IND],
        FORMAT(CAST(CA_INTERVIEWER_ASSIGN_DT AS DATE), 'MM/dd/yyyy') AS [ASSIGNED_DT],
        '14' AS [i], -- the sas library included a column with the count of the columns
        LOWER(NULLIF(PATIENT_NAME, 'NULL')) AS [name_l],
        SUBSTRING(a.PATIENT_LOCAL_ID, 4, 8) - 10000000 AS [patient_id],
        CASE
            WHEN PATIENT_AGE_REPORTED IS NULL THEN NULL
            WHEN LTRIM(RTRIM(PATIENT_AGE_REPORTED)) IN ('', '.') THEN NULL
            ELSE TRY_CAST(
                    LEFT(
                            LTRIM(RTRIM(PATIENT_AGE_REPORTED)),
                            CHARINDEX(' ', LTRIM(RTRIM(PATIENT_AGE_REPORTED)) + ' ') - 1
                    ) AS INT
                 )
            END AS [age]
    FROM
        shd a
            INNER JOIN RDB.DBO.INVESTIGATION e
                       ON a.INVESTIGATION_KEY = e.INVESTIGATION_KEY
    WHERE a.DIAGNOSIS_CD IS NOT NULL
      AND a.INVESTIGATOR_INTERVIEW_KEY IS NOT NULL
      AND a.inv_local_id IS NOT NULL
      AND e.inv_case_status in ('Probable', 'Confirmed')
      AND a.patient_sex = 'Female'
      AND (
        a.patient_pregnant_ind = 'Yes'
            OR a.pbi_preg_at_exam_ind = 'Yes'
            OR a.pbi_preg_at_ix_ind = 'Yes'
            OR a.pbi_preg_in_last_12mo_ind = 'Yes'
        )
    ORDER BY name_l, DIAGNOSIS_CD;
    """

    content = trx.query(sql_query)

    return ReportResult(content_type='table', content=content)
