from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """PA01 HIV and STD: Case Management Report.

    Conversion notes:
    """
    query = f"""
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
        ),
        cases AS
        (
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
        ),
        case_interview_dates AS
        (
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
        )
        SELECT *
        FROM case_interview_dates;
        """

    content = trx.query(query)

    return ReportResult(content_type='table', content=content)
