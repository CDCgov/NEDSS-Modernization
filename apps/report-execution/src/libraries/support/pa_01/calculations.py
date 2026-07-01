from src.libraries.support.pa_01.models import Pa01Row, Pa01Worker
from src.models import Table

# Constants
CASES_IXD = "Cases IX'D"
CASE_ASSIGNMENTS_AND_OUTCOMES = 'Case Assignments & Outcomes'
CA_PATIENT_INTV_STATUS = 'CA_PATIENT_INTV_STATUS'
DAYS = 'Days'
DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS = 'Dispositions - New Partners & Clusters'
DISPO_DOMESTIC_VIOLENCE_RISK = 'V - Domestic Violence Risk'
DISPO_INSUFFICIENT_INFO = 'G - Insufficient Info to Begin Investigation'
DISPO_NO_PREV_TEST_NEW_NEG = '6 - No Prev Test, New Neg'
DISPO_NO_PREV_TEST_NEW_POS = '5 - No Prev Test, New Pos'
DISPO_NO_PREV_TEST_NO_TEST = '7 - No Prev Test, No Test'
DISPO_OOJ = 'K - Sent Out Of Jurisdiction'
DISPO_OTHER = 'L - Other'
DISPO_PATIENT_DECEASED = 'X - Patient Deceased'
DISPO_PREV_NEG_NEW_POS = '2 - Prev. Neg, New Pos'
DISPO_PREV_NEG_NO_TEST = '4 - Prev. Neg, No Test'
DISPO_PREV_NEG_STILL_NEG = '3 - Prev. Neg, Still Neg'
DISPO_REFUSED_EXAM = 'J - Located, Not Examined and/or Interviewed'
DISPO_UNABLE_TO_LOCATE = 'H - Unable to Locate'
FL_FUP_DISPOSITION = 'FL_FUP_DISPOSITION'
INVESTIGATOR_INTERVIEW_KEY = 'INVESTIGATOR_INTERVIEW_KEY'
INV_LOCAL_ID = 'INV_LOCAL_ID'
IX_TYPE = 'IX_TYPE'
NEW_CLUSTERS_NOTIFIED = 'New Clusters Notified'
NEW_CLUSTERS_NOT_NOTIFIED = 'New Clusters Not Notified'
NEW_PARTNERS_NOTIFIED = 'New Partners Notified'
NEW_PARTNERS_NOT_NOTIFIED = 'New Partners Not Notified'
PARTNERS_AND_CLUSTERS_INITIATED = 'Partners & Clusters Initiated'
PROVIDER_QUICK_CODE = 'PROVIDER_QUICK_CODE'
TOTAL_CLUSTERS_INITIATED = 'Total Clusters Initiated'
TOTAL_PARTNERS_INITIATED = 'Total Partners Initiated'


def build_output_for_worker(
    tables: dict[str, Table], worker: Pa01Worker | None = None
) -> list[Pa01Row]:
    """Perform all needed calculations for a given worker, output data for
    the final CSV.

    Args:
        tables: Query results within a dict
        worker: The worker the data is being calculated for (None means "ALL")

    Returns:
        List of calculated data for a given worker, meant for the final CSV of PA01
    """
    rows: list[Pa01Row] = []

    rows.extend(_build_case_assignments_and_outcomes_output(tables, worker))
    rows.extend(_build_partners_and_clusters_initiated_output(tables, worker))
    rows.extend(_build_dispositions_new_partners_and_clusters_output(tables, worker))

    return rows


