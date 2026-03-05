import pandas as pd

from ..models import ReportResult, TimeRange


async def execute(trx, data_source_name: str, time_range: TimeRange):
    """This is a fake/stub library just to start to get the interface hooked up."""
    data_dict = {
        'one': pd.Series([1.0, 2.0, 3.0], index=['a', 'b', 'c']),
        'two': pd.Series([1.0, 2.0, 3.0, 4.0], index=['a', 'b', 'c', 'd']),
    }
    df = pd.DataFrame(data_dict)

    return ReportResult(
        content_type='table', content=df, description='Some hard coded data'
    )
