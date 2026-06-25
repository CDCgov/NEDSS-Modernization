from src.config import retrieve_config_value
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
    * FL_FUP_EXAM_DT logic in final SELECT is the equivalent of SAS in original script
    * All Datetime columns have been cast to Date in order to standardize our date
      formatting in CSV outputs.  This will look slightly different than the SAS output
      but the dates are functionally the same.
    * Sorting differs slightly from QA06.sas.  In original SAS file, there is only one
      ORDER BY statement (using PATIENT_NAME).  SQL uses this same ORDER BY but the
      actual sorting beyond that will differ between SAS and SQL Server.
    """
    # Dynamically look up the correct DB names
    rdb = retrieve_config_value(trx, 'rdb')
    query = f"""
        WITH STD_HIV_DATAMART AS ({subset_query}),

        -- gets information about each investigation
        INV AS (
          SELECT INVESTIGATION.INVESTIGATION_KEY,
                 INVESTIGATION.INV_CASE_STATUS,
                 INVESTIGATION.REFERRAL_BASIS,
                 EVENT_METRIC.ADD_USER_ID,
                 USER_PROFILE.PROVIDER_QUICK_CODE
          FROM {rdb}.[dbo].[INVESTIGATION]
            JOIN {rdb}.[dbo].[EVENT_METRIC]
                ON INVESTIGATION.CASE_UID = EVENT_METRIC.EVENT_UID
            JOIN {rdb}.[dbo].[USER_PROFILE]
                ON EVENT_METRIC.ADD_USER_ID = USER_PROFILE.NEDSS_ENTRY_ID
        ),

        -- gets the most recent speciment collection date for each investigation
        LAB AS (
          SELECT ltr.INVESTIGATION_KEY,
                 MAX(lt.SPECIMEN_COLLECTION_DT) AS SPECIMEN_COLLECTION_DT
          FROM {rdb}.[dbo].[LAB_TEST_RESULT] ltr
            INNER JOIN {rdb}.[dbo].[lab_test] lt ON ltr.lab_test_key = lt.lab_test_key
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
              CAST(SHD.CONFIRMATION_DT AS DATE) AS CONFIRMATION_DT,
              CAST(SHD.FL_FUP_EXAM_DT AS DATE) AS FL_FUP_EXAM_DT,
              CAST(LAB.SPECIMEN_COLLECTION_DT AS DATE)
                AS SPECIMEN_COLLECTION_DT,
              CAST(MR.DIAGNOSIS_DT AS DATE) AS DIAGNOSIS_DT
          FROM STD_HIV_DATAMART SHD
            INNER JOIN INV ON SHD.INVESTIGATION_KEY = INV.INVESTIGATION_KEY
            LEFT OUTER JOIN LAB ON SHD.INVESTIGATION_KEY = LAB.INVESTIGATION_KEY
            LEFT OUTER JOIN {rdb}.[dbo].[MORBIDITY_REPORT_EVENT] MRE
              ON SHD.INVESTIGATION_KEY = MRE.INVESTIGATION_KEY
            LEFT OUTER JOIN {rdb}.[dbo].[MORBIDITY_REPORT] MR
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
          TRY_CONVERT(int, SUBSTRING(PC.PATIENT_LOCAL_ID, 4, 8)) - 10000000 AS PATIENTID
        FROM PATIENT_CASES PC
        INNER JOIN CASE_COUNTS CC
          ON ((PC.PATIENT_NAME = CC.PATIENT_NAME) OR
              (PC.PATIENT_NAME IS NULL AND CC.PATIENT_NAME IS NULL))
        WHERE CASE_COUNT > 1
        ORDER BY PC.PATIENT_NAME;
        """

    content = trx.query(query)

    return ReportResult(content_type='table', content=content)
