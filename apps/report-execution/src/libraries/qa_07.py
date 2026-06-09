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
    days = library_params.get("report_days")
    if days is None:
        raise ValueError("library_params must contain 'days' (e.g., 30, 60, 90)")

    # Build a SQL query that performs the entire duplicate detection
    sql = f"""
    WITH derived AS (
        SELECT
            PATIENT_NAME,
            CAST(SUBSTR(PATIENT_LOCAL_ID, 4, 8) AS INT) - 10000000 AS PATIENT_ID,
            INV_LOCAL_ID,
            DIAGNOSIS,
            CONFIRMATION_DT,
            -- Derive FL_FUP_EXAM_DT
            COALESCE(
                FL_FUP_EXAM_DT,
                CASE
                    WHEN REFERRAL_BASIS = 'T1 - Positive Test' THEN SPECIMEN_COLLECTION_DT
                    WHEN REFERRAL_BASIS = 'T2 - Morbidity Report' THEN DIAGNOSIS_DT
                END
            ) AS FL_FUP_EXAM_DT
        FROM STD_HIV_DATAMART
        WHERE INV_LOCAL_ID IS NOT NULL
          AND DIAGNOSIS IS NOT NULL
          AND INV_CASE_STATUS IN ('Probable', 'Confirmed')
    ),
    -- Only rows with a valid FL_FUP_EXAM_DT
    with_date AS (
        SELECT * FROM derived
        WHERE FL_FUP_EXAM_DT IS NOT NULL
    ),
    -- Compute differences to previous and next exam dates within each group
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
            -- Difference from previous
            DATEDIFF(day, LAG(FL_FUP_EXAM_DT) OVER (
                PARTITION BY PATIENT_NAME, DIAGNOSIS
                ORDER BY FL_FUP_EXAM_DT
            ), FL_FUP_EXAM_DT) AS diff_prev,
            -- Difference to next
            DATEDIFF(day, FL_FUP_EXAM_DT, LEAD(FL_FUP_EXAM_DT) OVER (
                PARTITION BY PATIENT_NAME, DIAGNOSIS
                ORDER BY FL_FUP_EXAM_DT
            )) AS diff_next
        FROM with_date
    ),
    -- Flag rows that are within 'days' of a neighbor
    flagged AS (
        SELECT
            *,
            CASE
                WHEN diff_prev <= {days} OR diff_next <= {days} THEN 1
                ELSE 0
            END AS is_duplicate
        FROM diffs
    ),
    -- Keep only groups that contain at least one flagged row (i.e., duplicate group)
    groups_with_dup AS (
        SELECT
            PATIENT_NAME,
            DIAGNOSIS
        FROM flagged
        WHERE is_duplicate = 1
        GROUP BY PATIENT_NAME, DIAGNOSIS
    )
    -- Final output: all rows from groups that have duplicates
    SELECT
        f.PATIENT_NAME,
        f.PATIENT_ID,
        f.DIAGNOSIS,
        f.CONFIRMATION_DT,
        f.FL_FUP_EXAM_DT
    FROM flagged f
    JOIN groups_with_dup g
        ON f.PATIENT_NAME = g.PATIENT_NAME
        AND f.DIAGNOSIS = g.DIAGNOSIS
    ORDER BY f.PATIENT_NAME, f.DIAGNOSIS, f.FL_FUP_EXAM_DT
    """
    df = trx.query(sql)
    return ReportResult(content_type="table", content=df, header=header)