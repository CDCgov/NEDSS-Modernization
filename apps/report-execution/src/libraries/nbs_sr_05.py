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
        + 'SELECT state_cd, state, phc_code_short_desc, MONTH(event_date) as month, '
        + 'YEAR(event_date) as year, sum(group_case_cnt) as cases\n'
        + 'FROM subset\n'
        + 'WHERE state is not NULL\n'
        + 'AND event_date is not NULL\n'
        + 'AND DATEPART(dayofyear, event_date) <= '
        + '        DATEPART(dayofyear, CURRENT_TIMESTAMP)\n'
        + 'AND YEAR(event_date) >= (YEAR(CURRENT_TIMESTAMP) - 5)\n'
        + 'GROUP BY state_cd, state, phc_code_short_desc, '
        + 'MONTH(event_date), YEAR(event_date)\n'
        + 'ORDER BY phc_code_short_desc, YEAR(event_date), MONTH(event_date)'
    )

    header = 'SR5: Cases of Reportable Diseases by State'
    subheader = None
    if time_range is not None:
        subheader = f'\n{time_range.start} - {time_range.end}'

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
