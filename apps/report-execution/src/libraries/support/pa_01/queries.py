"""Queries for use in pa_01."""

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


def filtered_cases_query(subset_query: str) -> str:
    """Return the base PA01 case population.

    This is the SQL equivalent of SAS `STD_HIV_DATAMART1`: cases from the
    report subset that are probable/confirmed and have an interviewer assigned.
    """
    return f"""
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


def case_interview_rows_query(subset_query: str, report_variant: str) -> str:
    """Return case rows joined to interview and worker details.

    This is the SQL equivalent of SAS `pa1_new`, which preserves cases with
    missing interview rows by using left joins.
    """
    return f"""
      WITH base AS
      (
        {subset_query}
      ),
      filtered_cases AS
      (
        -- STD_HIV_DATAMART1 in SAS
        SELECT b.*
        FROM base b
          INNER JOIN RDB.dbo.INVESTIGATION i
                  ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
                 AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
                 AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
      )
      SELECT DISTINCT
             fc.INV_LOCAL_ID,
             di.IX_TYPE,
             i.INV_CASE_STATUS,
             i.RECORD_STATUS_CD,
             fc.CC_CLOSED_DT,
             fc.ADI_900_STATUS_CD,
             fc.HIV_POST_TEST_900_COUNSELING,
             fc.HIV_900_RESULT,
             fc.ADI_900_STATUS,
             fc.HIV_900_TEST_IND,
             fc.SOURCE_SPREAD,
             fc.FL_FUP_INIT_ASSGN_DT,
             i.CURR_PROCESS_STATE,
             fc.CA_PATIENT_INTV_STATUS,
             fc.INVESTIGATOR_INTERVIEW_KEY,
             fc.INVESTIGATOR_INTERVIEW_QC,
             DATEDIFF(
               DAY,
               CAST(fc.{PA1_NEW_DATE_COL[report_variant]} AS DATE),
               CAST(di.IX_DATE AS DATE)
             ) AS Days,
             dp.PROVIDER_QUICK_CODE
      FROM filtered_cases fc
        LEFT JOIN RDB.dbo.F_INTERVIEW_CASE fic
               ON fic.INVESTIGATION_KEY = fc.INVESTIGATION_KEY
        LEFT JOIN RDB.dbo.D_INTERVIEW di
               ON di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY
              AND di.RECORD_STATUS_CD <> 'LOG_DEL'
        LEFT JOIN RDB.dbo.D_PROVIDER dp
               ON dp.PROVIDER_KEY = fc.INVESTIGATOR_INTERVIEW_KEY
        LEFT JOIN RDB.dbo.INVESTIGATION i
               ON i.INVESTIGATION_KEY = fc.INVESTIGATION_KEY;
    """


def timed_interviews_query(subset_query: str, report_variant: str) -> str:
    """Return interview rows used for the days-to-interview buckets.

    This is the SQL equivalent of SAS `pa1_dte`, which only keeps cases with
    matching interview and worker rows and calculates the `Days` value.
    """
    # equivalent of pa1_dte in SAS
    return f"""
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
             DATEDIFF(DAY,fb.{PA1_DTE_DATE_COL[report_variant]},di.IX_DATE) AS Days,
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
                CAST(fb.CA_INIT_INTVWR_ASSGN_DT AS DATE);
    """


def partner_notification_query(subset_query: str) -> str:
    """Return partner notification counts grouped by worker.

    This is the SQL equivalent of SAS `pix`, used to calculate the Partner
    Notification Index.
    """
    return f"""
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
      SELECT dp.PROVIDER_QUICK_CODE,
             fb.INVESTIGATOR_INTERVIEW_KEY,
             COUNT(DISTINCT fb.INV_LOCAL_ID) AS partner_notification_count
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
      )
      GROUP BY dp.PROVIDER_QUICK_CODE,
               fb.INVESTIGATOR_INTERVIEW_KEY;
    """


def testing_index_query(subset_query: str) -> str:
    """Return tested partner counts grouped by worker.

    This is the SQL equivalent of SAS `testindex`, used to calculate the
    Testing Index.
    """
    return f"""
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
      SELECT dp.PROVIDER_QUICK_CODE,
             fb.INVESTIGATOR_INTERVIEW_KEY,
             COUNT(DISTINCT dcr.LOCAL_ID) AS testing_index_count
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
      )
      GROUP BY dp.PROVIDER_QUICK_CODE,
               fb.INVESTIGATOR_INTERVIEW_KEY;
    """


def period_partners_query(subset_query: str) -> str:
    """Return period partner rows used for total period partner counts.

    This is the SQL equivalent of SAS `pp`, including the calculated `count_Q`
    value later summed for Total Period Partners.
    """
    return f"""
      WITH base AS
      (
          {subset_query}
      ),
      filtered_cases AS
      (
          -- STD_HIV_DATAMART1 in SAS
          SELECT b.*
          FROM base b
            INNER JOIN RDB.dbo.INVESTIGATION i
                    ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
                   AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
                   AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
      )
      SELECT DISTINCT
             fcrc.CONTACT_INVESTIGATION_KEY,
             TRY_CONVERT(int, fc.STD_PRTNRS_PRD_TRNSGNDR_TTL)
                AS STD_PRTNRS_TRNSGNDR_TTL,
             contact_dm.FL_FUP_DISPOSITION,
             fc.INV_LOCAL_ID AS STD_INV_LOCAL_ID,
             TRY_CONVERT(int, fc.SOC_PRTNRS_PRD_FML_TTL) AS SOC_PRTNRS_FML_TTL,
             TRY_CONVERT(int, fc.SOC_PRTNRS_PRD_MALE_TTL) AS SOC_PRTNRS_MALE_TTL,
             COALESCE(TRY_CONVERT(int, fc.STD_PRTNRS_PRD_TRNSGNDR_TTL), 0)
             + COALESCE(TRY_CONVERT(int, fc.SOC_PRTNRS_PRD_FML_TTL), 0)
             + COALESCE(TRY_CONVERT(int, fc.SOC_PRTNRS_PRD_MALE_TTL), 0) AS count_Q,
             di.IX_TYPE,
             dp.PROVIDER_QUICK_CODE,
             dcr.LOCAL_ID AS INV_LOCAL_ID,
             fc.CA_PATIENT_INTV_STATUS,
             fc.INVESTIGATOR_INTERVIEW_KEY,
             fc.INVESTIGATOR_INTERVIEW_QC
      FROM filtered_cases fc
        INNER JOIN RDB.dbo.F_INTERVIEW_CASE fic
                ON fic.INVESTIGATION_KEY = fc.INVESTIGATION_KEY
        INNER JOIN RDB.dbo.D_INTERVIEW di
                ON di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY
               AND di.RECORD_STATUS_CD <> 'LOG_DEL'
        INNER JOIN RDB.dbo.D_PROVIDER dp
                ON dp.PROVIDER_KEY = fc.INVESTIGATOR_INTERVIEW_KEY
        INNER JOIN RDB.dbo.INVESTIGATION i
                ON i.INVESTIGATION_KEY = fc.INVESTIGATION_KEY
        INNER JOIN RDB.dbo.F_CONTACT_RECORD_CASE fcrc
                ON fcrc.SUBJECT_INVESTIGATION_KEY = fc.INVESTIGATION_KEY
               AND fcrc.CONTACT_INTERVIEW_KEY = di.D_INTERVIEW_KEY
               AND fcrc.CONTACT_INTERVIEW_KEY <> 1
        INNER JOIN RDB.dbo.STD_HIV_DATAMART contact_dm
                ON contact_dm.INVESTIGATION_KEY = fcrc.CONTACT_INVESTIGATION_KEY
        INNER JOIN RDB.dbo.D_CONTACT_RECORD dcr
                ON dcr.D_CONTACT_RECORD_KEY = fcrc.D_CONTACT_RECORD_KEY
               AND dcr.RECORD_STATUS_CD <> 'LOG_DEL'
      WHERE dcr.CTT_REFERRAL_BASIS IN (
          'P1 - Partner, Sex',
          'P2 - Partner, Needle-Sharing',
          'P3 - Partner, Both'
      )
      AND fc.CA_PATIENT_INTV_STATUS = 'I - Interviewed';
    """


def cases_with_no_partners_query(subset_query: str) -> str:
    """Return interviewed cases with no initiated partner records.

    This is the SQL equivalent of SAS `part2`, used for Cases With No Partners.
    """
    return f"""
      WITH base AS
      (
          {subset_query}
      ),
      filtered_cases AS
      (
          -- STD_HIV_DATAMART1 in SAS
          SELECT b.*
          FROM base b
            INNER JOIN RDB.dbo.INVESTIGATION i
                    ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
                   AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
                   AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
      ),
      pp AS
      (
          SELECT DISTINCT
                 fcrc.CONTACT_INVESTIGATION_KEY,
                 TRY_CONVERT(int, fc.STD_PRTNRS_PRD_TRNSGNDR_TTL)
                    AS STD_PRTNRS_TRNSGNDR_TTL,
                 contact_dm.FL_FUP_DISPOSITION,
                 fc.INV_LOCAL_ID AS STD_INV_LOCAL_ID,
                 TRY_CONVERT(int, fc.SOC_PRTNRS_PRD_FML_TTL) AS SOC_PRTNRS_FML_TTL,
                 TRY_CONVERT(int, fc.SOC_PRTNRS_PRD_MALE_TTL) AS SOC_PRTNRS_MALE_TTL,
                 COALESCE(TRY_CONVERT(int, fc.STD_PRTNRS_PRD_TRNSGNDR_TTL), 0)
                 + COALESCE(TRY_CONVERT(int, fc.SOC_PRTNRS_PRD_FML_TTL), 0)
                 + COALESCE(TRY_CONVERT(int, fc.SOC_PRTNRS_PRD_MALE_TTL), 0) AS count_Q,
                 di.IX_TYPE,
                 dp.PROVIDER_QUICK_CODE,
                 dcr.LOCAL_ID AS INV_LOCAL_ID,
                 fc.CA_PATIENT_INTV_STATUS,
                 fc.INVESTIGATOR_INTERVIEW_KEY,
                 fc.INVESTIGATOR_INTERVIEW_QC
          FROM filtered_cases fc
            INNER JOIN RDB.dbo.F_INTERVIEW_CASE fic
                    ON fic.INVESTIGATION_KEY = fc.INVESTIGATION_KEY
            INNER JOIN RDB.dbo.D_INTERVIEW di
                    ON di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY
                   AND di.RECORD_STATUS_CD <> 'LOG_DEL'
            INNER JOIN RDB.dbo.D_PROVIDER dp
                    ON dp.PROVIDER_KEY = fc.INVESTIGATOR_INTERVIEW_KEY
            INNER JOIN RDB.dbo.INVESTIGATION i
                    ON i.INVESTIGATION_KEY = fc.INVESTIGATION_KEY
            INNER JOIN RDB.dbo.F_CONTACT_RECORD_CASE fcrc
                    ON fcrc.SUBJECT_INVESTIGATION_KEY = fc.INVESTIGATION_KEY
                   AND fcrc.CONTACT_INTERVIEW_KEY = di.D_INTERVIEW_KEY
                   AND fcrc.CONTACT_INTERVIEW_KEY <> 1
            INNER JOIN RDB.dbo.STD_HIV_DATAMART contact_dm
                    ON contact_dm.INVESTIGATION_KEY = fcrc.CONTACT_INVESTIGATION_KEY
            INNER JOIN RDB.dbo.D_CONTACT_RECORD dcr
                    ON dcr.D_CONTACT_RECORD_KEY = fcrc.D_CONTACT_RECORD_KEY
                   AND dcr.RECORD_STATUS_CD <> 'LOG_DEL'
          WHERE dcr.CTT_REFERRAL_BASIS IN (
              'P1 - Partner, Sex',
              'P2 - Partner, Needle-Sharing',
              'P3 - Partner, Both'
          )
          AND fc.CA_PATIENT_INTV_STATUS = 'I - Interviewed'
      )
      SELECT DISTINCT
             fc.INV_LOCAL_ID,
             fc.INVESTIGATION_KEY,
             dp.PROVIDER_QUICK_CODE,
             dcr.CTT_REFERRAL_BASIS,
             dcr.CTT_PROCESSING_DECISION,
             fc.INVESTIGATOR_INTERVIEW_KEY,
             fc.INVESTIGATOR_INTERVIEW_QC,
             fcrc.CONTACT_INVESTIGATION_KEY
      FROM filtered_cases fc
        INNER JOIN RDB.dbo.D_PROVIDER dp
                ON dp.PROVIDER_KEY = fc.INVESTIGATOR_INTERVIEW_KEY
        LEFT JOIN RDB.dbo.F_CONTACT_RECORD_CASE fcrc
               ON fcrc.SUBJECT_INVESTIGATION_KEY = fc.INVESTIGATION_KEY
        LEFT JOIN RDB.dbo.D_CONTACT_RECORD dcr
               ON dcr.D_CONTACT_RECORD_KEY = fcrc.D_CONTACT_RECORD_KEY
              AND dcr.RECORD_STATUS_CD <> 'LOG_DEL'
      WHERE (
        dcr.CTT_REFERRAL_BASIS NOT IN (
            'P1 - Partner, Sex',
            'P2 - Partner, Needle-Sharing',
            'P3 - Partner, Both'
        )
        OR dcr.CTT_REFERRAL_BASIS IS NULL
      )
      AND fc.CA_PATIENT_INTV_STATUS = 'I - Interviewed'
      AND fc.INV_LOCAL_ID NOT IN (
          SELECT DISTINCT STD_INV_LOCAL_ID
          FROM pp
      );
    """


def clusters_initiated_query(subset_query: str) -> str:
    """Return initiated cluster rows.

    This is the SQL equivalent of SAS `cluster`, used for Total Clusters Initiated and
    related cluster calculations.
    """
    return f"""
      WITH base AS
      (
        {subset_query}
      ),
      filtered_cases AS
      (
        -- STD_HIV_DATAMART1 in SAS
        SELECT b.*
        FROM base b
          INNER JOIN RDB.dbo.INVESTIGATION i
                  ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
                 AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
                 AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
      )
      SELECT DISTINCT a.INVESTIGATION_KEY,
             PROVIDER_QUICK_CODE,
             a.inv_local_id AS STD_INV_LOCAL_ID,
             f.FL_FUP_DISPOSITION,
             e.LOCAL_ID AS INV_LOCAL_ID,
             a.FL_FUP_DISPO_DT,
             a.FL_FUP_INIT_ASSGN_DT,
             a.INVESTIGATOR_INTERVIEW_KEY,
             a.INVESTIGATOR_INTERVIEW_QC
      FROM filtered_cases a
        INNER JOIN RDB.dbo.F_INTERVIEW_CASE b
                ON a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
        INNER JOIN RDB.dbo.D_INTERVIEW c
                ON c.D_INTERVIEW_KEY = b.D_INTERVIEW_KEY
               AND c.RECORD_STATUS_CD <> 'LOG_DEL'
        INNER JOIN RDB.dbo.F_CONTACT_RECORD_CASE d
                ON a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
               AND c.d_interview_key = d.CONTACT_INTERVIEW_KEY
               AND d.CONTACT_INTERVIEW_KEY <> 1
        INNER JOIN RDB.dbo.D_CONTACT_RECORD e
                ON e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY
               AND e.RECORD_STATUS_CD <> 'LOG_DEL'
        INNER JOIN RDB.dbo.D_provider
                ON D_provider.provider_key = a.INVESTIGATOR_INTERVIEW_KEY
        INNER JOIN RDB.dbo.STD_HIV_DATAMART f
                ON d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
               AND e.CTT_REFERRAL_BASIS IN (
                 'A1 - Associate 1',
                 'A2 - Associate 2',
                 'A3 - Associate 3',
                 'S1 - Social Contact 1',
                 'S2 - Social Contact 2',
                 'S3 - Social Contact 3'
               );
    """


def cases_with_no_clusters_query(subset_query: str) -> str:
    """Return cases that do not have clusters associated.

    Equivalent SAS query is `clusters_No`.
    """
    return f"""
      WITH base AS
      (
        {subset_query}
      ),
      filtered_cases AS
      (
        -- STD_HIV_DATAMART1 in SAS
        SELECT b.*
        FROM base b
          INNER JOIN RDB.dbo.INVESTIGATION i
                  ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
                 AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
                 AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
      ),
      clusters AS (
        SELECT DISTINCT a.INVESTIGATION_KEY,
             PROVIDER_QUICK_CODE,
             a.inv_local_id AS STD_INV_LOCAL_ID,
             f.FL_FUP_DISPOSITION,
             e.LOCAL_ID AS INV_LOCAL_ID,
             a.FL_FUP_DISPO_DT,
             a.FL_FUP_INIT_ASSGN_DT,
             a.INVESTIGATOR_INTERVIEW_KEY,
             a.INVESTIGATOR_INTERVIEW_QC
      FROM filtered_cases a
        INNER JOIN RDB.dbo.F_INTERVIEW_CASE b
                ON a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
        INNER JOIN RDB.dbo.D_INTERVIEW c
                ON c.D_INTERVIEW_KEY = b.D_INTERVIEW_KEY
               AND c.RECORD_STATUS_CD <> 'LOG_DEL'
        INNER JOIN RDB.dbo.F_CONTACT_RECORD_CASE d
                ON a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
               AND c.d_interview_key = d.CONTACT_INTERVIEW_KEY
               AND d.CONTACT_INTERVIEW_KEY <> 1
        INNER JOIN RDB.dbo.D_CONTACT_RECORD e
                ON e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY
               AND e.RECORD_STATUS_CD <> 'LOG_DEL'
        INNER JOIN RDB.dbo.D_provider
                ON D_provider.provider_key = a.INVESTIGATOR_INTERVIEW_KEY
        INNER JOIN RDB.dbo.STD_HIV_DATAMART f
                ON d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
               AND e.CTT_REFERRAL_BASIS IN (
                     'A1 - Associate 1',
                     'A2 - Associate 2',
                     'A3 - Associate 3',
                     'S1 - Social Contact 1',
                     'S2 - Social Contact 2',
                     'S3 - Social Contact 3'
                   )
      )
      SELECT DISTINCT a.INV_LOCAL_ID,
             a.INVESTIGATION_KEY,
             PROVIDER_QUICK_CODE,
             f.CTT_REFERRAL_BASIS,
             f.CTT_PROCESSING_DECISION,
             INVESTIGATOR_INTERVIEW_KEY,
             INVESTIGATOR_INTERVIEW_QC,
             e.CONTACT_INVESTIGATION_KEY
      FROM filtered_cases a
        INNER JOIN RDB.dbo.D_provider
                ON D_provider.provider_key = a.INVESTIGATOR_INTERVIEW_KEY
        LEFT OUTER JOIN RDB.dbo.F_CONTACT_RECORD_CASE e
                     ON e.SUBJECT_INVESTIGATION_KEY = a.Investigation_key
        LEFT OUTER JOIN RDB.dbo.d_contact_record f
                     ON e.D_contact_record_key = f.d_contact_record_key
                    AND f.RECORD_STATUS_CD <> 'LOG_DEL'
      WHERE (
          f.CTT_REFERRAL_BASIS NOT IN (
              'A1 - Associate 1',
              'A2 - Associate 2',
              'A3 - Associate 3',
              'S1 - Social Contact 1',
              'S2 - Social Contact 2',
              'S3 - Social Contact 3'
          )
          OR f.CTT_REFERRAL_BASIS IS NULL
      )
      AND a.CA_PATIENT_INTV_STATUS IN ('I - Interviewed')
      AND NOT EXISTS (
        SELECT 1 FROM clusters c WHERE c.STD_INV_LOCAL_ID = a.INV_LOCAL_ID
      );
    """


def notified_partners_query(subset_query: str) -> str:
    """Return notified partner rows used for partner disposition counts.

    This is the SQL equivalent of SAS `partner2`, which is used to calculate
    New Partners Notified and its HIV partner disposition breakdowns.
    """
    return f"""
      WITH base AS
      (
        {subset_query}
      ),
      filtered_cases AS
      (
        -- STD_HIV_DATAMART1 in SAS
        SELECT b.*
        FROM base b
          INNER JOIN RDB.dbo.INVESTIGATION i
                  ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
                 AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
                 AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
      )
      SELECT DISTINCT a.INVESTIGATION_KEY,
             PROVIDER_QUICK_CODE,
             f.INV_LOCAL_ID,
             a.INIT_FUP_INITIAL_FOLL_UP,
             f.FL_FUP_DISPOSITION,
             a.FL_FUP_DISPO_DT,
             a.FL_FUP_INIT_ASSGN_DT,
             datepart(DAY,a.FL_FUP_DISPO_DT) - datepart(DAY,a.FL_FUP_INIT_ASSGN_DT)
                AS days,
             a.INVESTIGATOR_INTERVIEW_KEY,
             a.INVESTIGATOR_INTERVIEW_QC
      FROM filtered_cases a
        INNER JOIN RDB.dbo.F_INTERVIEW_CASE b
                ON a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
        INNER JOIN RDB.dbo.D_INTERVIEW c
                ON c.D_INTERVIEW_KEY = b.D_INTERVIEW_KEY
               AND c.RECORD_STATUS_CD <> 'LOG_DEL'
        INNER JOIN RDB.dbo.F_CONTACT_RECORD_CASE d
                ON a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
        INNER JOIN RDB.dbo.STD_HIV_DATAMART f
                ON d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
        INNER JOIN RDB.dbo.D_CONTACT_RECORD e
                ON e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY
               AND e.RECORD_STATUS_CD <> 'LOG_DEL'
        INNER JOIN RDB.dbo.D_provider
                ON D_provider.provider_key = a.INVESTIGATOR_INTERVIEW_KEY
               AND e.CTT_REFERRAL_BASIS IN (
                     'P1 - Partner, Sex',
                     'P2 - Partner, Needle-Sharing',
                     'P3 - Partner, Both'
                   )
               AND f.FL_FUP_DISPOSITION IN (
                     '2 - Prev. Neg, New Pos',
                     '3 - Prev. Neg, Still Neg',
                     '4 - Prev. Neg, No Test',
                     '5 - No Prev Test, New Pos',
                     '6 - No Prev Test, New Neg',
                     '7 - No Prev Test, No Test'
                   )
    """


def not_notified_partners_query(subset_query: str) -> str:
    """Return non-notified partner rows used for partner disposition counts.

    This is the SQL equivalent of SAS `pn`, which is used to calculate
    New Partners Not Notified and its HIV partner disposition breakdowns.
    """
    return f"""
      WITH base AS
      (
        {subset_query}
      ),
      filtered_cases AS
      (
        -- STD_HIV_DATAMART1 in SAS
        SELECT b.*
        FROM base b
          INNER JOIN RDB.dbo.INVESTIGATION i
                  ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
                 AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
                 AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
      )
      SELECT DISTINCT c.IX_TYPE,
             a.INVESTIGATION_KEY,
             PROVIDER_QUICK_CODE,
             h.FL_FUP_DISPOSITION,
             g.CTT_REFERRAL_BASIS,
             h.INV_LOCAL_ID,
             a.INVESTIGATOR_INTERVIEW_KEY
      FROM filtered_cases a
        INNER JOIN RDB.dbo.F_INTERVIEW_CASE b
                ON b.INVESTIGATION_KEY = a.INVESTIGATION_KEY
        INNER JOIN RDB.dbo.D_INTERVIEW c
                ON c.D_INTERVIEW_KEY = b.D_INTERVIEW_KEY
               AND c.RECORD_STATUS_CD <> 'LOG_DEL'
        INNER JOIN RDB.dbo.D_provider d
                ON d.provider_key = a.INVESTIGATOR_INTERVIEW_KEY
        INNER JOIN RDB.dbo.investigation e
                ON e.investigation_key = a.investigation_KEY
        INNER JOIN RDB.dbo.F_CONTACT_RECORD_CASE f
                ON a.investigation_key = f.SUBJECT_investigation_key
        INNER JOIN RDB.dbo.STD_HIV_DATAMART h
                ON f.CONTACT_INVESTIGATION_KEY = h.Investigation_key
        INNER JOIN RDB.dbo.d_contact_record g
                ON f.d_contact_record_key = g.d_contact_record_key
               AND g.RECORD_STATUS_CD <> 'LOG_DEL'
      WHERE g.CTT_REFERRAL_BASIS IN (
              'P1 - Partner, Sex',
              'P2 - Partner, Needle-Sharing',
              'P3 - Partner, Both'
            )
      AND   a.CA_PATIENT_INTV_STATUS IN ('I - Interviewed')
      AND   h.FL_FUP_DISPOSITION IN (
              'G - Insufficient Info to Begin Investigation',
              'H - Unable to Locate',
              'J - Located, Not Examined and/or Interviewed',
              'K - Sent Out Of Jurisdiction',
              'L - Other',
              'V - Domestic Violence Risk',
              'X - Patient Deceased'
            )
    """


def clusters_notified_query(subset_query: str) -> str:
    """Return notified cluster rows used for cluster disposition counts.

    This is the SQL equivalent of SAS `cm`, which is used to calculate
    New Clusters Notified and its HIV partner disposition breakdowns.
    """
    return f"""
      WITH base AS
      (
        {subset_query}
      ),
      filtered_cases AS
      (
        -- STD_HIV_DATAMART1 in SAS
        SELECT b.*
        FROM base b
          INNER JOIN RDB.dbo.INVESTIGATION i
                  ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
                 AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
                 AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
      )
      SELECT DISTINCT a.INVESTIGATION_KEY,
             PROVIDER_QUICK_CODE,
             a.FL_FUP_DISPOSITION,
             f.INV_LOCAL_ID,
             f.FL_FUP_DISPO_DT,
             f.FL_FUP_INVESTIGATOR_ASSGN_DT,
             a.INVESTIGATOR_INTERVIEW_KEY,
             a.INVESTIGATOR_INTERVIEW_QC,
             datepart(day, f.FL_FUP_DISPO_DT)
               - datepart(day, f.FL_FUP_INVESTIGATOR_ASSGN_DT) AS days
      FROM filtered_cases a
        INNER JOIN RDB.dbo.F_INTERVIEW_CASE b
                ON a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
        INNER JOIN RDB.dbo.D_INTERVIEW c
                ON c.D_INTERVIEW_KEY = b.D_INTERVIEW_KEY
               AND c.RECORD_STATUS_CD <> 'LOG_DEL'
        INNER JOIN RDB.dbo.F_CONTACT_RECORD_CASE d
                ON a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
               AND d.contact_interview_key = c.D_INTERVIEW_KEY
        INNER JOIN RDB.dbo.D_CONTACT_RECORD e
                ON e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY
               AND e.RECORD_STATUS_CD <> 'LOG_DEL'
        INNER JOIN RDB.dbo.D_provider
                ON D_provider.provider_key = a.INVESTIGATOR_INTERVIEW_KEY
        INNER JOIN RDB.dbo.STD_HIV_DATAMART f
                ON d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
      WHERE e.CTT_REFERRAL_BASIS IN (
              'A1 - Associate 1',
              'A2 - Associate 2',
              'A3 - Associate 3',
              'S1 - Social Contact 1',
              'S2 - Social Contact 2',
              'S3 - Social Contact 3'
            )
      AND   f.fl_fup_disposition IN (
              '2 - Prev. Neg, New Pos',
              '3 - Prev. Neg, Still Neg',
              '4 - Prev. Neg, No Test',
              '5 - No Prev Test, New Pos',
              '6 - No Prev Test, New Neg',
              '7 - No Prev Test, No Test'
            );
    """
