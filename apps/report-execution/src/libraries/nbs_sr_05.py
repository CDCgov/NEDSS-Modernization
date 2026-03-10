from src.db_transaction import Transaction
from src.models import ReportResult, TimeRange


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    time_range: TimeRange | None = None,
    **kwargs,
):
    """Basic tabular report. Executes the query described by the data
    source and filters (executing subset_sql) and returns the table.

    Conversion notes:
    * Lifted the size check to a global check and refined the env var names
    * Date formatting still needs to be figured out
    """
    content = trx.execute(
        f'WITH subset as ({subset_query})\n'
        + 'SELECT state_cd, state, phc_code_short_desc, MONTH(event_date) as month, '
        + 'YEAR(event_date) as year, sum(group_case_cnt) as cases\n'
        + 'FROM subset\n'
        + 'GROUP BY state_cd, state, phc_code_short_desc, '
        + 'MONTH(event_date), YEAR(event_date)\n'
        + 'ORDER BY phc_code_short_desc, YEAR(event_date), MONTH(event_date)'
    )

    description = 'SR5: Cases of Reportable Diseases by State'
    if time_range is not None:
        description += f'\n{time_range.start} - {time_range.end}'

    # TODO: datetimes are formatted '%0m/%0d/%Y %0H:%0M:%0S' in sas. # noqa: FIX002
    # Should this be a util/post processing/sql step in python land?

    return ReportResult(content_type='table', content=content, description=description)
