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
    * Parameterized date column handled safely.
    * Pushes heavy aggregations to SQL Server using fast integer date functions.
    """

    # --- Helper Function ---
    def get_sas_date(year, month, day=1):
        sas_epoch = date(1960, 1, 1)
        target_date = date(year, month, day)
        return (target_date - sas_epoch).days

    # --- Parameter Validation ---
    if not isinstance(library_params, dict) or 'count_column' not in library_params:
        raise ValueError(
            f"library_params must be a dictionary containing 'count_column'. Got: {library_params}"
        )

    count_column = library_params['count_column']

    query = f"""
        WITH subset AS (
            {subset_query}
        ),
        monthly_aggregates AS (
            SELECT 
                YEAR({count_column}) AS case_year,
                MONTH({count_column}) AS case_month,
                SUM(CASE WHEN count_status = 'Count as a TB Case' THEN 1 ELSE 0 END) AS counted_cases,
                SUM(CASE WHEN count_status != 'Count as a TB Case' OR count_status IS NULL THEN 1 ELSE 0 END) AS non_counted_cases
            FROM subset
            WHERE {count_column} IS NOT NULL
            GROUP BY YEAR({count_column}), MONTH({count_column})
        )
        SELECT case_year, case_month, counted_cases, non_counted_cases
        FROM monthly_aggregates
        ORDER BY case_year, case_month
    """

    db_rows = trx.query(query)

    # Define expected columns early
    columns = ["monthYear", "sasdate", "counted_cases", "non_counted_cases", "total_cases"]

    # Short circuit on empty data with full columns schema ---
    if not db_rows or (hasattr(db_rows, 'data') and not db_rows.data):
        return ReportResult(content_type="table", content={"columns": columns, "data": []})

    db_map = {}
    for row in db_rows.data:
        y = int(row[0])
        m = int(row[1])
        counted = int(row[2])
        non_counted = int(row[3])

        db_map[(y, m)] = (counted, non_counted)

    # --- Track timeline boundaries ---
    years_months = list(db_map.keys())
    start_date = date(years_months[0][0], years_months[0][1], 1)
    end_date = date(years_months[-1][0], years_months[-1][1], 1)

    # --- MonthYear Matrix Build to fill in months with no cases ---
    data_matrix = []
    current = start_date

    while current <= end_date:
        y, m = current.year, current.month

        counted, non_counted = db_map.get((y, m), (0, 0))
        total = counted + non_counted

        month_year_txt = current.strftime("%b%Y").upper()
        sas_date_val = get_sas_date(y, m, 1)

        data_matrix.append([
            month_year_txt,
            sas_date_val,
            counted,
            non_counted,
            total
        ])

        if current.month == 12:
            current = date(current.year + 1, 1, 1)
        else:
            current = date(current.year, current.month + 1, 1)

    # --- Package ReportResult content ---
    content = {
        "columns": columns,
        "data": data_matrix
    }

    return ReportResult(content_type="table", content=content)
