import re

from src.db_transaction import Transaction
from src.models import ReportResult, Table

"""
    Go backs:
    - do I need an equivalent query for pa1_new?
    - once all stats for ALL WORKERS are done, need to re-tool to calculate grouped
      by workers
    - loosen up and/or simplify report_title regex
    - tests with no data and edge cases
    - remove notes file from git
"""

# Constants
ALL = 'ALL'
CASE_ASSIGNMENTS_AND_OUTCOMES = 'Case Assignments & Outcomes'
CASES_IXD = "Cases IX'D"

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

# The date field differs in SAS for HIV vs. STD
PA1_DTE_DATE_COL = {
    'HIV': 'CA_INIT_INTVWR_ASSGN_DT',
    'STD': 'CA_INTERVIEWER_ASSIGN_DT',
}


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """PA01 HIV and STD: Case Management Report.

    Conversion notes:
    * This report is the combination of both `PA01_HIV.sas` and `PA01_STD.sas`
    * `report_title` matters here as we're parsing specific report variables from it.
    """
    title_parts = _get_report_title_parts(kwargs['report_title'])
    disease_type = title_parts['disease_type']

    # short circuit if there's no data from subset_query
    data_check = trx.query(subset_query)
    if len(data_check.data) == 0:
        content = Table(
            columns=CSV_COLUMNS,
            data=[],
        )

        return ReportResult(content_type='table', content=content)

    # STD_HIV_DATAMART1 in SAS
    case_interviews_query = f"""
      WITH base AS
      (
        {subset_query}
      )
      SELECT b.*
      FROM base b
        INNER JOIN RDB.dbo.INVESTIGATION i
                ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
               AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
               AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL;
    """

    # pa1_dte in SAS
    timed_interviews_query = f"""
      WITH base AS
      (
        {subset_query}
      ),
      filtered_base AS
      (
        -- STD_HIV_DATAMART1 in PA01_HIV.sas
        SELECT b.*
        FROM base b
          INNER JOIN RDB.dbo.INVESTIGATION i
                  ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
                 AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
                 AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
      )
      SELECT DISTINCT fb.INV_LOCAL_ID,
             di.IX_TYPE,
             i.INV_CASE_STATUS,
             i.RECORD_STATUS_CD,
             fb.CC_CLOSED_DT,
             fb.ADI_900_STATUS_CD,
             fb.HIV_POST_TEST_900_COUNSELING,
             fb.HIV_900_RESULT,
             fb.ADI_900_STATUS,
             fb.HIV_900_TEST_IND,
             fb.SOURCE_SPREAD,
             fb.FL_FUP_INIT_ASSGN_DT,
             i.CURR_PROCESS_STATE,
             fb.CA_PATIENT_INTV_STATUS,
             fb.INVESTIGATOR_INTERVIEW_KEY,
             fb.INVESTIGATOR_INTERVIEW_QC,
             DATEDIFF(DAY,fb.{PA1_DTE_DATE_COL[disease_type]},di.IX_DATE) AS Days,
             dp.PROVIDER_QUICK_CODE
      FROM filtered_base fb
        INNER JOIN RDB.dbo.F_INTERVIEW_CASE fic
                ON fic.INVESTIGATION_KEY = fb.INVESTIGATION_KEY
        INNER JOIN RDB.dbo.D_INTERVIEW di
                ON di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY
               AND di.RECORD_STATUS_CD <> 'LOG_DEL'
        INNER JOIN RDB.dbo.D_PROVIDER dp
                ON dp.PROVIDER_KEY = fb.INVESTIGATOR_INTERVIEW_KEY
        INNER JOIN RDB.dbo.INVESTIGATION i
                ON i.INVESTIGATION_KEY = fb.INVESTIGATION_KEY
      WHERE CAST(di.IX_DATE AS DATE) >=
                CAST(fb.{PA1_DTE_DATE_COL[disease_type]} AS DATE);
    """

    partner_notification_query = f"""
      WITH base AS
      (
        {subset_query}
      ),
      filtered_base AS
      (
        SELECT b.*
        FROM base b
          JOIN RDB.dbo.INVESTIGATION i
            ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
           AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
           AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
      )
      SELECT COUNT(DISTINCT fb.INV_LOCAL_ID) AS partner_notification_count
      FROM filtered_base fb
             JOIN RDB.dbo.F_CONTACT_RECORD_CASE fcrc
               ON fcrc.SUBJECT_INVESTIGATION_KEY = fb.INVESTIGATION_KEY
             JOIN RDB.dbo.STD_HIV_DATAMART contact_dm
               ON contact_dm.INVESTIGATION_KEY = fcrc.CONTACT_INVESTIGATION_KEY
             JOIN RDB.dbo.D_CONTACT_RECORD dcr
               ON dcr.D_CONTACT_RECORD_KEY = fcrc.D_CONTACT_RECORD_KEY
              AND dcr.RECORD_STATUS_CD <> 'LOG_DEL'
        LEFT JOIN RDB.dbo.D_PROVIDER dp
               ON dp.PROVIDER_KEY = fb.INVESTIGATOR_INTERVIEW_KEY
      WHERE dcr.CTT_REFERRAL_BASIS IN (
        'P1 - Partner, Sex',
        'P2 - Partner, Needle-Sharing',
        'P3 - Partner, Both'
      )
      AND contact_dm.FL_FUP_DISPOSITION IN (
        '2 - Prev. Neg, New Pos',
        '3 - Prev. Neg, Still Neg',
        '5 - No Prev Test, New Pos',
        '6 - No Prev Test, New Neg'
      );
    """

    testing_index_query = f"""
      WITH base AS
      (
        {subset_query}
      ),
      filtered_base AS
      (
        -- STD_HIV_DATAMART1 in PA01_HIV.sas
        SELECT b.*
        FROM base b
          INNER JOIN RDB.dbo.INVESTIGATION i
                  ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
                 AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
                 AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
      )
      SELECT COUNT(DISTINCT dcr.LOCAL_ID) AS testing_index_count
      FROM filtered_base fb
        INNER JOIN RDB.dbo.INVESTIGATION i 
                ON i.INVESTIGATION_KEY = fb.INVESTIGATION_KEY
        INNER JOIN RDB.dbo.F_CONTACT_RECORD_CASE fcrc
                ON fcrc.SUBJECT_INVESTIGATION_KEY = fb.INVESTIGATION_KEY
        INNER JOIN RDB.dbo.STD_HIV_DATAMART contact_dm
                ON contact_dm.INVESTIGATION_KEY = fcrc.CONTACT_INVESTIGATION_KEY
        INNER JOIN RDB.dbo.D_CONTACT_RECORD dcr
                ON dcr.D_CONTACT_RECORD_KEY = fcrc.D_CONTACT_RECORD_KEY
               AND dcr.RECORD_STATUS_CD <> 'LOG_DEL'
         LEFT JOIN RDB.dbo.F_INTERVIEW_CASE fic
                ON fic.INVESTIGATION_KEY = fb.INVESTIGATION_KEY
         LEFT JOIN RDB.dbo.D_INTERVIEW di
                ON di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY
               AND di.RECORD_STATUS_CD <> 'LOG_DEL'
         LEFT JOIN RDB.dbo.D_PROVIDER dp
                ON dp.PROVIDER_KEY = fb.INVESTIGATOR_INTERVIEW_KEY
      WHERE dcr.CTT_REFERRAL_BASIS IN (
         'P1 - Partner, Sex',
         'P2 - Partner, Needle-Sharing',
         'P3 - Partner, Both'
      )
      AND contact_dm.FL_FUP_DISPOSITION IN (
         '2 - Prev. Neg, New Pos',
         '3 - Prev. Neg, Still Neg',
         '5 - No Prev Test, New Pos',
         '6 - No Prev Test, New Neg'
      );
    """

    # query result tables
    case_interviews = trx.query(case_interviews_query)
    timed_interviews = trx.query(timed_interviews_query)
    partner_notification = trx.query(partner_notification_query)
    testing_index = trx.query(testing_index_query)

    tables = {
        'case_interviews': case_interviews,
        'timed_interviews': timed_interviews,
        'partner_notification': partner_notification,
        'testing_index': testing_index,
    }

    # output CSV data
    rows = _build_output_for_worker(tables)

    content = Table(
        columns=CSV_COLUMNS,
        data=rows,
    )

    # mismatches
    # - HIV TESTED (250 vs. 226)
    # - HIV NEW POSITIVE (percentage wrong)
    # - HIV POSTTEST COUNSEL (261 vs. 236)

    return ReportResult(content_type='table', content=content)


