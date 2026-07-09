from src.config import get_cached_config_value
from src.db_transaction import Transaction
from src.models import ReportResult

TB_CONDITION_CD = '102201'

# Question identifiers for the TB investigation form fields
_Q_CASE_VERIFICATION = 'INV1115'
_Q_DISEASE_SITE = 'INV1133'
_Q_INV_RPT_DT = 'INV111'


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """TB Case Verification Report.

    Produces per-investigation disease-site-level data matching the CSV export of
    Case_Verification_Report.sas (table TB_CASE_VER3 in the original SAS script).

    Each output row represents one disease site indicator for one TB investigation:
    - Disease Code: SRTE code for the disease site
    - Case Verification Code: SRTE code for the case verification status
    - Case Verification Description: Raw case verification text from the data mart
    - Disease Site Description: Full (pipe-delimited) disease site string from the data mart
    - Disease Site Indicator: Individual disease site label (one row per pipe-delimited value)
    - Investigation Key: Investigation identifier

    Conversion notes:
    * Matches the CSV export path of the SAS script (%export TB_CASE_VER3), not the
      HTML pivot-table report
    * Dynamic mart column names for disease site, case verification, and report date are
      resolved from NBS_UI_METADATA before the main query is built
    * Missing or blank disease site values are coerced to 'Unknown' before pipe splitting,
      matching the SAS DATA step that precedes TB_CASE_VER_INIT2
    * Pipe-delimited disease site values are split via SQL Server's STRING_SPLIT
    * Code lookups (CASE_VERIFIC_CODED, DISIEASE_SITE_CODED) join on description text,
      matching the SAS LEFT JOIN logic in TB_CASE_VER3
    * COMPRESS() in SAS (removes all blanks) is replicated with REPLACE(value, ' ', '')
    * Styling, formatting, proc tabulate, and proc report output are omitted
    """
    nbs_ods = get_cached_config_value('REPORT_DB_NBS_ODS')
    nbs_srt = get_cached_config_value('REPORT_DB_NBS_SRT')

    # Resolve dynamic mart column names from UI metadata for the TB investigation form.
    # The SAS script stores these in macro variables via call symputx on USER_DEFINED_COLUMN_NM.
    meta_result = trx.query(
        f"""
        SELECT m.QUESTION_IDENTIFIER, TRIM(r.USER_DEFINED_COLUMN_NM) AS col_name
        FROM {nbs_ods}.dbo.NBS_UI_METADATA m
        INNER JOIN {nbs_ods}.dbo.NBS_RDB_METADATA r
            ON m.NBS_UI_METADATA_UID = r.NBS_UI_METADATA_UID
        INNER JOIN {nbs_srt}.dbo.CONDITION_CODE cc
            ON cc.INVESTIGATION_FORM_CD = m.INVESTIGATION_FORM_CD
        INNER JOIN {nbs_ods}.dbo.NBS_PAGE p
            ON p.FORM_CD = cc.INVESTIGATION_FORM_CD
        WHERE m.QUESTION_IDENTIFIER IN (
                '{_Q_CASE_VERIFICATION}',
                '{_Q_DISEASE_SITE}',
                '{_Q_INV_RPT_DT}'
              )
          AND cc.CONDITION_CD = '{TB_CONDITION_CD}'
        """
    )

    col_names = {row[0]: row[1] for row in meta_result.data if row[1]}

    missing = [
        q
        for q in (_Q_CASE_VERIFICATION, _Q_DISEASE_SITE, _Q_INV_RPT_DT)
        if not col_names.get(q)
    ]
    if missing:
        raise ValueError(
            f'Could not resolve mart column names for question identifiers: {missing}. '
            f'Ensure NBS_UI_METADATA contains entries for condition {TB_CONDITION_CD}.'
        )

    case_ver_col = col_names[_Q_CASE_VERIFICATION]
    disease_site_col = col_names[_Q_DISEASE_SITE]
    inv_rpt_dt_col = col_names[_Q_INV_RPT_DT]

    content = trx.query(
        f"""
        WITH subset AS ({subset_query}),

        -- CASE_VERIFIC_CODED: code -> description lookup for case verification (INV1115)
        case_ver_codes AS (
            SELECT cvg.code AS case_verification_code,
                   cvg.code_short_desc_txt AS case_verification_code_desc
            FROM {nbs_ods}.dbo.NBS_UI_METADATA m
            INNER JOIN {nbs_ods}.dbo.NBS_RDB_METADATA r
                ON m.NBS_UI_METADATA_UID = r.NBS_UI_METADATA_UID
            INNER JOIN {nbs_srt}.dbo.CONDITION_CODE cc
                ON cc.INVESTIGATION_FORM_CD = m.INVESTIGATION_FORM_CD
            INNER JOIN {nbs_srt}.dbo.CODESET cs
                ON cs.CODE_SET_GROUP_ID = m.CODE_SET_GROUP_ID
            INNER JOIN {nbs_srt}.dbo.CODE_VALUE_GENERAL cvg
                ON cs.CODE_SET_NM = cvg.CODE_SET_NM
            WHERE m.QUESTION_IDENTIFIER = '{_Q_CASE_VERIFICATION}'
              AND cc.CONDITION_CD = '{TB_CONDITION_CD}'
        ),

        -- DISEASE_SITE_CODED: code -> description lookup for disease site (INV1133)
        disease_site_codes AS (
            SELECT REPLACE(cvg.code, ' ', '') AS disease_code,
                   cvg.code_short_desc_txt AS disease_code_desc
            FROM {nbs_ods}.dbo.NBS_UI_METADATA m
            INNER JOIN {nbs_ods}.dbo.NBS_RDB_METADATA r
                ON m.NBS_UI_METADATA_UID = r.NBS_UI_METADATA_UID
            INNER JOIN {nbs_srt}.dbo.CONDITION_CODE cc
                ON cc.INVESTIGATION_FORM_CD = m.INVESTIGATION_FORM_CD
            INNER JOIN {nbs_srt}.dbo.CODESET cs
                ON cs.CODE_SET_GROUP_ID = m.CODE_SET_GROUP_ID
            INNER JOIN {nbs_srt}.dbo.CODE_VALUE_GENERAL cvg
                ON cs.CODE_SET_NM = cvg.CODE_SET_NM
            WHERE m.QUESTION_IDENTIFIER = '{_Q_DISEASE_SITE}'
              AND cc.CONDITION_CD = '{TB_CONDITION_CD}'
        ),

        -- TB_CASE_VER: base TB cases filtered to condition 102201 with a report date,
        -- coercing missing/blank disease site to 'Unknown' (SAS DATA step TB_CASE_VER)
        tb_cases AS (
            SELECT
                COALESCE(
                    NULLIF(TRIM(CAST({disease_site_col} AS VARCHAR(MAX))), ''),
                    'Unknown'
                ) AS disease_site_desc,
                {case_ver_col} AS case_verification_desc,
                INVESTIGATION_KEY
            FROM subset
            WHERE DISEASE_CD = '{TB_CONDITION_CD}'
              AND {inv_rpt_dt_col} IS NOT NULL
        ),

        -- TB_CASE_VER_INIT2: expand pipe-delimited disease site into one row per value
        tb_split AS (
            SELECT
                disease_site_desc,
                case_verification_desc,
                TRIM(s.value) AS disease_site_ind,
                INVESTIGATION_KEY
            FROM tb_cases
            CROSS APPLY STRING_SPLIT(disease_site_desc, '|') s
            WHERE TRIM(s.value) != ''
        )

        -- TB_CASE_VER3: join code lookups onto the expanded rows
        SELECT
            REPLACE(d.disease_code, ' ', '') AS [Disease Code],
            TRIM(c.case_verification_code) AS [Case Verification Code],
            t.case_verification_desc AS [Case Verification Description],
            t.disease_site_desc AS [Disease Site Description],
            TRIM(t.disease_site_ind) AS [Disease Site Indicator],
            t.INVESTIGATION_KEY AS [Investigation Key]
        FROM tb_split t
        LEFT JOIN case_ver_codes c
            ON c.case_verification_code_desc = t.case_verification_desc
        LEFT JOIN disease_site_codes d
            ON REPLACE(d.disease_code_desc, ' ', '') = REPLACE(t.disease_site_ind, ' ', '')
        ORDER BY t.INVESTIGATION_KEY
        """
    )

    description = """
**<u>Report content</u>**

**Output:** Report provides investigation-level TB case verification and disease site data for all TB cases (condition code 102201). Each row represents one disease site indicator for one investigation. Output:

* Does not include Investigations that have been logically deleted

* Is filtered to TB cases (condition code 102201) with a non-null investigation report date

* Pipe-delimited disease site values are expanded to one row per disease site per investigation

* Disease site and case verification codes are resolved from the NBS SRTE codeset via description-text matching

* Is ordered by Investigation Key
"""  # noqa: E501

    return ReportResult(
        content_type='table',
        content=content,
        description=description,
    )
