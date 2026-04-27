from src.db_transaction import Transaction
from src.models import ReportResult
from src.utils import build_case_count_query, gen_subheader


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """Standard Report 08: Report of Disease Cases Over Selected Time Period

    Conversion notes:
    * Matched export format
    * Removed references to 'State Map' or 'choropleth map'
    * Removed calculations section of descriptions as those were run-format specific
    """
    column_mapping = {
        'state_cd': 'State Code',
        'state': 'State',
        'county': 'County',
        'phc_code_short_desc': 'Condition',
        'event_date': 'Event Date',
        'cnty_cd': 'County Code',
    }
    content = trx.query(
        build_case_count_query(column_mapping, subset_query),
    )
    # Get the unique state(s) in the data set for subheader display
    state_list = content.get_unique_column('State')
    subheader = gen_subheader(states=state_list)

    description = (
        '*<u>Report content</u>*\n'
        '*Data Source:* nbs_ods.PHCDemographic (publichealthcasefact)\n'
        '*Output:* Report demonstrates, in table form, the total number of '
        'Investigation(s) [both Individual and Summary] by County irrespective of '
        'Case Status.\n'
        'Output:\n'
        '* Does not include Investigation(s) that have been logically deleted\n'
        '* Is filtered based on the state, disease, time frame and '
        'advanced criteria selected by user\n'
        '* Will not include Investigation(s) that do not have a value for the State '
        'selected by the user\n'
        '* Will not include Investigation(s) that do not have a value for the '
        'County (even though a value for State may ex\n'
        '* Is based on the calculated Event Date\n'
    )

    return ReportResult(
        content_type='table',
        content=content,
        subheader=subheader,
        description=description,
    )
