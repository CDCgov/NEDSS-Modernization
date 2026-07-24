from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    sort_by: str | None,
    **kwargs,
):
    """Basic tabular report. Executes the query described by the data
    source and filters (executing subset_sql) and returns the table.

    Conversion notes:
    * Lifted the size check to a global check and refined the env var names
    * Date formatting still needs to be figured out
    """
    # the order by has the alias, so we need to order after selecting
    query = (
        f'WITH subset as ({subset_query}) SELECT * FROM subset ORDER BY ' + sort_by
        if sort_by
        else subset_query
    )
    content = trx.query(query)

    return ReportResult(content=content)
