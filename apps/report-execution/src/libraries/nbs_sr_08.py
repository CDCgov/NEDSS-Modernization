from src.db_transaction import Transaction
from src.models import ReportResult
from src.utils import gen_subheader


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """Standard Report 08: Report of Disease Cases Over Selected Time Period.

    Conversion notes:
    * Matched export format
    * Allow null values for state_cd (original SAS report would error)
    * Removed references to 'State Map' or 'choropleth map'
    * Removed 'State and county boundaries are based on the
    * SAS maps library uscounty dataset' in description
    * Removed calculations section of descriptions as those were run-format specific
    """
    content = trx.query(
        f"""
        WITH subset AS ({subset_query})
        SELECT 
            state_cd AS [State Code],
            state AS [State],
            county AS [County],
            phc_code_short_desc AS [Condition],
            UPPER(FORMAT(event_date, 'ddMMMyyyy:HH:mm:ss.fff')) AS [Event Date],
            cnty_cd AS [County Code],
            sum(group_case_cnt) AS [Cases]
        FROM subset
        GROUP BY state_cd, state, county, phc_code_short_desc, event_date, cnty_cd
        ORDER BY state_cd, state, county, phc_code_short_desc, event_date, cnty_cd;
        """
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
        'County (even though a value for State may exist)\n'
        '* Is based on the calculated Event Date\n'
    )

    return ReportResult(
        content_type='table',
        content=content,
        subheader=subheader,
        description=description,
    )
