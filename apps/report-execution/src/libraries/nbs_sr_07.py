from itertools import chain

from src.db_transaction import Transaction
from src.libraries.nbs_sr_05 import execute as execute_nbs_sr_05
from src.models import ReportResult, Table


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """Standard Report 07: Cases of Selected Diseases vs. 5-Year Median for a
    specific state.

    Each row has columns for the:
    * Disease
    * type (e.g. Five Year Median YTD or Current YTD)
    * Number of Cases

    Conversion notes:
    * Matched "export format"
    * Remove references to "Bar Graph" since export is a table
    * Use results of nbs_sr_05.py for the following:
    * - context_header
    * - content (data is modified to fit expected table format of nbs_sr_07.py)
    """
    nbs_sr_05_report_result = execute_nbs_sr_05(
        trx, subset_query, data_source_name, **kwargs
    )
    nbs_sr_05_report_result_rows = nbs_sr_05_report_result.content.data

    modified_rows = list(
        chain.from_iterable(
            ((row[3], 'Five Year Median YTD', row[6]), (row[3], 'Current YTD', row[2]))
            for row in nbs_sr_05_report_result_rows
        )
    )
    modified_table = Table(
        columns=['Disease', 'type', 'Number of Cases'], data=modified_rows
    )

    description = """
**<u>Report content</u>**

**Output:** Report demonstrates, in table form, Investigations [both Individual and Summary] by year-to-date, and 5-year median irrespective of Case Status. Output:

* Does not include Investigations that have been logically deleted

* Is filtered based on the state, diseases and advanced criteria selected by user

* Will not include Investigations that do not have a value for the State selected by the user

* Is based on month and year of the calculated Event Date

**Calculations:**

* **Current Year Totals by disease:** Total Investigations [both Individual and Summary] where the Year of the Event Date equal the current Year

* **5-Year median:** Median number of Investigations [both Individual and Summary] for the past five years

* **Event Date:** Derived using the hierarchy of Onset Date, Diagnosis Date, Report to County, Report to State and Date the Investigation was created in the NBS.
    """  # noqa: E501

    return ReportResult(
        content=modified_table,
        context_header=nbs_sr_05_report_result.context_header,
        description=description,
    )
