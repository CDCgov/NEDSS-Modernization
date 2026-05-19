from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """QA03 STD Program Report: Case Listing Report.

    Conversion notes:
    * Matched export format
    """
    sql_query = f"""
    WITH shd AS ({subset_query})
    SELECT DISTINCT
        shd.INV_LOCAL_ID,
        shd.PATIENT_NAME,
        shd.PATIENT_LOCAL_ID,
        shd.DIAGNOSIS_CD,
        shd.PATIENT_AGE_REPORTED AS [PATIENT_AGE_REPORTED],
        FORMAT(CAST(shd.CONFIRMATION_DT AS DATETIME), 'MM/dd/yyyy') 
            AS [CONFIRMATION_DT],
        shd.JURISDICTION_NM,
        o.ORGANIZATION_NAME,
        LTRIM(
        RTRIM(
        CONCAT(
            dp.provider_first_name,
            ' ',
            dp.provider_last_name,
            ' ',
            dp.provider_name_suffix))) AS [PROVIDER],
       SUBSTRING(shd.PATIENT_LOCAL_ID, 7, 5) AS [PATIENTID]
    FROM shd
    INNER JOIN 
        [RDB].[dbo].[INVESTIGATION] i ON shd.INVESTIGATION_KEY = i.INVESTIGATION_KEY
    LEFT OUTER JOIN 
        [RDB].[dbo].[D_PROVIDER] dp ON shd.PHYSICIAN_KEY = dp.PROVIDER_KEY
    LEFT OUTER JOIN 
        [RDB].[dbo].[D_ORGANIZATION] o ON shd.ORDERING_FACILITY_KEY = o.ORGANIZATION_KEY
    WHERE
        shd.INV_LOCAL_ID IS NOT NULL
        AND i.INV_CASE_STATUS in ('Probable','Confirmed')
    ORDER BY shd.PATIENT_NAME ASC;
    """

    content = trx.query(sql_query)

    return ReportResult(content_type='table', content=content)
