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
    """
    days = library_params.get('report_days')
    if days is None:
        raise ValueError("library_params must contain 'days' (e.g., 30, 60, 90)")

    # Build a SQL query that performs the entire duplicate detection
    sql = f"""
    WITH subset AS (
        {subset_query}
    ),
    derived AS (
        SELECT
            s.PATIENT_NAME,
            s.PATIENT_LOCAL_ID,
            s.INV_LOCAL_ID,
            s.DIAGNOSIS,
            s.CONFIRMATION_DT,
            s.FL_FUP_EXAM_DT,
            s.REFERRAL_BASIS,
            s.INV_CASE_STATUS,
            -- Max specimen collection date per investigation
            lab.SPECIMEN_COLLECTION_DT,
            -- Diagnosis date from morbidity report
            mr.DIAGNOSIS_DT,
            -- Patient ID derived from PATIENT_LOCAL_ID
            CAST(SUBSTRING(s.PATIENT_LOCAL_ID, 4, 8) AS BIGINT) - 10000000 AS PATIENT_ID
        FROM subset s
        LEFT JOIN (
            SELECT 
                b.INVESTIGATION_KEY,
                MAX(d.SPECIMEN_COLLECTION_DT) AS SPECIMEN_COLLECTION_DT
            FROM rdb.dbo.LAB_TEST_RESULT b
            INNER JOIN rdb.dbo.LAB_TEST d ON b.LAB_TEST_KEY = d.LAB_TEST_KEY
            GROUP BY b.INVESTIGATION_KEY
        ) lab ON s.INVESTIGATION_KEY = lab.INVESTIGATION_KEY
        LEFT JOIN rdb.dbo.MORBIDITY_REPORT_EVENT mre
        ON s.INVESTIGATION_KEY = mre.INVESTIGATION_KEY
        LEFT JOIN rdb.dbo.MORBIDITY_REPORT mr ON mre.MORB_RPT_KEY = mr.MORB_RPT_KEY
        WHERE s.INV_LOCAL_ID IS NOT NULL
          AND s.DIAGNOSIS IS NOT NULL
          AND s.INV_CASE_STATUS IN ('Probable', 'Confirmed')
    ),
    with_date AS (
        SELECT
            PATIENT_NAME,
            PATIENT_ID,
            INV_LOCAL_ID,
            DIAGNOSIS,
            CONFIRMATION_DT,
            -- Derive FL_FUP_EXAM_DT
            COALESCE(
                FL_FUP_EXAM_DT,
                CASE
                    WHEN REFERRAL_BASIS = 'T1 - Positive Test'
                    THEN SPECIMEN_COLLECTION_DT
                    WHEN REFERRAL_BASIS = 'T2 - Morbidity Report'
                    THEN DIAGNOSIS_DT
                END
            ) AS FL_FUP_EXAM_DT
        FROM derived
    ),
    filtered AS (
        SELECT * FROM with_date
        WHERE FL_FUP_EXAM_DT IS NOT NULL
    ),
    diffs AS (
        SELECT
            *,
            LAG(FL_FUP_EXAM_DT) OVER (
                PARTITION BY PATIENT_NAME, DIAGNOSIS
                ORDER BY FL_FUP_EXAM_DT
            ) AS prev_date,
            LEAD(FL_FUP_EXAM_DT) OVER (
                PARTITION BY PATIENT_NAME, DIAGNOSIS
                ORDER BY FL_FUP_EXAM_DT
            ) AS next_date,
            DATEDIFF(day, LAG(FL_FUP_EXAM_DT) OVER (
                PARTITION BY PATIENT_NAME, DIAGNOSIS
                ORDER BY FL_FUP_EXAM_DT
            ), FL_FUP_EXAM_DT) AS diff_prev,
            DATEDIFF(day, FL_FUP_EXAM_DT, LEAD(FL_FUP_EXAM_DT) OVER (
                PARTITION BY PATIENT_NAME, DIAGNOSIS
                ORDER BY FL_FUP_EXAM_DT
            )) AS diff_next
        FROM filtered
    ),
    flagged AS (
        SELECT
            *,
            CASE
                WHEN diff_prev <= {days} OR diff_next <= {days} THEN 1
                ELSE 0
            END AS is_duplicate
        FROM diffs
    ),
    groups_with_dup AS (
        SELECT
            PATIENT_NAME,
            DIAGNOSIS
        FROM flagged
        WHERE is_duplicate = 1
        GROUP BY PATIENT_NAME, DIAGNOSIS
    )
    SELECT
        f.PATIENT_NAME,
        f.PATIENT_ID,
        f.DIAGNOSIS,
        f.CONFIRMATION_DT,
        f.FL_FUP_EXAM_DT
    FROM flagged f
    INNER JOIN groups_with_dup g
        ON f.PATIENT_NAME = g.PATIENT_NAME
        AND f.DIAGNOSIS = g.DIAGNOSIS
    ORDER BY f.PATIENT_NAME, f.DIAGNOSIS, f.FL_FUP_EXAM_DT
    """
    content = trx.query(sql)
    return ReportResult(content_type='table', content=content)
