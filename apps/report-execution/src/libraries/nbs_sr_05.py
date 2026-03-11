from src.db_transaction import Transaction
from src.models import ReportResult, TimeRange


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    time_range: TimeRange | None = None,
    **kwargs,
):
    """Standard Report 05: Cases of Reportable Diseases for a specific state.

    Each row is a disease with columns for the:
    * Current month total
    * YTD total
    * Prior YTD total
    * 5 Year Median YTD total
    * % change Current YTD vs 5 Year Median YTD

    Conversion notes:
    * Lifted the size check to a global check and refined the env var names
    * Date formatting still needs to be figured out
    """
    content = trx.query(
        f'WITH subset as ({subset_query})\n'
        + 'SELECT state_cd, state, phc_code_short_desc, MONTH(event_date) as month, '
        + 'YEAR(event_date) as year, sum(group_case_cnt) as cases\n'
        + 'FROM subset\n'
        + 'WHERE state is not NULL\n'
        + 'AND event_date is not NULL\n'
        + 'AND DATEPART(dayofyear, event_date) <= '
        + '        DATEPART(dayofyear, CURRENT_TIMESTAMP)\n'
        + 'AND YEAR(event_date) >= (YEAR(CURRENT_TIMESTAMP) - 5)\n'
        + 'GROUP BY state_cd, state, phc_code_short_desc, '
        + 'MONTH(event_date), YEAR(event_date)\n'
        + 'ORDER BY phc_code_short_desc, YEAR(event_date), MONTH(event_date)'
    )

    description = 'SR5: Cases of Reportable Diseases by State'
    if time_range is not None:
        description += f'\n{time_range.start} - {time_range.end}'

    return ReportResult(content_type='table', content=content, description=description)
