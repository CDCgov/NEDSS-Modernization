from src.config import get_cached_config_value
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
    nbs_rdb = get_cached_config_value('REPORT_DB_NBS_RDB')
    sql_query = f"""
    WITH shd AS ({subset_query})
    SELECT DISTINCT
        shd.INV_LOCAL_ID,
        shd.PATIENT_NAME,
        shd.PATIENT_LOCAL_ID,
        shd.DIAGNOSIS_CD,
        shd.PATIENT_AGE_REPORTED AS [PATIENT_AGE_REPORTED],
        CAST(shd.CONFIRMATION_DT AS DATETIME) AS [CONFIRMATION_DT],
        shd.JURISDICTION_NM,
        o.ORGANIZATION_NAME,
        IIF(PROVIDER_NAME_SUFFIX IS NULL,
            TRIM(CONCAT(
                        dp.provider_first_name,
                        ' ',
                        dp.provider_last_name)),
            TRIM(CONCAT(
                    dp.provider_first_name,
                    ' ',
                    dp.provider_last_name,
                    ', ',
                    dp.provider_name_suffix))
        ) AS [PROVIDER],
       CAST(SUBSTRING(PATIENT_LOCAL_ID, 4, 8) AS INT) - 10000000 AS PATIENTID
    FROM shd
    INNER JOIN 
        [{nbs_rdb}].[dbo].[INVESTIGATION] i 
            ON shd.INVESTIGATION_KEY = i.INVESTIGATION_KEY
    LEFT OUTER JOIN 
        [{nbs_rdb}].[dbo].[D_PROVIDER] dp 
            ON shd.PHYSICIAN_KEY = dp.PROVIDER_KEY
    LEFT OUTER JOIN 
        [{nbs_rdb}].[dbo].[D_ORGANIZATION] o 
            ON shd.ORDERING_FACILITY_KEY = o.ORGANIZATION_KEY
    WHERE
        shd.INV_LOCAL_ID IS NOT NULL
        AND i.INV_CASE_STATUS in ('Probable','Confirmed')
    ORDER BY shd.PATIENT_NAME ASC;
    """

    content = trx.query(sql_query)

    return ReportResult(content=content)
