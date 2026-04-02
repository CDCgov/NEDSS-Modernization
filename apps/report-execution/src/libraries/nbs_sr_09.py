import datetime

from src.db_transaction import Transaction
from src.models import ReportResult, TimeRange


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    time_range: TimeRange | None = None,
    **kwargs,
):
    """Standard Report 09: Monthly Cases by Disease and County for Selected State 
    and Time Frame

    Conversion notes:
    * We are only returning the underlying data for the graph in a tabular format, 
    whereas the SAS version created the bar graph.

    """
    # time_range will always be provided
    start_date = time_range.start
    end_date = time_range.end

    # Single query to get monthly aggregated data
    content = trx.query(
        f'WITH subset as ({subset_query})\n'
        # Clean data
        ', cleaned_data as (\n'
        'SELECT \n'
        '  state_cd,\n'
        '  state,\n'
        '  county,\n'
        '  cnty_cd,\n'
        '  phc_code_short_desc,\n'
        '  event_date,\n'
        '  group_case_cnt\n'
        'FROM subset\n'
        'WHERE event_date IS NOT NULL\n'
        f"  AND event_date >= '{start_date}'\n"
        f"  AND event_date <= '{end_date}'\n"
        '  AND phc_code_short_desc IS NOT NULL\n'
        "  AND phc_code_short_desc != ''\n"
        ')\n'
        # Monthly aggregation with exact column names matching the export
        'SELECT \n'
        '  state_cd as [State Code],\n'
        '  state as [State],\n'
        '  county as [County],\n'
        '  phc_code_short_desc as [Condition],\n'
        "  FORMAT(event_date, 'MMM') as monyr,\n"
        "  FORMAT(event_date, 'yyyyMM') as ord,\n"
        '  SUM(group_case_cnt) as Cases\n'
        'FROM cleaned_data\n'
        'GROUP BY \n'
        '  state_cd, \n'
        '  state, \n'
        '  county, \n'
        '  phc_code_short_desc, \n'
        "  FORMAT(event_date, 'MMM'), \n"
        "  FORMAT(event_date, 'yyyyMM')\n"
        'ORDER BY \n'
        '  phc_code_short_desc, \n'
        '  ord, \n'
        '  state, \n'
        '  county'
    )

    # Parse states and diseases from the content
    col_index = {col: idx for idx, col in enumerate(content.columns)}
    
    state_set = set()
    disease_set = set()
    
    for row in content.data:
        state = row[col_index['State']]
        disease = row[col_index['Condition']]
        if state is not None:
            state_set.add(state)
        if disease is not None:
            disease_set.add(disease)
    
    state_list = sorted(state_set)
    disease_list = sorted(disease_set)

    # Format the time period string
    time_period_str = f'{start_date} to {end_date}'

    header = 'SR9: Monthly Cases by Disease, County, and State'
    subheader = (
        f'State(s): {", ".join(state_list) if state_list else "All"} | '
        f'Disease(s): {", ".join(disease_list) if disease_list else "All"} | '
        f'Time Period: {time_period_str}'
    )

    description = (
        '*<u>Report Content</u>*\n'
        '*Data Source:* nbs_ods.PHCDemographic (publichealthcasefact)\n'
        '*Output:* Report provides the total number of monthly Investigation(s) '
        '[both Individual and Summary] for selected disease(s) and state(s), '
        'irrespective of Case '
        'Status. Output:\n'
        '* Does not include Investigation(s) that have been logically deleted\n'
        '* Is filtered based on the state, disease(s), time frame and advanced '
        'filter criteria selected by the user\n'
        '* Will not include Investigation(s) that do not have a value for the '
        'State selected by the user\n'
        '* Is based on the calculated Event Date\n'
        '*Calculations:*\n'
        '* *Total Monthly Cases:* Total Investigation(s) [both Individual and '
        'Summary] by state, county, and disease for each month over the selected '
        'time frame\n'
        '* *Event Date:* Derived using the hierarchy of Onset Date, Diagnosis '
        'Date, Report to County, Report to State and Date the Investigation was '
        'created in the NBS.\n'
    )

    return ReportResult(
        content_type='table',
        content=content,
        header=header,
        subheader=subheader,
        description=description,
    )