"""This is a fake/stub library just to start to get the interface/pipes hooked up"""

from ..db_transaction import Transaction
from ..models import ReportResult, TimeRange, LibraryMetadata

library_metadata = LibraryMetadata(
    version = 1,
    name = "hello_world",
    description = """Basic tabular report. Executes the described SQL by the data 
    source and filters and returns the table.""",
)


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    time_range: TimeRange = None,
    **kwargs,
):
    content = trx.execute(subset_query)

    return ReportResult(
        content_type="table", content=content, description="Pass through query"
    )
