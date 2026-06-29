from src.config import get_config_value
from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    library_params: dict,
    **kwargs,
):
    """QA07: Duplicate Cases. The Report will consist of 3 reports, identical
    except for the number of days used, and will display in the NBS Report Module as:
    •	QA07 - Duplicate Cases (30 Days)
    •	QA07 - Duplicate Cases (60 Days)
    •	QA07 - Duplicate Cases (90 Days)
    This report generates a list, by name, of individuals that have possible duplicate
    case incidents.
    User filtering includes a time period (based on case confirmation date), diagnoses
    and range between occurrences. “Cases” only include investigations with Case Status
    of Probable or Confirmed.

    Conversion notes:
    * The original SAS code had the days value hard coded in the library, but we made
    it a parameter that can be passed in from the API.
    * The output is sorted by PATIENT_NAME, DIAGNOSIS, FL_FUP_EXAM_DT, and
    INVESTIGATION_KEY (the last is used only as a tie‑breaker and does not appear
    in the final output). SAS does not have a tiebreaker value.
    """
    nbs_rdb = get_config_value(trx, 'nbs_rdb')
    if not isinstance(library_params, dict):
        raise ValueError(f"""
            library_params must be a dictionary containing 'days_value' \
            (e.g., 30, 60, 90), got {library_params}
        """)
    days = library_params.get('days_value')
    if days is None:
        raise ValueError(f"""
            library_params must contain 'days_value' \
            (e.g., 30, 60, 90), got {library_params}
        """)

    sql = f"""
    WITH subset AS (
        {subset_query}
    ),
    inv AS (
        SELECT
            i.INVESTIGATION_KEY,
            i.INV_CASE_STATUS,
            i.REFERRAL_BASIS,
            e.ADD_USER_ID,
            up.PROVIDER_QUICK_CODE
        FROM {nbs_rdb}.dbo.INVESTIGATION i
        INNER JOIN {nbs_rdb}.dbo.EVENT_METRIC e ON i.CASE_UID = e.EVENT_UID
        INNER JOIN {nbs_rdb}.dbo.USER_PROFILE up ON e.ADD_USER_ID = up.NEDSS_ENTRY_ID
    ),
    lab_max AS (
        SELECT
            b.INVESTIGATION_KEY,
            MAX(d.SPECIMEN_COLLECTION_DT) AS SPECIMEN_COLLECTION_DT
        FROM {nbs_rdb}.dbo.LAB_TEST_RESULT b
        INNER JOIN {nbs_rdb}.dbo.LAB_TEST d ON b.LAB_TEST_KEY = d.LAB_TEST_KEY
        GROUP BY b.INVESTIGATION_KEY
    ),
    derived AS (
        SELECT
            s.INVESTIGATION_KEY,
            s.PATIENT_NAME,
            s.PATIENT_LOCAL_ID,
            s.INV_LOCAL_ID,
            s.DIAGNOSIS,
            s.CONFIRMATION_DT,
            s.FL_FUP_EXAM_DT AS ORIG_FL_FUP_EXAM_DT,
            inv.REFERRAL_BASIS,
            inv.INV_CASE_STATUS,
            lab.SPECIMEN_COLLECTION_DT,
            mr.DIAGNOSIS_DT,
            CAST(SUBSTRING(s.PATIENT_LOCAL_ID, 4, 8) AS BIGINT) - 10000000 AS PATIENTID
        FROM subset s
        INNER JOIN inv ON s.INVESTIGATION_KEY = inv.INVESTIGATION_KEY
        LEFT JOIN lab_max lab ON s.INVESTIGATION_KEY = lab.INVESTIGATION_KEY
        LEFT JOIN {nbs_rdb}.dbo.MORBIDITY_REPORT_EVENT mre
        ON s.INVESTIGATION_KEY = mre.INVESTIGATION_KEY
        LEFT JOIN {nbs_rdb}.dbo.MORBIDITY_REPORT mr 
        ON mre.MORB_RPT_KEY = mr.MORB_RPT_KEY
        WHERE s.INV_LOCAL_ID IS NOT NULL
          AND s.DIAGNOSIS IS NOT NULL
          AND inv.INV_CASE_STATUS IN ('Probable', 'Confirmed')
    ),
    with_fup_dt AS (
        SELECT
            INVESTIGATION_KEY,
            PATIENT_NAME,
            PATIENT_LOCAL_ID,
            INV_LOCAL_ID,
            DIAGNOSIS,
            CONFIRMATION_DT,
            PATIENTID,
            COALESCE(
                ORIG_FL_FUP_EXAM_DT,
                CASE
                    WHEN REFERRAL_BASIS = 'T1 - Positive Test'
                    THEN SPECIMEN_COLLECTION_DT
                    WHEN REFERRAL_BASIS = 'T2 - Morbidity Report'
                    THEN DIAGNOSIS_DT
                END
            ) AS FL_FUP_EXAM_DT
        FROM derived
        WHERE ORIG_FL_FUP_EXAM_DT IS NOT NULL
           OR (
               REFERRAL_BASIS = 'T1 - Positive Test'
               AND SPECIMEN_COLLECTION_DT IS NOT NULL
            )
           OR (REFERRAL_BASIS = 'T2 - Morbidity Report' AND DIAGNOSIS_DT IS NOT NULL)
    ),
    days_desc AS (
        SELECT
            *,
            LAG(FL_FUP_EXAM_DT) OVER (
                PARTITION BY PATIENT_LOCAL_ID, DIAGNOSIS
                ORDER BY FL_FUP_EXAM_DT DESC, INVESTIGATION_KEY
            ) AS lag_desc,
            DATEDIFF(day, LAG(FL_FUP_EXAM_DT) OVER (
                PARTITION BY PATIENT_LOCAL_ID, DIAGNOSIS
                ORDER BY FL_FUP_EXAM_DT DESC, INVESTIGATION_KEY
            ), FL_FUP_EXAM_DT) AS DAYS_raw
        FROM with_fup_dt
    ),
    days_asc AS (
        SELECT
            *,
            LAG(FL_FUP_EXAM_DT) OVER (
                PARTITION BY PATIENT_LOCAL_ID, DIAGNOSIS
                ORDER BY FL_FUP_EXAM_DT ASC, INVESTIGATION_KEY
            ) AS lag_asc,
            DATEDIFF(day, LAG(FL_FUP_EXAM_DT) OVER (
                PARTITION BY PATIENT_LOCAL_ID, DIAGNOSIS
                ORDER BY FL_FUP_EXAM_DT ASC, INVESTIGATION_KEY
            ), FL_FUP_EXAM_DT) AS DAYS1_raw
        FROM with_fup_dt
    ),
    merged AS (
        SELECT
            d.INVESTIGATION_KEY,
            d.PATIENT_NAME,
            d.PATIENT_LOCAL_ID,
            d.INV_LOCAL_ID,
            d.DIAGNOSIS,
            d.CONFIRMATION_DT,
            d.FL_FUP_EXAM_DT,
            d.PATIENTID,
            ISNULL(d.DAYS_raw, 0) AS DAYS,
            ISNULL(a.DAYS1_raw, 0) AS DAYS1
        FROM days_desc d
        INNER JOIN days_asc a ON d.INVESTIGATION_KEY = a.INVESTIGATION_KEY
    ),
    filtered AS (
        SELECT *,
            CASE
                WHEN DAYS >= -{days} AND DAYS1 <= {days}
                THEN 1 ELSE 0
            END AS meets_condition
        FROM merged
    ),
    counts AS (
        SELECT PATIENT_LOCAL_ID, DIAGNOSIS, SUM(meets_condition) AS cnt
        FROM filtered
        GROUP BY PATIENT_LOCAL_ID, DIAGNOSIS
    )
    SELECT
        f.PATIENT_NAME,
        f.PATIENT_LOCAL_ID,
        f.INV_LOCAL_ID,
        f.DIAGNOSIS,
        CONVERT(varchar, f.CONFIRMATION_DT, 101) AS CONFIRMATION_DT,
        CONVERT(varchar, f.FL_FUP_EXAM_DT, 101) AS FL_FUP_EXAM_DT,
        f.PATIENTID,
        f.DAYS,
        f.DAYS1,
        c.cnt AS COUNT
    FROM filtered f
    INNER JOIN counts c
        ON f.PATIENT_LOCAL_ID = c.PATIENT_LOCAL_ID
        AND f.DIAGNOSIS = c.DIAGNOSIS
    WHERE f.meets_condition = 1 AND c.cnt > 1
    ORDER BY f.PATIENT_NAME, f.DIAGNOSIS, f.FL_FUP_EXAM_DT, f.INVESTIGATION_KEY
    """
    content = trx.query(sql)
    return ReportResult(content_type='table', content=content)
