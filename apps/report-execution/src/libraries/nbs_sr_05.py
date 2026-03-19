import datetime

from src.db_transaction import Transaction
from src.models import ReportResult


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
    * Export included the year as a column
    * Export has columns in different order
    * Use pipe separator instead of new line for subheader
    * Remove "by county" from the description since this isn't by county
    """
    today = datetime.date.today()

    # date-based variables for use in the queries
    # lifting these to pythong (vs using CURRENT_TIMESTAMP) allows easier testing
    # by freezing time and using a set data set vs needing one always relative to today
    curr_month = today.strftime('%b%Y').upper()
    year = today.year
    month = today.month
    day_of_year = int(today.strftime('%j'))
    last_year = year - 1
    years = range(year, year - 6, -1)

    # Place holder value used when ensuring all disease+year have data
    filler_state = '<FILLER>'

    trx.execute(
        # State filtering is assumed to happen in the filters/subset
        f'WITH subset as ({subset_query})\n'
        # diseases CTE done without any filtering
        ', diseases as (\n'
        'SELECT DISTINCT phc_code_short_desc\n'
        'FROM subset\n'
        ')\n'
        # all diseases and all years with zero's pre-filtering CTE
        # ensures that row exists for all diseases and median is correct
        ', disease_year as (\n'
        f"SELECT phc_code_short_desc, '{filler_state}' as state, "
        f'{month} as month, year, 0 as cases\n'
        'FROM diseases, \n'
        f'(VALUES {", ".join([f"({y})" for y in years])}) as year_values(year)\n'
        ')\n'
        # base_data CTE
        ', base_data as (\n'
        'SELECT phc_code_short_desc, state, MONTH(event_date) as month, '
        'YEAR(event_date) as year, sum(group_case_cnt) as cases\n'
        'FROM subset\n'
        'WHERE event_date is not NULL\n'
        f'AND DATEPART(dayofyear, event_date) <= {day_of_year}\n'
        f'AND YEAR(event_date) > ({year} - 5)\n'
        'GROUP BY phc_code_short_desc, state, MONTH(event_date), YEAR(event_date)\n'
        ')\n'
        # base_data temp table
        'SELECT *\n'
        'INTO #base_data\n'
        'FROM base_data\n'
        'UNION ALL\n'
        'SELECT * FROM disease_year\n'
    )

    # Because the base data is guaranteed to have a "0" entry for every year and disease
    # in the current month, we know that all diseases for all years will be present
    # and don't need to worry about null coalescing
    content = trx.query(
        # year_data CTE
        'WITH year_data as (\n'
        'SELECT phc_code_short_desc, year, SUM(cases) as cases\n'
        'FROM #base_data\n'
        'GROUP BY phc_code_short_desc, year'
        ')\n'
        # this_month CTE
        ', this_month as (\n'
        'SELECT phc_code_short_desc, SUM(cases) as curr_month\n'
        'FROM #base_data\n'
        f'WHERE month = {month}\n'
        f'AND year = {year}\n'
        'GROUP BY phc_code_short_desc'
        ')\n'
        # this_year CTE
        ', this_year as (\n'
        'SELECT phc_code_short_desc, SUM(cases) as curr_ytd\n'
        'FROM year_data\n'
        f'WHERE year = {year}\n'
        'GROUP BY phc_code_short_desc'
        ')\n'
        # last_year CTE
        ', last_year as (\n'
        'SELECT phc_code_short_desc, SUM(cases) as last_ytd\n'
        'FROM year_data\n'
        f'WHERE year = ({year} - 1)\n'
        'GROUP BY phc_code_short_desc'
        ')\n'
        # median_year CTE
        ', median_year as (\n'
        'SELECT DISTINCT phc_code_short_desc, PERCENTILE_CONT(0.5) WITHIN GROUP '
        '(ORDER BY cases) OVER (PARTITION BY phc_code_short_desc) as median_ytd\n'
        'FROM year_data\n'
        f'WHERE year != {year}\n'
        ')\n'
        # Result select
        'SELECT \n'
        'FORMAT('
        '  IIF('
        '    median_ytd = 0, '
        '    0, '
        '    (curr_ytd - median_ytd) / median_ytd'
        '  ), '
        "  'P0', 'en-us'"
        ')'
        f'       as [Percent Change {year} vs 5 Year Median], \n'
        f'last_ytd as [Cumulative for {last_year} to Date], \n'
        f'curr_ytd as [Cumulative for {year} to Date], \n'
        'ty.phc_code_short_desc as [Disease], \n'
        f'{year} as [Year], \n'
        'curr_ytd as [Cases], \n'
        'median_ytd as [5 Year Median Year to Date], \n'
        f'curr_month as [{curr_month}] \n'
        'FROM this_year ty\n'
        'LEFT JOIN this_month tm on tm.phc_code_short_desc = ty.phc_code_short_desc\n'
        'LEFT JOIN last_year ly on ly.phc_code_short_desc = ty.phc_code_short_desc\n'
        'LEFT JOIN median_year my on my.phc_code_short_desc = ty.phc_code_short_desc\n'
        'ORDER BY ty.phc_code_short_desc asc'
    )

    # Get the state(s) in the data set for subheader display
    states = trx.query('SELECT DISTINCT state FROM #base_data ORDER BY state')
    state_list = [
        row[0] if row[0] is not None else 'N/A'
        for row in states.data
        if row[0] != filler_state
    ]

    trx.execute('DROP TABLE #base_data')

    header = 'SR5: Cases of Reportable Diseases by State'
    subheader = f'{", ".join(state_list)} | {today.strftime("%m/%d/%Y")}'
    description = (
        '*<u>Report Content</u>*\n'
        '*Data Source:* nbs_ods.PHCDemographic (publichealthcasefact)\n'
        '*Output:* Report demonstrates, in table form, the total number of '
        'Investigation(s) [both Individual and Summary] irrespective of Case Status.\n'
        'Output:\n'
        '* Does not include Investigation(s) that have been logically deleted\n'
        '* Is filtered based on the state, disease(s) and advanced criteria selected '
        'by user\n'
        '* Will not include Investigation(s) that do not have a value for the State '
        'selected by the user\n'
        '* Is based on month and year of the calculated Event Date\n'
        '*Calculations:*'
        '* *Current Month Totals by disease:* Total Investigation(s) [both Individual '
        'and Summary] where the Year and Month of the Event Date equal the current '
        'Year and Month\n'
        '* *Current Year Totals by disease:* Total Investigation(s) [both Individual '
        'and Summary] where the Year of the Event Date equal the current Year\n'
        '* *Previous Year  Totals by disease:* Total Investigation(s) [both '
        'Individual and Summary] where the Year of the Event Date equal last Year\n'
        '* *5-Year median:* Median number of Investigation(s) [both Individual and '
        'Summary] for the past five years\n'
        '* *Percentage change (current year vs. 5 year median):* Percentage change '
        'between the Current Year Totals by disease and the 5-Year median\n'
        ' * *Event Date:* Derived using the hierarchy of Onset Date, Diagnosis Date, '
        'Report to County, Report to State and Date the Investigation was created in '
        'the NBS.\n'
    )

    return ReportResult(
        content_type='table',
        content=content,
        header=header,
        subheader=subheader,
        description=description,
    )
