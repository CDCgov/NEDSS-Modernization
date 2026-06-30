from src.config import get_config_value
from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """QA Report 05: Number of Records Entered by User ID.

    Conversion notes:
    * Matched "export format"
    """
    # Dynamically look up the correct DB names
    nbs_rdb = get_config_value(trx, 'REPORT_DB_NBS_RDB')
    nbs_srt = get_config_value(trx, 'REPORT_DB_NBS_SRT')
    content = trx.query(
        f"""
        WITH v_event_metric as ({subset_query}),
        
        PROG_AREA as (
            SELECT DISTINCT PROG_AREA_CD
            FROM {nbs_srt}.dbo.condition_code
            WHERE 
                condition_cd in ('10560', '900')
                OR nnd_entity_identifier = 'STD_Case_Map_v1.0'
        ),

        INV as (
            SELECT DISTINCT 
                count(*) as OOJ_REFF,
                em.ADD_USER_ID
            FROM v_event_metric em
            INNER JOIN {nbs_rdb}.dbo.STD_HIV_DATAMART hiv 
                on em.LOCAL_ID = hiv.INV_LOCAL_ID
            INNER JOIN {nbs_rdb}.dbo.F_STD_PAGE_CASE std 
                on hiv.INVESTIGATION_KEY = std.INVESTIGATION_KEY
            INNER JOIN {nbs_rdb}.dbo.D_INV_ADMINISTRATIVE adm 
                on std.D_INV_ADMINISTRATIVE_KEY = adm.D_INV_ADMINISTRATIVE_KEY
            WHERE 
                em.EVENT_TYPE in ('PHCInvForm')
                AND adm.ADM_REFERRAL_BASIS_OOJ IS NOT NULL
            GROUP BY em.ADD_USER_ID
        ),

        LAB_MORB as (
            SELECT DISTINCT
                COUNT (*) as REACTOR,
                em.ADD_USER_ID
            FROM v_event_metric em
            INNER JOIN PROG_AREA pa on pa.PROG_AREA_CD = em.prog_area_cd
            WHERE
                em.EVENT_TYPE in ('LabReport', 'MorbReport')
                AND em.ELECTRONIC_IND = 'N'
            GROUP BY em.ADD_USER_ID
        ),

        CONTACT as (
            SELECT COUNT (*) as PART_CLUS,
                em.ADD_USER_ID
        FROM v_event_metric em
        INNER JOIN PROG_AREA pa on pa.PROG_AREA_CD = em.prog_area_cd
        WHERE
            em.EVENT_TYPE in ('CONTACT')
        GROUP BY em.ADD_USER_ID
        ),

        RESULT as (
            SELECT
                COALESCE(
                    INV.ADD_USER_ID,
                    LAB_MORB.ADD_USER_ID,
                    CONTACT.ADD_USER_ID
                ) as ADD_USER_ID,
                COALESCE(INV.OOJ_REFF, 0) as OOJ_REFF,
                COALESCE(LAB_MORB.REACTOR, 0) as REACTOR,
                COALESCE(CONTACT.PART_CLUS, 0) as PART_CLUS
            FROM INV
            FULL JOIN LAB_MORB on INV.ADD_USER_ID = LAB_MORB.ADD_USER_ID
            FULL JOIN CONTACT on INV.ADD_USER_ID = CONTACT.ADD_USER_ID 
                    or LAB_MORB.ADD_USER_ID = CONTACT.ADD_USER_ID
        )

        SELECT 
            -- SAS trim leaves a ' ' behind if otherwise empty
            TRIM(CONCAT(
                COALESCE(TRIM(usr.PROVIDER_QUICK_CODE), ' '),
                ' - ',
                COALESCE(TRIM(usr.FIRST_NM), ' '),
                ' ',
                COALESCE(TRIM(usr.LAST_NM), ' ')
            )) as user_qc,
            RESULT.OOJ_REFF,
            usr.FIRST_NM,
            usr.LAST_NM,
            usr.PROVIDER_QUICK_CODE,
            RESULT.ADD_USER_ID,
            RESULT.REACTOR,
            RESULT.PART_CLUS
        FROM RESULT
        LEFT JOIN {nbs_rdb}.dbo.USER_PROFILE usr 
            on usr.NEDSS_ENTRY_ID = RESULT.ADD_USER_ID
        ORDER BY user_qc
        """
    )

    return ReportResult(
        content_type='table',
        content=content,
    )
