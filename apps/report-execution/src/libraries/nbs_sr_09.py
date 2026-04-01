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
    """Standard Report 09: Monthly Cases by Disease and State.

    'SR9: Bar Graph of Selected Disease by Month.  Report demonstrates, using a
    vertical bar graph, the total number of monthly Investigation(s) [both
    Individual and Summary] for a given disease, by State, irrespective of Case
    Status.'

    NOTE: In python, we are only returning the underlying data for the graph in a
    tabular format, whereas the SAS version created the bar graph.

    """
    today = datetime.date.today()

    # Handle time range filtering
    if time_range:
        start_date = time_range.start
        end_date = time_range.end
    else:
        # Default to last 12 months if no time range specified
        start_date = (today.replace(day=1) - datetime.timedelta(days=365)).strftime(
            '%Y-%m-%d'
        )
        end_date = today.strftime('%Y-%m-%d')

    # Main query to get monthly aggregated data
    content = trx.query(
        f'WITH subset as ({subset_query})\n'
        # Clean up NULL values (matching SAS behavior)
        ', cleaned_data as (\n'
        'SELECT \n'
        "  COALESCE(state_cd, 'N/A') as state_cd,\n"
        "  COALESCE(state, 'N/A') as state,\n"
        "  COALESCE(county, 'N/A') as county,\n"
        "  COALESCE(cnty_cd, 'N/A') as cnty_cd,\n"
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
        # Monthly aggregation
        'SELECT \n'
        '  state_cd,\n'
        '  state,\n'
        '  county,\n'
        '  phc_code_short_desc as disease,\n'
        "  FORMAT(event_date, 'MMM') as month_name,\n"
        "  FORMAT(event_date, 'yyyyMM') as month_code,\n"
        '  SUM(group_case_cnt) as cases\n'
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
        '  month_code, \n'
        '  state, \n'
        '  county'
    )

    # Get state(s) for subheader display
    states = trx.query(
        f'WITH subset as ({subset_query})\n'
        "SELECT DISTINCT COALESCE(state, 'N/A') as state\n"
        'FROM subset\n'
        "WHERE state IS NOT NULL AND state != ''\n"
        'ORDER BY state'
    )

    state_list = [row[0] for row in states.data if row[0] != 'N/A']

    # Get disease(s) for subheader display
    diseases = trx.query(
        f'WITH subset as ({subset_query})\n'
        'SELECT DISTINCT phc_code_short_desc as disease\n'
        'FROM subset\n'
        'WHERE phc_code_short_desc IS NOT NULL\n'
        "  AND phc_code_short_desc != ''\n"
        'ORDER BY disease'
    )

    disease_list = [row[0] for row in diseases.data]

    # Format the time period string
    time_period_str = f'{start_date} to {end_date}' if time_range else 'Last 12 Months'

    header = 'SR9: Monthly Cases by Disease and State'
    subheader = (
        f'State(s): {", ".join(state_list) if state_list else "All"} | '
        f'Disease(s): {", ".join(disease_list) if disease_list else "All"} | '
        f'Time Period: {time_period_str} | '
        f'{today.strftime("%m/%d/%Y")}'
    )

    description = (
        '*<u>Report Content</u>*\n'
        '*Data Source:* nbs_ods.PHCDemographic (publichealthcasefact)\n'
        '*Output:* Report provides the underlying data for a vertical bar graph, '
        'displaying the total number of monthly Investigation(s) [both Individual '
        'and Summary] for selected disease(s) and state(s), irrespective of Case '
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
