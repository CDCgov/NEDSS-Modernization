import pandas as pd

from src.db_transaction import Transaction
from src.models import ReportResult, Table


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """SR18: TB Case Verification Report. Computes statistics about TB cases."""
    query = f"""
        WITH subset AS (
            {subset_query}
        )

        SELECT CASE_VERIFICATION, CALC_DISEASE_SITE
        FROM subset;
    """

    content = trx.query(query)

    return ReportResult(content_type='table', content=content)