def _build_output_for_worker(tables: dict, worker=None) -> list[Pa01Row]:
    """Perform all needed calculations for a given worker, output data for
    the final CSV.

    Args:
        tables: Dict of Tables, which are query results indexed by their name
        worker: The worker the data is being calculated for (None means "ALL")

    Returns:
        List of calculated data for a given worker, meant for the final CSV of PA01
    """
    # "Case Assignments & Outcomes" section
    cases_assigned = _calc_cases_assigned(tables['case_interviews'])
    cases_closed, cases_closed_percent = _calc_cases_closed(
        tables['case_interviews'], cases_assigned
    )
    cases_ixd, cases_ixd_percent = _calc_cases_ixd(
        tables['case_interviews'], cases_assigned
    )
    cases_ixd_buckets = _calc_interview_day_buckets(
        tables['timed_interviews'], cases_ixd
    )
    cases_reinterviewed, cases_reinterviewed_percent = _calc_cases_reinterviewed(
        tables['timed_interviews'], cases_ixd
    )
    hiv_previous_positive, hiv_previous_positive_percent = _calc_hiv_previous_positive(
        tables['case_interviews'], cases_assigned
    )
    hiv_tested, hiv_tested_percent = _calc_hiv_tested(
        tables['case_interviews'], cases_assigned
    )
    hiv_new_positive, hiv_new_positive_percent = _calc_hiv_new_positive(
        tables['case_interviews'], hiv_tested
    )
    hiv_posttest_counsel, hiv_posttest_counsel_percent = _calc_hiv_posttest_counsel(
        tables['case_interviews'], hiv_tested
    )
    partner_notification_index = _calc_partner_notification_index(
        tables['partner_notification'], cases_ixd
    )
    testing_index = _calc_testing_index(tables['testing_index'], cases_ixd)

    # output CSV data
    rows: list[Pa01Row] = [
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'Cases Assigned',
            None,
            cases_assigned,
            None,
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'Cases Closed',
            None,
            cases_closed,
            cases_closed_percent,
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            CASES_IXD,
            None,
            cases_ixd,
            cases_ixd_percent,
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            CASES_IXD,
            'Within 3 days',
            cases_ixd_buckets[3][0],
            cases_ixd_buckets[3][1],
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            CASES_IXD,
            'Within 5 days',
            cases_ixd_buckets[5][0],
            cases_ixd_buckets[5][1],
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            CASES_IXD,
            'Within 7 days',
            cases_ixd_buckets[7][0],
            cases_ixd_buckets[7][1],
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            CASES_IXD,
            'Within 14 days',
            cases_ixd_buckets[14][0],
            cases_ixd_buckets[14][1],
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'Cases Reinterviewed',
            None,
            cases_reinterviewed,
            cases_reinterviewed_percent,
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'HIV Previous Positive',
            None,
            hiv_previous_positive,
            hiv_previous_positive_percent,
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'HIV Tested',
            None,
            hiv_tested,
            hiv_tested_percent,
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'HIV New Positive',
            None,
            hiv_new_positive,
            hiv_new_positive_percent,
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'HIV Posttest Counsel',
            None,
            hiv_posttest_counsel,
            hiv_posttest_counsel_percent,
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'Partner Notification Index',
            None,
            None,
            None,
            partner_notification_index,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            'Testing Index',
            None,
            None,
            None,
            testing_index,
        ),
    ]

    return rows


