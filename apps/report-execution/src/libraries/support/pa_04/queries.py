from src.config import get_cached_config_value
from src.libraries.contact_record import (
    PARTNER_BASES,
    VALID_PROCESSING_DECISIONS,
)
from src.libraries.support.pa_04.models import (
    CLUSTER_BASES,
    INDEX_DISPOSITIONS,
    STD_TREATMENT_DISPOSITIONS,
)

_ALL_REFERRAL_BASES = PARTNER_BASES | CLUSTER_BASES

# The STD_HIV_DATAMART1 base filter (PA04_HIV.sas:26-29): case status,
# assign date, closed date, and interview status are all required, and this
# filter is shared by every query in this module. Because of it, several of
# the per-metric WHERE clauses in the source SAS re-check conditions
# (CC_CLOSED_DT IS NOT NULL, CA_PATIENT_INTV_STATUS = 'I - Interviewed') that
# are already guaranteed true here -- those redundant re-checks are dropped
# rather than reproduced.
_BASE_CASES_CTE = """
    cases AS (
        SELECT shd.*
        FROM shd
        INNER JOIN {nbs_rdb}.DBO.INVESTIGATION i
            ON i.INVESTIGATION_KEY = shd.INVESTIGATION_KEY
        WHERE i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
          AND shd.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
          AND shd.CC_CLOSED_DT IS NOT NULL
          AND shd.CA_PATIENT_INTV_STATUS = 'I - Interviewed'
    )
"""


def _sql_string_list(values) -> str:
    return ', '.join(f"'{value}'" for value in sorted(values))


def case_query(subset_query: str) -> str:
    """Equivalent to the PA04 dataset build (PA04_HIV.sas:33-56), used for
    Val_A through Val_K. Drops columns selected in SAS but never referenced by
    any Val_* computation (ADI_900_STATUS_CD, ADI_900_STATUS, source_spread,
    FL_FUP_INIT_ASSGN_DT, DIAGNOSIS_CD, INVESTIGATOR_INTERVIEW_QC,
    provider_last_name, CURR_PROCESS_STATE, record_status_cd) -- they're dead
    columns in the original report.

    STD_PRTNRS_PRD_TRNSGNDR_TTL / SOC_PRTNRS_PRD_FML_TTL /
    SOC_PRTNRS_PRD_MALE_TTL are returned individually rather than pre-summed
    into count_q: SAS's SUM() ignores missing arguments (only returns missing
    if *all* are missing), which differs from SQL '+' propagating NULL, so
    that sum is computed in Python instead (see calculations.py).
    """
    nbs_rdb = get_cached_config_value('REPORT_DB_NBS_RDB')

    return f"""
    WITH shd AS (
        {subset_query}
    ),
    {_BASE_CASES_CTE.format(nbs_rdb=nbs_rdb)}
    SELECT DISTINCT
        cases.INV_LOCAL_ID,
        di.IX_TYPE,
        fcrc.CONTACT_INVESTIGATION_KEY,
        cases.HIV_900_TEST_IND,
        cases.HIV_900_RESULT,
        cases.HIV_POST_TEST_900_COUNSELING,
        TRY_CAST(cases.STD_PRTNRS_PRD_TRNSGNDR_TTL AS INT)
            AS STD_PRTNRS_PRD_TRNSGNDR_TTL,
        TRY_CAST(cases.SOC_PRTNRS_PRD_FML_TTL AS INT) AS SOC_PRTNRS_PRD_FML_TTL,
        TRY_CAST(cases.SOC_PRTNRS_PRD_MALE_TTL AS INT) AS SOC_PRTNRS_PRD_MALE_TTL
    FROM cases
    LEFT JOIN {nbs_rdb}.DBO.F_INTERVIEW_CASE fic
        ON fic.INVESTIGATION_KEY = cases.INVESTIGATION_KEY
    LEFT JOIN {nbs_rdb}.DBO.D_INTERVIEW di
        ON di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY
       AND di.RECORD_STATUS_CD <> 'LOG_DEL'
    LEFT JOIN {nbs_rdb}.DBO.F_CONTACT_RECORD_CASE fcrc
        ON fcrc.SUBJECT_INVESTIGATION_KEY = cases.INVESTIGATION_KEY;
    """


