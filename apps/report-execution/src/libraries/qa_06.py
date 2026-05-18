from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """QA Report 06: Patients with Multiple Cases.

    Conversion notes:
    """
    content = trx.query(
        """
        SELECT 1;
        """
    )

    return ReportResult(
        content_type='table',
        content=content
    )