def _calc_cases_assigned(table: Table) -> int:
    """Calculate 'Cases Assigned' count."""
    return len(table.get_unique_column('INV_LOCAL_ID'))


def _calc_cases_closed(table: Table, cases_assigned: int) -> tuple[int, str]:
    """Calculate 'Cases Closed' count and percentage."""
    data = {
        d['INV_LOCAL_ID']
        for d in table.data_as_dicts()
        if d['CA_INTERVIEWER_ASSIGN_DT'] is not None and d['CC_CLOSED_DT'] is not None
    }

    cases_closed = len(data)
    cases_closed_percent = (
        round((cases_closed / cases_assigned) * 100, 1) if cases_assigned else 0
    )

    return cases_closed, f'{cases_closed_percent}%'


def _calc_cases_ixd(table: Table, cases_assigned: int) -> tuple[int, str]:
    """Calculate "Cases IX'D" count and percentage."""
    data = table.data_as_dicts()
    case_ids = {
        d['INV_LOCAL_ID']
        for d in data
        if d['CA_PATIENT_INTV_STATUS'] == 'I - Interviewed'
    }

    count = len(case_ids)
    percent = round((count / cases_assigned) * 100, 1) if cases_assigned else 0

    return count, f'{percent}%'


def _calc_interview_day_buckets(
    table: Table, cases_ixd: int
) -> dict[int, tuple[int, str]]:
    """Calculate "Cases IX'D" count and percentage for within 3, 5, 7, and 14 days."""
    rows = [
        d
        for d in table.data_as_dicts()
        if d['IX_TYPE'] == 'Initial/Original'
        and d['Days'] is not None
        and d['Days'] <= 14
    ]

    results = dict()
    for threshold in (3, 5, 7, 14):
        count = sum(1 for d in rows if d['Days'] <= threshold)
        percent = round((count / cases_ixd) * 100, 1) if cases_ixd else 0

        results[threshold] = (count, f'{percent}%')

    return results


