from src.db_transaction import Transaction
from src.models import ReportResult
from src.utils import gen_context_header


def execute(
    trx: Transaction,
    subset_query: str,
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
        f"""
        WITH subset AS
        (
          {subset_query}
        ),
        -- Monthly aggregation with exact column names matching the export
        monthly_agg AS
        (
          SELECT COALESCE(state_cd,'N/A') AS "State Code",
                 COALESCE(state,'N/A') AS State,
                 COALESCE(county,'N/A') AS County,
                 phc_code_short_desc AS Condition,
                 FORMAT(event_date,'MMM') AS monyr,
                 FORMAT(event_date,'yyyyMM') AS ord,
                 SUM(group_case_cnt) AS Cases
          FROM subset
          GROUP BY state_cd,
                   state,
                   county,
                   phc_code_short_desc,
                   FORMAT(event_date,'MMM'),
                   FORMAT(event_date,'yyyyMM')
        )
        -- final output, sub '.' for NULL values in mony and ord to match SAS script
        SELECT "State Code",
               State,
               County,
               Condition,
               COALESCE(monyr,'.') AS monyr,
               COALESCE(ord,'.') AS ord,
               Cases
        FROM monthly_agg
        ORDER BY "State Code",
                 State,
                 County,
                 Condition,
                 ord,
                 monyr;
        """
    )

    state_list = content.get_unique_column('State')
    context_header = gen_context_header(states=state_list)

    description = """
**<u>Report content</u>**

**Output:** Report provides the total number of monthly Investigations [both Individual and Summary] for selected diseases and states, irrespective of Case Status. Output:

* Does not include Investigations that have been logically deleted

* Is filtered based on the state, diseases, time frame and advanced filter criteria selected by the user

* Will not include Investigations that do not have a value for the State selected by the user

* Is based on the calculated Event Date

**Calculations:**

* **Cases:** Total Investigations [both Individual and Summary] by state, county, and disease for each month over the selected time frame

* **Event Date:** Derived using the hierarchy of Onset Date, Diagnosis Date, Report to County, Report to State and Date the Investigation was created in the NBS.
"""  # noqa: E501

    return ReportResult(
        content=content,
        context_header=context_header,
        description=description,
    )
