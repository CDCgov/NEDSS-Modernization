from src.config import get_config_value
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
    nbs_rdb = get_config_value(trx, 'REPORT_DB_NBS_RDB')
    sql_query = f"""
    WITH shd AS ({subset_query})
    SELECT
        shd.INVESTIGATION_KEY,
        shd.PATIENT_NAME,
        shd.PATIENT_LOCAL_ID,
        shd.PATIENT_AGE_REPORTED,
        SUBSTRING(PATIENT_SEX, 1, 1) AS [PATIENT_SEX],
        shd.DIAGNOSIS_CD,
        shd.JURISDICTION_NM,
        INVESTIGATOR_INTERVIEW_QC,
        CAST(shd.CC_CLOSED_DT AS DATE) as CLOSED_DT,
        CASE WHEN shd.CC_CLOSED_DT IS NULL THEN 'Y' ELSE 'N' END as Open_Status,
        SUBSTRING(shd.PATIENT_PREGNANT_IND, 1, 1) AS [PATIENT_PREGNANT_IND],
        PBI_PREG_IN_LAST_12MO_IND AS [PBI_PREG_IN_LAST_12MO_IND],
        shd.PBI_PREG_AT_EXAM_IND AS [PBI_PREG_AT_EXAM_IND],
        PBI_PREG_AT_IX_IND AS [PBI_PREG_AT_IX_IND],
        PBI_PREG_IN_LAST_12MO_IND AS [PBI_PREG_IN_LAST_12MO_IND],
        CAST(CA_INTERVIEWER_ASSIGN_DT AS DATE) AS [ASSIGNED_DT],
        '14' AS [i], -- the sas library included a column with the count of the columns
        LOWER(PATIENT_NAME) AS [name_l],
        SUBSTRING(shd.PATIENT_LOCAL_ID, 4, 8) - 10000000 AS [patient_id],
        TRY_CAST(
            LEFT(
                TRIM(PATIENT_AGE_REPORTED), 
                CHARINDEX(' ', TRIM(PATIENT_AGE_REPORTED) + ' ')) AS INT
        ) AS [age]
    FROM
        shd 
            INNER JOIN {nbs_rdb}.DBO.INVESTIGATION inv
                       ON shd.INVESTIGATION_KEY = inv.INVESTIGATION_KEY
    WHERE shd.DIAGNOSIS_CD IS NOT NULL
      AND shd.INVESTIGATOR_INTERVIEW_KEY IS NOT NULL
      AND shd.INV_LOCAL_ID IS NOT NULL
      AND inv.INV_CASE_STATUS in ('Probable', 'Confirmed')
      AND shd.PATIENT_SEX = 'Female'
      AND (
        shd.PATIENT_PREGNANT_IND = 'Yes'
            OR shd.PBI_PREG_AT_EXAM_IND = 'Yes'
            OR shd.PBI_PREG_AT_IX_IND = 'Yes'
            OR shd.PBI_PREG_IN_LAST_12MO_IND = 'Yes'
        )
    ORDER BY name_l, DIAGNOSIS_CD;
    """

    content = trx.query(sql_query)

    return ReportResult(content_type='table', content=content)
