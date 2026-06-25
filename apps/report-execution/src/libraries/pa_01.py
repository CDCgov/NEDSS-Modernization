from src.db_transaction import Transaction
from src.libraries.support.pa_01.calculations import (
    build_case_assignments_and_outcomes_output,
    build_partners_and_clusters_initiated_output,
)
from src.libraries.support.pa_01.models import (
    CSV_COLUMNS,
    Pa01Row,
    Pa01Tables,
    Pa01Worker,
)
from src.libraries.support.pa_01.queries import (
    case_interview_rows_query,
    filtered_cases_query,
    partner_notification_query,
    period_partners_query,
    testing_index_query,
    timed_interviews_query,
)
from src.models import ReportResult, Table


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    library_params: dict,
    **kwargs,
):
    """PA01 HIV and STD: Case Management Report.

    Conversion notes:
    * Report is too large for a single file, additional files found in
      directory `./support/pa_01/`
    * This report is the combination of both `PA01_HIV.sas` and `PA01_STD.sas`
    * The variant (STD or HIV) is determined by `library_params['report_variant']`
      which is defined in the Report_Library db table.
    * For the data point "HIV Tested", the SAS PDF output does not include
      percentages for individual workers, only "ALL WORKERS".  This report
      includes percentages for individual workers.
    """
    if not isinstance(library_params, dict):
        raise ValueError(
            f"Parameter 'library_params' is not a dict (is {type(library_params)})"
        )

    report_variant = library_params.get('report_variant')

    if report_variant is None:
        raise ValueError(
            f"Parameter 'library_params' missing key 'report_variant': {library_params}"
        )

    # short circuit if there's no data from subset_query
    data_check = trx.query(subset_query)
    if len(data_check.data) == 0:
        content = Table(
            columns=CSV_COLUMNS,
            data=[],
        )

        return ReportResult(content_type='table', content=content)

    # get list of workers (nb. None treated as "ALL WORKERS")
    case_interview_rows = trx.query(
        case_interview_rows_query(subset_query, report_variant)
    )
    workers: list[Pa01Worker | None] = [None]
    workers.extend(_get_workers(case_interview_rows))

    # query result tables
    pa01_tables = Pa01Tables(
        filtered_cases=trx.query(filtered_cases_query(subset_query)),
        case_interview_rows=case_interview_rows,
        timed_interviews=trx.query(
            timed_interviews_query(subset_query, report_variant)
        ),
        partner_notification=trx.query(partner_notification_query(subset_query)),
        testing_index=trx.query(testing_index_query(subset_query)),
        period_partners=trx.query(period_partners_query(subset_query)),
    )

    # build output CSV data for each worker
    output_rows: list[Pa01Row] = []
    for worker in workers:
        output_rows.extend(_build_output_for_worker(pa01_tables, worker))

    content = Table(
        columns=CSV_COLUMNS,
        data=output_rows,
    )

    return ReportResult(content_type='table', content=content)


def _build_output_for_worker(
    tables: Pa01Tables, worker: Pa01Worker | None = None
) -> list[Pa01Row]:
    """Perform all needed calculations for a given worker, output data for
    the final CSV.

    Args:
        tables: Query results within a Pa01Tables instance
        worker: The worker the data is being calculated for (None means "ALL")

    Returns:
        List of calculated data for a given worker, meant for the final CSV of PA01
    """
    rows: list[Pa01Row] = []

    rows.extend(build_case_assignments_and_outcomes_output(tables, worker))
    rows.extend(build_partners_and_clusters_initiated_output(tables, worker))

    return rows


def _get_workers(case_interview_rows: Table) -> list[Pa01Worker]:
    """Get all unique workers within the report's data.  A worker is defined as the
    combination of 'investigator_interview_key' and 'provider_quick_code'.
    """
    workers = {
        Pa01Worker(
            row['INVESTIGATOR_INTERVIEW_KEY'],
            row['PROVIDER_QUICK_CODE'],
        )
        for row in case_interview_rows.data_as_dicts()
        if row['INVESTIGATOR_INTERVIEW_KEY'] is not None
        and row['PROVIDER_QUICK_CODE'] is not None
    }

    # nb. sorting should mimic the ordering of workers found in the PDF output
    return sorted(
        workers, key=lambda w: (w.provider_quick_code, w.investigator_interview_key)
    )
