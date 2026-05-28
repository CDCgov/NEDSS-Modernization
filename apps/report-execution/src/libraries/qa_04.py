from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """QA04 Cases Missing Lab and/or Treatment.

    Cases Missing Lab or Treatment. This report generates a list, by name,
    of individuals with cases that are not linked to a positive lab test record
    (for this reported case) or to a treatment record.

    Conversion notes:

    """
    full_query = f"""
    WITH subset AS ({subset_query})
    SELECT DISTINCT
        a.INV_LOCAL_ID,
        a.PATIENT_NAME,
        a.PATIENT_LOCAL_ID,
        a.DIAGNOSIS_CD,
        CONVERT(varchar(10), a.CONFIRMATION_DT, 101) AS CONFIRMATION_DT,
        CASE
            WHEN b.LAB_TEST_KEY IS NULL AND c.TREATMENT_KEY IS NULL
                THEN 'No Treatment or Lab'
            WHEN b.LAB_TEST_KEY IS NULL
                THEN 'No Lab'
            WHEN c.TREATMENT_KEY IS NULL
                THEN 'No Treatment'
            ELSE 'N/A'
        END AS ERROR_TXT,
        SUBSTRING(a.PATIENT_LOCAL_ID, 4, 8) - 10000000 AS PATIENTID
    FROM subset a
    INNER JOIN rdb.dbo.investigation e
        ON a.INVESTIGATION_KEY = e.INVESTIGATION_KEY
    LEFT JOIN rdb.dbo.LAB_TEST_RESULT b
        ON a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
    LEFT JOIN rdb.dbo.TREATMENT_EVENT c
        ON a.INVESTIGATION_KEY = c.INVESTIGATION_KEY
    WHERE a.INV_LOCAL_ID IS NOT NULL
        AND e.INV_CASE_STATUS IN ('Probable', 'Confirmed')
        AND (
            b.LAB_TEST_KEY IS NULL
            OR c.TREATMENT_KEY IS NULL
        )
    ORDER BY a.PATIENT_NAME
    """

    content = trx.query(full_query)

    return ReportResult(
        content_type='table',
        content=content
    )