def _build_case_assignments_and_outcomes_output(
    tables: dict, worker: Pa01Worker | None = None
) -> list[Pa01Row]:
    """Perform all needed calculations for the "Case Assignments and Outcomes" section
    for a given worker, output data for the final CSV.
    """
    cases_assigned = _calc_cases_assigned(tables['case_interview_rows'], worker)
    cases_closed, cases_closed_percent = _calc_cases_closed(
        tables['case_interview_rows'], cases_assigned, worker
    )
    cases_ixd, cases_ixd_percent = _calc_cases_ixd(
        tables['case_interview_rows'], cases_assigned, worker
    )
    cases_ixd_buckets = _calc_interview_day_buckets(
        tables['timed_interviews'], cases_ixd, worker
    )
    cases_reinterviewed, cases_reinterviewed_percent = _calc_cases_reinterviewed(
        tables['case_interview_rows'], cases_ixd, worker
    )
    hiv_previous_positive, hiv_previous_positive_percent = _calc_hiv_previous_positive(
        tables['case_interview_rows'], cases_assigned, worker
    )
    hiv_tested, hiv_tested_percent = _calc_hiv_tested(
        tables['case_interview_rows'], cases_assigned, worker
    )
    hiv_new_positive, hiv_new_positive_percent = _calc_hiv_new_positive(
        tables['case_interview_rows'], hiv_tested, worker
    )
    hiv_posttest_counsel, hiv_posttest_counsel_percent = _calc_hiv_posttest_counsel(
        tables['case_interview_rows'], hiv_tested, worker
    )
    partner_notification_index = _calc_partner_notification_index(
        tables['partner_notification'], cases_ixd, worker
    )
    testing_index = _calc_testing_index(tables['testing_index'], cases_ixd, worker)

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
    tables: dict[str, Table], worker: Pa01Worker | None = None
) -> list[Pa01Row]:
    """Perform all needed calculations for the "Partners and Clusters Initiated"
    section for a given worker, output data for the final CSV.
    """
    cases_assigned = _calc_cases_assigned(tables['case_interview_rows'], worker)
    cases_ixd, _ = _calc_cases_ixd(
        tables['case_interview_rows'], cases_assigned, worker
    )
    total_period_partners, total_period_partners_index = _calc_total_period_partners(
        tables['period_partners'], cases_ixd, worker
    )
    total_partners_initiated = _calc_total_partners_initiated(
        tables['period_partners'], worker
    )
    total_partners_initiated_oi = _calc_total_partners_initiated_oi(
        tables['period_partners'], worker
    )
    total_partners_initiated_ri = _calc_total_partners_initiated_ri(
        tables['period_partners'], worker
    )
    contact_index = _calc_contact_index(tables['period_partners'], cases_ixd, worker)
    cases_with_no_partners, cases_with_no_partners_percentage = (
        _calc_cases_with_no_partners(
            tables['cases_with_no_partners'], cases_ixd, worker
        )
    )
    total_clusters_initiated = _calc_total_clusters_initiated(
        tables['clusters_initiated'], worker
    )
    cluster_index = _calc_cluster_index(tables['clusters_initiated'], cases_ixd, worker)
    cases_with_no_clusters, cases_with_no_clusters_percentage = (
        _calc_cases_with_no_clusters(
            tables['cases_with_no_clusters'], cases_ixd, worker
        )
    )

    # output CSV data
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
        (
            _worker_for_csv(worker),
            PARTNERS_AND_CLUSTERS_INITIATED,
            TOTAL_PARTNERS_INITIATED,
            None,
            total_partners_initiated,
            None,
            None,
        ),
        (
            _worker_for_csv(worker),
            PARTNERS_AND_CLUSTERS_INITIATED,
            TOTAL_PARTNERS_INITIATED,
            'From OI',
            total_partners_initiated_oi,
            None,
            None,
        ),
        (
            _worker_for_csv(worker),
            PARTNERS_AND_CLUSTERS_INITIATED,
            TOTAL_PARTNERS_INITIATED,
            'From RI',
            total_partners_initiated_ri,
            None,
            None,
        ),
        (
            _worker_for_csv(worker),
            PARTNERS_AND_CLUSTERS_INITIATED,
            'Contact Index',
            None,
            None,
            None,
            contact_index,
        ),
        (
            _worker_for_csv(worker),
            PARTNERS_AND_CLUSTERS_INITIATED,
            'Cases W/No Partners',
            None,
            cases_with_no_partners,
            cases_with_no_partners_percentage,
            None,
        ),
        (
            _worker_for_csv(worker),
            PARTNERS_AND_CLUSTERS_INITIATED,
            TOTAL_CLUSTERS_INITIATED,
            None,
            total_clusters_initiated,
            None,
            None,
        ),
        (
            _worker_for_csv(worker),
            PARTNERS_AND_CLUSTERS_INITIATED,
            TOTAL_CLUSTERS_INITIATED,
            'Cluster Index',
            None,
            None,
            cluster_index,
        ),
        (
            _worker_for_csv(worker),
            PARTNERS_AND_CLUSTERS_INITIATED,
            TOTAL_CLUSTERS_INITIATED,
            'Cases /W No Clusters',
            cases_with_no_clusters,
            cases_with_no_clusters_percentage,
            None,
        ),
    ]

    return rows


