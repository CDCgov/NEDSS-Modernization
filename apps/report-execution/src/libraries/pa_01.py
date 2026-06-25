from dataclasses import dataclass

from src.db_transaction import Transaction
from src.libraries.support.pa_01.queries import (
    case_interview_rows_query,
    filtered_cases_query,
    partner_notification_query,
    period_partners_query,
    testing_index_query,
    timed_interviews_query,
)
from src.models import ReportResult, Table

"""
    Go backs:
    - once all stats for ALL WORKERS are done, need to re-tool to calculate grouped
      by workers
"""

# Constants
ALL = 'ALL'
CASE_ASSIGNMENTS_AND_OUTCOMES = 'Case Assignments & Outcomes'
CASES_IXD = "Cases IX'D"
PARTNERS_AND_CLUSTERS_INITIATED = 'Partners & Clusters Initiated'

# CSV row data shape
Pa01Row = tuple[
    str,  # Worker
    str,  # Category 1
    str,  # Category 2
    str | None,  # Category 3
    int | None,  # Count
    str | None,  # Percentage
    str | None,  # Index
]

# CSV columns
CSV_COLUMNS = [
    'Worker',
    'Category 1',
    'Category 2',
    'Category 3',
    'Count',
    'Percentage',
    'Index',
]

# The date field differs in SAS for HIV vs. STD
PA1_NEW_DATE_COL = {
    'HIV': 'CA_INTERVIEWER_ASSIGN_DT',
    'STD': 'CA_INIT_INTVWR_ASSGN_DT',
}

# The date field for "days" differs in SAS for HIV vs. STD
PA1_DTE_DATE_COL = {
    'HIV': 'CA_INIT_INTVWR_ASSGN_DT',
    'STD': 'CA_INTERVIEWER_ASSIGN_DT',
}


@dataclass(frozen=True)
class Pa01Tables:
    """Query result Tables for the report."""

    filtered_cases: Table
    case_interview_rows: Table
    timed_interviews: Table
    partner_notification: Table
    testing_index: Table
    period_partners: Table


