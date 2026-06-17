from src.db_transaction import Transaction
from src.models import ReportResult
from src.utils import gen_subheader


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
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
        f"""
        WITH subset AS ({subset_query})
        SELECT 
            COALESCE(state, 'N/A') AS [State],
            COALESCE(county, 'N/A') AS [County],
            phc_code_short_desc AS [Condition],
            sum(group_case_cnt) AS [Cases]
        FROM subset
        GROUP BY state, county, phc_code_short_desc
        ORDER BY [State], [County], phc_code_short_desc;
        """
    )

    # Get the unique state(s) in the data set for subheader display
    state_list = content.get_unique_column('State')
    subheader = gen_subheader(states=state_list)

    description = """
**<u>Report content</u>**

**Output:** Report demonstrates, in table form, the total number of Investigations [both Individual and Summary] by County irrespective of Case Status. Output:

* Does not include Investigations that have been logically deleted

* Is filtered based on the state, counties, diseases, time frame and  advanced criteria selected by user

* Will not include Investigations that do not have a value for the State selected by the user

* Will not include Investigations that do not have a value for the Counties selected by the user

* Is based on the calculated Event Date
"""  # noqa: E501

    return ReportResult(
        content_type='table',
        content=content,
        subheader=subheader,
        description=description,
    )
