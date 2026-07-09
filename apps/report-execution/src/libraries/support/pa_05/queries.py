from src.config import get_cached_config_value


def activity_query(subset_query: str) -> str:
    nbs_rdb = get_cached_config_value('REPORT_DB_NBS_RDB')

    # Equivalent to the PA05 dataset build (PA05.sas lines 122-132). Drops
    # INVESTIGATION_KEY and INVESTIGATOR_INTERVIEW_QC from the SAS SELECT list:
    # both are per-case attributes already 1:1 with INV_LOCAL_ID (so they can't
    # affect SELECT DISTINCT row counts), and neither is referenced anywhere
    # else in PA05.sas either -- they're dead columns in the original report.
    return f"""
    WITH shd AS (
        {subset_query}
    ),
    std_cases AS (
        SELECT shd.*
        FROM shd
        INNER JOIN {nbs_rdb}.DBO.INVESTIGATION i
            ON i.INVESTIGATION_KEY = shd.INVESTIGATION_KEY
        WHERE i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
          AND shd.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
    )
    SELECT DISTINCT
        std_cases.INV_LOCAL_ID,
        di.LOCAL_ID AS INTERVIEW_LOCAL_ID,
        di.IX_TYPE,
        di.IX_LOCATION,
        std_cases.INVESTIGATOR_INTERVIEW_KEY,
        di.IX_DATE,
        std_cases.CA_INTERVIEWER_ASSIGN_DT,
        dp.PROVIDER_QUICK_CODE,
        std_cases.CC_CLOSED_DT,
        std_cases.CA_PATIENT_INTV_STATUS,
        TRY_CAST(
            std_cases.STD_PRTNRS_PRD_TRNSGNDR_TTL AS INT
        ) AS STD_PRTNRS_PRD_TRNSGNDR_TTL,
        TRY_CAST(std_cases.SOC_PRTNRS_PRD_FML_TTL AS INT) AS SOC_PRTNRS_PRD_FML_TTL,
        TRY_CAST(std_cases.SOC_PRTNRS_PRD_MALE_TTL AS INT) AS SOC_PRTNRS_PRD_MALE_TTL
    FROM std_cases
    LEFT JOIN {nbs_rdb}.DBO.F_INTERVIEW_CASE fic
        ON fic.INVESTIGATION_KEY = std_cases.INVESTIGATION_KEY
    LEFT JOIN {nbs_rdb}.DBO.D_INTERVIEW di
        ON di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY
       AND di.RECORD_STATUS_CD <> 'LOG_DEL'
    LEFT JOIN {nbs_rdb}.DBO.D_PROVIDER dp
        ON dp.PROVIDER_KEY = std_cases.INVESTIGATOR_INTERVIEW_KEY;
    """