@dataclass(frozen=True)
class Pa01Worker:
    """Individual worker within the context of this report."""

    investigator_interview_key: int
    provider_quick_code: str


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    library_params: dict,
    **kwargs,
):
    """PA01 HIV and STD: Case Management Report.

    Conversion notes:
    * This report is the combination of both `PA01_HIV.sas` and `PA01_STD.sas`
    * The variant (STD or HIV) is determined by `library_params['report_variant']`
      which is defined in the Report_Library db table.
    * For the data point "HIV Tested", the SAS PDF output does not include
      percentages for individual workers, only "ALL WORKERS".  This report
      includes percentages for every worker.
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

    # query result tables
    filtered_cases = trx.query(filtered_cases_query(subset_query))
    case_interview_rows = trx.query(
        case_interview_rows_query(subset_query, report_variant)
    )
    timed_interviews = trx.query(timed_interviews_query(subset_query, report_variant))
    partner_notification = trx.query(partner_notification_query(subset_query))
    testing_index = trx.query(testing_index_query(subset_query))
    period_partners = trx.query(period_partners_query(subset_query))

    pa01_tables = Pa01Tables(
        filtered_cases,
        case_interview_rows,
        timed_interviews,
        partner_notification,
        testing_index,
        period_partners,
    )

    # nb. None treated as "ALL WORKERS"
    workers: list[Pa01Worker | None] = [None]
    workers.extend(_get_workers(case_interview_rows))

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

    rows.extend(_build_case_assignments_and_outcomes_output(tables, worker))
    rows.extend(_build_partners_and_clusters_initiated_output(tables, worker))

    return rows


def _build_case_assignments_and_outcomes_output(
    tables: Pa01Tables, worker: Pa01Worker | None = None
) -> list[Pa01Row]:
    """Perform all needed calculations for the "Case Assignments and Outcomes" section
    for a given worker, output data for the final CSV.
    """
    cases_assigned = _calc_cases_assigned(tables.case_interview_rows, worker)
    cases_closed, cases_closed_percent = _calc_cases_closed(
        tables.case_interview_rows, cases_assigned, worker
    )
    cases_ixd, cases_ixd_percent = _calc_cases_ixd(
        tables.case_interview_rows, cases_assigned, worker
    )
    cases_ixd_buckets = _calc_interview_day_buckets(
        tables.timed_interviews, cases_ixd, worker
    )
    cases_reinterviewed, cases_reinterviewed_percent = _calc_cases_reinterviewed(
        tables.case_interview_rows, cases_ixd, worker
    )
    hiv_previous_positive, hiv_previous_positive_percent = _calc_hiv_previous_positive(
        tables.case_interview_rows, cases_assigned, worker
    )
    hiv_tested, hiv_tested_percent = _calc_hiv_tested(
        tables.case_interview_rows, cases_assigned, worker
    )
    hiv_new_positive, hiv_new_positive_percent = _calc_hiv_new_positive(
        tables.case_interview_rows, hiv_tested, worker
    )
    hiv_posttest_counsel, hiv_posttest_counsel_percent = _calc_hiv_posttest_counsel(
        tables.case_interview_rows, hiv_tested, worker
    )
    partner_notification_index = _calc_partner_notification_index(
        tables.partner_notification, cases_ixd, worker
    )
    testing_index = _calc_testing_index(tables.testing_index, cases_ixd, worker)

    # output CSV data
    rows: list[Pa01Row] = [
        (
            _worker_for_csv(worker),
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'Cases Assigned',
            None,
            cases_assigned,
            None,
            None,
        ),
        (
            _worker_for_csv(worker),
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'Cases Closed',
            None,
            cases_closed,
            cases_closed_percent,
            None,
        ),
        (
            _worker_for_csv(worker),
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            CASES_IXD,
            None,
            cases_ixd,
            cases_ixd_percent,
            None,
        ),
        (
            _worker_for_csv(worker),
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            CASES_IXD,
            'Within 3 days',
            cases_ixd_buckets[3][0],
            cases_ixd_buckets[3][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            CASES_IXD,
            'Within 5 days',
            cases_ixd_buckets[5][0],
            cases_ixd_buckets[5][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            CASES_IXD,
            'Within 7 days',
            cases_ixd_buckets[7][0],
            cases_ixd_buckets[7][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            CASES_IXD,
            'Within 14 days',
            cases_ixd_buckets[14][0],
            cases_ixd_buckets[14][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'Cases Reinterviewed',
            None,
            cases_reinterviewed,
            cases_reinterviewed_percent,
            None,
        ),
        (
            _worker_for_csv(worker),
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'HIV Previous Positive',
            None,
            hiv_previous_positive,
            hiv_previous_positive_percent,
            None,
        ),
        (
            _worker_for_csv(worker),
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'HIV Tested',
            None,
            hiv_tested,
            hiv_tested_percent,
            None,
        ),
        (
            _worker_for_csv(worker),
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'HIV New Positive',
            None,
            hiv_new_positive,
            hiv_new_positive_percent,
            None,
        ),
        (
            _worker_for_csv(worker),
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'HIV Posttest Counsel',
            None,
            hiv_posttest_counsel,
            hiv_posttest_counsel_percent,
            None,
        ),
        (
            _worker_for_csv(worker),
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'Partner Notification Index',
            None,
            None,
            None,
            partner_notification_index,
        ),
        (
            _worker_for_csv(worker),
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'Testing Index',
            None,
            None,
            None,
            testing_index,
        ),
    ]

    return rows


def _build_partners_and_clusters_initiated_output(
    tables: Pa01Tables, worker: Pa01Worker | None = None
) -> list[Pa01Row]:
    """Perform all needed calculations for the "Partners and Clusters Initiated"
    section for a given worker, output data for the final CSV.
    """
    cases_assigned = _calc_cases_assigned(tables.case_interview_rows, worker)
    cases_ixd, _ = _calc_cases_ixd(tables.case_interview_rows, cases_assigned, worker)
    total_period_partners, total_period_partners_index = _calc_total_period_partners(
        tables.period_partners, cases_ixd, worker
    )

    rows: list[Pa01Row] = [
        (
            _worker_for_csv(worker),
            PARTNERS_AND_CLUSTERS_INITIATED,
            'Total Period Partners',
            None,
            total_period_partners,
            None,
            total_period_partners_index,
        ),
    ]

    return rows


def _calc_cases_assigned(
    case_interview_rows: Table, worker: Pa01Worker | None = None
) -> int:
    """Calculate 'Cases Assigned' count for a given worker.  Calculates for all workers
    if passed in worker is None.
    """
    rows = case_interview_rows.data_as_dicts()

    if worker is not None:
        rows = _filter_rows_for_worker(rows, worker)

    return len({row['INV_LOCAL_ID'] for row in rows})


def _calc_cases_closed(
    case_interview_rows: Table, cases_assigned: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'Cases Closed' count and percentage for a given worker.  Calculates
    for all workers if passed in worker is None.
    """
    rows = case_interview_rows.data_as_dicts()

    if worker is not None:
        rows = _filter_rows_for_worker(rows, worker)

    case_ids = {row['INV_LOCAL_ID'] for row in rows if row['CC_CLOSED_DT'] is not None}

    count = len(case_ids)
    return count, _percent_for_csv(count, cases_assigned)


