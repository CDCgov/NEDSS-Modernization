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
    """SR19: TB Record Count - Count of cases by "countability" and month.

    Conversion Notes:
    * Parameterized date column handled safely. This change allowed for the
    merging of SR20 into SR19 as the input date column was the only difference
    * date_column is now a required library_param
    * total_cases will always be the SUM total. The SAS library did not include
    the total when one of the count_values was 0.
    """
    # --- Parameter Validation ---
    if not isinstance(library_params, dict) or 'date_column' not in library_params:
        raise InvalidLibraryParamsError("'date_column' is required but was absent.")

    date_column = library_params['date_column']

    query = f"""
        WITH subset AS (
            {subset_query}
        ),
        
        -- Find the first month/year and last month/year from the subset
        boundaries AS (
            SELECT 
                MIN(DATEFROMPARTS(
                    YEAR({date_column}), MONTH({date_column}), 1))
                ) AS start_date,
                MAX(DATEFROMPARTS(
                    YEAR({date_column}), MONTH({date_column}), 1))
                ) AS end_date
            FROM subset
            WHERE {date_column} IS NOT NULL
        ),
        
        -- compile list of month/years filling in missing months
        calendar AS (
            SELECT start_date AS month_date, end_date 
            FROM boundaries 
            WHERE start_date IS NOT NULL
            UNION ALL
            SELECT DATEADD(month, 1, month_date), end_date 
            FROM calendar 
            WHERE month_date < end_date
        ),
        
        -- compile the case counts
        monthly_aggregates AS (
            SELECT 
                YEAR({date_column}) AS case_year,
                MONTH({date_column}) AS case_month,
                SUM(CASE 
                        WHEN count_status = 'Count as a TB Case' 
                            THEN 1 
                        ELSE 0 END
                    ) AS counted_cases,
                SUM(CASE 
                        WHEN count_status != 'Count as a TB Case' 
                            OR count_status IS NULL 
                                THEN 1 
                        ELSE 0 END
                    ) AS non_counted_cases
            FROM subset
            WHERE {date_column} IS NOT NULL
            GROUP BY YEAR({date_column}), MONTH({date_column})
        )
        
        -- join to fill in missing months
        SELECT 
            UPPER(FORMAT(c.month_date, 'MMMyyyy')) AS monthYear,
            DATEDIFF(day, '1960-01-01', c.month_date) AS sasdate,
            COALESCE(a.counted_cases, 0) AS counted_cases,
            COALESCE(a.non_counted_cases, 0) AS non_counted_cases,
            COALESCE(a.counted_cases, 0) + COALESCE(a.non_counted_cases, 0)
                AS total_cases
        FROM calendar c
        LEFT JOIN monthly_aggregates a 
            ON YEAR(c.month_date) = a.case_year 
           AND MONTH(c.month_date) = a.case_month
        ORDER BY c.month_date
        OPTION (MAXRECURSION 0);
    """

    content = trx.query(query)

    return ReportResult(content_type='table', content=content)
