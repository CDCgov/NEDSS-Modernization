import re

from src.db_transaction import Transaction
from src.models import ReportResult


# is_export: bool
# is_builtin: bool
# report_title: str = Field(min_length=1)
# library_name: str = Field(min_length=1)
# data_source_name: str = Field(min_length=1)
# subset_query: str = Field(min_length=1)
# days_value: int | None = None  # Specific to potntl_dup_inv_sum
# column_map: list[list[str]] | None = None
# library_params: Json[Any] | None = Field(default_factory=dict)
def execute(
    trx: Transaction,
    subset_query: str,
    library_name: str,
    report_title: str**kwargs,
):
    """PA01 HIV and STD: Case Management Report.

    Conversion notes:
    """
    
    title_parts = _get_report_title_parts(report_title)
    date_type = title_parts[0]
    disease_type = title_parts[1]

    cases_query = f"""
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
      -- pa1_new in PA01_HIV.sas
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
             -- /* Should it be CA_INIT_INTVWR_ASSGN_DT
             -- or CA_INTERVIEWER_ASSIGN_DT? */
             DATEDIFF(DAY,fb.CA_INTERVIEWER_ASSIGN_DT,di.IX_DATE) AS Days,
             dp.PROVIDER_QUICK_CODE
      FROM filtered_base fb
        LEFT OUTER JOIN RDB.dbo.F_INTERVIEW_CASE fic
                     ON fic.INVESTIGATION_KEY = fb.INVESTIGATION_KEY
        LEFT OUTER JOIN RDB.dbo.D_INTERVIEW di
                     ON di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY
                    AND di.RECORD_STATUS_CD <> 'LOG_DEL'
        LEFT OUTER JOIN RDB.dbo.D_PROVIDER dp
                     ON dp.PROVIDER_KEY = fb.INVESTIGATOR_INTERVIEW_KEY
        LEFT OUTER JOIN RDB.dbo.INVESTIGATION i
                     ON i.INVESTIGATION_KEY = fb.INVESTIGATION_KEY
    """

    interview_dates_query = f"""
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
      -- pa1_dte in PA01_HIV.sas
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
             DATEDIFF(DAY,fb.CA_INIT_INTVWR_ASSGN_DT,di.IX_DATE) AS Days,
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
      WHERE CAST(di.IX_DATE AS DATE) >= CAST(fb.CA_INIT_INTVWR_ASSGN_DT AS DATE)
    """

    cases = trx.query(cases_query)
    interview_dates = trx.query(interview_dates_query)

    return ReportResult(content_type='table', content=None)


def _get_report_title_parts(report_title: str) -> tuple[str, str]:
    """Get the needed parts from the report title to differentiate between types.

    - STD/HIV
    - Interview Assign Date/Closed Date
    """
    match = re.match(
        r'^PA01 Case Management Report \((.+)\) - (STD|HIV)$', report_title
    )
    groups = match.groups()

    return (groups[0], groups[1])
