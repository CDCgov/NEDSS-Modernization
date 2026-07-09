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
    * count_column is now a required library_param
    """
    # --- Parameter Validation ---
    if not isinstance(library_params, dict) or 'count_column' not in library_params:
        raise InvalidLibraryParamsError("'count_column' is required but was absent.")

    count_column = library_params['count_column']

    query = f"""
        WITH subset AS (
            {subset_query}
        ),
        
        -- Find the first month/year and last month/year from the subset
        boundaries AS (
            SELECT 
                MIN(CAST(DATEFROMPARTS(
                    YEAR({count_column}), MONTH({count_column}), 1) AS DATE)
                ) AS start_date,
                MAX(CAST(DATEFROMPARTS(
                    YEAR({count_column}), MONTH({count_column}), 1) AS DATE)
                ) AS end_date
            FROM subset
            WHERE {count_column} IS NOT NULL
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
                YEAR({count_column}) AS case_year,
                MONTH({count_column}) AS case_month,
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
            WHERE {count_column} IS NOT NULL
            GROUP BY YEAR({count_column}), MONTH({count_column})
        )
        
        -- join to fill in missing months
        SELECT 
            UPPER(FORMAT(c.month_date, 'MMMyyyy')) AS monthYear,
            DATEDIFF(day, '1960-01-01', c.month_date) AS sasdate,
            CAST(ISNULL(a.counted_cases, 0) AS INT) AS counted_cases,
            CAST(ISNULL(a.non_counted_cases, 0) AS INT) AS non_counted_cases,
            CAST(
                ISNULL(a.counted_cases, 0) 
                + ISNULL(a.non_counted_cases, 0) AS INT
            ) AS total_cases
        FROM calendar c
        LEFT JOIN monthly_aggregates a 
            ON YEAR(c.month_date) = a.case_year 
           AND MONTH(c.month_date) = a.case_month
        ORDER BY c.month_date
        OPTION (MAXRECURSION 0);
    """

    content = trx.query(query)

    return ReportResult(content_type='table', content=content)
