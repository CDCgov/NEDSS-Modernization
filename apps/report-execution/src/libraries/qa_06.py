from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """QA Report 06: Patients with Multiple Cases.

    Conversion notes:
    """
    content = trx.query(
        """
        WITH LAB AS
        (
          SELECT ltr.INVESTIGATION_KEY,
                 MAX(lt.SPECIMEN_COLLECTION_DT) AS SPECIMEN_COLLECTION_DT
          FROM [RDB].[dbo].[LAB_TEST_RESULT] ltr
            INNER JOIN [RDB].[dbo].[lab_test] lt ON ltr.lab_test_key = lt.lab_test_key
          GROUP BY ltr.INVESTIGATION_KEY
        ),
        INV AS (
          SELECT INVESTIGATION.INVESTIGATION_KEY,
                 INVESTIGATION.INV_CASE_STATUS,
                 REFERRAL_BASIS,
                 EVENT_METRIC.ADD_USER_ID,
                 USER_PROFILE.PROVIDER_QUICK_CODE
          FROM [RDB].[dbo].[INVESTIGATION]
            JOIN [RDB].[dbo].[EVENT_METRIC] 
                ON INVESTIGATION.CASE_UID = EVENT_METRIC.EVENT_UID
            JOIN [RDB].[dbo].[USER_PROFILE] 
                ON EVENT_METRIC.ADD_USER_ID = USER_PROFILE.NEDSS_ENTRY_ID
        )
        SELECT *
        FROM INV i
        LEFT JOIN LAB l ON l.INVESTIGATION_KEY = i.INVESTIGATION_KEY;
        """
    )

    return ReportResult(
        content_type='table',
        content=content
    )
