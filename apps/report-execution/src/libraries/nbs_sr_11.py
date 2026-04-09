from src.db_transaction import Transaction
from src.models import ReportResult, TimeRange
from src.utils import gen_subheader


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    time_range: TimeRange | None = None,
    **kwargs,
):
    """Standard Report 11: Cases of Selected Diseases By Year Over Time.

    Conversion notes:
    * Matched export and run formats without pivot
    """
    content = trx.query(
        f"""
        WITH subset as ({subset_query})
        SELECT COALESCE(state_cd, 'N/A') as [State Code], 
        COALESCE(state, 'N/A') as [State], 
        COALESCE(county, 'N/A') as [County],
        COALESCE(phc_code_short_desc, 'N/A') as [Condition], 
        datepart(year, event_date) as [year],
        sum(group_case_cnt) as [Cases]
        FROM subset
        GROUP BY state, state_cd, county, phc_code_short_desc, 
        datepart(year, event_date)
        ORDER BY state, state_cd, county, phc_code_short_desc, 
        datepart(year, event_date)
        """
    )

    header = 'SR11: Cases of Selected Diseases By Year Over Time'
    state_list = content.get_unique_column('State')
    condition_list = content.get_unique_column('Condition')
    subheader = gen_subheader(
        states=state_list,
        time_range=time_range,
        diseases=condition_list,
        date_format='%Y',
    )

    description = (
        '*<u>Report content</u>*\n'
        '*Data Source:* nbs_ods.PHCDemographic (publichealthcasefact)\n'
        '*Output:* Report demonstrates, in table form, the total number of '
        'Investigation(s) [both Individual and Summary] by calculated MMWR Year '
        'irrespective of Case Status.\n'
        'Output:\n'
        '* Does not include Investigation(s) that have been logically deleted\n'
        '* Is filtered based on the state, disease(s), time frame and '
        'advanced criteria selected by user\n'
        '* Will not include Investigation(s) that do not have a value for the State '
        'selected by the user\n'
        'Is based on the year of the calculated Event Date (not the MMWR Year '
        'defined by the user)\n'
        '* Is based on the calculated Event Date\n'
        'Calculations:\n'
        '*Event Date*: Derived using the hierarchy of Onset Date, Diagnosis Date, '
        'Report to County, Report to State and Date the Investigation was created '
        'in the NBS.\n'
    )

    return ReportResult(
        content_type='table',
        content=content,
        header=header,
        subheader=subheader,
        description=description,
    )
