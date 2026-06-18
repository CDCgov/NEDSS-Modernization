import re

from src.db_transaction import Transaction
from src.models import ReportResult, Table

"""
    Go backs:
    - do I need pa1_new?  If so name it "case_interviews"
    - once all stats for ALL WORKERS are done, need to re-tool to calculate grouped
      by workers
    - loosen up and/or simplify report_title regex
"""

Pa01Row = tuple[
    str,  # Worker
    str,  # Category 1
    str,  # Category 2
    str | None,  # Category 3
    int | None,  # Count
    float | None,  # Percentage
    float | None,  # Index
]

ALL = 'ALL'
CASE_ASSIGNMENTS_AND_OUTCOMES = 'Case Assignments & Outcomes'

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

    # STD_HIV_DATAMART1 in SAS
    base_query = f"""
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

    # tables
    base = trx.query(base_query)
    timed_interviews = trx.query(timed_interviews_query)

    # calculations
    cases_assigned = _calc_cases_assigned(base)
    cases_closed, cases_closed_percent = _calc_cases_closed(base, cases_assigned)
    cases_ixd, cases_ixd_percent = _calc_cases_ixd(base, cases_assigned)
    cases_ixd_buckets = _calc_interview_day_buckets(timed_interviews, cases_ixd)
    cases_reinterviewed, cases_reinterviewed_percent = _calc_cases_reinterviewed(
        timed_interviews, cases_ixd
    )

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
            "Cases IX'D",
            None,
            cases_ixd,
            cases_ixd_percent,
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            "Cases IX'D",
            'Within 3 days',
            cases_ixd_buckets[3][0],
            cases_ixd_buckets[3][1],
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            "Cases IX'D",
            'Within 5 days',
            cases_ixd_buckets[5][0],
            cases_ixd_buckets[5][1],
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            "Cases IX'D",
            'Within 7 days',
            cases_ixd_buckets[7][0],
            cases_ixd_buckets[7][1],
            None,
        ),
        (
            ALL,
            CASE_ASSIGNMENTS_AND_OUTCOMES,
            "Cases IX'D",
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
    ]

    breakpoint()

    content = Table(
        columns=CSV_COLUMNS,
        data=rows,
    )

    return ReportResult(content_type='table', content=content)


def _get_report_title_parts(report_title: str) -> dict:
    """Get the needed parts from the report title to differentiate between types.

    - STD/HIV
    - Interview Assign Date/Closed Date
    """
    match = re.match(
        r'^PA01 Case Management Report \((.+)\) - (STD|HIV)$', report_title
    )
    groups = match.groups()

    return {'date_type': groups[0], 'disease_type': groups[1]}


def _calc_cases_assigned(table: Table) -> int:
    return len(table.get_unique_column('INV_LOCAL_ID'))


def _calc_cases_closed(table: Table, cases_assigned: int) -> tuple[int, str]:
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


def _calc_cases_reinterviewed(table: Table, cases_assigned: int) -> tuple[int, str]:
    case_ids = {
        d['INV_LOCAL_ID']
        for d in table.data_as_dicts()
        if d['IX_TYPE'] == 'Re-Interview'
    }

    count = len(case_ids)
    percent = round((count / cases_assigned) * 100, 1) if cases_assigned else 0

    return count, f'{percent}%'


# cases_query = f"""
#       WITH base AS
#       (
#         {subset_query}
#       ),
#       filtered_base AS
#       (
#         -- STD_HIV_DATAMART1 in PA01_HIV.sas
#         SELECT b.*
#         FROM base b
#           INNER JOIN RDB.dbo.INVESTIGATION i
#                   ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
#                  AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
#                  AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
#       )
#       -- pa1_new in PA01_HIV.sas
#       SELECT DISTINCT fb.INV_LOCAL_ID,
#              di.IX_TYPE,
#              i.INV_CASE_STATUS,
#              i.RECORD_STATUS_CD,
#              fb.CC_CLOSED_DT,
#              fb.ADI_900_STATUS_CD,
#              fb.HIV_POST_TEST_900_COUNSELING,
#              fb.HIV_900_RESULT,
#              fb.ADI_900_STATUS,
#              fb.HIV_900_TEST_IND,
#              fb.SOURCE_SPREAD,
#              fb.FL_FUP_INIT_ASSGN_DT,
#              i.CURR_PROCESS_STATE,
#              fb.CA_PATIENT_INTV_STATUS,
#              fb.INVESTIGATOR_INTERVIEW_KEY,
#              fb.INVESTIGATOR_INTERVIEW_QC,
#              DATEDIFF(DAY,fb.{PA1_NEW_DATE_COL[disease_type]},di.IX_DATE) AS Days,
#              dp.PROVIDER_QUICK_CODE
#       FROM filtered_base fb
#         LEFT OUTER JOIN RDB.dbo.F_INTERVIEW_CASE fic
#                      ON fic.INVESTIGATION_KEY = fb.INVESTIGATION_KEY
#         LEFT OUTER JOIN RDB.dbo.D_INTERVIEW di
#                      ON di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY
#                     AND di.RECORD_STATUS_CD <> 'LOG_DEL'
#         LEFT OUTER JOIN RDB.dbo.D_PROVIDER dp
#                      ON dp.PROVIDER_KEY = fb.INVESTIGATOR_INTERVIEW_KEY
#         LEFT OUTER JOIN RDB.dbo.INVESTIGATION i
#                      ON i.INVESTIGATION_KEY = fb.INVESTIGATION_KEY
#     """
#
#     interview_dates_query = f"""
#       WITH base AS
#       (
#         {subset_query}
#       ),
#       filtered_base AS
#       (
#         -- STD_HIV_DATAMART1 in PA01_HIV.sas
#         SELECT b.*
#         FROM base b
#           INNER JOIN RDB.dbo.INVESTIGATION i
#                   ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
#                  AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
#                  AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
#       )
#       -- pa1_dte in PA01_HIV.sas
#       SELECT DISTINCT fb.INV_LOCAL_ID,
#              di.IX_TYPE,
#              i.INV_CASE_STATUS,
#              i.RECORD_STATUS_CD,
#              fb.CC_CLOSED_DT,
#              fb.ADI_900_STATUS_CD,
#              fb.HIV_POST_TEST_900_COUNSELING,
#              fb.HIV_900_RESULT,
#              fb.ADI_900_STATUS,
#              fb.HIV_900_TEST_IND,
#              fb.SOURCE_SPREAD,
#              fb.FL_FUP_INIT_ASSGN_DT,
#              i.CURR_PROCESS_STATE,
#              fb.CA_PATIENT_INTV_STATUS,
#              fb.INVESTIGATOR_INTERVIEW_KEY,
#              fb.INVESTIGATOR_INTERVIEW_QC,
#              DATEDIFF(DAY,fb.{PA1_DTE_DATE_COL[disease_type]},di.IX_DATE) AS Days,
#              dp.PROVIDER_QUICK_CODE
#       FROM filtered_base fb
#         INNER JOIN RDB.dbo.F_INTERVIEW_CASE fic
#                 ON fic.INVESTIGATION_KEY = fb.INVESTIGATION_KEY
#         INNER JOIN RDB.dbo.D_INTERVIEW di
#                 ON di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY
#                AND di.RECORD_STATUS_CD <> 'LOG_DEL'
#         INNER JOIN RDB.dbo.D_PROVIDER dp
#                 ON dp.PROVIDER_KEY = fb.INVESTIGATOR_INTERVIEW_KEY
#         INNER JOIN RDB.dbo.INVESTIGATION i
#                 ON i.INVESTIGATION_KEY = fb.INVESTIGATION_KEY
#       WHERE CAST(di.IX_DATE AS DATE) >=
#                CAST(fb.{PA1_DTE_DATE_COL[disease_type]} AS DATE)
#     """