def _build_dispositions_new_partners_and_clusters_output(
    tables: dict[str, Table], worker: Pa01Worker | None = None
) -> list[Pa01Row]:
    """Perform all needed calculations for the "Dispositions - New Partners & Clusters"
    section for a given worker, output data for the final CSV.
    """
    total_partners_initiated = _calc_total_partners_initiated(
        tables['period_partners'], worker
    )
    new_partners_notified, new_partners_notified_percentage = (
        _calc_new_partners_notified(
            tables['notified_partners'], total_partners_initiated, worker
        )
    )
    new_partners_notified_buckets = _calc_new_partners_notified_buckets(
        tables['notified_partners'], new_partners_notified, worker
    )
    new_partners_not_notified, new_partners_not_notified_percentage = (
        _calc_new_partners_not_notified(
            tables['not_notified_partners'], total_partners_initiated, worker
        )
    )
    new_partners_not_notified_buckets = _calc_new_partners_not_notified_buckets(
        tables['not_notified_partners'], new_partners_not_notified, worker
    )
    new_partners_previous_pos, new_partners_previous_pos_percentage = (
        _calc_new_partners_previous_pos(
            tables['partner_case_dispositions'], total_partners_initiated, worker
        )
    )
    new_partners_open, new_partners_open_percentage = _calc_new_partners_open(
        tables['partner_case_dispositions'], total_partners_initiated, worker
    )
    total_clusters_initiated = _calc_total_clusters_initiated(
        tables['clusters_initiated'], worker
    )
    new_clusters_notified, new_clusters_notified_percentage = (
        _calc_new_clusters_notified(
            tables['notified_clusters'], total_clusters_initiated, worker
        )
    )
    new_clusters_notified_buckets = _calc_new_clusters_notified_buckets(
        tables['notified_clusters'], new_clusters_notified, worker
    )
    new_clusters_not_notified, new_clusters_not_notified_percentage = (
        _calc_new_clusters_not_notified(
            tables['not_notified_clusters'], total_clusters_initiated, worker
        )
    )
    new_clusters_not_notified_buckets = _calc_new_clusters_not_notified_buckets(
        tables['not_notified_clusters'], new_clusters_not_notified, worker
    )

    rows: list[Pa01Row] = [
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOTIFIED,
            None,
            new_partners_notified,
            new_partners_notified_percentage,
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOTIFIED,
            'Prev. Neg, New Pos',
            new_partners_notified_buckets[DISPO_PREV_NEG_NEW_POS][0],
            new_partners_notified_buckets[DISPO_PREV_NEG_NEW_POS][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOTIFIED,
            'Prev. Neg, Still Neg',
            new_partners_notified_buckets[DISPO_PREV_NEG_STILL_NEG][0],
            new_partners_notified_buckets[DISPO_PREV_NEG_STILL_NEG][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOTIFIED,
            'Prev. Neg, No Test',
            new_partners_notified_buckets[DISPO_PREV_NEG_NO_TEST][0],
            new_partners_notified_buckets[DISPO_PREV_NEG_NO_TEST][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOTIFIED,
            'No Prev. Test, New Pos',
            new_partners_notified_buckets[DISPO_NO_PREV_TEST_NEW_POS][0],
            new_partners_notified_buckets[DISPO_NO_PREV_TEST_NEW_POS][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOTIFIED,
            'No Prev. Test, New Neg',
            new_partners_notified_buckets[DISPO_NO_PREV_TEST_NEW_NEG][0],
            new_partners_notified_buckets[DISPO_NO_PREV_TEST_NEW_NEG][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOTIFIED,
            'No Prev. Test, No Test',
            new_partners_notified_buckets[DISPO_NO_PREV_TEST_NO_TEST][0],
            new_partners_notified_buckets[DISPO_NO_PREV_TEST_NO_TEST][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOT_NOTIFIED,
            None,
            new_partners_not_notified,
            new_partners_not_notified_percentage,
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOT_NOTIFIED,
            'Insufficient Info',
            new_partners_not_notified_buckets[DISPO_INSUFFICIENT_INFO][0],
            new_partners_not_notified_buckets[DISPO_INSUFFICIENT_INFO][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOT_NOTIFIED,
            'Unable to Locate',
            new_partners_not_notified_buckets[DISPO_UNABLE_TO_LOCATE][0],
            new_partners_not_notified_buckets[DISPO_UNABLE_TO_LOCATE][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOT_NOTIFIED,
            'Refused Exam',
            new_partners_not_notified_buckets[DISPO_REFUSED_EXAM][0],
            new_partners_not_notified_buckets[DISPO_REFUSED_EXAM][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOT_NOTIFIED,
            'OOJ',
            new_partners_not_notified_buckets[DISPO_OOJ][0],
            new_partners_not_notified_buckets[DISPO_OOJ][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOT_NOTIFIED,
            'Other',
            new_partners_not_notified_buckets[DISPO_OTHER][0],
            new_partners_not_notified_buckets[DISPO_OTHER][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOT_NOTIFIED,
            'Domestic Violence Risk',
            new_partners_not_notified_buckets[DISPO_DOMESTIC_VIOLENCE_RISK][0],
            new_partners_not_notified_buckets[DISPO_DOMESTIC_VIOLENCE_RISK][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_PARTNERS_NOT_NOTIFIED,
            'Patient Deceased',
            new_partners_not_notified_buckets[DISPO_PATIENT_DECEASED][0],
            new_partners_not_notified_buckets[DISPO_PATIENT_DECEASED][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            'Previous Pos',
            None,
            new_partners_previous_pos,
            new_partners_previous_pos_percentage,
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            'Open',
            None,
            new_partners_open,
            new_partners_open_percentage,
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOTIFIED,
            None,
            new_clusters_notified,
            new_clusters_notified_percentage,
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOTIFIED,
            'Prev. Neg, New Pos',
            new_clusters_notified_buckets[DISPO_PREV_NEG_NEW_POS][0],
            new_clusters_notified_buckets[DISPO_PREV_NEG_NEW_POS][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOTIFIED,
            'Prev. Neg, Still Neg',
            new_clusters_notified_buckets[DISPO_PREV_NEG_STILL_NEG][0],
            new_clusters_notified_buckets[DISPO_PREV_NEG_STILL_NEG][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOTIFIED,
            'Prev. Neg, No Test',
            new_clusters_notified_buckets[DISPO_PREV_NEG_NO_TEST][0],
            new_clusters_notified_buckets[DISPO_PREV_NEG_NO_TEST][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOTIFIED,
            'No Prev. Test, New Pos',
            new_clusters_notified_buckets[DISPO_NO_PREV_TEST_NEW_POS][0],
            new_clusters_notified_buckets[DISPO_NO_PREV_TEST_NEW_POS][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOTIFIED,
            'No Prev. Test, New Neg',
            new_clusters_notified_buckets[DISPO_NO_PREV_TEST_NEW_NEG][0],
            new_clusters_notified_buckets[DISPO_NO_PREV_TEST_NEW_NEG][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOTIFIED,
            'No Prev. Test, No Test',
            new_clusters_notified_buckets[DISPO_NO_PREV_TEST_NO_TEST][0],
            new_clusters_notified_buckets[DISPO_NO_PREV_TEST_NO_TEST][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOT_NOTIFIED,
            None,
            new_clusters_not_notified,
            new_clusters_not_notified_percentage,
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOT_NOTIFIED,
            'Insufficient Info',
            new_clusters_not_notified_buckets[DISPO_INSUFFICIENT_INFO][0],
            new_clusters_not_notified_buckets[DISPO_INSUFFICIENT_INFO][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOT_NOTIFIED,
            'Unable to Locate',
            new_clusters_not_notified_buckets[DISPO_UNABLE_TO_LOCATE][0],
            new_clusters_not_notified_buckets[DISPO_UNABLE_TO_LOCATE][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOT_NOTIFIED,
            'Refused Exam',
            new_clusters_not_notified_buckets[DISPO_REFUSED_EXAM][0],
            new_clusters_not_notified_buckets[DISPO_REFUSED_EXAM][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOT_NOTIFIED,
            'OOJ',
            new_clusters_not_notified_buckets[DISPO_OOJ][0],
            new_clusters_not_notified_buckets[DISPO_OOJ][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOT_NOTIFIED,
            'Other',
            new_clusters_not_notified_buckets[DISPO_OTHER][0],
            new_clusters_not_notified_buckets[DISPO_OTHER][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOT_NOTIFIED,
            'Domestic Violence Risk',
            new_clusters_not_notified_buckets[DISPO_DOMESTIC_VIOLENCE_RISK][0],
            new_clusters_not_notified_buckets[DISPO_DOMESTIC_VIOLENCE_RISK][1],
            None,
        ),
        (
            _worker_for_csv(worker),
            DISPOSITIONS_NEW_PARTNERS_AND_CLUSTERS,
            NEW_CLUSTERS_NOT_NOTIFIED,
            'Patient Deceased',
            new_clusters_not_notified_buckets[DISPO_PATIENT_DECEASED][0],
            new_clusters_not_notified_buckets[DISPO_PATIENT_DECEASED][1],
            None,
        ),
    ]

    return rows


# NEW PARTNERS NOT NOTIFIED
# PREVIOUS POS, pp1, where fl_fup_disposition = 'E - Previously Treated...
# OPEN, pp1, where fl_fup_disposition is null

# NEW CLUSTERS NOT NOTIFIED
# PREVIOUS POS, c1 as is
# OPEN, cluster where fl_fup_dispositions is null


# actual calculations
def _calc_cases_assigned(
    case_interview_rows: Table, worker: Pa01Worker | None = None
) -> int:
    """Calculate 'Cases Assigned' count for a given worker.  Calculates for all workers
    if passed in worker is None.
    """
    rows = _rows_for_worker(case_interview_rows, worker)

    return _count_distinct_case_ids(rows)


def _calc_cases_closed(
    case_interview_rows: Table, cases_assigned: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'Cases Closed' count and percentage for a given worker.  Calculates
    for all workers if passed in worker is None.
    """
    rows = _rows_for_worker(case_interview_rows, worker)
    count = _count_distinct_case_ids(rows, lambda row: row['CC_CLOSED_DT'] is not None)

    return count, _percent_for_csv(count, cases_assigned)


def _calc_cases_ixd(
    case_interview_rows: Table, cases_assigned: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate "Cases IX'D" count and percentage.  Calculates for all workers if
    passed in worker is None.
    """
    rows = _rows_for_worker(case_interview_rows, worker)
    count = _count_distinct_case_ids(
        rows, lambda row: row[CA_PATIENT_INTV_STATUS] == 'I - Interviewed'
    )

    return count, _percent_for_csv(count, cases_assigned)


def _calc_interview_day_buckets(
    timed_interviews: Table, cases_ixd: int, worker: Pa01Worker | None = None
) -> dict[int, tuple[int, str]]:
    """Calculate "Cases IX'D" count and percentage for within 3, 5, 7, and 14 days.
    Calculates for all workers if passed in worker is None.
    """
    rows = _rows_for_worker(timed_interviews, worker)
    rows = [
        row
        for row in rows
        if row[IX_TYPE] == 'Initial/Original'
        and row[DAYS] is not None
        and row[DAYS] <= 14
    ]

    results = dict()
    for threshold in (3, 5, 7, 14):
        count = sum(1 for d in rows if d[DAYS] <= threshold)
        results[threshold] = (count, _percent_for_csv(count, cases_ixd))

    return results


def _calc_cases_reinterviewed(
    case_interview_rows: Table, cases_ixd: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'Cases Reinterviewed' count and percentage.  Calculates for all
    workers if passed in worker is None.
    """
    rows = _rows_for_worker(case_interview_rows, worker)
    count = _count_distinct_case_ids(
        rows,
        lambda row: (
            row[IX_TYPE] == 'Re-Interview'
            and row[CA_PATIENT_INTV_STATUS] == 'I - Interviewed'
        ),
    )

    return count, _percent_for_csv(count, cases_ixd)


def _calc_hiv_previous_positive(
    case_interview_rows: Table, cases_assigned: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'HIV Previous Positive' count and percentage.  Calculates for all
    workers if passed in worker is None.
    """
    rows = _rows_for_worker(case_interview_rows, worker)
    count = _count_distinct_case_ids(
        rows,
        lambda row: (
            row[CA_PATIENT_INTV_STATUS] == 'I - Interviewed'
            and row['ADI_900_STATUS_CD'] in ('03', '04', '05')
        ),
    )

    return count, _percent_for_csv(count, cases_assigned)


def _calc_hiv_tested(
    case_interview_rows: Table, cases_assigned: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'HIV Tested' count and percentage.  Calculates for all workers if
    passed in worker is None.
    """
    rows = _rows_for_worker(case_interview_rows, worker)

    # nb. mirrors creation of "hiv_tested" table in SAS
    groups: dict[tuple, set] = {}
    for row in rows:
        if (
            row[CA_PATIENT_INTV_STATUS] == 'I - Interviewed'
            and row['HIV_900_TEST_IND'] == 'Yes'
        ):
            worker_key = (
                row[INVESTIGATOR_INTERVIEW_KEY],
                row[PROVIDER_QUICK_CODE],
            )

            groups.setdefault(worker_key, set()).add(row[INV_LOCAL_ID])

    count = sum(len(case_ids) for case_ids in groups.values())

    return count, _percent_for_csv(count, cases_assigned)


def _calc_hiv_new_positive(
    case_interview_rows: Table, hiv_tested: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'HIV New Positive' count and percentage.  Calculates for all workers
    if passed in worker is None.
    """
    rows = _rows_for_worker(case_interview_rows, worker)

    positive_results = {
        '13-Positive/Reactive',
        '21-HIV-1 Pos',
        '22-HIV-1 Pos, Possible Acute',
        '23-HIV-2 Pos',
        '24-HIV-Undifferentiated',
        '12-Prelim Positive',
    }

    count = _count_distinct_case_ids(
        rows, lambda row: row['HIV_900_RESULT'] in positive_results
    )

    return count, _percent_for_csv(count, hiv_tested)


def _calc_hiv_posttest_counsel(
    case_interview_rows: Table, hiv_tested: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'HIV Posttest Counsel' count and percentage.  Calculates for all
    workers if passed in worker is None.
    """
    rows = _rows_for_worker(case_interview_rows, worker)

    # mirrors creation of "hiv_post_test" table in SAS
    groups: dict[tuple, set] = {}
    for row in rows:
        if (
            row[CA_PATIENT_INTV_STATUS] == 'I - Interviewed'
            and row['HIV_POST_TEST_900_COUNSELING'] == 'Yes'
        ):
            worker_key = (
                row[PROVIDER_QUICK_CODE],
                row[INVESTIGATOR_INTERVIEW_KEY],
            )

            groups.setdefault(worker_key, set()).add(row[INV_LOCAL_ID])

    count = sum(len(case_ids) for case_ids in groups.values())

    return count, _percent_for_csv(count, hiv_tested)


def _calc_partner_notification_index(
    partner_notification: Table, cases_ixd: int, worker: Pa01Worker | None = None
) -> str:
    """Calculate 'Partner Notification Index'.  Calculates for all workers if passed
    in worker is None.
    """
    rows = _rows_for_worker(partner_notification, worker)
    count = sum(row['partner_notification_count'] for row in rows)

    return _index_for_csv(count, cases_ixd)


def _calc_testing_index(
    testing_index: Table, cases_ixd: int, worker: Pa01Worker | None = None
) -> str:
    """Calculate 'Testing Index'.  Calculates for all workers if passed in
    worker is None.
    """
    rows = _rows_for_worker(testing_index, worker)
    count = sum(row['testing_index_count'] for row in rows)

    return _index_for_csv(count, cases_ixd)


def _calc_total_period_partners(
    period_partners: Table, cases_ixd: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'Total Period Partners' count and index.  Calculates for all workers
    if passed in worker is None.
    """
    rows = _rows_for_worker(period_partners, worker)
    count = sum(row['count_Q'] for row in rows)

    return count, _index_for_csv(count, cases_ixd, 1)


def _calc_total_partners_initiated(
    period_partners: Table, worker: Pa01Worker | None = None
) -> int:
    """Calculate 'Total Partners Initiated' count.  Calculates for all workers
    if passed in worker is None.
    """
    rows = _rows_for_worker(period_partners, worker)

    return _count_distinct_case_ids(rows)


def _calc_total_partners_initiated_oi(
    period_partners: Table, worker: Pa01Worker | None = None
) -> int:
    """Calculate 'Total Partners Initiated From OI' count.  Calculates for all workers
    if passed in worker is None.
    """
    rows = _rows_for_worker(period_partners, worker)
    count = _count_distinct_case_ids(
        rows, lambda row: row[IX_TYPE] == 'Initial/Original'
    )

    return count


def _calc_total_partners_initiated_ri(
    period_partners: Table, worker: Pa01Worker | None = None
) -> int:
    """Calculate 'Total Partners Initiated From RI' count.  Calculates for all workers
    if passed in worker is None.
    """
    rows = _rows_for_worker(period_partners, worker)
    count = _count_distinct_case_ids(rows, lambda row: row[IX_TYPE] == 'Re-Interview')

    return count


def _calc_contact_index(
    period_partners: Table, cases_ixd: int, worker: Pa01Worker | None = None
) -> str:
    """Calculate 'Contact Index'.  Calculates for all workers if passed in worker is
    None.
    """
    rows = _rows_for_worker(period_partners, worker)
    count = _count_distinct_case_ids(rows)

    # nb. this is to align with the precision found in the SAS report
    precision = 2 if worker is None else 1
    return _index_for_csv(count, cases_ixd, precision)


def _calc_cases_with_no_partners(
    cases_with_no_partners: Table, cases_ixd: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'Cases W/No Partners' count and percentage.  Calculates for all workers
    if passed in worker is None.
    """
    rows = _rows_for_worker(cases_with_no_partners, worker)
    count = _count_distinct_case_ids(rows)

    return count, _percent_for_csv(count, cases_ixd)


def _calc_total_clusters_initiated(
    clusters_initiated: Table, worker: Pa01Worker | None = None
) -> int:
    """Calculate 'Total Clusters Initiated' count. Calculates for all workers if passed
    in worker is None.
    """
    rows = _rows_for_worker(clusters_initiated, worker)
    return _count_distinct_case_ids(rows)


def _calc_cluster_index(
    clusters_initiated: Table, cases_ixd: int, worker: Pa01Worker | None = None
) -> str:
    """Calculate 'Cluster Index'.  Calculates for all workers if passed in worker is
    None.
    """
    rows = _rows_for_worker(clusters_initiated, worker)
    count = _count_distinct_case_ids(rows)

    return _index_for_csv(count, cases_ixd)


def _calc_cases_with_no_clusters(
    cases_with_no_clusters: Table, cases_ixd: int, worker: Pa01Worker | None = None
) -> tuple[int, str]:
    """Calculate 'Cases /W No Clusters' count and percentage.  Calculates for all
    workers if passed in worker is None.
    """
    rows = _rows_for_worker(cases_with_no_clusters, worker)
    count = _count_distinct_case_ids(rows)

    return count, _percent_for_csv(count, cases_ixd)


def _calc_new_partners_notified(
    notified_partners: Table,
    total_partners_initiated: int,
    worker: Pa01Worker | None = None,
) -> tuple[int, str]:
    """Calculate 'New Partners Notified' count and percentage.  Calculates for all
    workers if passed in worker is None.
    """
    rows = _rows_for_worker(notified_partners, worker)
    count = _count_distinct_case_ids(rows)

    return count, _percent_for_csv(count, total_partners_initiated)


def _calc_new_partners_notified_buckets(
    notified_partners: Table,
    new_partners_notified: int,
    worker: Pa01Worker | None = None,
) -> dict[str, tuple[int, str]]:
    """Calculate the count and percentage for all of the sub sections of 'New Partners
    Notified'.  Calculates for all workers if passed in worker is None.
    """
    rows = _rows_for_worker(notified_partners, worker)
    fl_fup_dispositions = [
        DISPO_PREV_NEG_NEW_POS,
        DISPO_PREV_NEG_STILL_NEG,
        DISPO_PREV_NEG_NO_TEST,
        DISPO_NO_PREV_TEST_NEW_POS,
        DISPO_NO_PREV_TEST_NEW_NEG,
        DISPO_NO_PREV_TEST_NO_TEST,
    ]

    result = {}
    for dispo in fl_fup_dispositions:
        count = _count_distinct_case_ids(
            rows,
            lambda row, dispo=dispo: row[FL_FUP_DISPOSITION] == dispo,
        )
        result[dispo] = (count, _percent_for_csv(count, new_partners_notified))

    return result


def _calc_new_partners_not_notified(
    not_notified_partners: Table,
    total_partners_initiated: int,
    worker: Pa01Worker | None = None,
) -> tuple[int, str]:
    """Calculate the count and percentage for 'New Partners Not Notified'.  Calculates
    for all workers if passed in worker is None.
    """
    rows = _rows_for_worker(not_notified_partners, worker)
    count = _count_distinct_case_ids(rows)

    return count, _percent_for_csv(count, total_partners_initiated)


def _calc_new_partners_not_notified_buckets(
    not_notified_partners: Table,
    new_partners_not_notified: int,
    worker: Pa01Worker | None = None,
) -> dict[str, tuple[int, str]]:
    """Calculate the count and percentage for all of the sub sections of 'New Partners
    Not Notified'.  Calculates for all workers if passed in worker is None.
    """
    rows = _rows_for_worker(not_notified_partners, worker)
    fl_fup_dispositions = [
        DISPO_INSUFFICIENT_INFO,
        DISPO_UNABLE_TO_LOCATE,
        DISPO_REFUSED_EXAM,
        DISPO_OOJ,
        DISPO_OTHER,
        DISPO_DOMESTIC_VIOLENCE_RISK,
        DISPO_PATIENT_DECEASED,
    ]

    result = {}
    for dispo in fl_fup_dispositions:
        count = _count_distinct_case_ids(
            rows,
            lambda row, dispo=dispo: row[FL_FUP_DISPOSITION] == dispo,
        )
        result[dispo] = (count, _percent_for_csv(count, new_partners_not_notified))

    return result


def _calc_new_partners_previous_pos(
    partner_case_dispositions: Table,
    total_partners_initiated: int,
    worker: Pa01Worker | None = None,
) -> tuple[int, str]:
    """Calculate the count and percentage of 'New Partners Previous Pos'.  Calculates
    for all workers if passed in worker is None.
    """
    rows = _rows_for_worker(partner_case_dispositions, worker)
    count = _count_distinct_case_ids(
        rows,
        lambda row: (
            row[FL_FUP_DISPOSITION] == 'E - Previously Treated for This Infection'
        ),
    )

    return count, _percent_for_csv(count, total_partners_initiated)


def _calc_new_partners_open(
    partner_case_dispositions: Table,
    total_partners_initiated: int,
    worker: Pa01Worker | None = None,
) -> tuple[int, str]:
    """Calculate the count and percentage of 'New Partners Open'.  Calculates
    for all workers if passed in worker is None.
    """
    rows = _rows_for_worker(partner_case_dispositions, worker)
    count = _count_distinct_case_ids(rows, lambda row: row[FL_FUP_DISPOSITION] is None)

    return count, _percent_for_csv(count, total_partners_initiated)


def _calc_new_clusters_notified(
    clusters_notified: Table,
    total_clusters_initiated: int,
    worker: Pa01Worker | None = None,
) -> tuple[int, str]:
    """Calculate 'New Clusters Notified' count and percentage.  Calculates for all
    workers if passed in worker is None.
    """
    rows = _rows_for_worker(clusters_notified, worker)

    # nb. normally I would use the _count_distinct_case_ids for both ALL WORKERS
    #     and individual workers.  However the SAS script has a different count
    #     approach when ALL WORKERS is being calculated.
    count = len(rows) if worker is None else _count_distinct_case_ids(rows)

    return count, _percent_for_csv(count, total_clusters_initiated)


def _calc_new_clusters_notified_buckets(
    clusters_notified: Table,
    new_clusters_notified: int,
    worker: Pa01Worker | None = None,
) -> dict[str, tuple[int, str]]:
    """Calculate the count and percentage for all of the sub sections of 'New Clusters
    Notified'.  Calculates for all workers if passed in worker is None.
    """
    rows = _rows_for_worker(clusters_notified, worker)
    fl_fup_dispositions = [
        DISPO_PREV_NEG_NEW_POS,
        DISPO_PREV_NEG_STILL_NEG,
        DISPO_PREV_NEG_NO_TEST,
        DISPO_NO_PREV_TEST_NEW_POS,
        DISPO_NO_PREV_TEST_NEW_NEG,
        DISPO_NO_PREV_TEST_NO_TEST,
    ]

    result = {}
    for dispo in fl_fup_dispositions:
        count = _count_distinct_case_ids(
            rows,
            lambda row, dispo=dispo: row[FL_FUP_DISPOSITION] == dispo,
        )
        result[dispo] = (count, _percent_for_csv(count, new_clusters_notified))

    return result


def _calc_new_clusters_not_notified(
    not_notified_clusters: Table,
    total_clusters_initiated: int,
    worker: Pa01Worker | None = None,
) -> tuple[int, str]:
    """Calculate 'New Clusters Not Notified' count and percentage.  Calculates for all
    workers if passed in worker is None.
    """
    rows = _rows_for_worker(not_notified_clusters, worker)
    count = _count_distinct_case_ids(rows)

    return count, _percent_for_csv(count, total_clusters_initiated)


def _calc_new_clusters_not_notified_buckets(
    not_notified_clusters: Table,
    new_clusters_not_notified: int,
    worker: Pa01Worker | None = None,
) -> dict[str, tuple[int, str]]:
    """Calculate the count and percentage for all of the sub sections of 'New Clusters
    Not Notified'.  Calculates for all workers if passed in worker is None.
    """
    rows = _rows_for_worker(not_notified_clusters, worker)
    fl_fup_dispositions = [
        DISPO_INSUFFICIENT_INFO,
        DISPO_UNABLE_TO_LOCATE,
        DISPO_REFUSED_EXAM,
        DISPO_OOJ,
        DISPO_OTHER,
        DISPO_DOMESTIC_VIOLENCE_RISK,
        DISPO_PATIENT_DECEASED,
    ]

    result = {}
    for dispo in fl_fup_dispositions:
        count = _count_distinct_case_ids(
            rows,
            lambda row, dispo=dispo: row[FL_FUP_DISPOSITION] == dispo,
        )
        result[dispo] = (count, _percent_for_csv(count, new_clusters_not_notified))

    return result


# helpers
def _rows_for_worker(table: Table, worker: Pa01Worker | None = None) -> list[dict]:
    """Filter a given table's data for the given worker.  If the given worker is None
    then return all rows.
    """
    rows = table.data_as_dicts()
    return _filter_rows_for_worker(rows, worker) if worker else rows


def _filter_rows_for_worker(rows: list[dict], worker: Pa01Worker) -> list[dict]:
    """Given rows from a table (via table.data_as_dicts()), filter them so they only
    represent rows associated with the given worker.  Assumes necessary columns are
    available.
    """
    return [
        row
        for row in rows
        if row[INVESTIGATOR_INTERVIEW_KEY] == worker.investigator_interview_key
        and row[PROVIDER_QUICK_CODE] == worker.provider_quick_code
    ]


def _count_distinct_case_ids(rows: list[dict], predicate=lambda row: True) -> int:
    """Count distinct case ids in the given rows, applying the given predicate to each.
    If no predicate is given then the rows will not be filtered.
    """
    return len({row[INV_LOCAL_ID] for row in rows if predicate(row)})


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
    return 'ALL' if worker is None else worker.provider_quick_code
