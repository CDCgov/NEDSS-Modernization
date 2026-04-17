from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """Basic tabular report. Executes the query described by the data
    source and filters (executing subset_sql) and returns the table.

    Conversion notes:
    * Lifted the size check to a global check and refined the env var names
    * Date formatting still needs to be figured out
    """
    content = trx.query(subset_query)

    header = f'Custom Report For Table: {data_source_name}'

    # TODO: datetimes are formatted '%0m/%0d/%Y %0H:%0M:%0S' in sas. # noqa: FIX002
    # Should this be a util/post processing/sql step in python land?

    return ReportResult(content_type='table', content=content, header=header)
