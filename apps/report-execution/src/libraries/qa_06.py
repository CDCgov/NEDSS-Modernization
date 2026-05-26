from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """QA Report 06: Patients with Multiple Cases. This report generates a list,
    by name, of individuals who have multiple occasions of cases within a time
    period.

    Conversion notes:
    * MSSQL Datetime conversions use constants, "101" is for mm/dd/yyyy
    * FL_FUP_EXAM_DT logic in final SELECT is the equivalent of SAS in orig. script
    * NULL filtering for PATIENT_NAME does not match SAS script (since script
      joins by PATIENT_NAME, it doesn't make sense in SQL to join on NULL
      values in this instance)
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
              CONVERT(varchar, SHD.CONFIRMATION_DT, 101) AS CONFIRMATION_DT,
              CONVERT(varchar, SHD.FL_FUP_EXAM_DT, 101) AS FL_FUP_EXAM_DT,
              CONVERT(varchar, LAB.SPECIMEN_COLLECTION_DT, 101)
                AS SPECIMEN_COLLECTION_DT,
              CONVERT(varchar, MR.DIAGNOSIS_DT, 101) AS DIAGNOSIS_DT
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
        ),

        -- calculate case counts per patient
        CASE_COUNTS AS (
          SELECT PATIENT_NAME,
            COUNT(PATIENT_LOCAL_ID) AS CASE_COUNT
          FROM PATIENT_CASES
          WHERE PATIENT_NAME IS NOT NULL
          GROUP BY PATIENT_NAME
        )

        SELECT PC.PATIENT_NAME,
          PC.PATIENT_LOCAL_ID,
          PC.INV_LOCAL_ID,
          PC.REFERRAL_BASIS,
          PC.PROVIDER_QUICK_CODE,
          PC.DIAGNOSIS,
          PC.CMP_PID_IND,
          PC.CONFIRMATION_DT,
          CASE
            WHEN PC.FL_FUP_EXAM_DT IS NULL
            AND PC.REFERRAL_BASIS = 'T1 - Positive Test'
              THEN PC.SPECIMEN_COLLECTION_DT
            WHEN PC.FL_FUP_EXAM_DT IS NULL
            AND PC.REFERRAL_BASIS = 'T2 - Morbidity Report'
              THEN PC.DIAGNOSIS_DT
            ELSE PC.FL_FUP_EXAM_DT
          END AS FL_FUP_EXAM_DT,
          PC.SPECIMEN_COLLECTION_DT,
          PC.DIAGNOSIS_DT,
          CC.CASE_COUNT AS COUNT,
          CAST(
            TRY_CONVERT(int, SUBSTRING(PC.PATIENT_LOCAL_ID, 4, 8)) - 10000000
                AS varchar(10)
          ) AS PATIENTID
        FROM PATIENT_CASES PC
        INNER JOIN CASE_COUNTS CC
          ON PC.PATIENT_NAME = CC.PATIENT_NAME
        WHERE CASE_COUNT > 1
        ORDER BY PC.PATIENT_NAME;
        """
    # columns from nbs7demo output csv:
    # PATIENT_NAME
    # PATIENT_LOCAL_ID
    # INV_LOCAL_ID
    # REFERRAL_BASIS
    # PROVIDER_QUICK_CODE
    # DIAGNOSIS
    # CMP_PID_IND
    # CONFIRMATION_DT
    # FL_FUP_EXAM_DT
    # SPECIMEN_COLLECTION_DT
    # DIAGNOSIS_DT
    # COUNT
    # PATIENTID

    content = trx.query(query)

    return ReportResult(
        content_type='table',
        content=content
    )
