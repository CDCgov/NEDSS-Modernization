from src.config import get_cached_config_value
from src.db_transaction import Transaction
from src.errors import InvalidLibraryParamsError
from src.models import ReportResult


def execute(
        trx: Transaction,
        subset_query: str,
        data_source_name: str,
        library_params: dict,
        **kwargs,
):
    """TB Record Count - Summary Report by Report Date - 2020 RVCT.

    Conversion Notes:
    """
    # --- Parameter Validation ---
    if (
            not isinstance(library_params, dict)
            or 'date_column' not in library_params
            or 'status_column' not in library_params
    ):
        raise InvalidLibraryParamsError(
            "'date_column' (e.g., DATE_REPORTED) and 'status_column' "
            "(e.g., ADM_PREV_COUNT_CASE) are required library parameters."
        )

    nbs_ods = get_cached_config_value('REPORT_DB_NBS_ODS')
    nbs_srt = get_cached_config_value('REPORT_DB_NBS_SRT')
    date_column = library_params['date_column']
    status_column = library_params['status_column']

    query = f"""
        WITH subset AS (
            {subset_query}
        ),
        
        -- Pull the system description-to-code translations from NBS metadata tables
        metadata_codes AS (
            SELECT 
                cvg.CODE AS case_summary_code,
                cvg.CODE_SHORT_DESC_TXT AS case_summary_code_desc
            FROM {nbs_ods}.NBS_UI_METADATA ui
            INNER JOIN {nbs_ods}.NBS_RDB_METADATA rdb 
                ON ui.NBS_UI_METADATA_UID = rdb.NBS_UI_METADATA_UID
            INNER JOIN {nbs_srt}.CONDITION_CODE cc 
                ON cc.INVESTIGATION_FORM_CD = ui.INVESTIGATION_FORM_CD
            INNER JOIN {nbs_srt}.CODESET cs 
                ON cs.CODE_SET_GROUP_ID = ui.CODE_SET_GROUP_ID
            INNER JOIN {nbs_srt}.CODE_VALUE_GENERAL cvg 
                ON cs.CODE_SET_NM = cvg.CODE_SET_NM
            WHERE ui.QUESTION_IDENTIFIER IN ('INV1109')
              AND cc.CONDITION_CD = '102201'
        ),
        
        -- Determine report timeline boundaries strictly filtered by disease code
        boundaries AS (

        ),
        
        -- Generate the contiguous baseline calendar grid (proc expand)
        calendar AS (
            SELECT start_date AS month_date, end_date 
            FROM boundaries 
            WHERE start_date IS NOT NULL
            UNION ALL
            SELECT DATEADD(month, 1, month_date), end_date 
            FROM calendar 
            WHERE month_date < end_date
        ),
        
        -- Aggregate data into Counted vs Non-Counted buckets based on metadata code matches
        monthly_aggregates AS (
            SELECT 
                YEAR(s.{date_column}) AS case_year,
                MONTH(s.{date_column}) AS case_month,
                SUM(CASE WHEN LTRIM(RTRIM(mc.case_summary_code)) = 'N' THEN 1 ELSE 0 END) AS counted_cases,
                SUM(CASE 
                    WHEN LTRIM(RTRIM(mc.case_summary_code)) IN ('PHC659', 'PHC660') 
                         OR mc.case_summary_code IS NULL 
                    THEN 1 ELSE 0 END) AS non_counted_cases
            FROM subset s
            LEFT JOIN metadata_codes mc 
                ON mc.case_summary_code_desc = s.{status_column}
            WHERE s.{date_column} IS NOT NULL 
              AND s.disease_cd = '102201'
            GROUP BY YEAR(s.{date_column}), MONTH(s.{date_column})
        )
        
        -- Outer join calendar and aggregates to enforce zero-filled timelines
        SELECT 
            UPPER(FORMAT(c.month_date, 'MMMyyyy')) AS monthYearTxt,
            COALESCE(a.counted_cases, 0) AS counted_cases,
            COALESCE(a.non_counted_cases, 0) AS non_counted_cases,
            COALESCE(a.counted_cases, 0) + COALESCE(a.non_counted_cases, 0) AS total_cases,
            DATEDIFF(day, '1960-01-01', c.month_date) AS sasdate,
        FROM calendar c
        LEFT JOIN monthly_aggregates a 
            ON YEAR(c.month_date) = a.case_year 
           AND MONTH(c.month_date) = a.case_month
        ORDER BY c.month_date
        OPTION (MAXRECURSION 0);
    """

    content = trx.query(query)

    return ReportResult(content_type='table', content=content)