def contact_query(subset_query: str, variant: str = 'HIV') -> str:
    """Equivalent to the PP04_OI / CLUSTER dataset builds (PA04_HIV.sas:175-
    245, PA04_Std.sas:176-213) used for the Partner/Cluster Initiated,
    Examined, and disposition breakdown metrics. Partner vs. Cluster is just a
    different CTT_REFERRAL_BASIS set, so both are built from one query here
    and bucketed in Python (calculations.py) by which basis set the row's
    CTT_REFERRAL_BASIS falls into.

    Notably requires an interviewer that exists in D_PROVIDER (INNER JOIN, no
    filter condition -- matching the source's `inner join ... D_provider d on
    d.provider_key = a.INVESTIGATOR_INTERVIEW_KEY`) and a CTT_PROCESSING_DECISION
    in the valid set, and does *not* filter D_CONTACT_RECORD on
    RECORD_STATUS_CD (the source's join to d_contact_record here has no such
    filter, unlike index_query below).

    KNOWN SAS QUIRK (STD only): PA04_Std.sas:190/212 adds
    `g.CONTACT_INTERVIEW_KEY ~=1`, excluding contacts still at the 'no
    interview yet' sentinel value -- a filter HIV's PP04_OI/CLUSTER queries
    don't have.

    KNOWN SAS QUIRK (STD only): PA04_Std.sas:180/200 also adds an unfiltered,
    unselected `inner join nbs_rdb.F_INTERVIEW_CASE b on
    b.INVESTIGATION_KEY=a.INVESTIGATION_KEY` -- unlike case_query's identically
    -shaped join (which is a LEFT JOIN and genuinely a no-op), this one is an
    INNER JOIN, so it silently requires the case to have *at least one*
    F_INTERVIEW_CASE row to appear in the result at all. HIV's PP04_OI/CLUSTER
    queries have no such join.
    """
    nbs_rdb = get_cached_config_value('REPORT_DB_NBS_RDB')
    referral_bases_sql = _sql_string_list(_ALL_REFERRAL_BASES)
    valid_decisions_sql = _sql_string_list(VALID_PROCESSING_DECISIONS)
    std_only_join = (
        f"""
    INNER JOIN {nbs_rdb}.DBO.F_INTERVIEW_CASE fic
        ON fic.INVESTIGATION_KEY = cases.INVESTIGATION_KEY"""
        if variant == 'STD'
        else ''
    )
    sentinel_filter = (
        '\n      AND fcrc.CONTACT_INTERVIEW_KEY <> 1' if variant == 'STD' else ''
    )

    return f"""
    WITH shd AS (
        {subset_query}
    ),
    {_BASE_CASES_CTE.format(nbs_rdb=nbs_rdb)}
    SELECT DISTINCT
        contact_std.INV_LOCAL_ID AS CONTACT_INV_LOCAL_ID,
        di.IX_TYPE,
        dcr.CTT_REFERRAL_BASIS,
        contact_std.FL_FUP_DISPOSITION
    FROM cases{std_only_join}
    INNER JOIN {nbs_rdb}.DBO.D_PROVIDER dp
        ON dp.PROVIDER_KEY = cases.INVESTIGATOR_INTERVIEW_KEY
    INNER JOIN {nbs_rdb}.DBO.F_CONTACT_RECORD_CASE fcrc
        ON fcrc.SUBJECT_INVESTIGATION_KEY = cases.INVESTIGATION_KEY
    INNER JOIN {nbs_rdb}.DBO.D_INTERVIEW di
        ON di.D_INTERVIEW_KEY = fcrc.CONTACT_INTERVIEW_KEY
       AND di.RECORD_STATUS_CD <> 'LOG_DEL'
    INNER JOIN {nbs_rdb}.DBO.STD_HIV_DATAMART contact_std
        ON contact_std.INVESTIGATION_KEY = fcrc.CONTACT_INVESTIGATION_KEY
    INNER JOIN {nbs_rdb}.DBO.D_CONTACT_RECORD dcr
        ON dcr.D_CONTACT_RECORD_KEY = fcrc.D_CONTACT_RECORD_KEY
    WHERE dcr.CTT_REFERRAL_BASIS IN ({referral_bases_sql})
      AND dcr.CTT_PROCESSING_DECISION IN ({valid_decisions_sql}){sentinel_filter};
    """


