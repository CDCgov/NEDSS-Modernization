from src.config import get_cached_config_value
from src.db_transaction import Transaction
from src.models import ReportResult

QUESTION_IDENTIFIER = {
    'INV1133': 'INV1133',
    'INV1115': 'INV1115',
    'INV111': 'INV111',
}


def execute(
    trx: Transaction,
    subset_query: str,
    **kwargs,
):
    """TB Case Verification Report - 2020 RVCT.

    Conversion notes:
    * In the exported CSV from Case_Verification_Report.SAS there is a column named
      "_TEMA001".  This is a SAS generated column name because no alias was explicitly
      declared after the "TRIM" method was used on column "DISEASE_SITE_IND".  In the
      Python generated CSV this column is named "DISEASE_SITE_IND".
    * Case_Verification_Report.SAS has a number of calculations that are specific for
      the "Report" ("Run" in the UI) code path.  Since we are only recreating the
      "Export" code path the Python implementation focuses soley on calculations for
      "Export" and does not recreate the "Report" calculations.
    * In data parity tests the sorting of the result data is  different when comparing
      the Python and SAS output.  Data parity tests show that all of the data is equal
      between the two.
    """
    # Pull column names for use in the main query
    metadata = trx.query(_metadata_query())
    disease_site_desc_colname: str | None = None
    case_verification_desc_colname: str | None = None
    inv_rpt_dt_colname: str | None = None

    for row in metadata.data:
        if row[0] == QUESTION_IDENTIFIER['INV1133']:
            disease_site_desc_colname = row[3]
        elif row[0] == QUESTION_IDENTIFIER['INV1115']:
            case_verification_desc_colname = row[3]
        elif row[0] == QUESTION_IDENTIFIER['INV111']:
            inv_rpt_dt_colname = row[3]

    if None in [
        disease_site_desc_colname,
        case_verification_desc_colname,
        inv_rpt_dt_colname,
    ]:
        raise ValueError(
            (
                'Column name metadata missing from initial query. Values found: ',
                f'disease_site_desc_colname "{disease_site_desc_colname}", ',
                f'case_verification_desc_colname "{case_verification_desc_colname}", ',
                f'inv_rpt_dt_colname: "{inv_rpt_dt_colname}"',
            )
        )

    # Returns the equivalent of TB_CASE_VER3 in SAS
    cases = trx.query(
        _tb_case_ver_query(
            subset_query,
            disease_site_desc_colname,
            case_verification_desc_colname,
            inv_rpt_dt_colname,
        )
    )

    return ReportResult(content_type='table', content=cases)


def _metadata_query() -> str:
    """Query which fetches the proper SQL column names from metadata tables for use in
    later queries. Column names are searched for by the given QUESTION_IDENTIFIER
    values.
    """
    nbs_ods = get_cached_config_value('REPORT_DB_NBS_ODS')
    nbs_srt = get_cached_config_value('REPORT_DB_NBS_SRT')

    return f"""
      SELECT QUESTION_IDENTIFIER,
             RDB_COLUMN_NM,
             RDB_TABLE_NM,
             USER_DEFINED_COLUMN_NM,
             DATAMART_NM
      FROM {nbs_ods}.dbo.NBS_UI_METADATA num
        INNER JOIN {nbs_ods}.dbo.NBS_RDB_METADATA nrm
                ON num.NBS_UI_METADATA_UID = nrm.NBS_UI_METADATA_UID
        INNER JOIN {nbs_srt}.dbo.CONDITION_CODE cc
                ON cc.INVESTIGATION_FORM_CD = num.INVESTIGATION_FORM_CD
        INNER JOIN {nbs_ods}.dbo.NBS_PAGE np
                ON np.FORM_CD = cc.INVESTIGATION_FORM_CD
      WHERE QUESTION_IDENTIFIER IN (
              '{QUESTION_IDENTIFIER['INV1115']}',
              '{QUESTION_IDENTIFIER['INV1133']}',
              '{QUESTION_IDENTIFIER['INV111']}')
      AND   CONDITION_CD IN ('102201');
    """


