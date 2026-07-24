from src.config import get_cached_config_value
from src.db_transaction import Transaction
from src.errors import InvalidLibraryParamsError
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    library_params: dict,
    **kwargs,
):
    """TB Record Count - Summary Report by Report Date - 2020 RVCT.

    Conversion Notes:
    * Parameterized date column handled safely. This change allowed for the
        merging of tb_summary_count into tb_summary_count_date as the input date
        column was the only difference
    * date_column is a required library_param
    * total_cases will always be the SUM total. The SAS library did not include
        the total when one of the count_values was 0.
    * The date parameters are not passed into the python. Instead start and end
      dates are derived from the output data. The outcome is the oldest and
      newest dates in the output may not equal the From Date and To Date.
    """
    nbs_ods = get_cached_config_value('REPORT_DB_NBS_ODS')
    nbs_srt = get_cached_config_value('REPORT_DB_NBS_SRT')

    if not isinstance(library_params, dict) or 'date_column' not in library_params:
        raise InvalidLibraryParamsError("'date_column' is required but was absent.")

    date_column = library_params['date_column']  # INV_RPT_DT
    query = f"""
        WITH subset AS (
            {subset_query}
        ),
        
        -- Pull the system description-to-code translations from NBS metadata tables
        metadata_codes AS (
            SELECT 
                cvg.CODE AS case_summary_code,
                cvg.CODE_SHORT_DESC_TXT AS case_summary_code_desc
            FROM {nbs_ods}.dbo.NBS_UI_METADATA ui
            INNER JOIN {nbs_ods}.dbo.NBS_RDB_METADATA rdb 
                ON ui.NBS_UI_METADATA_UID = rdb.NBS_UI_METADATA_UID
            INNER JOIN {nbs_srt}.dbo.CONDITION_CODE cc 
                ON cc.INVESTIGATION_FORM_CD = ui.INVESTIGATION_FORM_CD
            INNER JOIN {nbs_srt}.dbo.CODESET cs 
                ON cs.CODE_SET_GROUP_ID = ui.CODE_SET_GROUP_ID
            INNER JOIN {nbs_srt}.dbo.CODE_VALUE_GENERAL cvg 
                ON cs.CODE_SET_NM = cvg.CODE_SET_NM
            WHERE ui.QUESTION_IDENTIFIER IN ('INV1109')
              AND cc.CONDITION_CD = '102201'
        ),
        
        -- Determine report timeline boundaries strictly filtered by disease code
        boundaries AS (
            SELECT 
                MIN(
                    DATEFROMPARTS(
                        YEAR({date_column}), MONTH({date_column}), 1)
                ) AS start_date,
                MAX(
                    DATEFROMPARTS(
                        YEAR({date_column}), MONTH({date_column}), 1)
                ) AS end_date
            FROM subset
            WHERE {date_column} IS NOT NULL 
              AND disease_cd = '102201'
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
        
        -- Aggregate data into Counted vs Non-Counted buckets
        monthly_aggregates AS (
            SELECT 
                YEAR(s.{date_column}) AS case_year,
                MONTH(s.{date_column}) AS case_month,
                SUM(CASE 
                    WHEN TRIM(mc.case_summary_code) = 'N' THEN 1 
                    ELSE 0 END) AS counted_cases,
                SUM(CASE 
                    WHEN TRIM(mc.case_summary_code) IN ('PHC659', 'PHC660') 
                         OR mc.case_summary_code IS NULL 
                    THEN 1 
                    ELSE 0 END) AS non_counted_cases
            FROM subset s
            LEFT JOIN metadata_codes mc 
                ON mc.case_summary_code_desc = s.PREV_COUNT_CASE
            WHERE s.{date_column} IS NOT NULL 
              AND s.disease_cd = '102201'
            GROUP BY YEAR(s.{date_column}), MONTH(s.{date_column})
        )
        
        -- Outer join calendar and aggregates to enforce zero-filled timelines
        SELECT 
            LEFT(FORMAT(c.month_date, 'MMMM') + SPACE(15), 15) 
                + FORMAT(c.month_date, 'yyyy') AS monthYearTxt,
            UPPER(FORMAT(c.month_date, 'MMMyyyy')) AS monthYear,
            DATEDIFF(day, '1960-01-01', c.month_date) AS sasdate,
            COALESCE(a.counted_cases, 0) AS counted_cases,
            COALESCE(a.non_counted_cases, 0) AS non_counted_cases,
            COALESCE(a.counted_cases, 0) 
                + COALESCE(a.non_counted_cases, 0) AS total_cases
            
        FROM calendar c
        LEFT JOIN monthly_aggregates a 
            ON YEAR(c.month_date) = a.case_year 
           AND MONTH(c.month_date) = a.case_month
        ORDER BY c.month_date
        OPTION (MAXRECURSION 0);
    """

    content = trx.query(query)

    return ReportResult(content_type='table', content=content)
