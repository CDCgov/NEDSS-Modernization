from src.db_transaction import Transaction
from src.models import ReportResult
from src.utils import gen_context_header


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """Standard Report 11: Cases of Selected Diseases By Year Over Time.

    Conversion notes:
    * Matched export format without pivot
    * Made "Year" header uppercase y to match SR12 output (and other columns)
    """
    content = trx.query(
        f"""
        WITH subset as ({subset_query})
        SELECT COALESCE(state_cd, 'N/A') as [State Code], 
        COALESCE(state, 'N/A') as [State], 
        COALESCE(county, 'N/A') as [County],
        phc_code_short_desc as [Condition], 
        YEAR(event_date) as [Year],
        SUM(group_case_cnt) as [Cases]
        FROM subset
        WHERE event_date IS NOT NULL
        GROUP BY state_cd, state, county, phc_code_short_desc, YEAR(event_date)
        ORDER BY [State Code], [State], [County], phc_code_short_desc, YEAR(event_date)
        """
    )

    state_list = content.get_unique_column('State')
    condition_list = content.get_unique_column('Condition')
    context_header = gen_context_header(
        states=state_list,
        diseases=condition_list,
    )

    description = """
**<u>Report content</u>**

**Output:** Report demonstrates, in table form, the total number of Investigations [both Individual and Summary] by calculated MMWR Year irrespective of Case Status. Output:

* Does not include Investigations that have been logically deleted

* Is filtered based on the state, diseases, time frame and advanced criteria selected by user

* Will not include Investigations that do not have a value for the State 
selected by the user

* Is based on the year of the calculated Event Date (not the MMWR Year defined by the user)

**Calculations:**

* **Event Date:** Derived using the hierarchy of Onset Date, Diagnosis Date, Report to County, Report to State and Date the Investigation was created in the NBS.
"""  # noqa: E501

    return ReportResult(
        content=content,
        context_header=context_header,
        description=description,
    )
