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
    if not isinstance(library_params, dict):
        raise ValueError(f"library_params must be a dictionary containing 'days_value' (e.g., 30, 60, 90), got {library_params}")
    days = library_params.get('days_value')
    if days is None:
        raise ValueError(f"library_params must contain 'days_value' (e.g., 30, 60, 90), got {library_params}")

    # Build a SQL query that performs the entire duplicate detection
    sql = f"""
        WITH subset AS (
            {subset_query}
        ),
        derived AS (
            SELECT
                s.INVESTIGATION_KEY,
                s.PATIENT_NAME,
                s.PATIENT_LOCAL_ID,
                s.INV_LOCAL_ID,
                s.DIAGNOSIS,
                s.CONFIRMATION_DT,
                s.FL_FUP_EXAM_DT,
                s.REFERRAL_BASIS,
                s.INV_CASE_STATUS,
                lab.SPECIMEN_COLLECTION_DT,
                mr.DIAGNOSIS_DT,
                CAST(SUBSTRING(s.PATIENT_LOCAL_ID, 4, 8) AS BIGINT) - 10000000 AS PATIENT_ID
            FROM subset s
            LEFT JOIN (
                SELECT
                    b.INVESTIGATION_KEY,
                    MAX(d.SPECIMEN_COLLECTION_DT) AS SPECIMEN_COLLECTION_DT
                FROM [RDB].[dbo].[LAB_TEST_RESULT] b
                INNER JOIN [RDB].[dbo].[LAB_TEST] d ON b.LAB_TEST_KEY = d.LAB_TEST_KEY
                GROUP BY b.INVESTIGATION_KEY
            ) lab ON s.INVESTIGATION_KEY = lab.INVESTIGATION_KEY
            LEFT JOIN [RDB].[dbo].[MORBIDITY_REPORT_EVENT] mre
                ON s.INVESTIGATION_KEY = mre.INVESTIGATION_KEY
            LEFT JOIN [RDB].[dbo].[MORBIDITY_REPORT] mr
                ON mre.MORB_RPT_KEY = mr.MORB_RPT_KEY
            WHERE s.INV_LOCAL_ID IS NOT NULL
            AND s.DIAGNOSIS IS NOT NULL
            AND s.INV_CASE_STATUS IN ('Probable', 'Confirmed')
        ),
        with_date AS (
            SELECT
                INVESTIGATION_KEY,
                PATIENT_NAME,
                PATIENT_ID,
                INV_LOCAL_ID,
                DIAGNOSIS,
                CONFIRMATION_DT,
                COALESCE(
                    FL_FUP_EXAM_DT,
                    CASE
                        WHEN REFERRAL_BASIS = 'T1 - Positive Test' THEN SPECIMEN_COLLECTION_DT
                        WHEN REFERRAL_BASIS = 'T2 - Morbidity Report' THEN DIAGNOSIS_DT
                    END
                ) AS FL_FUP_EXAM_DT
            FROM derived
        ),
        filtered AS (
            SELECT *
            FROM with_date
            WHERE FL_FUP_EXAM_DT IS NOT NULL
        ),
        asc_diffs AS (
            -- Differences in ascending order
            SELECT
                *,
                LAG(FL_FUP_EXAM_DT) OVER (
                    PARTITION BY PATIENT_NAME, DIAGNOSIS
                    ORDER BY FL_FUP_EXAM_DT, INVESTIGATION_KEY
                ) AS prev_date_asc,
                LEAD(FL_FUP_EXAM_DT) OVER (
                    PARTITION BY PATIENT_NAME, DIAGNOSIS
                    ORDER BY FL_FUP_EXAM_DT, INVESTIGATION_KEY
                ) AS next_date_asc,
                DATEDIFF(day, LAG(FL_FUP_EXAM_DT) OVER (
                    PARTITION BY PATIENT_NAME, DIAGNOSIS
                    ORDER BY FL_FUP_EXAM_DT, INVESTIGATION_KEY
                ), FL_FUP_EXAM_DT) AS diff_prev_asc,
                DATEDIFF(day, FL_FUP_EXAM_DT, LEAD(FL_FUP_EXAM_DT) OVER (
                    PARTITION BY PATIENT_NAME, DIAGNOSIS
                    ORDER BY FL_FUP_EXAM_DT, INVESTIGATION_KEY
                )) AS diff_next_asc
            FROM filtered
        ),
        desc_diffs AS (
            -- Differences in descending order
            SELECT
                *,
                LAG(FL_FUP_EXAM_DT) OVER (
                    PARTITION BY PATIENT_NAME, DIAGNOSIS
                    ORDER BY FL_FUP_EXAM_DT DESC, INVESTIGATION_KEY
                ) AS prev_date_desc,
                LEAD(FL_FUP_EXAM_DT) OVER (
                    PARTITION BY PATIENT_NAME, DIAGNOSIS
                    ORDER BY FL_FUP_EXAM_DT DESC, INVESTIGATION_KEY
                ) AS next_date_desc,
                DATEDIFF(day, LAG(FL_FUP_EXAM_DT) OVER (
                    PARTITION BY PATIENT_NAME, DIAGNOSIS
                    ORDER BY FL_FUP_EXAM_DT DESC, INVESTIGATION_KEY
                ), FL_FUP_EXAM_DT) AS diff_prev_desc,
                DATEDIFF(day, FL_FUP_EXAM_DT, LEAD(FL_FUP_EXAM_DT) OVER (
                    PARTITION BY PATIENT_NAME, DIAGNOSIS
                    ORDER BY FL_FUP_EXAM_DT DESC, INVESTIGATION_KEY
                )) AS diff_next_desc
            FROM filtered
        ),
        combined AS (
            SELECT
                a.INVESTIGATION_KEY,
                a.PATIENT_NAME,
                a.PATIENT_ID,
                a.INV_LOCAL_ID,
                a.DIAGNOSIS,
                a.CONFIRMATION_DT,
                a.FL_FUP_EXAM_DT,
                -- Use ascending diff to next, and descending diff to previous
                a.diff_next_asc AS days1,   -- positive difference to next row in chronological order
                d.diff_prev_desc AS days    -- negative difference to previous row in reverse chronological order
            FROM asc_diffs a
            INNER JOIN desc_diffs d
                ON a.INVESTIGATION_KEY = d.INVESTIGATION_KEY
        ),
        filtered_rows AS (
            SELECT *
            FROM combined
            WHERE
                -- Treat NULL as 0 (first/last row)
                (days >= -{days} OR days IS NULL)
                AND (days1 <= {days} OR days1 IS NULL)
                AND (days IS NOT NULL OR days1 IS NOT NULL)
        )
        SELECT
            PATIENT_NAME,
            PATIENT_ID,
            DIAGNOSIS,
            CONFIRMATION_DT,
            FL_FUP_EXAM_DT
        FROM filtered_rows
        ORDER BY PATIENT_NAME, DIAGNOSIS, FL_FUP_EXAM_DT, INVESTIGATION_KEY
    """
    content = trx.query(sql)
    return ReportResult(content_type='table', content=content)
