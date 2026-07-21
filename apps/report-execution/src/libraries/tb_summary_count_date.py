from src.db_transaction import Transaction
from src.libraries.tb_summary_count import execute as execute_tb_summary_count
from src.models import ReportResult, Table


def execute(
        trx: Transaction,
        subset_query: str,
        data_source_name: str,
        library_params: dict,
        **kwargs,
):
    """TB Record Count - Summary Report by Count Date - 2020 RVCT.

    # Mapping:
    # row[0] = monthYearTxt
    # row[1] = sasdate
    # row[3] = counted_cases
    """
    tb_summary_count_result = execute_tb_summary_count(
        trx, subset_query, data_source_name, library_params, **kwargs
    )
    tb_summary_count_result_rows = tb_summary_count_result.content.data

    modified_rows = [(row[0], row[2], row[3]) for row in tb_summary_count_result_rows]

    modified_table = Table(
        columns=['monthYearTxt', 'sasdate', 'counted_cases'], data=modified_rows
    )

    return ReportResult(content_type='table', content=modified_table)