def _calc_cases_ixd(
    case_interview_rows: Table, cases_assigned: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate "Cases IX'D" count and percentage.  Calculates for all workers if
    passed in worker is None.
    """
    rows = case_interview_rows.data_as_dicts()

    if worker is not None:
        rows = _filter_rows_for_worker(rows, worker)

    case_ids = {
        row['INV_LOCAL_ID']
        for row in rows
        if row['CA_PATIENT_INTV_STATUS'] == 'I - Interviewed'
    }

    count = len(case_ids)

    return count, _percent_for_csv(count, cases_assigned)


def _calc_interview_day_buckets(
    timed_interviews: Table, cases_ixd: int, worker: Pa01Worker | None = None
) -> dict[int, tuple[int, str]]:
    """Calculate "Cases IX'D" count and percentage for within 3, 5, 7, and 14 days.
    Calculates for all workers if passed in worker is None.
    """
    rows = timed_interviews.data_as_dicts()

    if worker is not None:
        rows = _filter_rows_for_worker(rows, worker)

    rows = [
        row
        for row in rows
        if row['IX_TYPE'] == 'Initial/Original'
        and row['Days'] is not None
        and row['Days'] <= 14
    ]

    results = dict()
    for threshold in (3, 5, 7, 14):
        count = sum(1 for d in rows if d['Days'] <= threshold)
        results[threshold] = (count, _percent_for_csv(count, cases_ixd))

    return results


def _calc_cases_reinterviewed(
    case_interview_rows: Table, cases_ixd: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'Cases Reinterviewed' count and percentage.  Calculates for all
    workers if passed in worker is None.
    """
    rows = case_interview_rows.data_as_dicts()

    if worker is not None:
        rows = _filter_rows_for_worker(rows, worker)

    case_ids = {
        row['INV_LOCAL_ID']
        for row in rows
        if row['IX_TYPE'] == 'Re-Interview'
        and row['CA_PATIENT_INTV_STATUS'] == 'I - Interviewed'
    }

    count = len(case_ids)

    return count, _percent_for_csv(count, cases_ixd)


def _calc_hiv_previous_positive(
    case_interview_rows: Table, cases_assigned: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'HIV Previous Positive' count and percentage.  Calculates for all
    workers if passed in worker is None.
    """
    rows = case_interview_rows.data_as_dicts()

    if worker is not None:
        rows = _filter_rows_for_worker(rows, worker)

    case_ids = {
        row['INV_LOCAL_ID']
        for row in rows
        if row['CA_PATIENT_INTV_STATUS'] == 'I - Interviewed'
        and row['ADI_900_STATUS_CD'] in ('03', '04', '05')
    }

    count = len(case_ids)

    return count, _percent_for_csv(count, cases_assigned)


def _calc_hiv_tested(
    case_interview_rows: Table, cases_assigned: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'HIV Tested' count and percentage.  Calculates for all workers if
    passed in worker is None.
    """
    rows = case_interview_rows.data_as_dicts()

    if worker is not None:
        rows = _filter_rows_for_worker(rows, worker)

    # nb. mirrors creation of "hiv_tested" table in SAS
    groups: dict[tuple, set] = {}
    for row in rows:
        if (
            row['CA_PATIENT_INTV_STATUS'] == 'I - Interviewed'
            and row['HIV_900_TEST_IND'] == 'Yes'
        ):
            worker_key = (
                row['INVESTIGATOR_INTERVIEW_KEY'],
                row['PROVIDER_QUICK_CODE'],
            )

            groups.setdefault(worker_key, set()).add(row['INV_LOCAL_ID'])

    count = sum(len(case_ids) for case_ids in groups.values())

    return count, _percent_for_csv(count, cases_assigned)


def _calc_hiv_new_positive(
    case_interview_rows: Table, hiv_tested: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'HIV New Positive' count and percentage.  Calculates for all workers
    if passed in worker is None.
    """
    rows = case_interview_rows.data_as_dicts()

    if worker is not None:
        rows = _filter_rows_for_worker(rows, worker)

    positive_results = {
        '13-Positive/Reactive',
        '21-HIV-1 Pos',
        '22-HIV-1 Pos, Possible Acute',
        '23-HIV-2 Pos',
        '24-HIV-Undifferentiated',
        '12-Prelim Positive',
    }

    case_ids = {
        row['INV_LOCAL_ID'] for row in rows if row['HIV_900_RESULT'] in positive_results
    }

    count = len(case_ids)

    return count, _percent_for_csv(count, hiv_tested)


def _calc_hiv_posttest_counsel(
    case_interview_rows: Table, hiv_tested: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'HIV Posttest Counsel' count and percentage.  Calculates for all
    workers if passed in worker is None.
    """
    rows = case_interview_rows.data_as_dicts()

    if worker is not None:
        rows = _filter_rows_for_worker(rows, worker)

    # mirrors creation of "hiv_post_test" table in SAS
    groups: dict[tuple, set] = {}
    for row in rows:
        if (
            row['CA_PATIENT_INTV_STATUS'] == 'I - Interviewed'
            and row['HIV_POST_TEST_900_COUNSELING'] == 'Yes'
        ):
            worker_key = (
                row['PROVIDER_QUICK_CODE'],
                row['INVESTIGATOR_INTERVIEW_KEY'],
            )

            groups.setdefault(worker_key, set()).add(row['INV_LOCAL_ID'])

    count = sum(len(case_ids) for case_ids in groups.values())

    return count, _percent_for_csv(count, hiv_tested)


def _calc_partner_notification_index(
    partner_notification: Table, cases_ixd: int, worker: Pa01Worker | None = None
) -> str:
    """Calculate 'Partner Notification Index'.  Calculates for all workers if passed
    in worker is None.
    """
    rows = partner_notification.data_as_dicts()

    if worker is not None:
        rows = _filter_rows_for_worker(rows, worker)

    count = sum(row['partner_notification_count'] for row in rows)

    return _index_for_csv(count, cases_ixd)


def _calc_testing_index(
    testing_index: Table, cases_ixd: int, worker: Pa01Worker | None = None
) -> str:
    """Calculate 'Testing Index'.  Calculates for all workers if passed in
    worker is None.
    """
    rows = testing_index.data_as_dicts()

    if worker is not None:
        rows = _filter_rows_for_worker(rows, worker)

    count = sum(row['testing_index_count'] for row in rows)

    return _index_for_csv(count, cases_ixd)


def _calc_total_period_partners(
    period_partners: Table, cases_ixd: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'Total Period Partners' count and index.  Calculates for all workers
    if passed in worker is None.
    """
    rows = period_partners.data_as_dicts()

    if worker is not None:
        rows = _filter_rows_for_worker(rows, worker)

    count = sum(row['count_Q'] for row in rows)

    return count, _index_for_csv(count, cases_ixd, 1)


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


def _percent_for_csv(numerator: int, denominator: int, precision: int = 1) -> str:
    """Format a percent that matches the PDF format for output in the CSV."""
    return f'{round((numerator / denominator) * 100, precision) if denominator else 0}%'


def _index_for_csv(numerator: int, denominator: int, precision: int = 2) -> str:
    """Format an index that matches the PDF format for output in the CSV."""
    return f'{round(numerator / denominator, precision) if denominator else 0}'


def _worker_for_csv(worker: Pa01Worker | None = None) -> str:
    """Return the str value for the worker column for output in the CSV.  If worker
    is None then ALL is returned.
    """
    return ALL if worker is None else worker.provider_quick_code


def _filter_rows_for_worker(rows: list[dict], worker: Pa01Worker) -> list[dict]:
    """Given rows from a table (via table.data_as_dicts()), filter them so they only
    represent rows associated with the given worker.  Assumes necessary columns are
    available.
    """
    return [
        row
        for row in rows
        if row['INVESTIGATOR_INTERVIEW_KEY'] == worker.investigator_interview_key
        and row['PROVIDER_QUICK_CODE'] == worker.provider_quick_code
    ]
