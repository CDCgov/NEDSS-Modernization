from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    library_params: dict,
    **kwargs,
):
    """
    QA07: Duplicate Cases. The Report will consist of 3 reports, identical
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
        FROM RDB.dbo.INVESTIGATION i
        INNER JOIN RDB.dbo.EVENT_METRIC e
            ON i.CASE_UID = e.EVENT_UID
        INNER JOIN RDB.dbo.USER_PROFILE up
            ON e.ADD_USER_ID = up.NEDSS_ENTRY_ID
    ),
    lab_max AS (
        SELECT
            b.INVESTIGATION_KEY,
            MAX(d.SPECIMEN_COLLECTION_DT) AS SPECIMEN_COLLECTION_DT
        FROM RDB.dbo.LAB_TEST_RESULT b
        INNER JOIN RDB.dbo.LAB_TEST d ON b.LAB_TEST_KEY = d.LAB_TEST_KEY
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
        LEFT JOIN RDB.dbo.MORBIDITY_REPORT_EVENT mre
            ON s.INVESTIGATION_KEY = mre.INVESTIGATION_KEY
        LEFT JOIN RDB.dbo.MORBIDITY_REPORT mr
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
                    WHEN REFERRAL_BASIS = 'T1 - Positive Test' THEN SPECIMEN_COLLECTION_DT
                    WHEN REFERRAL_BASIS = 'T2 - Morbidity Report' THEN DIAGNOSIS_DT
                END
            ) AS FL_FUP_EXAM_DT
        FROM derived
        WHERE ORIG_FL_FUP_EXAM_DT IS NOT NULL
           OR (REFERRAL_BASIS = 'T1 - Positive Test' AND SPECIMEN_COLLECTION_DT IS NOT NULL)
           OR (REFERRAL_BASIS = 'T2 - Morbidity Report' AND DIAGNOSIS_DT IS NOT NULL)
    ),
    with_prev_asc AS (
        SELECT
            *,
            LAG(FL_FUP_EXAM_DT) OVER (
                PARTITION BY PATIENT_NAME, DIAGNOSIS
                ORDER BY FL_FUP_EXAM_DT, INVESTIGATION_KEY
            ) AS prev_date_asc
        FROM with_fup_dt
    ),
    cluster_starts AS (
        SELECT
            *,
            CASE
                WHEN prev_date_asc IS NULL
                     OR DATEDIFF(day, prev_date_asc, FL_FUP_EXAM_DT) > {days}
                THEN 1
                ELSE 0
            END AS new_cluster
        FROM with_prev_asc
    ),
    clustered_rows AS (
        SELECT
            *,
            SUM(new_cluster) OVER (
                PARTITION BY PATIENT_NAME, DIAGNOSIS
                ORDER BY FL_FUP_EXAM_DT, INVESTIGATION_KEY
            ) AS cluster_id
        FROM cluster_starts
    ),
    days_calc AS (
        SELECT
            *,
            LAG(FL_FUP_EXAM_DT) OVER (
                PARTITION BY PATIENT_NAME, DIAGNOSIS, cluster_id
                ORDER BY FL_FUP_EXAM_DT DESC, INVESTIGATION_KEY
            ) AS lag_desc
        FROM clustered_rows
    ),
    days1_calc AS (
        SELECT
            *,
            LEAD(FL_FUP_EXAM_DT) OVER (
                PARTITION BY PATIENT_NAME, DIAGNOSIS, cluster_id
                ORDER BY FL_FUP_EXAM_DT ASC, INVESTIGATION_KEY
            ) AS lead_asc
        FROM days_calc
    ),
    with_diffs AS (
        SELECT
            *,
            ISNULL(
                CASE WHEN lag_desc IS NOT NULL
                     THEN DATEDIFF(day, lag_desc, FL_FUP_EXAM_DT)
                END, 0
            ) AS DAYS,
            ISNULL(
                CASE WHEN lead_asc IS NOT NULL
                     THEN DATEDIFF(day, FL_FUP_EXAM_DT, lead_asc)
                END, 0
            ) AS DAYS1
        FROM days1_calc
    ),
    flagged AS (
        SELECT
            *,
            CASE
                WHEN DAYS >= -{days} AND DAYS1 <= {days} THEN 1
                ELSE 0
            END AS meets_condition
        FROM with_diffs
    ),
    counts AS (
        SELECT
            PATIENT_NAME,
            DIAGNOSIS,
            SUM(meets_condition) AS cnt
        FROM flagged
        GROUP BY PATIENT_NAME, DIAGNOSIS
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
    FROM flagged f
    INNER JOIN counts c
        ON (f.PATIENT_NAME = c.PATIENT_NAME OR (f.PATIENT_NAME IS NULL AND c.PATIENT_NAME IS NULL))
        AND (f.DIAGNOSIS = c.DIAGNOSIS OR (f.DIAGNOSIS IS NULL AND c.DIAGNOSIS IS NULL))
    WHERE f.meets_condition = 1
      AND c.cnt > 1
    ORDER BY f.PATIENT_NAME, f.DIAGNOSIS, f.FL_FUP_EXAM_DT, f.INVESTIGATION_KEY
    """
    content = trx.query(sql)
    return ReportResult(content_type='table', content=content)