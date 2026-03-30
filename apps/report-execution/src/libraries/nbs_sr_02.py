from src.db_transaction import Transaction
from src.models import ReportResult, TimeRange


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    time_range: TimeRange | None = None,
    **kwargs,
):
    """Standard Report 02: Cases of Reportable Diseases by County for Selected Time
    Frame.

    Conversion notes:
    * Matched export format without pivot
    * Removed calculations section of descriptions as those were run-format specific
    * Capitalized the `F` in `Time Frame`
    """
    content = trx.query(
        f'WITH subset as ({subset_query})\n'
        + 'SELECT state as State, county as County, phc_code_short_desc as Condition, '
        'sum(group_case_cnt) as Cases\n'
        + 'FROM subset\n'
        + 'GROUP BY state, county, phc_code_short_desc\n'
        + 'ORDER BY state, county, phc_code_short_desc'
    )

    # Get the unique state(s) in the data set for subheader display
    state_list = list(
        set([row[0] if row[0] is not None else 'N/A' for row in content.data])
    )
    state_list.sort()

    header = 'SR2: Counts of Reportable Diseases by County for Selected Time Frame'

    subheader = None
    if len(state_list) > 0:
        subheader = f'For {", ".join(state_list)}'
        if time_range is not None:
            subheader += f' and From {time_range.start} To {time_range.end}'

    description = (
        '*<u>Report Content</u>*\n'
        '*Data Source:* nbs_ods.PHCDemographic (publichealthcasefact)\n'
        '*Output:* Report demonstrates, in table form, the total number of '
        'Investigation(s) [both Individual and Summary] by County irrespective of '
        'Case Status.\n'
        'Output:\n'
        '* Does not include Investigation(s) that have been logically deleted\n'
        '* Is filtered based on the state, county(s), disease(s) and advanced criteria '
        'selected '
        'by user\n'
        '* Will not include Investigation(s) that do not have a value for the State '
        'selected by the user\n'
        '* Will not include Investigation(s) that do not have a value for the '
        'County(s) selected by the user\n'
        '* Is based on the calculated Event Date\n'
    )

    return ReportResult(
        content_type='table',
        content=content,
        header=header,
        subheader=subheader,
        description=description,
    )
