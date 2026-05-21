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
    query = f"""
        WITH STD_HIV_DATAMART AS ({subset_query}),

        -- gets information about each investigation
        INV AS (
          SELECT INVESTIGATION.INVESTIGATION_KEY,
                 INVESTIGATION.INV_CASE_STATUS,
                 INVESTIGATION.REFERRAL_BASIS,
                 EVENT_METRIC.ADD_USER_ID,
                 USER_PROFILE.PROVIDER_QUICK_CODE
          FROM [RDB].[dbo].[INVESTIGATION]
            JOIN [RDB].[dbo].[EVENT_METRIC]
                ON INVESTIGATION.CASE_UID = EVENT_METRIC.EVENT_UID
            JOIN [RDB].[dbo].[USER_PROFILE]
                ON EVENT_METRIC.ADD_USER_ID = USER_PROFILE.NEDSS_ENTRY_ID
        ),

        -- gets the most recent speciment collection date for each investigation
        LAB AS (
          SELECT ltr.INVESTIGATION_KEY,
                 MAX(lt.SPECIMEN_COLLECTION_DT) AS SPECIMEN_COLLECTION_DT
          FROM [RDB].[dbo].[LAB_TEST_RESULT] ltr
            INNER JOIN [RDB].[dbo].[lab_test] lt ON ltr.lab_test_key = lt.lab_test_key
          GROUP BY ltr.INVESTIGATION_KEY
        ),

        -- build out patient cases
        PATIENT_CASES AS (
          SELECT DISTINCT SHD.PATIENT_NAME,
              SHD.PATIENT_LOCAL_ID,
              SHD.INV_LOCAL_ID,
              INV.REFERRAL_BASIS,
              INV.PROVIDER_QUICK_CODE,
              SHD.DIAGNOSIS,
              SHD.CMP_PID_IND,
              SHD.CONFIRMATION_DT,
              SHD.FL_FUP_EXAM_DT,
              LAB.SPECIMEN_COLLECTION_DT,
              MR.DIAGNOSIS_DT
          FROM STD_HIV_DATAMART SHD
            INNER JOIN INV ON SHD.INVESTIGATION_KEY = INV.INVESTIGATION_KEY
            LEFT OUTER JOIN LAB ON SHD.INVESTIGATION_KEY = LAB.INVESTIGATION_KEY
            LEFT OUTER JOIN [RDB].[dbo].[MORBIDITY_REPORT_EVENT] MRE
              ON SHD.INVESTIGATION_KEY = MRE.INVESTIGATION_KEY
            LEFT OUTER JOIN [RDB].[dbo].[MORBIDITY_REPORT] MR
              ON MR.MORB_RPT_KEY = MRE.MORB_RPT_KEY
          WHERE SHD.INV_LOCAL_ID IS NOT NULL
            AND SHD.DIAGNOSIS IS NOT NULL
            AND INV.INV_CASE_STATUS IN ('Probable', 'Confirmed')
        )

        SELECT *
        FROM PATIENT_CASES;
        """
    # PATIENT_NAME,PATIENT_LOCAL_ID,INV_LOCAL_ID,REFERRAL_BASIS,PROVIDER_QUICK_CODE,DIAGNOSIS,CMP_PID_IND,CONFIRMATION_DT,FL_FUP_EXAM_DT,SPECIMEN_COLLECTION_DT,DIAGNOSIS_DT,COUNT,PATIENTID

    breakpoint()
    content = trx.query(query)

    return ReportResult(
        content_type='table',
        content=content
    )
