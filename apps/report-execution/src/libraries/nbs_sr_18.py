import pandas as pd

from src.db_transaction import Transaction
from src.models import ReportResult, Table


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """SR18: TB Case Verification Report. Computes statistics about TB cases.

    Conversion Notes:
    * Ordering is not found in the SAS script however it is needed for Python library
      snapshot testing to stay consistent (the output is still sufficient to match
      the SAS export, just with slightly different ordering)
    * The SAS report is _considerably_ different than the export and this library simply
      matches the export, as the data found in the report is calculable given the raw
      data in the export.
    """
    query = f"""
        WITH subset AS (
            {subset_query}
        )

        SELECT CASE_VERIFICATION, CALC_DISEASE_SITE
        FROM subset
        ORDER BY CASE_VERIFICATION, CALC_DISEASE_SITE;
    """

    content = trx.query(query)

    return ReportResult(content_type='table', content=content)