def _calc_cases_reinterviewed(table: Table, cases_ixd: int) -> tuple[int, str]:
    """Calculate 'Cases Reinterviewed' count and percentage."""
    case_ids = {
        d['INV_LOCAL_ID']
        for d in table.data_as_dicts()
        if d['IX_TYPE'] == 'Re-Interview'
    }

    count = len(case_ids)
    percent = round((count / cases_ixd) * 100, 1) if cases_ixd else 0

    return count, f'{percent}%'


def _calc_hiv_previous_positive(
    case_interviews: Table, cases_assigned: int
) -> tuple[int, str]:
    """Calculate 'HIV Previous Positive' count and percentage."""
    case_ids = {
        d['INV_LOCAL_ID']
        for d in case_interviews.data_as_dicts()
        if d['CA_PATIENT_INTV_STATUS'] == 'I - Interviewed'
        and d['ADI_900_STATUS_CD'] in ('03', '04', '05')
    }

    count = len(case_ids)
    percent = round((count / cases_assigned) * 100, 1) if cases_assigned else 0

    return count, f'{percent}%'


def _calc_hiv_tested(case_interviews: Table, cases_assigned: int) -> tuple[int, str]:
    """Calculate 'HIV Tested' count and percentage."""
    case_ids = {
        d['INV_LOCAL_ID']
        for d in case_interviews.data_as_dicts()
        if d['CA_PATIENT_INTV_STATUS'] == 'I - Interviewed'
        and d['HIV_900_TEST_IND'] == 'Yes'
    }

    count = len(case_ids)
    percent = round((count / cases_assigned) * 100, 1) if cases_assigned else 0

    return count, f'{percent}%'


def _calc_hiv_new_positive(case_interviews: Table, hiv_tested: int) -> tuple[int, str]:
    """Calculate 'HIV New Positive' count and percentage."""
    positive_results = {
        '13-Positive/Reactive',
        '21-HIV-1 Pos',
        '22-HIV-1 Pos, Possible Acute',
        '23-HIV-2 Pos',
        '24-HIV-Undifferentiated',
        '12-Prelim Positive',
    }

    case_ids = {
        d['INV_LOCAL_ID']
        for d in case_interviews.data_as_dicts()
        if d['HIV_900_RESULT'] in positive_results
    }

    count = len(case_ids)
    percent = round((count / hiv_tested) * 100, 1) if hiv_tested else 0

    return count, f'{percent}%'


def _calc_hiv_posttest_counsel(
    case_interviews: Table, hiv_tested: int
) -> tuple[int, str]:
    """Calculate 'HIV Posttest Counsel' count and percentage."""
    case_ids = {
        d['INV_LOCAL_ID']
        for d in case_interviews.data_as_dicts()
        if d['CA_PATIENT_INTV_STATUS'] == 'I - Interviewed'
        and d['HIV_POST_TEST_900_COUNSELING'] == 'Yes'
    }

    count = len(case_ids)
    percent = round((count / hiv_tested) * 100, 1) if hiv_tested else 0

    return count, f'{percent}%'


def _calc_partner_notification_index(
    partner_notification: Table, cases_ixd: int
) -> str:
    """Calculate 'Partner Notification Index'."""
    if len(partner_notification.data) != 1 or len(partner_notification.data[0]) != 1:
        raise ValueError('Query data for "Partner Notification Index" is malformed')

    partner_notification_count = partner_notification.data[0][0]
    index = round(partner_notification_count / cases_ixd, 2) if cases_ixd else 0

    return f'{index:0.2f}'


def _calc_testing_index(testing_index: Table, cases_ixd: int) -> str:
    """Calculate 'Testing Index'."""
    if len(testing_index.data) != 1 or len(testing_index.data[0]) != 1:
        raise ValueError('Query data for "Testing Index" is malformed')

    testing_index_count = testing_index.data[0][0]
    index = round(testing_index_count / cases_ixd, 2) if cases_ixd else 0

    return f'{index:0.2f}'


def _get_report_title_parts(report_title: str) -> dict:
    """Get the needed parts from the report title to differentiate between types.

    - STD/HIV
    - Interview Assign Date/Closed Date
    """
    match: re.Match | None = re.match(
        r'^PA01 Case Management Report \((.+)\) - (STD|HIV)$', report_title
    )

    if not match:
        raise ValueError('Report Title analysis regex did not match input string')

    groups = match.groups()

    return {'date_type': groups[0], 'disease_type': groups[1]}
