from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """This is a stub custom library just to start to get the interface hooked up."""
    content = trx.query(subset_query)

    return ReportResult(
        content_type='table', content=content, description='Custom pass through query'
    )
