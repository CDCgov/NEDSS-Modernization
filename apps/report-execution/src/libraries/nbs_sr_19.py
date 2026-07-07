from datetime import date
from src.db_transaction import Transaction
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
    * Tailored for SQL Server (T-SQL) using YEAR() and MONTH().
    * Aggregation is pushed to SQL Server via CTEs.
    * Python handles time-series expansion to fill in 0-count month gaps.
    """

    # --- Helper Function ---
    def get_sas_date(year, month, day=1):
        sas_epoch = date(1960, 1, 1)
        target_date = date(year, month, day)
        return (target_date - sas_epoch).days

    if not isinstance(library_params, dict):
        raise ValueError(f"""
            library_params must be a dictionary containing 'count_column' \
            (e.g., COUNT_DATE, DATE_REPORTED), got {library_params}
        """)
    count_column = library_params.get('count_column')
    if count_column is None:
        raise ValueError(f"""
            library_params must be a dictionary containing 'count_column' \
            (e.g., COUNT_DATE, DATE_REPORTED), got {library_params}
        """)

    # --- Step 1: Push Aggregation to SQL Server via CTE ---
    query = f"""
        WITH subset AS (
            {subset_query}
        ),
        monthly_aggregates AS (
            SELECT 
                YEAR({count_column}) AS case_year,
                MONTH({count_column}) AS case_month,
                SUM(CASE WHEN count_status = 'Count as a TB Case' THEN 1 ELSE 0 END) AS counted,
                SUM(CASE WHEN count_status != 'Count as a TB Case' OR count_status IS NULL THEN 1 ELSE 0 END) AS non_counted
            FROM subset
            WHERE {count_column} IS NOT NULL
            GROUP BY YEAR({count_column}), MONTH({count_column})
        )
        SELECT case_year, case_month, counted, non_counted 
        FROM monthly_aggregates
        ORDER BY case_year, case_month
    """

    db_rows = trx.query(query)

    if not db_rows:
        return ReportResult(content_type="table", content=[])

    # --- Step 2: Map DB results into a quick-lookup dictionary ---
    # Mapping: (year, month) -> (counted, non_counted)
    db_map = {}
    for row in db_rows:
        y = (
            int(row.get("case_year"))
            if isinstance(row, dict)
            else int(row.case_year)
        )
        m = (
            int(row.get("case_month"))
            if isinstance(row, dict)
            else int(row.case_month)
        )
        counted = (
            int(row.get("counted")) if isinstance(row, dict) else int(row.counted)
        )
        non_counted = (
            int(row.get("non_counted"))
            if isinstance(row, dict)
            else int(row.non_counted)
        )
        db_map[(y, m)] = (counted, non_counted)

    # --- Step 3: Determine Date Boundaries from the Aggregated Rows ---
    years_months = list(db_map.keys())
    start_year, start_month = years_months[0]
    end_year, end_month = years_months[-1]

    start_date = date(start_year, start_month, 1)
    end_date = date(end_year, end_month, 1)

    # --- Step 4: Time-series Generation (Gap-Filling) & SAS Date Calculation ---
    content = []
    current = start_date

    while current <= end_date:
        y, m = current.year, current.month

        # Pull from our SQL aggregate map, defaulting to 0 if the month didn't exist in DB
        counted, non_counted = db_map.get((y, m), (0, 0))
        total = counted + non_counted

        content.append(
            {
                "monthYear": current.strftime("%b%Y").upper(),
                "sasdate": get_sas_date(y, m, 1),
                "counted_cases": counted,
                "non_counted_cases": non_counted,
                "total_cases": total,
            }
        )

        # Move to next month
        if current.month == 12:
            current = date(current.year + 1, 1, 1)
        else:
            current = date(current.year, current.month + 1, 1)

    return ReportResult(content_type="table", content=content)
