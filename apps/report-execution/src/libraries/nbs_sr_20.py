from src.db_transaction import Transaction
from src.libraries.nbs_sr_19 import execute as execute_nbs_sr_19
from src.models import ReportResult, Table


def execute(
    trx: Transaction,
    subset_query: str,
    **kwargs,
):
    """Wrapper report leveraging SR19 core engine with a modified column layout."""
    nbs_sr_19_report_result = execute_nbs_sr_19(
        trx, subset_query, **kwargs
    )
    nbs_sr_19_report_result_rows = nbs_sr_19_report_result.content.data

    # Mapping:
    # row[0] = monthYear
    # row[1] = sasdate
    # row[4] = total_cases
    modified_rows = [(row[0], row[1], row[4]) for row in nbs_sr_19_report_result_rows]

    modified_table = Table(
        columns=['monthYear', 'sasdate', 'counted_cases'], data=modified_rows
    )

    return ReportResult(content=modified_table)
