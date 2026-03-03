from ..db_transaction import Transaction
from ..models import LibraryMetadata, ReportResult, TimeRange

library_metadata = LibraryMetadata(
    version=1,
    name="nbs_custom",
    description="""Basic tabular report. Executes the query described by the data 
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
    
    description = f"Custom Report For Table: f{data_source_name}"
    if time_range is not None:
        description += f"\n{time_range.start} - {time_range.end}"
        
    # TODO: datetimes are formatted '%0m/%0d/%Y %0H:%0M:%0S' in sas. Should this be a 
    # util/post processing/sql step in python land?

    return ReportResult(
        content_type="table", content=content, description=description
    )
