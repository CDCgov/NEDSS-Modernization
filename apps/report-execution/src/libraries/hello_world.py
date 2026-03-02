from ..models import TimeRange, ReportResult
import pandas as pd


async def execute(trx, data_source_name: str, time_range: TimeRange):
    data_dict = {
        "one": pd.Series([1.0, 2.0, 3.0], index=["a", "b", "c"]),
        "two": pd.Series([1.0, 2.0, 3.0, 4.0], index=["a", "b", "c", "d"]),
    }
    df = pd.DataFrame(data_dict)

    return ReportResult(
        content_type="table", content=df, description="Some hard coded data"
    )