def _tb_case_ver_query(
    subset_query: str,
    disease_site_desc_colname: str | None,
    case_verification_desc_colname: str | None,
    inv_rpt_dt_colname: str | None,
) -> str:
    """Query that Builds the equivalent of the TB_CASE_VER sequence of tables from the
    SAS file up through TB_CASE_VER3, which it returns.
    """
    # This is checked earlier in the code path but I have to put this here since I have
    # to label the "*_colname" values as potentitally None to make the type checker
    # happy.
    if None in [
        disease_site_desc_colname,
        case_verification_desc_colname,
        inv_rpt_dt_colname,
    ]:
        raise ValueError('One or more of the passed in column names is missing.')

    nbs_ods = get_cached_config_value('REPORT_DB_NBS_ODS')
    nbs_srt = get_cached_config_value('REPORT_DB_NBS_SRT')

    return f"""
      WITH DM_INV_TB AS
      (
        {subset_query}
      ),
      -- case verification codes, filtered by QUESTION_IDENTIFIER and CONDITION_CD
      CASE_VERIFIC_CODED AS
      (
        SELECT TRIM(cvg.CODE) AS CASE_VERIFICATION_CODE,
               TRIM(cvg.CODE_SHORT_DESC_TXT) AS CASE_VERIFICATION_CODE_DESC
        FROM {nbs_ods}.dbo.NBS_UI_METADATA num
          INNER JOIN {nbs_ods}.dbo.NBS_RDB_METADATA nrm
                  ON num.NBS_UI_METADATA_UID = nrm.NBS_UI_METADATA_UID
          INNER JOIN {nbs_srt}.dbo.CONDITION_CODE cc
                  ON cc.INVESTIGATION_FORM_CD = num.INVESTIGATION_FORM_CD
          INNER JOIN {nbs_srt}.dbo.CODESET cs
                  ON cs.CODE_SET_GROUP_ID = num.CODE_SET_GROUP_ID
          INNER JOIN {nbs_srt}.dbo.CODE_VALUE_GENERAL cvg
                  ON cs.CODE_SET_NM = cvg.CODE_SET_NM
        WHERE QUESTION_IDENTIFIER IN ('{QUESTION_IDENTIFIER['INV1115']}')
        AND   CONDITION_CD IN ('102201')
      ),
      -- disease site codes, filtered by QUESTION_IDENTIFIER and CONDITION_CD
      DISEASE_SITE_CODED AS
      (
        SELECT TRIM(cvg.CODE) AS DISEASE_CODE,
               TRIM(cvg.CODE_SHORT_DESC_TXT) AS DISEASE_CODE_DESC
        FROM {nbs_ods}.dbo.NBS_UI_METADATA num
          INNER JOIN {nbs_ods}.dbo.NBS_RDB_METADATA nrm
                  ON num.NBS_UI_METADATA_UID = nrm.NBS_UI_METADATA_UID
          INNER JOIN {nbs_srt}.dbo.CONDITION_CODE cc
                  ON cc.INVESTIGATION_FORM_CD = num.INVESTIGATION_FORM_CD
          INNER JOIN {nbs_srt}.dbo.CODESET cs
                  ON cs.CODE_SET_GROUP_ID = num.CODE_SET_GROUP_ID
          INNER JOIN {nbs_srt}.dbo.CODE_VALUE_GENERAL cvg
                  ON cs.CODE_SET_NM = cvg.CODE_SET_NM
        WHERE QUESTION_IDENTIFIER IN ('{QUESTION_IDENTIFIER['INV1133']}')
        AND   CONDITION_CD IN ('102201')
      ),
      -- filtered DM_INV_TB
      TB_CASE_VER AS (
        SELECT COALESCE(NULLIF({disease_site_desc_colname}, ''), 'Unknown')
                 AS DISEASE_SITE_DESC,
               {case_verification_desc_colname} AS CASE_VERIFICATION_DESC,
               INVESTIGATION_KEY
        FROM DM_INV_TB
        WHERE DISEASE_CD = '102201'
        AND   {inv_rpt_dt_colname} IS NOT NULL
      ),
      -- TB_CASE_VER with DISEASE_SITE_DESC unspooled into DISEASE_SITE_IND
      -- nb. this particularly nasty CTE is called a 'recursive CTE splitter' and it is
      --     used here because STRING_SPLIT is, unfortunately, not supported by the
      --     compatibility level of SQL Server.  It is required to replicate SAS
      --     functionality that splits on a '|' character and creates new rows from
      --     the results.
      TB_CASE_VER_INIT2 AS
      (
        SELECT DISEASE_SITE_DESC,
               CASE_VERIFICATION_DESC,
               INVESTIGATION_KEY,
               CAST(
                 CASE
                   WHEN CHARINDEX('|', DISEASE_SITE_DESC) > 0
                     THEN LEFT(
                            DISEASE_SITE_DESC,
                            CHARINDEX('|', DISEASE_SITE_DESC) - 1
                          )
                   ELSE DISEASE_SITE_DESC
                 END
                 AS VARCHAR(4000)
               ) AS DISEASE_SITE_IND,
               CAST(
                 CASE
                   WHEN CHARINDEX('|', DISEASE_SITE_DESC) > 0
                     THEN SUBSTRING(
                            DISEASE_SITE_DESC,
                            CHARINDEX('|', DISEASE_SITE_DESC) + 1, 4000
                          )
                   ELSE ''
                 END
                 AS VARCHAR(4000)
               ) AS remaining
        FROM TB_CASE_VER

        UNION ALL

        SELECT
            DISEASE_SITE_DESC,
            CASE_VERIFICATION_DESC,
            INVESTIGATION_KEY,
            CAST(
              CASE
                WHEN CHARINDEX('|', remaining) > 0
                  THEN LEFT(remaining, CHARINDEX('|', remaining) - 1)
                ELSE remaining
              END
              AS VARCHAR(4000)
            ) AS DISEASE_SITE_IND,
            CAST(
              CASE
                WHEN CHARINDEX('|', remaining) > 0
                  THEN SUBSTRING(
                         remaining,
                         CHARINDEX('|', remaining) + 1, 4000
                       )
                ELSE ''
              END
              AS VARCHAR(4000)
            ) AS remaining
        FROM TB_CASE_VER_INIT2
        WHERE remaining <> ''
      )
      -- join TB_CASE_VER_INIT2 to the case verification and disease codes, add
      -- DISEASE_SITE_VALUE based on DISEASE_CODE
      SELECT REPLACE(dsc.DISEASE_CODE, ' ', '') AS DISEASE_CODE,
             cvc.CASE_VERIFICATION_CODE,
             tcv.CASE_VERIFICATION_DESC,
             tcv.DISEASE_SITE_DESC,
             TRIM(DISEASE_SITE_IND) AS DISEASE_SITE_IND,
             tcv.INVESTIGATION_KEY,
             CASE
                 WHEN dsc.DISEASE_CODE IS NULL OR dsc.DISEASE_CODE = 'PHC5'
                   THEN 'Unknown'
                 WHEN dsc.DISEASE_CODE IN (
                        '23451007', '362102006', '53505006', '66754008',
                        '87612001', '59820001', '110522009', '14016003',
                        '12738006', '76752008', '17401000', '71854001',
                        '38848004', '110547006', '32849002', '16014003',
                        'PHC4', 'C0230999', '28231008', '80891009',
                        '110611003', '48477009', '10200004',
                        'PHC2', 'PHC3', '1231004', '110708006',
                        '123851003', '45206002', '71836000',
                        'OTH', '15776009', '120228005', '76848001',
                        '83670000', '54066008', '56329008',
                        '110973009', '34402009', '385294005',
                        '39937001', '2748008', '78961009',
                        '69695003', '21514008', '281777001',
                        '69831007', '25087005', '71966008',
                        '9875009', '297261005', '21974007',
                        '303337002', '44567001')
                   THEN 'Extrapulmonary TB'
                 WHEN dsc.DISEASE_CODE IN ('281778006', '39607008', '3120008')
                   THEN 'Pulmonary TB'
                 ELSE NULL
             END AS DISEASE_SITE_VALUE
      FROM TB_CASE_VER_INIT2 tcv
      LEFT JOIN CASE_VERIFIC_CODED cvc
             ON cvc.CASE_VERIFICATION_CODE_DESC = tcv.CASE_VERIFICATION_DESC
      LEFT JOIN DISEASE_SITE_CODED dsc
             ON REPLACE(dsc.DISEASE_CODE_DESC, ' ', '')
                  = REPLACE(tcv.DISEASE_SITE_IND, ' ', '')
      WHERE TRIM(tcv.DISEASE_SITE_IND) <> ''
      ORDER BY DISEASE_CODE,
               CASE_VERIFICATION_CODE,
               CASE_VERIFICATION_DESC,
               DISEASE_SITE_DESC,
               DISEASE_SITE_IND,
               INVESTIGATION_KEY;
    """
