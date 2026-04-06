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
    * - subheader
    * - content (data is modified to fit expected table format of nbs_sr_07.py)
    """
    nbs_sr_05_report_result = execute_nbs_sr_05(
        trx, subset_query, data_source_name, **kwargs
    )
    nbs_sr_05_report_result_rows = nbs_sr_05_report_result.content.data

    modified_table = Table(columns=['Disease', 'type', 'Number of Cases'], data=[])

    modified_rows = list(
        chain.from_iterable(
            ((row[3], 'Five Year Median YTD', row[6]), (row[3], 'Current YTD', row[2]))
            for row in nbs_sr_05_report_result_rows
        )
    )
    modified_table.data = modified_rows

    header = (
        'SR7: Cases of Selected Diseases vs. 5-Year Median for Selected Time Period'
    )
    description = (
        '<u>Report content</u>\n'
        'Data Source: nbs_ods.PHCDemographic (publichealthcasefact)\n'
        'Output: Report demonstrates, in table form, '
        'Investigation(s) [both Individual and Summary] '
        'by year-to-date, and 5-year median irrespective of Case Status.\n'
        'Output:\n'
        '1) Does not include Investigation(s) that have been logically deleted\n'
        '2) Is filtered based on the state, disease(s) and advanced criteria selected '
        'by user\n'
        '3) Will not include Investigation(s) that do not have a value for the State '
        'selected by the user\n'
        '4) Is based on month and year of the calculated Event Date\n'
        'Calculations:'
        '1) Current Year Totals by disease: Total Investigation(s) [both Individual '
        'and Summary] where the Year of the Event Date equal the current Year\n'
        '2) 5-Year median: Median number of Investigation(s) [both Individual and '
        'Summary] for the past five years\n'
        '3) Event Date: Derived using the hierarchy of Onset Date, Diagnosis Date, '
        'Report to County, Report to State and Date the Investigation was created in '
        'the NBS.\n'
    )

    return ReportResult(
        content_type='table',
        content=modified_table,
        header=header,
        subheader=nbs_sr_05_report_result.subheader,
        description=description,
    )
