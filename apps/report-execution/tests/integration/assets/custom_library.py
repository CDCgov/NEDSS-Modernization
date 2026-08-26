import logging

import pandas

from src.db_transaction import Transaction
from src.models import ReportResult, Table


def execute(
    trx: Transaction,
    subset_query: str,
    **kwargs,
):
    """This is a stub custom library just to start to get the interface hooked up."""
    content = trx.query(subset_query)

    df = pandas.DataFrame.from_records(content.data, columns=content.columns)
    logging.warning(df)

    stats = df.describe(include="all")
    logging.warning(stats)

    data = stats.to_records()
    logging.warning(data)
    columns = [col for col in stats.columns]
    logging.warning(columns)

    return ReportResult(
        content=Table(data=data, columns=columns),
        description="""
        Custom pass through query

        It is many lines _with_ *markdown*
        """,
        context_header='custom header',
    )
