from src.db_transaction import Transaction
from src.models import ReportResult, TimeRange


def execute(
        trx: Transaction,
        subset_query: str,
        data_source_name: str,
        time_range: TimeRange | None = None,
        **kwargs,
):
    """Standard Report 17: TODO

    Conversion notes:
    TODO
    """
    sql_query = f"""
    WITH subset AS ({subset_query}),
    
    -- Map codes to descriptions first to fix the scoping error
    base_data AS (
        SELECT 
            phc_code_short_desc,
            group_case_cnt,
            (SELECT code_short_desc_txt 
             FROM nbs_srte.dbo.code_value_general cvg 
             WHERE phc.case_class_cd = cvg.code 
             AND cvg.code_set_nm = 'PHC_CLASS') as case_class_desc
        FROM subset phc
    ),
    
    -- Get the actual counts from the data
    actual_counts AS (
        SELECT 
            phc_code_short_desc,
            case_class_desc,
            SUM(group_case_cnt) as cases
        FROM base_data
        WHERE case_class_desc IS NOT NULL
        GROUP BY phc_code_short_desc, case_class_desc
    ),
    
    -- Define the static list of statuses (mimics SAS 'inv_status')
    status AS (
        SELECT 'Confirmed' as status_name UNION ALL
        SELECT 'Not a Case' UNION ALL
        SELECT 'Probable' UNION ALL
        SELECT 'Suspect' UNION ALL
        SELECT 'Unknown'
    ),
    
    -- Get unique conditions present in the subset
    unique_conditions AS (
        SELECT DISTINCT phc_code_short_desc 
        FROM actual_counts
    ),
    
    -- Create the Cartesian product for condition:status
    report_grid AS (
        SELECT c.phc_code_short_desc, s.status_name
        FROM unique_conditions c
        CROSS JOIN status s
    )
    
    -- Join actual counts to the grid, converting NULL to 0
    SELECT 
        COALESCE(ac.cases, 0) as "Case Count",
        rg.phc_code_short_desc as "Condition",
        rg.status_name as "Case Status"
    FROM report_grid rg
    LEFT JOIN actual_counts ac 
        ON rg.phc_code_short_desc = ac.phc_code_short_desc 
        AND rg.status_name = ac.case_class_desc
    ORDER BY rg.phc_code_short_desc, rg.status_name
    """

    content = trx.query(sql_query)

    header = 'SR17: Counts of Selected Diseases By Case Status'

    description = (
        '*<u>Report content</u>*\n'
        '*Data Source:* nbs_ods.PHCDemographic (publichealthcasefact)\n'
        '*Output:* Report demonstrates, in table form, the total number of '
        'Investigation(s) [both Individual and Summary]. \n'
        'Output:\n'
        '* Does not include Investigation(s) that have been logically deleted\n'
        '* Is filtered based on the disease(s) and advanced criteria selected by user\n'
        '* Will not include Investigation(s) that do not have a value for Case Status\n'
        '\n'
        '*Calculations:*\n'
        '* *Total Count:* Total Investigations for each disease irrespective of case status\n'
        '* *Total Count for all diseases:* Total Investigations for all diseases irrespective of case status\n'
    )

    return ReportResult(
        content_type='table',
        content=content,
        header=header,
        subheader=None,
        description=description,
    )