def ixs_query(subset_query: str) -> str:
    nbs_rdb = get_cached_config_value('REPORT_DB_NBS_RDB')

    # Equivalent to the PA05_IXS_INIT dataset build (PA05.sas lines 141-168),
    # including the worker-key/date-bound MIN/MAX derivation (lines 135-139).
    return f"""
    WITH shd AS (
        {subset_query}
    ),
    std_cases AS (
        SELECT shd.*
        FROM shd
        INNER JOIN {nbs_rdb}.DBO.INVESTIGATION i
            ON i.INVESTIGATION_KEY = shd.INVESTIGATION_KEY
        WHERE i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
          AND shd.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
    ),
    worker_keys AS (
        SELECT DISTINCT INVESTIGATOR_INTERVIEW_KEY
        FROM std_cases
        WHERE INVESTIGATOR_INTERVIEW_KEY <> 1
    ),
    assign_bounds AS (
        SELECT
            MIN(CA_INTERVIEWER_ASSIGN_DT) AS MIN_ASSIGN_DT,
            MAX(CA_INTERVIEWER_ASSIGN_DT) AS MAX_ASSIGN_DT
        FROM std_cases
        WHERE INVESTIGATOR_INTERVIEW_KEY <> 1
    ),
    closed_cases AS (
        SELECT CC_CLOSED_DT
        FROM std_cases
        WHERE INVESTIGATOR_INTERVIEW_KEY <> 1
          AND CC_CLOSED_DT IS NOT NULL
    ),
    closed_bounds AS (
        SELECT
            COUNT(*) AS NUM_CLOSED,
            MIN(CC_CLOSED_DT) AS MIN_CLOSED_DT,
            MAX(CC_CLOSED_DT) AS MAX_CLOSED_DT
        FROM closed_cases
    ),
    open_bounds AS (
        SELECT
            SUM(CASE WHEN CC_CLOSED_DT IS NULL THEN 1 ELSE 0 END) AS NUM_OPEN
        FROM std_cases
        WHERE INVESTIGATOR_INTERVIEW_KEY <> 1
    )
    SELECT DISTINCT
        di.IX_TYPE,
        dp.PROVIDER_QUICK_CODE,
        fic.IX_INTERVIEWER_KEY AS INVESTIGATOR_INTERVIEW_KEY,
        di.D_INTERVIEW_KEY,
        fcrc.D_CONTACT_RECORD_KEY,
        fcrc.CONTACT_INVESTIGATION_KEY,
        dcr.CTT_REFERRAL_BASIS,
        dcr.CTT_PROCESSING_DECISION
    FROM {nbs_rdb}.DBO.D_INTERVIEW di
    INNER JOIN {nbs_rdb}.DBO.F_INTERVIEW_CASE fic
        ON di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY
       AND di.RECORD_STATUS_CD <> 'LOG_DEL'
    INNER JOIN {nbs_rdb}.DBO.STD_HIV_DATAMART std
        ON fic.INVESTIGATION_KEY = std.INVESTIGATION_KEY
    INNER JOIN {nbs_rdb}.DBO.D_PROVIDER dp
        ON fic.IX_INTERVIEWER_KEY = dp.PROVIDER_KEY
    LEFT JOIN {nbs_rdb}.DBO.F_CONTACT_RECORD_CASE fcrc
        ON fcrc.CONTACT_INTERVIEW_KEY = di.D_INTERVIEW_KEY
    LEFT JOIN {nbs_rdb}.DBO.D_CONTACT_RECORD dcr
        ON fcrc.D_CONTACT_RECORD_KEY = dcr.D_CONTACT_RECORD_KEY
       AND dcr.RECORD_STATUS_CD <> 'LOG_DEL'
    CROSS JOIN assign_bounds ab
    CROSS JOIN closed_bounds cb
    CROSS JOIN open_bounds ob
    WHERE std.INVESTIGATOR_INTERVIEW_KEY IN (
        SELECT INVESTIGATOR_INTERVIEW_KEY FROM worker_keys
    )
      AND CAST(std.CA_INTERVIEWER_ASSIGN_DT AS DATE) >= CAST(ab.MIN_ASSIGN_DT AS DATE)
      AND CAST(std.CA_INTERVIEWER_ASSIGN_DT AS DATE) <= CAST(ab.MAX_ASSIGN_DT AS DATE)
      AND (
            cb.NUM_CLOSED = 0
            OR (
                std.CC_CLOSED_DT IS NOT NULL
                AND CAST(std.CC_CLOSED_DT AS DATE) >= CAST(cb.MIN_CLOSED_DT AS DATE)
                AND CAST(std.CC_CLOSED_DT AS DATE) <= CAST(cb.MAX_CLOSED_DT AS DATE)
            )
            OR std.CC_CLOSED_DT IN (SELECT CC_CLOSED_DT FROM closed_cases)
            -- SAS's fallback subquery (PA05.sas lines 166-167) selects CC_CLOSED_DT
            -- from the subset without excluding NULLs, and SAS PROC SQL's IN
            -- operator treats missing = missing as a match. That means an open
            -- candidate case (CC_CLOSED_DT IS NULL) matches whenever the subset
            -- itself contains any open case, regardless of whether the subset also
            -- has closed cases. T-SQL's IN never matches NULL, so this clause
            -- reproduces that behavior explicitly to keep parity with SAS.
            OR (std.CC_CLOSED_DT IS NULL AND ob.NUM_OPEN > 0)
      );
    """
