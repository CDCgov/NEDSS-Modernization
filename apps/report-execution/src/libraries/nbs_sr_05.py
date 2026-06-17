import datetime

from src.db_transaction import Transaction
from src.models import ReportResult
from src.utils import gen_subheader


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """Standard Report 05: Cases of Reportable Diseases for a specific state.

    Each row is a disease with columns for the:
    * Current month total
    * YTD total
    * Prior YTD total
    * 5 Year Median YTD total
    * % change Current YTD vs 5 Year Median YTD

    Conversion notes:
    * Matched "export format"
    * Run doesn't include the Year and Cases columns
    * Run has columns in different order
    * Use pipe separator instead of new line for subheader
    * Remove "by county" from the description since this isn't by county
    * Negative precentages are displayed with parentheses to match SAS
      output (e.g. -0.12 gets displayed as (12%)).  The SAS script uses
      the `percent9.0` format, meaning the result is always 9 characters
      wide and there are no decimal places.  The additional character padding
      is NOT replicated in the SQL below for simplicity's sake (SQL Server
      does not have an equivalent padded format and the padding would have
      to be done by hand).
    """
    today = datetime.date.today()

    # date-based variables for use in the queries
    # lifting these to pythong (vs using CURRENT_TIMESTAMP) allows easier testing
    # by freezing time and using a set data set vs needing one always relative to today
    curr_month = today.strftime('%b%Y').upper()
    year = today.year
    month = today.month
    last_year = year - 1
    years = range(year, year - 6, -1)

    # Place holder value used when ensuring all disease+year have data
    filler_state = '<FILLER>'

    temp_table_query = f"""
    -- State filtering is assumed to happen in the filters/subset
    WITH subset as ({subset_query}),

    -- diseases CTE done without any filtering
    diseases as (
        SELECT DISTINCT phc_code_short_desc
        FROM subset
    ),

    -- all diseases and all years with zero's pre-filtering CTE ensures that row
    -- exists for all diseases and median is correct
    disease_year as (
        SELECT phc_code_short_desc, '{filler_state}' as state,
        {month} as month, year, 0 as cases
        FROM diseases,
        (VALUES {', '.join([f'({y})' for y in years])}) as year_values(year)
    ),

    -- base_data CTE
    base_data as (
        SELECT phc_code_short_desc, state, MONTH(event_date) as month,
        YEAR(event_date) as year, sum(group_case_cnt) as cases
        FROM subset
        WHERE event_date is not NULL
        AND MONTH(event_date) <= {month}
        AND YEAR(event_date) >= ({year} - 5)
        GROUP BY phc_code_short_desc, state, MONTH(event_date), YEAR(event_date)
    )

    -- base_data temp table
    SELECT *
    INTO #base_data
    FROM base_data
    UNION ALL
    SELECT *
    FROM disease_year
    """

    trx.execute(temp_table_query)

    # Because the base data is guaranteed to have a "0" entry for every year and disease
    # in the current month, we know that all diseases for all years will be present
    # and don't need to worry about null coalescing
    main_query = f"""
    -- year_data CTE
    WITH year_data as (
        SELECT phc_code_short_desc, year, SUM(cases) as cases
        FROM #base_data
        GROUP BY phc_code_short_desc, year
    ),

    -- this_month CTE
    this_month as (
        SELECT phc_code_short_desc, SUM(cases) as curr_month
        FROM #base_data
        WHERE month = {month}
        AND year = {year}
        GROUP BY phc_code_short_desc
    ),

    -- this_year CTE
    this_year as (
        SELECT phc_code_short_desc, SUM(cases) as curr_ytd
        FROM year_data
        WHERE year = {year}
        GROUP BY phc_code_short_desc
    ),

    -- last_year CTE
    last_year as (
        SELECT phc_code_short_desc, SUM(cases) as last_ytd
        FROM year_data
        WHERE year = ({year} - 1)
        GROUP BY phc_code_short_desc
    ),

    -- median_year CTE
    median_year as (
        SELECT DISTINCT phc_code_short_desc, PERCENTILE_CONT(0.5) WITHIN GROUP
        (ORDER BY cases) OVER (PARTITION BY phc_code_short_desc) as median_ytd
        FROM year_data
        WHERE year != {year}
    )

    -- Result select
    SELECT FORMAT(
             CASE
               WHEN median_ytd = 0 THEN 0
               ELSE (curr_ytd - median_ytd) / median_ytd
             END,
             '0%;(0%);0%'
           ) AS [Percent Change {year} vs 5 Year Median],
           last_ytd AS [Cumulative for {last_year} to Date],
           curr_ytd AS [Cumulative for {year} to Date],
           ty.phc_code_short_desc AS [Disease],
           {year} AS [Year],
           curr_ytd AS [Cases],
           median_ytd AS [5 Year Median Year to Date],
           curr_month AS [{curr_month}]
    FROM this_year ty
      LEFT JOIN this_month tm ON tm.phc_code_short_desc = ty.phc_code_short_desc
      LEFT JOIN last_year ly ON ly.phc_code_short_desc = ty.phc_code_short_desc
      LEFT JOIN median_year my ON my.phc_code_short_desc = ty.phc_code_short_desc
    ORDER BY ty.phc_code_short_desc ASC
    """

    content = trx.query(main_query)

    # Get the state(s) in the data set for subheader display
    states = trx.query('SELECT DISTINCT state FROM #base_data ORDER BY state')
    state_list = states.get_unique_column('state')
    state_list = [s for s in state_list if s != filler_state]

    trx.execute('DROP TABLE #base_data')

    subheader = gen_subheader(states=state_list)
    description = """
**<u>Report content</u>**

**Output:** Report demonstrates, in table form, the total number of Investigations [both Individual and Summary] irrespective of Case Status. Output:

* Does not include Investigations that have been logically deleted

* Is filtered based on the state, diseases and advanced criteria selected by user

* Will not include Investigations that do not have a value for the State selected by the user

* Is based on month and year of the calculated Event Date

**Calculations:**

* **Current Month Totals by disease:** Total Investigations [both Individual and Summary] where the Year and Month of the Event Date equal the current Year and Month

* **Current Year Totals by disease:** Total Investigations [both Individual and Summary] where the Year of the Event Date equal the current Year

* **Previous Year  Totals by disease:** Total Investigations [both Individual and Summary] where the Year of the Event Date equal last Year

* **5-Year median:** Median number of Investigations [both Individual and Summary] for the past five years

* **Percentage change (current year vs. 5 year median):** Percentage change between the Current Year Totals by disease and the 5-Year median

* **Event Date:** Derived using the hierarchy of Onset Date, Diagnosis Date, Report to County, Report to State and Date the Investigation was created in the NBS.
"""  # noqa: E501

    return ReportResult(
        content_type='table',
        content=content,
        subheader=subheader,
        description=description,
    )
