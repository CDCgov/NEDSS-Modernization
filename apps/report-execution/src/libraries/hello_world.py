from ..db_transaction import Transaction
from ..models import ReportResult, TimeRange


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    time_range: TimeRange | None = None,
    **kwargs,
):
    """This is a fake/stub library just to start to get the interface hooked up."""
    content = trx.execute(subset_query)

    return ReportResult(
        content_type='table', content=content, description='Pass through query'
    )
