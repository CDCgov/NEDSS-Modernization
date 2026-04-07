from src.db_transaction import Transaction
from src.models import ReportResult, TimeRange


def execute(
        trx: Transaction,
        subset_query: str,
        data_source_name: str,
        time_range: TimeRange | None = None,
        **kwargs,
):
    """Standard Report 13: Counts of Selected Diseases By Case Status

    Conversion notes:
    * Simplified report logic to only show existing data; removed SAS `inv_status`
      zero-fill template for case status.
    * Integrated `nbs_srte` lookup via `LEFT JOIN` for better performance and
      driver compatibility.
    """
    sql_query = f"""
    SELECT 
        SUM(group_case_cnt) as "Case Count",
        phc_code_short_desc as "Condition",
        cvg.code_short_desc_txt as "Case Status"
    FROM ({subset_query}) phc
    LEFT JOIN nbs_srte.dbo.code_value_general cvg 
        ON phc.case_class_cd = cvg.code 
        AND cvg.code_set_nm = 'PHC_CLASS'
    WHERE cvg.code_short_desc_txt IS NOT NULL
    GROUP BY phc_code_short_desc, cvg.code_short_desc_txt
    ORDER BY phc_code_short_desc, cvg.code_short_desc_txt
    """

    content = trx.query(sql_query)

    header = 'SR13: Counts of Selected Diseases By Case Status'

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
        '* *Total Count:* Total Investigations for each disease irrespective of case '
        'status\n'
        '* *Total Count for all diseases:* Total Investigations for all diseases '
        'irrespective of case status\n'
    )

    return ReportResult(
        content_type='table',
        content=content,
        header=header,
        subheader=None,
        description=description,
    )
