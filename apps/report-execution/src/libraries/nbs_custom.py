from ..db_transaction import Transaction
from ..models import ReportResult, TimeRange

library_metadata = {
    "version": 1,
    "name": "nbs_custom",
    "description": """Basic tabular report. Executes the described SQL by the data 
    source and filters and returns the table.""",
    "owner_email": "nbs@cdc.gov",
}


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
