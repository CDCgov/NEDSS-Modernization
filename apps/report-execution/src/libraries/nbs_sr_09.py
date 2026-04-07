from src.db_transaction import Transaction
from src.models import ReportResult, TimeRange
from src.utils import gen_subheader


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    time_range: TimeRange,
    **kwargs,
):
    """Standard Report 09: Monthly Cases by Disease and County for Selected State
    and Time Frame.

    Conversion notes:
    * We are only returning the underlying data for the graph in a tabular format,
    whereas the SAS version created the bar graph.
    * The subset_query already contains all user filters (including time range),
    so no additional date filtering is applied here.

    """
    content = trx.query(
        f'''
        WITH subset AS ({subset_query})
        -- Clean data
        , cleaned_data AS (
            SELECT 
                COALESCE(state_cd, 'N/A') AS state_cd,
                COALESCE(state, 'N/A') AS state,
                COALESCE(county, 'N/A') AS county,
                COALESCE(cnty_cd, 'N/A') AS cnty_cd,
                phc_code_short_desc,
                event_date,
                group_case_cnt
            FROM subset
            WHERE event_date IS NOT NULL
                AND phc_code_short_desc IS NOT NULL
                AND phc_code_short_desc != ''
        )
        -- Monthly aggregation with exact column names matching the export
        SELECT 
            state_cd AS [State Code],
            state AS [State],
            county AS [County],
            phc_code_short_desc AS [Condition],
            FORMAT(event_date, 'MMM') AS monyr,
            FORMAT(event_date, 'yyyyMM') AS ord,
            SUM(group_case_cnt) AS Cases
        FROM cleaned_data
        GROUP BY 
            state_cd,
            state,
            county,
            phc_code_short_desc,
            FORMAT(event_date, 'MMM'),
            FORMAT(event_date, 'yyyyMM')
        HAVING SUM(group_case_cnt) > 0  -- Exclude rows with zero cases
        ORDER BY 
            phc_code_short_desc,
            ord,
            state,
            county
        '''
    )

    header = 'SR9: Monthly Cases of Selected Disease by County and State'
    subheader = gen_subheader(time_range.start, time_range.end, content)

    description = '''
    *<u>Report Content</u>*
    *Data Source:* nbs_ods.PHCDemographic (publichealthcasefact)
    *Output:* Report provides the total number of monthly Investigation(s) 
    [both Individual and Summary] for selected disease(s) and state(s), 
    irrespective of Case Status. Output:
    * Does not include Investigation(s) that have been logically deleted
    * Is filtered based on the state, disease(s), time frame and advanced 
    filter criteria selected by the user
    * Will not include Investigation(s) that do not have a value for the 
    State selected by the user
    * Is based on the calculated Event Date
    *Calculations:*
    * *Cases:* Total Investigation(s) [both Individual and Summary] 
    by state, county, and disease for each month over the selected time frame
    * *Event Date:* Derived using the hierarchy of Onset Date, Diagnosis 
    Date, Report to County, Report to State and Date the Investigation was 
    created in the NBS.
    '''

    return ReportResult(
        content_type='table',
        content=content,
        header=header,
        subheader=subheader,
        description=description,
    )