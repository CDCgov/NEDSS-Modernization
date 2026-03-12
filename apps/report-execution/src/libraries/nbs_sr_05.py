from src.db_transaction import Transaction
from src.models import ReportResult, TimeRange


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    time_range: TimeRange | None = None,
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
    * Lifted the size check to a global check and refined the env var names
    * Date formatting still needs to be figured out
    """
    content = trx.query(
        f'WITH subset as ({subset_query})\n'

        ', base_data as ('
        'SELECT state_cd, state, phc_code_short_desc, MONTH(event_date) as month, '
        'YEAR(event_date) as year, sum(group_case_cnt) as cases\n'
        'FROM subset\n'
        'WHERE state is not NULL\n'
        'AND event_date is not NULL\n'
        'AND DATEPART(dayofyear, event_date) <= '
        '        DATEPART(dayofyear, CURRENT_TIMESTAMP)\n'
        'AND YEAR(event_date) >= (YEAR(CURRENT_TIMESTAMP) - 5)\n'
        'GROUP BY state_cd, state, phc_code_short_desc, '
        'MONTH(event_date), YEAR(event_date)\n'
        ')\n'

        ', diseases as (\n'
        'SELECT DISTINCT phc_code_short_desc\n'
        'FROM base_data\n'
        ')\n'

        ', year_data as (\n'
        'SELECT phc_code_short_desc, year, SUM(cases) as cases\n'
        'FROM base_data\n'
        'GROUP BY phc_code_short_desc, year'
        ')\n'

        ', this_month as (\n'
        'SELECT phc_code_short_desc, SUM(cases) as curr_month\n'
        'FROM base_data\n'
        'WHERE month = MONTH(CURRENT_TIMESTAMP)\n'
        'AND year = YEAR(CURRENT_TIMESTAMP)\n'
        'GROUP BY phc_code_short_desc'
        ')\n'

        ', this_year as (\n'
        'SELECT phc_code_short_desc, SUM(cases) as curr_ytd\n'
        'FROM year_data\n'
        'WHERE year = YEAR(CURRENT_TIMESTAMP)\n'
        'GROUP BY phc_code_short_desc'
        ')\n'

        ', last_year as (\n'
        'SELECT phc_code_short_desc, SUM(cases) as last_ytd\n'
        'FROM year_data\n'
        'WHERE year = (YEAR(CURRENT_TIMESTAMP) - 1)\n'
        'GROUP BY phc_code_short_desc'
        ')\n'

        ', median_year as (\n'
        'SELECT DISTINCT phc_code_short_desc, PERCENTILE_CONT(0.5) WITHIN GROUP '
        '(ORDER BY cases) OVER (PARTITION BY phc_code_short_desc) as median_ytd\n'
        'FROM year_data\n'
        ')\n'
        
        'SELECT d.phc_code_short_desc, COALESCE(curr_month, 0) as curr_month, \n'
        'COALESCE(curr_ytd, 0) as curr_ytd, COALESCE(last_ytd, 0) as last_ytd, \n'
        'COALESCE(median_ytd, 0) as median_ytd, \n'
        'IIF('
        '  COALESCE(median_ytd, 0) = 0, '
        '  0, '
        '  COALESCE((curr_ytd - median_ytd) / median_ytd, 0)) as pct_chg\n'
        'FROM diseases d\n'
        'LEFT JOIN this_month tm on tm.phc_code_short_desc = d.phc_code_short_desc\n'
        'LEFT JOIN this_year ty on ty.phc_code_short_desc = d.phc_code_short_desc\n'
        'LEFT JOIN last_year ly on ly.phc_code_short_desc = d.phc_code_short_desc\n'
        'LEFT JOIN median_year my on my.phc_code_short_desc = d.phc_code_short_desc\n'
        'ORDER BY d.phc_code_short_desc asc'
    )

    header = 'SR5: Cases of Reportable Diseases by State'
    subheader = None
    if time_range is not None:
        subheader = f'{time_range.start} - {time_range.end}'

    description = (
        '*Data Source:* nbs_ods.PHCDemographic (publichealthcasefact)\n'
        '*Output:* Report demonstrates, in table form, the total number of '
        'Investigation(s) [both Individual and Summary] by County irrespective of '
        'Case Status.\n'
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