def index_query(subset_query: str) -> str:
    """Equivalent to the pix / testindex / pix1 / testindex1 dataset builds
    (PA04_HIV.sas:191-287), the numerator for the Notification/Testing Index.

    Unlike contact_query, this does not require a matching D_PROVIDER row or a
    valid CTT_PROCESSING_DECISION, uses a LEFT (not INNER) join to D_INTERVIEW,
    and *does* filter D_CONTACT_RECORD on RECORD_STATUS_CD -- all matching the
    source's differing join structure for pix/testindex vs. PP04_OI/CLUSTER.

    KNOWN SAS QUIRK: 'testindex' adds `AND a.CA_INTERVIEWER_ASSIGN_DT IS NOT
    NULL` on top of 'pix', but that's already guaranteed true by the base case
    filter -- so this single query serves as the numerator for both the
    Notification Index and the (always-identical) Testing Index.
    """
    nbs_rdb = get_cached_config_value('REPORT_DB_NBS_RDB')
    referral_bases_sql = _sql_string_list(_ALL_REFERRAL_BASES)
    index_dispositions_sql = _sql_string_list(INDEX_DISPOSITIONS)

    return f"""
    WITH shd AS (
        {subset_query}
    ),
    {_BASE_CASES_CTE.format(nbs_rdb=nbs_rdb)}
    SELECT DISTINCT
        cases.INV_LOCAL_ID AS CASE_INV_LOCAL_ID,
        di.IX_TYPE,
        dcr.CTT_REFERRAL_BASIS,
        contact_std.FL_FUP_DISPOSITION
    FROM cases
    INNER JOIN {nbs_rdb}.DBO.F_CONTACT_RECORD_CASE fcrc
        ON fcrc.SUBJECT_INVESTIGATION_KEY = cases.INVESTIGATION_KEY
    INNER JOIN {nbs_rdb}.DBO.STD_HIV_DATAMART contact_std
        ON contact_std.INVESTIGATION_KEY = fcrc.CONTACT_INVESTIGATION_KEY
    INNER JOIN {nbs_rdb}.DBO.D_CONTACT_RECORD dcr
        ON dcr.D_CONTACT_RECORD_KEY = fcrc.D_CONTACT_RECORD_KEY
       AND dcr.RECORD_STATUS_CD <> 'LOG_DEL'
    LEFT JOIN {nbs_rdb}.DBO.D_INTERVIEW di
        ON di.D_INTERVIEW_KEY = fcrc.CONTACT_INTERVIEW_KEY
       AND di.RECORD_STATUS_CD <> 'LOG_DEL'
    WHERE dcr.CTT_REFERRAL_BASIS IN ({referral_bases_sql})
      AND contact_std.FL_FUP_DISPOSITION IN ({index_dispositions_sql});
    """


def std_index_query(subset_query: str) -> str:
    """Equivalent to the Val_TWO/Val_three/Val_four/Val_five dataset builds
    (PA04_Std.sas:335-366, 1110-1142), the numerator for the STD variant's DI
    Index.

    Unlike HIV's index_query, this *does* require a matching D_PROVIDER row
    and a valid CTT_PROCESSING_DECISION (an INNER JOIN each, same as
    contact_query), and uses an INNER (not LEFT) join to D_INTERVIEW --
    matching PA04_Std.sas's differing join structure for this dataset vs. its
    HIV counterpart. Selects the *case's* own local id (cases.INV_LOCAL_ID),
    not the contact's, since the DI Index counts cases with at least one
    matching treated contact, not the contacts themselves.
    """
    nbs_rdb = get_cached_config_value('REPORT_DB_NBS_RDB')
    referral_bases_sql = _sql_string_list(_ALL_REFERRAL_BASES)
    valid_decisions_sql = _sql_string_list(VALID_PROCESSING_DECISIONS)
    treatment_dispositions_sql = _sql_string_list(STD_TREATMENT_DISPOSITIONS)

    return f"""
    WITH shd AS (
        {subset_query}
    ),
    {_BASE_CASES_CTE.format(nbs_rdb=nbs_rdb)}
    SELECT DISTINCT
        cases.INV_LOCAL_ID AS CASE_INV_LOCAL_ID,
        di.IX_TYPE,
        dcr.CTT_REFERRAL_BASIS,
        contact_std.FL_FUP_DISPOSITION
    FROM cases
    INNER JOIN {nbs_rdb}.DBO.D_PROVIDER dp
        ON dp.PROVIDER_KEY = cases.INVESTIGATOR_INTERVIEW_KEY
    INNER JOIN {nbs_rdb}.DBO.F_CONTACT_RECORD_CASE fcrc
        ON fcrc.SUBJECT_INVESTIGATION_KEY = cases.INVESTIGATION_KEY
    INNER JOIN {nbs_rdb}.DBO.STD_HIV_DATAMART contact_std
        ON contact_std.INVESTIGATION_KEY = fcrc.CONTACT_INVESTIGATION_KEY
    INNER JOIN {nbs_rdb}.DBO.D_CONTACT_RECORD dcr
        ON dcr.D_CONTACT_RECORD_KEY = fcrc.D_CONTACT_RECORD_KEY
       AND dcr.RECORD_STATUS_CD <> 'LOG_DEL'
    INNER JOIN {nbs_rdb}.DBO.D_INTERVIEW di
        ON di.D_INTERVIEW_KEY = fcrc.CONTACT_INTERVIEW_KEY
       AND di.RECORD_STATUS_CD <> 'LOG_DEL'
    WHERE dcr.CTT_REFERRAL_BASIS IN ({referral_bases_sql})
      AND dcr.CTT_PROCESSING_DECISION IN ({valid_decisions_sql})
      AND contact_std.FL_FUP_DISPOSITION IN ({treatment_dispositions_sql});
    """
