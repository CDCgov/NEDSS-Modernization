"""This is a fake/stub library just to start to get the interface/pipes hooked up"""

from ..models import TimeRange, ReportResult
from ..db_transaction import Transaction


def execute(trx: Transaction, data_source_name: str, time_range: TimeRange, **kwargs):
    content = trx.execute("select * from #work")

    return ReportResult(
        content_type="table", content=content, description="Some hard coded data"
    )
